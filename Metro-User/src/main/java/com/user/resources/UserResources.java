package com.user.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.User;
import com.user.service.UserService;

@RestController
public class UserResources {

	@Autowired
	UserService userService;
	
	//works
	@GetMapping(path = "users/{id}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	public User userLoginResource(@PathVariable("id") String userName, @PathVariable("password") String password) {
		return userService.userLogin(userName, password);
	}
	
	//works with insert native query in dao- not the save method in service-
	//kind of makes sense because new user is not null as we are making and passing it
	//through the rest API right?
	@RequestMapping(path = "/users", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String registerUserResource(@RequestBody User user) {
		if(userService.addnewUser(user))
			return "user registered";
		return "user not registered";
	}	
	
}
