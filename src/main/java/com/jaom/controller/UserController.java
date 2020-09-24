package com.jaom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaom.exception.ResourceNotFoundException;
import com.jaom.model.User;
import com.jaom.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	//GetAllUsers
	@GetMapping(path = "/allusers")
	public List<User> getAllUsers() {
		return this.userRepository.findAll();		
	}
	//GetUserById
	@GetMapping("/{id}")
	public User getUserById(@PathVariable(value = "id") long userId) {
		return this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not fund with id:" + userId));
	}
	
	//Create user
	@PostMapping
	public User createUser(@RequestBody User user) {
		return this.userRepository.save(user);
	}
	
	//Update user by id
	@PutMapping
	public User updateUser(@RequestBody User user, @PathVariable("id") long userId) {
		User existingUser = this.userRepository.findById(userId)
		.orElseThrow(() -> new ResourceNotFoundException("User not fund with id:" + userId));
		
		existingUser.setUsername(user.getUsername());
		existingUser.setPassword(user.getPassword());
		existingUser.setEmail(user.getEmail());
		
		return this.userRepository.save(existingUser);
	}
	
	//Delete user
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") long userId){
		User existingUser = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not fund with id:" + userId));
		this.userRepository.delete(existingUser);
		return ResponseEntity.ok().build();
	}

}
