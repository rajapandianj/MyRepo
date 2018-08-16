package com.rp.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rp.user.modal.User;
import com.rp.user.repository.UserRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/userAPI")
public class UserController {

	@Autowired
	UserRepository repo;
	
	@ApiOperation(
			value = "Api to get all Users!",
			notes = "Returns Users(List) as response",
			response = User.class,
			responseContainer = "List",
			httpMethod = "GET",
			nickname = "Get All Users",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping("/getUsers")
	public List<User> getAllUsers() {
		return repo.findAll();
	}
	
	@ApiOperation(
			value = "Api to get a User by Id!",
			notes = "Return a User as response",
			response = User.class,
			httpMethod = "GET",
			nickname = "Get User By userId",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping("/getUser/{id}")
	public User getUserById(@PathVariable(value = "id") Long userId) {
		return repo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
	}	
	
	@ApiOperation(
			value = "Api to update a User by Id!",
			notes = "Return updated User as response",
			response = User.class,
			httpMethod = "PUT",
			nickname = "Get Updated User By userId",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PutMapping("/updateUser/{id}")
	public User updateUserById(@PathVariable(value = "id") Long userId, @Valid @RequestBody User user) {
	
		User updateUser = repo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		updateUser.setAge(user.getAge());
		updateUser.setEmail(user.getEmail());
		updateUser.setFirstName(user.getFirstName());
		updateUser.setLastName(user.getLastName());
		updateUser.setSex(user.getSex());
		
		return repo.save(updateUser);
	}		
	
	@ApiOperation(
			value = "Api to delete a User by Id!",
			notes = "Delte a User for the given UserId",
			response = User.class,
			httpMethod = "DELETE",
			nickname = "Delete User By userId",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable(value = "id") Long userId) {
		
		User user = repo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		repo.delete(user);
		
		return ResponseEntity.ok().build();
	}	
	
	@ApiOperation( 
			value = "Api to add a User!",
			notes = "Returns added User as response",
			response = User.class,
			httpMethod = "POST",
			nickname = "Add User",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)	
	@PostMapping("/addUser")
	public User addUser(@Valid @RequestBody User user) {
		return repo.save(user);
	}
}
