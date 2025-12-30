package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.User;
import com.example.demo.constant.ErrorConstant;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.UserServiceException;
import com.example.demo.repository.BedRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BedRepository bedRepository;
	
	@Autowired
    private JavaMailSender javaMailSender;

	@Override
	public void saveUser(UserDto userDto) {

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

		User user2 = userRepository.save(user);
		if (user2 == null) {
			throw new UserServiceException(ErrorConstant.USER_SAVE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Generate the opt
		String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        user.setOtp(otp);
        user.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5)); // OTP valid 5 mins
        userRepository.save(user);
        
        // send the email
        sendOtpEmail(user.getEmail(), otp);
        
	}

	@Override
	public String verifyOtp(String email, String otp) {

	    User user = userRepository.findByEmail(email);

	    if (user == null) {
	        throw new UserServiceException(ErrorConstant.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
	    }

	    if (user.getOtp() == null) {
	        throw new UserServiceException(ErrorConstant.OTP_NOT_GENERATED, HttpStatus.BAD_REQUEST);
	    }

	    if (user.getOtpExpiryTime().isBefore(LocalDateTime.now())) {
	        throw new UserServiceException(ErrorConstant.OTP_EXPIRED, HttpStatus.BAD_REQUEST);
	    }

	    if (!otp.equals(user.getOtp())) {
	        throw new UserServiceException(ErrorConstant.INVALID_OTP, HttpStatus.BAD_REQUEST);
	    }

	    user.setEmailVerified(true);
	    user.setOtp(null);
	    user.setOtpExpiryTime(null);
	    userRepository.save(user);

	    return "Email verified successfully";
	}

	
	@Override
	public UserDto getUserById(int id) {

		User user = userRepository.findById(id).get();
		if (user == null) {
			throw new UserServiceException(ErrorConstant.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

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

		return dto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			throw new UserServiceException(ErrorConstant.USER_LIST_EMPTY, HttpStatus.BAD_REQUEST);
		}

		List<UserDto> dtoList = new ArrayList<>();

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

		return dtoList;
	}

	@Override
	public void deleteUser(int id) {
		User user = userRepository.findById(id).get();
		if (user == null) {
			throw new UserServiceException(ErrorConstant.USER_DELETE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		userRepository.delete(user);

	}

	@Override
	public void deleteAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			throw new UserServiceException(ErrorConstant.USER_DELETE_ALL_EXCEPTION, HttpStatus.BAD_REQUEST);
		}

		userRepository.deleteAll(users);

	}
	
	
	private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Verification Code");
        message.setText("Your OTP is: " + otp);
        javaMailSender.send(message);
    }
	

	
}
