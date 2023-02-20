package com.station.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.station.entity.Journey;

@Repository
public interface JourneyDao extends JpaRepository<Journey, Integer>{

	//get all stations via findAll in service layer
	
	//find the journies by the station (to get order for each station)
	public Journey searchJourneyByStations(String station);
	
	public Journey findByStations(String station);
	
}
