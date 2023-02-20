package com.station.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.station.entity.Journey;
import com.station.service.JourneyService;

@RestController
public class JourneyResource {

	@Autowired
	private JourneyService journeyService;
	
	//2 get mappings for all journies and journies via station 
	
	@GetMapping(path = "/journies", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Journey> allJourniesresource(){
		return journeyService.getAllJournies();
	}
	
	//works- has a mistake before where i was using @Param instead of @PathVariable
	//@Param is for the native query- collecting the parameters 
	@GetMapping(path = "/journey/{station}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Journey getJourneyByStationResource(@PathVariable("station") String station) {
		return journeyService.getJournyByStat2(station);
	}
}
