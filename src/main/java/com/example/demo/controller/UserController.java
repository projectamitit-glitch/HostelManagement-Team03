package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constant.Constant;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/User")
public class UserController {
 
	@Autowired
	UserService userService;

	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody UserDto userDto) {
		userService.saveUser(userDto);
		return new ResponseEntity<>(Constant.USER_SAVED, HttpStatus.CREATED);

	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
		UserDto user = userService.getUserById(id);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> users = userService.getAllUsers();
		return new ResponseEntity<>(users,HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable int id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(Constant.USER_DELETE,HttpStatus.OK);
	}

	@DeleteMapping("/deleteAll")
	public ResponseEntity<String> deleteAllUsers() {
		userService.deleteAllUsers();
		return new ResponseEntity<>(Constant.ALL_USER_DELETE,HttpStatus.OK);
	}
}
