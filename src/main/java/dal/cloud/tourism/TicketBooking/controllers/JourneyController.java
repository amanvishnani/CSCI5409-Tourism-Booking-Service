package dal.cloud.tourism.TicketBooking.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dal.cloud.tourism.TicketBooking.model.Journey;
import dal.cloud.tourism.TicketBooking.repository.JourneyRepository;

@RestController
@RequestMapping("journey")
public class JourneyController {

	@Autowired
	JourneyRepository journeyRepository;
	
	@RequestMapping("/all")
	public List<Journey> getJourneys(){
		List<Journey> list = new ArrayList<Journey>();
		list = journeyRepository.findAll();
		return list;
	}
	
	@RequestMapping("/journeyById")
	public Journey getJourneyById(@RequestParam("journeyId") int journeyId){
		Journey journey= journeyRepository.getJourneyById(journeyId);
		return journey;
	}
	
	@RequestMapping("/journeyByRouteId")
	public List<Journey> getJourneyByRouteId(@RequestParam("routeId") int routeId){
		List<Journey> list = new ArrayList<Journey>();
		list = journeyRepository.getJourneyByRouteId(routeId);
		return list;
	}
	
	@RequestMapping("/journeyByCompanyId")
	public List<Journey> getJourneyByCompanyId(@RequestParam("companyId") int companyId){
		List<Journey> list = new ArrayList<Journey>();
		list = journeyRepository.getJourneyByCompanyId(companyId);
		return list;
	}
}
