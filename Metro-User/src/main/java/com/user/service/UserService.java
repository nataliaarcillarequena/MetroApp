package com.user.service;

import com.user.entity.User;

public interface UserService {

	public User registerUser(User newUser);
	
	public User userLogin(String userName, String password);
	
	public boolean addnewUser(User user);
	
}
