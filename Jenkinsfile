pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven_3.9.6'
    }

    // Appears only in manual builds
    parameters {
        choice(name: 'ENVIRONMENT', choices: ['none', 'dev', 'prod'], description: 'Choose environment for deployment (none = build only)')
    }

    environment {
        PROJECT = "team3"
        APP_PORT = "8085"
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo "📦 Checking out branch: ${env.BRANCH_NAME}"
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: "*/${env.BRANCH_NAME}"]],
                    userRemoteConfigs: [[url: 'https://github.com/projectamitit-glitch/HostelManagement-Team03']]
                ])
            }
        }

        stage('Initialize Environment Variables') {
            steps {
                script {
                    // Make branch name safe for Docker
                    env.SAFE_BRANCH = env.BRANCH_NAME.replaceAll('/', '-')
                    env.IMAGE_NAME = "${PROJECT}-${env.SAFE_BRANCH}-springboot-app"

                    if (params.ENVIRONMENT == 'prod') {
                        env.CONTAINER_NAME = "${PROJECT}-${env.SAFE_BRANCH}-springboot-prod"
                        env.HOST_PORT = "8089"
                        env.DB_HOST = "team_3_prod_postgres"
                        env.DB_NAME = "team_3_prod_db"
                        CRED_ID = "team3_prod_credentials"
                    } else if (params.ENVIRONMENT == 'dev') {
                        env.CONTAINER_NAME = "${PROJECT}-${env.SAFE_BRANCH}-springboot-dev"
                        env.HOST_PORT = "8090"
                        env.DB_HOST = "team_3_dev_postgres"
                        env.DB_NAME = "team_3_db"
                        CRED_ID = "team3_dev_credentials"
                    } else {
                        // Build-only mode (no deployment)
                        env.CONTAINER_NAME = "${PROJECT}-${env.SAFE_BRANCH}-build"
                        env.HOST_PORT = "none"
                        env.DB_HOST = "team_3_dev_postgres"
                        env.DB_NAME = "team_3_db"
                        CRED_ID = "team3_dev_credentials"
                    }

                    env.DB_URL = "jdbc:postgresql://${env.DB_HOST}:5432/${env.DB_NAME}"

                    echo """
                    🌿 Branch: ${env.BRANCH_NAME}
                    🌍 Environment: ${params.ENVIRONMENT}
                    📦 Image: ${env.IMAGE_NAME}
                    🧱 Container: ${env.CONTAINER_NAME}
                    🗄 DB_URL: ${env.DB_URL}
                    """
                }
            }
        }

        stage('Build JAR') {
            steps {
                echo "⚙️ Building Spring Boot JAR..."
                sh 'mvn clean package -DskipTests'
                echo "✅ JAR built successfully"
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "🐳 Building Docker image..."
                    def tag = (params.ENVIRONMENT == 'none') ? 'build' : params.ENVIRONMENT
                    sh "docker build -t ${IMAGE_NAME}:${tag} ."
                    echo "✅ Docker image built successfully"
                }
            }
        }


        stage('Archive Artifacts') {
            steps {
                echo "🗂 Archiving JAR..."
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Stop Previous Container') {
            when { expression { return params.ENVIRONMENT != 'none' } }
            steps {
                echo "🛑 Stopping previous container if exists..."
                sh """
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true
                """
            }
        }

        stage('Run New Container') {
            when { expression { return params.ENVIRONMENT != 'none' } }
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: CRED_ID,
                                                      usernameVariable: 'DB_USER',
                                                      passwordVariable: 'DB_PASS')]) {

                        echo "🚀 Deploying ${env.BRANCH_NAME} branch to ${params.ENVIRONMENT} environment..."

                        sh """
                            if docker ps --format '{{.Ports}}' | grep -q ':${HOST_PORT}->'; then
                              echo "⚠️ Port ${HOST_PORT} in use. Stopping container..."
                              docker ps --format '{{.ID}} {{.Ports}}' | grep ':${HOST_PORT}->' | awk '{print \$1}' | xargs -r docker stop
                            fi

                            docker run -d \
                              --name ${CONTAINER_NAME} \
                              --network jenkins-net \
                              -p ${HOST_PORT}:${APP_PORT} \
                              -v /logs/log_team3/dev:/logs/log_team3/dev \
                              -v /logs/log_team3/prod:/logs/log_team3/prod \
                              -e SPRING_PROFILES_ACTIVE=${params.ENVIRONMENT} \
                              -e SPRING_DATASOURCE_URL=${DB_URL} \
                              -e SPRING_DATASOURCE_USERNAME=$DB_USER \
                              -e SPRING_DATASOURCE_PASSWORD=$DB_PASS \
                              -e SERVER_PORT=${APP_PORT} \
                              ${IMAGE_NAME}:${params.ENVIRONMENT}
                        """
                    }
                }
            }
        }

        stage('Verify Deployment') {
            when { expression { return params.ENVIRONMENT != 'none' } }
            steps {
                echo "🕒 Waiting for container startup..."
                sh 'sleep 10'

                echo "🔍 Checking if container is running..."
                sh """
                    if docker ps --format '{{.Names}} {{.Status}}' | grep -q '${CONTAINER_NAME}.*Up'; then
                        echo "✅ Container '${CONTAINER_NAME}' is running successfully."
                    else
                        echo "❌ Container '${CONTAINER_NAME}' is not running!"
                        echo "📋 Showing recent logs for debugging:"
                        docker logs ${CONTAINER_NAME} --tail 100 || true
                        exit 1
                    fi
                """
            }
        }


        stage('Summary') {
            steps {
                script {
                    if (params.ENVIRONMENT == 'none') {
                        echo """
                        ✅ Build-only mode completed for branch '${env.BRANCH_NAME}'
                        🔹 Docker image: ${IMAGE_NAME}:build
                        🔹 No deployment performed automatically.
                        """
                    } else {
                        echo """
                        🎉 Successfully deployed '${env.BRANCH_NAME}' to ${params.ENVIRONMENT}
                        🌍 URL: http://168.220.248.40:${HOST_PORT}
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Jenkins pipeline completed successfully for branch ${env.BRANCH_NAME} (${params.ENVIRONMENT})"
        }
        failure {
            echo "❌ Pipeline failed for branch ${env.BRANCH_NAME} (${params.ENVIRONMENT})"
            sh 'docker logs ${CONTAINER_NAME} || true'
        }
        always {
            echo "📦 Pipeline finished execution."
        }
    }
}
