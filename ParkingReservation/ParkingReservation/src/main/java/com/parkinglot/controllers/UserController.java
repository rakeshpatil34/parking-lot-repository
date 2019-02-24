package com.parkinglot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.entities.User;
import com.parkinglot.exceptions.UserNotFoundException;
import com.parkinglot.services.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	public User getUserById(@PathVariable Long userId) throws UserNotFoundException {
		return userService.getUserById(userId);
	}
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public List<User> getAllUsers() throws UserNotFoundException {
		return userService.getAllUsers();
	}
	
	@RequestMapping(value="",method=RequestMethod.POST)
	public User registerUser(@RequestBody User user) throws Exception{
		return userService.registerUser(user);
	}
	
}
