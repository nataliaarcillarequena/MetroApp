package com.metro.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metro.entity.Journey;
import com.metro.entity.User;
import com.metro.entity.UserMetro;
import com.metro.service.MetroService;

@Controller
public class MetroController {

	@Autowired
	private MetroService metroService;
	
	//login/registration ------------------------------
	//this puts the initial login page on the screen
	@RequestMapping("/")
	public ModelAndView loginPageController() {
		return new ModelAndView("Login", "user", new User());
	}//user i think- yes 
	
	//user registration page --------
	@RequestMapping("/registerUser")
	public ModelAndView registrationPage() {
		return new ModelAndView("Registration", "user", new User());
	}
	
	//registering a user
	@RequestMapping("/register")
	public ModelAndView registratingUser(@ModelAttribute("user") User user, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		User userRegistration = null;
		
		//returns null if not successfully registered- if username is taken
		userRegistration = metroService.registration(user);
		
		//get the journies
		List<Journey> allJournies = metroService.getAllJournies();
		
		if(userRegistration!=null) {
			modelAndView.addObject("user", userRegistration);
			session.setAttribute("user", userRegistration);
			//set to the main page once created--------------- (Menu)
			modelAndView.addObject("journies", allJournies); //added
			modelAndView.setViewName("Menu");
		}
		else {
			modelAndView.addObject("message", "User Name already exists, please log in if already registered or choose a different user name");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("Registration");
		}
		
		return modelAndView;
	}
	
	//logging in a user 
	@RequestMapping("/login")
	public ModelAndView loginUser(@ModelAttribute("user") User user, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		User userToLogin = null;
		
		userToLogin = metroService.login(user.getUserName(), user.getPassword());
		
		//get the journies
		List<Journey> allJournies = metroService.getAllJournies();
		//this below does not get put as a th: object 
		//session.setAttribute("journies", allJournies);
		
		if(userToLogin !=null) {
			modelAndView.addObject("user", userToLogin);
			session.setAttribute("user", userToLogin);
			//set to the main page once created--------------- (Menu)
			modelAndView.addObject("journies", allJournies); //added
			modelAndView.setViewName("Menu");
		}
		else {
			modelAndView.addObject("message", "No such username/password comination, please try again or register if you are a new user");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("Login");
		}
		
		return modelAndView;
		
	}
	
	//to get back to the menu page with the swipe ins/outs
	@RequestMapping("/menu")
	public ModelAndView menuPage(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("Menu");
		//get the journies to show 
		List<Journey> allJournies = metroService.getAllJournies();
		modelAndView.addObject("journies", allJournies);
		
		return modelAndView;
	}
	
	
	//TODO next: make the swipe in/out buttons work, top up page 
	//@RequestMapping("/swipeInChoice")
	//@RequestMapping("/swipeOutChoice")
	//@RequestMapping("/topUpPage")
	
	//user swipe in 
	@RequestMapping("/swipeInChoice")
	public ModelAndView swipeIn(@RequestParam("station") String station, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		//1. check for already swipped in
		//2. do swipe in method- if null then its because balance does not meet min requirmement 
		//3. else- a successful swipe in 
		
		User user = (User) session.getAttribute("user");
		UserMetro swipeInUser = metroService.swipeIn(user.getUserName(), station);
		
		//still display the journies 
		List<Journey> allJournies = metroService.getAllJournies();
		modelAndView.addObject("journies", allJournies);
		//all functionality here stays on the menu page so can just set the view 
		//to menu here
		modelAndView.setViewName("Menu");
		
		if(swipeInUser!=null) {
			//possitive outcome- message- successful swipe in 
			modelAndView.addObject("message1", "Successfully swipped in at " + station);
			//modelAndView.setViewName("Menu");
		}else if (!metroService.swipInCheck(user.getUserName())) {
			//error message - already swiped in, please swipe out
			modelAndView.addObject("message2", "You have already swiped in, please swipe out before swiping in again");
			//modelAndView.setViewName("Menu");
		}else {
			//error message- please top up balance 
			modelAndView.addObject("message2", "Minimum balance required to swipe in is 20R's, please top up");
			//modelAndView.setViewName("Menu");
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/swipeOutChoice")
	public ModelAndView swipeOut(@RequestParam("station") String station, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		//consult the Metro card DB for swipe out functionality 
		User user = (User) session.getAttribute("user");
		UserMetro swipeOutUser = metroService.swipeOut(user.getUserName(), station);
		
		//still display the journies 
		List<Journey> allJournies = metroService.getAllJournies();
		modelAndView.addObject("journies", allJournies);
		modelAndView.setViewName("Menu");
		
		if(swipeOutUser != null) {
			//say success
			modelAndView.addObject("message1", "Successfully swipped out at "+ station + ". Total Fare: " + swipeOutUser.getTotalFare()+"Rs");
		}else {
			//error- need to swipe in first before swipping out 
			modelAndView.addObject("message2", "You have not yet swipped in, please swipe in to swipe out");
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/topUpPage")
	public ModelAndView topUpPage(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		//get the balance to display on the screen
		User user = (User) session.getAttribute("user");
		UserMetro userCard = metroService.userCard(user.getUserName());
		
		//puts the users balance on the screen
		modelAndView.addObject("balance", userCard.getBalance()+"Rs");
		modelAndView.setViewName("TopUp");
		
		return modelAndView;
	}
	
	@RequestMapping("/topUp")
	public ModelAndView topUpBalance(@RequestParam("increment")double inc, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		User user = (User) session.getAttribute("user");
		if(metroService.topUp(user.getUserName(), inc)) {
			//successfully topped up message
			modelAndView.addObject("message1", "Balance successfully topped up!");
		}else {
			//unsuccessfull top up
			modelAndView.addObject("message2", "Top up unsuccessful, please try again");
		}
		
		modelAndView.setViewName("TopUp");
		UserMetro userCard = metroService.userCard(user.getUserName());
		//shows updated balance
		modelAndView.addObject("balance", userCard.getBalance()+"Rs");
		
		return modelAndView;
	}
	
	//TODO restrict users ability to take away from their balance, 
	//restrict in the html file- no negative amounts
	//TODO log out page 
	
	@RequestMapping("/logout")
	public ModelAndView logOut() {
		return new ModelAndView("Logout");
	}
	
	//TODO: when registering user- it messes up- comes up with error straight away
	
}
