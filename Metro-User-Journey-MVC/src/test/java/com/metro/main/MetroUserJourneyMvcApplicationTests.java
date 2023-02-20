package com.metro.main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import com.metro.entity.User;
import com.metro.entity.UserMetro;
import com.metro.service.MetroServiceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class MetroUserJourneyMvcApplicationTests {

	@Autowired
	MetroServiceImpl metroServiceImpl;
	
	//dont need to mock the rest API i dont think 
	@Test
	@Order(1)
	void testLoginPositive() {
		User user = new User("Na", "Arc", "Natar", "pass1", 100);
		assertEquals(user, metroServiceImpl.login("Natar", "pass1"));
	}
	
	@Test
	@Order(2)
	void testLoginNegative() {
		User user = new User("Na", "Arc", "Natar", "pass1", 100);
		assertNotEquals(user, metroServiceImpl.login("Natar", "pass2"));
	}
	
	@Test
	@Order(3)
	void registrationTestPositive() {
		User user = new User("Na", "ba", "shatar", "pass10", 100);
		assertEquals(user, metroServiceImpl.registration(new User("Na", "ba", "shatar", "pass10", 100)));
	}
	
	@Test
	@Order(4)
	void registrationTestNegative() {
		assertNull(metroServiceImpl.registration(new User("Na", "ba", "shatar", "pass10", 100)));
	}
	
	@Test
	@Order(5)
	void swipeInTestPositive() {
		UserMetro userCard = new UserMetro("shatar", 100, "Grays", null, 0);
		
		assertEquals(userCard, metroServiceImpl.swipeIn("shatar","Grays"));
	}
	
	@Test
	@Order(6)
	void swipeInTestNegative() {
		assertNull(metroServiceImpl.swipeIn("shatar",null));
	}
	
	@Test
	@Order(7)
	void swipeOutPositive() {
		UserMetro userCard = new UserMetro("shatar", 80, null, "West Ham", 20);
		
		assertEquals(userCard, metroServiceImpl.swipeOut("shatar", "West Ham"));
	}
	
	//yes it should work- table used so will never have a null entry for the swipeOut/in anyway
	@Test
	@Order(8)
	void swipeOutNegative() {
		assertNull(metroServiceImpl.swipeOut("shatar", null));
	}
	
	@Test
	@Order(9)
	void topUpPositive() {
		assertTrue(metroServiceImpl.topUp("shatar", 60));
	}
	
	@Test
	@Order(10)
	void topUpNegative() {
		assertFalse(metroServiceImpl.topUp("batars", 20));
	}
	
	@Test
	@Order(11)
	void swipeInCheck(){
		assertTrue(metroServiceImpl.swipInCheck("shatar"));
	}
	
	@Test
	@Order(12)
	void swipeInPositive2(){
		UserMetro userCard = new UserMetro("shatar", 120, "Grays", "West Ham", 20);
		
		assertEquals(userCard, metroServiceImpl.swipeIn("shatar","Grays"));
	}
	
}
