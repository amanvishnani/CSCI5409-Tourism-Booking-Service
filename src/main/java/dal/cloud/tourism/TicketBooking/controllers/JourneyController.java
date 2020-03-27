package dal.cloud.tourism.TicketBooking.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@RequestMapping("/journeyBySourceDestination")
	public List<Object> getJourneyBySourceDestination(@RequestParam("sourceId") int sourceId, @RequestParam("destinationId") int destinationId){
		List<Object> list = new ArrayList<Object>();
		list = journeyRepository.getJourneyBySourceDestination(sourceId, destinationId);
		return list;
	}
	
	@RequestMapping("/journeyBySourceDestinationMap")
	public List<Map<String,String>> getJourneyBySourceDestinationMap(@RequestParam("sourceId") int sourceId, @RequestParam("destinationId") int destinationId){
		List<Object[]> list = new ArrayList<Object[]>();
		list = journeyRepository.getJourneyBySourceDestinationMap(sourceId, destinationId);
		
		Map<String,String> map;
		List<Map<String,String>> lst = new ArrayList<Map<String,String>>();
	
		for(int i=0;i<list.size();i++) {
			map = new HashMap<String,String>();
			Object[] ob = list.get(i);
			for(int j = 0;j<ob.length;j++){
				
				String val = ob[j]+"";
				
				switch(j){
				
				case 0: map.put("date",val);
				break;
				
				case 1: map.put("duration",val);
				break;
				
				case 2: map.put("amount",val);
				break;
				
				case 3: map.put("seatType",val);
				break;
				
				case 4: map.put("busCapacity",val);
				break;
				
				case 5: map.put("companyName",val);
				break;
				
				case 6: map.put("contact",val);
				break;
				
				case 7: map.put("journeyId",val);
				break;
				
				}
			}
			
			lst.add(map);
		}
		
		return lst;
	}
	
	@RequestMapping("/journeyBySourceDestinationDate")
	public List<Object> getJourneyBySDDate(@RequestParam("sourceId") int sourceId, @RequestParam("destinationId") int destinationId, @RequestParam("date") String date){
		List<Object> list = new ArrayList<Object>();
		list = journeyRepository.getJourneyBySourceDestinationDate(sourceId, destinationId, date);
		return list;
	}
	
	@RequestMapping("/journeyBySourceDestinationDateType")
	public List<Object> getJourneyBySDDType(@RequestParam("sourceId") int sourceId, @RequestParam("destinationId") int destinationId,  @RequestParam("date") String date,  @RequestParam("type") String type){
		List<Object> list = new ArrayList<Object>();
		list = journeyRepository.getJourneyBySourceDestinationDateType(sourceId, destinationId, date, type);
		return list;
	}
}
