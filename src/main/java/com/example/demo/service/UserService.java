package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.UserDto;

public interface UserService {
	
	public void saveUser(UserDto  userDto);

	public List<UserDto> getAllUsers();

	UserDto getUserById(int id);

	void deleteUser(int id);

	void deleteAllUsers();
}
