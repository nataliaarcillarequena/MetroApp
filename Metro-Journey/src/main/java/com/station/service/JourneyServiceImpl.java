package com.station.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.station.entity.Journey;
import com.station.persistence.JourneyDao;

import lombok.Setter;

@Service
public class JourneyServiceImpl implements JourneyService {

	@Setter //needed for JUnit testing- but maybe try the 
	//other way which does not require this
	@Autowired
	JourneyDao journeyDao;
	
	@Override
	public List<Journey> getAllJournies() {
		return journeyDao.findAll();
	}

	@Override
	public Journey getJourneyByStation(String station) {
		return journeyDao.searchJourneyByStations(station);
		
	}

	@Override
	public Journey getJournyByStat2(String station) {
		return journeyDao.findByStations(station);
	}

}
