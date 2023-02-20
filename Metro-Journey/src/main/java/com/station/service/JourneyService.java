package com.station.service;

import java.util.List;

import com.station.entity.Journey;

public interface JourneyService {

	public List<Journey> getAllJournies();
	
	public Journey getJourneyByStation(String station);
	
	public Journey getJournyByStat2(String station);
	
}
