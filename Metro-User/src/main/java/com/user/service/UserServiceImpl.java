package com.user.service;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.User;
import com.user.persistence.UserDao;

import lombok.Setter;

@Service
public class UserServiceImpl implements UserService {
	
	@Setter
	@Autowired
	UserDao userDao;
	
	//registering a new user; returns the user if it has been successfully registered
	@Override
	public User registerUser(User newUser) {
		
		//if the userName exists in the DB, return null- so use the findBy method
		if(userDao.findById(newUser.getUserName())!=null) //just needed the or else
			//null here because it was coming up empty as the condition was not satisfied
			//but not NULL 
			return null;
		else 
			userDao.save(newUser);
		
		return newUser;
	}

	//logs in the user- returns null if user not found in DB
	@Override
	public User userLogin(String userName, String password) {
		
		if(userDao.findByUserNameAndPassword(userName, password)!=null)
			return userDao.findByUserNameAndPassword(userName, password);
		
		return null;
	}

	//using the insert native query??- try it - want to get the POST request working
	@Override
	public boolean addnewUser(User user) {
		
		//this findById does not want to work for mockito test 
//		if(userDao.findById(user.getUserName()).orElse(null)!=null)
//			return false;
		
		//this works for post request in REST API as well as the findById.orElse above
		if(userDao.searchUserByUserName(user.getUserName())!=null)
			return false;
		
		try {
			userDao.insertUser(user.getFirstName(), user.getSurname(), 
					user.getUserName(), user.getPassword(), user.getBalance());
			
			return true;
			
		}catch(Exception ex) {
			return false;
		}
	}
}