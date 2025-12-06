package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	UserRepository userRepository;

	@Autowired
	BedRepository bedRepository;

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
}
