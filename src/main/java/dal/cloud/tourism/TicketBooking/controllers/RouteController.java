package dal.cloud.tourism.TicketBooking.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dal.cloud.tourism.TicketBooking.model.Route;
import dal.cloud.tourism.TicketBooking.repository.RouteRepository;

@RestController
@RequestMapping("routes")
public class RouteController {

	@Autowired
	RouteRepository routeRepository;
	
	@RequestMapping("/all")
	public List<Route> getRoutes(){
		List<Route> list = new ArrayList<Route>();
		list = routeRepository.findAll();
		return list;
	}
	
	@RequestMapping("/routeById")
	public Route getRouteById(@RequestParam("routeId") int routeId){
		Route route= routeRepository.getRouteById(routeId);
		return route;
	}
	
	@RequestMapping("/routeBySourceDestination")
	public List<Route> getRouteBySourceDestination(@RequestParam("sourceId") int sourceId, @RequestParam("destinationId") int destinationId){
		List<Route> list = new ArrayList<Route>();
		list = routeRepository.getRouteBySourceDestination(sourceId, destinationId);
		return list;
	}
	
}
