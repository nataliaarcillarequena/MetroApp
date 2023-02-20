package com.metro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.metro.entity.Journey;
import com.metro.entity.User;
import com.metro.entity.UserMetro;
import com.metro.persistence.MetroDao;

@Service
public class MetroServiceImpl implements MetroService {

	@Autowired
	private MetroDao metroDao;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public User login(String userName, String password) {
		return restTemplate.getForObject("http://localhost:8080/users/"+userName+"/"+ password, User.class);
	}
	
	//if registration successful then will return non-null user (works)
	@Override
	public User registration(User user) {
		
		//using post request to register user
		String registered = restTemplate.postForObject("http://localhost:8080/users", user, String.class);
		
		if(registered.equals("user registered")) {
			//saving the user to a card with balance 100Rs for the registered user-
			UserMetro userCard = new UserMetro(user.getUserName(), 0, null, null, 0);
			metroDao.save(userCard);
			metroDao.updateBalance(user.getUserName(), 100);
			return user;
		}
		
		return null;
	}
	
	//swipe in functionality- returns non-null UserMetro object if swipe in 
	//is successful i.e. minimum balance required is met 
	@Override
	public UserMetro swipeIn(String userName, String sourceStation) {

		UserMetro metroUser = metroDao.searchUserMetroByUserName(userName);
		
		//condition for when balance on card is not sufficient to swipe in 
		//(below 20Rs)
		if(metroUser.getBalance()<20)
			return null;
		
		//condition if user has already swiped in somewhere else, not swiped
		//out and wants to swipe in again
		if(metroUser.getSwipeIn()!=null)
			return null;
		
		//set the swipe in station 
		metroUser.setSwipeIn(sourceStation);
		//save the swipped in metro user to the UserMetro DB
		metroDao.save(metroUser);
		
		return metroUser;
	}

	//return non-null UserMetro object if user has swipped in
	@Override
	public UserMetro swipeOut(String userName, String outStation) {
		
		//get the users record via the user name (the users card)
		UserMetro user = metroDao.searchUserMetroByUserName(userName);
		
		//find the station that the user swiped in to
		String swipeIn = user.getSwipeIn();
		//if not swiped in then can't swipe out
		if(swipeIn==null)
			return null;
		
		user.setSwipeOut(outStation);
		
		//use the swipe in station to get the total far using the journey 
		//DB 
		Journey inJourney = restTemplate.getForObject("http://localhost:8081/journey/"+swipeIn, Journey.class);
		int inOrder = inJourney.getOrders();
		
		Journey outJourney = restTemplate.getForObject("http://localhost:8081/journey/"+outStation, Journey.class);
		int outOrder = outJourney.getOrders();
		
		int diff = Math.abs(inOrder - outOrder);
		double totalFare = diff * 5;
		user.setTotalFare(totalFare);
		//set swipeIn back to null for when need to swipe in again (covers the 
		//going below 20 by having to always swipe in- and that condition is checked in
		//swipe in function 
		user.setSwipeIn(null);
		
		//save the records (new total fare and swipe out station)
		metroDao.save(user);
		//update the balance in the userMetro DB
		metroDao.updateBalance(userName, -1*totalFare);
		user = metroDao.searchUserMetroByUserName(userName);
		
		return user;
	}

	//returns true if it has updated 
	@Override
	public boolean topUp(String userName, double amount) {
		
		//is it updating? should be- yes 
		if(metroDao.updateBalance(userName, amount)!=0)
			return true;
		
		return false;
	}

	//returns true if not yet swiped in 
	@Override
	public boolean swipInCheck(String userName) {
		UserMetro userCard = metroDao.searchUserMetroByUserName(userName);
		if(userCard.getSwipeIn()!=null)
			return false;
		return true;
	}
	
	//get all the journies 
	@Override
	public List<Journey> getAllJournies() {
		
		List<Journey> allJournies = new ArrayList<Journey>();
		
		//all all the journies to the array class then add that to the array List
		//to get the correct return type
		Journey[] journies = restTemplate.getForObject("http://localhost:8081/journies", Journey[].class);
		
		for(Journey j:journies) {
			allJournies.add(j);
		}
		
		return allJournies;
	}

	//finds the user card records via the user name
	@Override
	public UserMetro userCard(String userName) {
		return metroDao.searchUserMetroByUserName(userName);
	}
	
}
