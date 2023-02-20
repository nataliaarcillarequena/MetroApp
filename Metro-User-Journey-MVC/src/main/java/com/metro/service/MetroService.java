package com.metro.service;

import java.util.List;

import com.metro.entity.Journey;
import com.metro.entity.User;
import com.metro.entity.UserMetro;

public interface MetroService {

	//user login 
	public User login(String userName, String password);
	
	//user registration
	public User registration(User user);
	
	//swipe in functionality 
	public UserMetro swipeIn(String userName, String sourceStation);
	
	//check if user has already swiped in 
	public boolean swipInCheck(String userName);
	
	//swipe out functionality 
	public UserMetro swipeOut(String userName, String outStation);
	
	//top up functionality- true if balance successfully updated 
	public boolean topUp(String userName, double amount);
	
	//get all the stations from the journey DB 
	public List<Journey> getAllJournies();
	
	//get the metroCard entry by user login 
	public UserMetro userCard(String userName);
	
}
