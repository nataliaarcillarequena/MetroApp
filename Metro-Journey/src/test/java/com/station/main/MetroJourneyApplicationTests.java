package com.station.main;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
//import java.util.ArrayList; //for some reason, this is not allowed- gives error
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.station.entity.Journey;
import com.station.persistence.JourneyDao;
import com.station.service.JourneyServiceImpl;
//import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MetroJourneyApplicationTests {

	private JourneyDao journeyDao;
	private JourneyServiceImpl journeyServiceImpl;
	
	
	@BeforeEach
	void setUp() throws Exception{
		
		//setting up the service impl to use for the 
		//mock dao
		journeyServiceImpl = new JourneyServiceImpl();
		journeyDao = Mockito.mock(JourneyDao.class);
		
		journeyServiceImpl.setJourneyDao(journeyDao);
		
	}
	
	@AfterEach
	void tearDown() throws Exception{
		
	}
	
	//get all journies- possitive case - passed
	@Test
	void testGetAllJourniesPossitive() {
		List<Journey> journiesList = new ArrayList();
		//why doesn't declaring a new array list work in testing!!!- it does apparently
		//it just didnt work with the <> (ArrayList<>())
		journiesList.add(new Journey("Grays", 1));
		
		when(journeyDao.findAll()).thenReturn(journiesList);
		assertEquals(journiesList, journeyServiceImpl.getAllJournies());	
	}
	
	@Test
	void testGetAllJourniesNegative() {
		List<Journey> journeyList = new ArrayList();
		
		when(journeyDao.findAll()).thenReturn(journeyList);
		assertEquals(journeyList, journeyServiceImpl.getAllJournies());
	}
	
	@Test
	void testGetJourneyByStationPositive() {
		Journey myJourney = new Journey("Grays", 1);
		
		when(journeyDao.searchJourneyByStations("Grays")).thenReturn(myJourney);
		assertEquals(myJourney, journeyServiceImpl.getJourneyByStation("Grays"));
	}
	
	@Test
	void testGetJourneyByStationNegative() {
		when(journeyDao.searchJourneyByStations("Huntingdon")).thenReturn(null);
		assertEquals(null, journeyServiceImpl.getJourneyByStation("Huntingdon"));
	}
	
	

}
