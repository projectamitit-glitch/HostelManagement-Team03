package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.User;
import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.UserServiceException;
import com.example.demo.repository.BedRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	BedRepository bedRepository;

	@Override
	public void saveUser(UserDto userDto) {

	    logger.info("Request received to save new user");
	    logger.debug("Incoming UserDto: {}", userDto);

	    User user = new User();
	    user.setUserName(userDto.getUserName());
	    user.setName(userDto.getName());
	    user.setEmail(userDto.getEmail());
	    user.setContactNo(userDto.getContactNo());
	    user.setPassword(userDto.getPassword());
	    user.setIdProofNumber(userDto.getIdProofNumber());
	    user.setGender(userDto.getGender());
	    user.setDateOfBirth(userDto.getDateOfBirth());
	    user.setCurrentAddress(userDto.getCurrentAddress());
	    user.setPermanentAddress(userDto.getPermanentAddress());
	    user.setProfession(userDto.getProfession());
	    user.setGuardianName(userDto.getGuardianName());
	    user.setGuardianContact(userDto.getGuardianContact());

	    logger.debug("User entity prepared for saving: {}", user);

	    User user2 = userRepository.save(user);

	    if (user2 == null) {
	        logger.error("Failed to save user. Repository returned null.");
	        throw new UserServiceException(ErrorConstant.USER_SAVE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    logger.info("User saved successfully with ID: {}", user2.getId());
	}


	@Override
	public UserDto getUserById(int id) {

	    logger.info("Request received to fetch user by ID: {}", id);

	    Optional<User> o = userRepository.findById(id);
	    logger.debug("User lookup result for ID {}: {}", id, o);

	    if (!o.isPresent()) {
	        logger.error("User not found for ID: {}", id);
	        throw new UserServiceException(ErrorConstant.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
	    }

	    User user = o.get();
	    logger.debug("User retrieved from DB: {}", user);

	    UserDto dto = new UserDto();
	    dto.setUserName(user.getUserName());
	    dto.setName(user.getName());
	    dto.setEmail(user.getEmail());
	    dto.setContactNo(user.getContactNo());
	    dto.setPassword(user.getPassword());
	    dto.setIdProofNumber(user.getIdProofNumber());
	    dto.setGender(user.getGender());
	    dto.setDateOfBirth(user.getDateOfBirth());
	    dto.setCurrentAddress(user.getCurrentAddress());
	    dto.setPermanentAddress(user.getPermanentAddress());
	    dto.setProfession(user.getProfession());
	    dto.setGuardianName(user.getGuardianName());
	    dto.setGuardianContact(user.getGuardianContact());

	    logger.info("User DTO prepared successfully for ID: {}", id);
	    return dto;
	}


	@Override
	public List<UserDto> getAllUsers() {

	    logger.info("Request received to fetch all users");

	    List<User> users = userRepository.findAll();
	    logger.debug("Users fetched from DB: {}", users);

	    if (users.isEmpty()) {
	        logger.error("User list is empty — no users found");
	        throw new UserServiceException(ErrorConstant.USER_LIST_EMPTY, HttpStatus.BAD_REQUEST);
	    }

	    List<UserDto> dtoList = new ArrayList<>();
	    logger.debug("Converting User entities to UserDto list");

	    for (User user : users) {
	        UserDto dto = new UserDto();
	        dto.setName(user.getName());
	        dto.setEmail(user.getEmail());
	        dto.setUserName(user.getUserName());
	        dto.setContactNo(user.getContactNo());
	        dto.setGender(user.getGender());
	        dto.setIdProofNumber(user.getIdProofNumber());
	        dto.setDateOfBirth(user.getDateOfBirth());
	        dto.setCurrentAddress(user.getCurrentAddress());
	        dto.setPermanentAddress(user.getPermanentAddress());
	        dto.setPassword(user.getPassword());
	        dto.setProfession(user.getProfession());
	        dto.setGuardianName(user.getGuardianName());
	        dto.setGuardianContact(user.getGuardianContact());

	        dtoList.add(dto);
	    }

	    logger.info("Successfully fetched {} users", dtoList.size());
	    return dtoList;
	}


	@Override
	public void deleteUser(int id) {

	    logger.info("Request received to delete user with ID: {}", id);

	    Optional<User> o = userRepository.findById(id);
	    logger.debug("User lookup result for ID {}: {}", id, o);

	    if (!o.isPresent()) {
	        logger.error("User not found or cannot be deleted for ID: {}", id);
	        throw new UserServiceException(ErrorConstant.USER_DELETE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    User user = o.get();
	    logger.debug("User retrieved for deletion: {}", user);

	    userRepository.delete(user);
	    logger.info("User deleted successfully for ID: {}", id);
	}


	@Override
	public void deleteAllUsers() {

	    logger.info("Request received to delete all users");

	    List<User> users = userRepository.findAll();
	    logger.debug("Users fetched for deletion: {}", users);

	    if (users.isEmpty()) {
	        logger.error("No users found to delete");
	        throw new UserServiceException(ErrorConstant.USER_DELETE_ALL_EXCEPTION, HttpStatus.BAD_REQUEST);
	    }

	    userRepository.deleteAll(users);
	    logger.info("All users deleted successfully. Total users removed: {}", users.size());
	}

	}

