package com.user.main;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.user.entity.User;
import com.user.persistence.UserDao;
import com.user.service.UserServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MetroUserApplicationTests {

	private UserServiceImpl userServiceImpl;
	private UserDao userDao;
	
	@BeforeEach
	void setUp() throws Exception{
		
		userServiceImpl = new UserServiceImpl();
		userDao = Mockito.mock(UserDao.class);
		
		//works thanks to @Setter notation in UserServiceImpl when declaring 
		//userDao
		userServiceImpl.setUserDao(userDao);
	}
	
	@AfterEach
	void tearDown() throws Exception{
		
	}
	
	//testing the user login- positive case- passed
	@Test
	void testUserLogin() {
		
		User user = new User("Na", "Arc", "Natar", "pass1", 100);
		
		when(userDao.findByUserNameAndPassword("Natar", "pass1")).thenReturn(user);
		
		assertEquals(user, userServiceImpl.userLogin("Natar", "pass1"));
	}
	
	@Test
	void testUserLoginNegatice() {
		
		when(userDao.findByUserNameAndPassword("Natar", "pass10")).thenReturn(null);
		
		assertEquals(null, userServiceImpl.userLogin("Natar", "pass10"));
		
	}
	
	@Test
	void testUserRegistration() {
		
		User user = new User("h", "h", "h", "h", 100);
		
		//when(userDao.findById("h").orElse(null)).thenReturn(null);
		
		when(userDao.insertUser("h", "h", "h", "h", 100)).thenReturn(1);
		
		assertTrue(userServiceImpl.addnewUser(user));
		
	}
	
	@Test
	void testUserregistrationNegative() {
		
		User user = new User("Na", "Arc", "Natar", "pass1", 100);
		
		when(userDao.searchUserByUserName("Natar")).thenReturn(user);
		
		assertFalse(userServiceImpl.addnewUser(user));
		
	}
	
	

}
