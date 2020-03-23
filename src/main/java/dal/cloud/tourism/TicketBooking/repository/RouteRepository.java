package dal.cloud.tourism.TicketBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dal.cloud.tourism.TicketBooking.model.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
	
	@Query(value = "SELECT * FROM route r "
			+ "where r.source_id = :sourceId "
			+ "and "
			+ "r.destination_id = :destinationId", 
			nativeQuery = true) 
	public List<Route> getRouteBySourceDestination(int sourceId, int destinationId);
	
	@Query(value = "SELECT * FROM route r "
			+ "where r.route_id = :routeId", nativeQuery = true)
	public Route getRouteById(int routeId);
	
	@Query(value = "SELECT c.name FROM city c "
			+ "JOIN route r ON r.source_Id = c.city_Id "
			+ "where r.route_id = :routeId", nativeQuery = true)
	public String getSourceNameByRouteById(int routeId);

	@Query(value = "SELECT c.name FROM city c "
			+ "JOIN route r ON r.destination_Id = c.city_Id "
			+ "where r.route_id = :routeId", nativeQuery = true)
	public String getDestinationNameByRouteById(int routeId);

}
