package com.parkinglot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parkinglot.entities.User;
import com.parkinglot.exceptions.DuplicateUserException;
import com.parkinglot.exceptions.UserNotFoundException;
import com.parkinglot.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Transactional(readOnly=true)
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@Transactional(readOnly=true)
	public User getUserById(Long userId) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return user;
		}
		
		throw new UserNotFoundException(userId);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public User registerUser(User user) throws Exception {
		if(user.getName()==null || user.getName().isEmpty() || user.getPhoneNumber()==null || user.getPhoneNumber().isEmpty())
			throw new Exception("Username or phoneNumber can not be empty ");
		
		User existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
		
		if (existingUser==null) {
			return userRepository.save(user);
		}
		
		throw new DuplicateUserException(user);
	}
	
}
