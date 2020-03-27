package dal.cloud.tourism.TicketBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dal.cloud.tourism.TicketBooking.model.Journey;
import dal.cloud.tourism.TicketBooking.model.Journey;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Integer> {
	
	@Query(value = "SELECT * FROM journey j "
			+ "where j.route_id = :routeId ", 
			nativeQuery = true) 
	public List<Journey> getJourneyByRouteId(int routeId);
	
	@Query(value = "SELECT * FROM journey j "
			+ "where j.journey_id = :journeyId", nativeQuery = true)
	public Journey getJourneyById(int journeyId);

	@Query(value = "SELECT * FROM journey j "
			+ "where j.company_id = :companyId", nativeQuery = true)
	public List<Journey> getJourneyByCompanyId(int companyId);

	@Query(value = "SELECT DATE_ADD(j.date, INTERVAL 1 DAY), j.duration, j.amount, b.type, b.capacity, c.name, c.contact, j.journey_Id FROM route r "
			+ "JOIN journey j on r.route_id = j.route_id "
			+ "JOIN bus b on j.bus_no = b.bus_no "
			+ "JOIN company c on j.company_id = c.company_id "
			+ "where r.source_id = :sourceId and r.destination_id = :destinationId", nativeQuery = true)
	public List<Object> getJourneyBySourceDestination(int sourceId, int destinationId);
	
	@Query(value = "SELECT DATE_ADD(j.date, INTERVAL 1 DAY), j.duration, j.amount, b.type, b.capacity, c.name, c.contact, j.journey_Id FROM route r "
			+ "JOIN journey j on r.route_id = j.route_id "
			+ "JOIN bus b on j.bus_no = b.bus_no "
			+ "JOIN company c on j.company_id = c.company_id "
			+ "where r.source_id = :sourceId and r.destination_id = :destinationId", nativeQuery = true)
	public List<Object[]> getJourneyBySourceDestinationMap(int sourceId, int destinationId);
	
	@Query(value = "SELECT DATE_ADD(j.date, INTERVAL 1 DAY), j.duration, j.amount, b.type, b.capacity, c.name, c.contact, j.journey_Id FROM route r "
			+ "JOIN journey j on r.route_id = j.route_id "
			+ "JOIN bus b on j.bus_no = b.bus_no "
			+ "JOIN company c on j.company_id = c.company_id "
			+ "where r.source_id = :sourceId and r.destination_id = :destinationId and j.date = :jdate", nativeQuery = true)
	public List<Object> getJourneyBySourceDestinationDate(int sourceId, int destinationId, String jdate);
	
	@Query(value = "SELECT DATE_ADD(j.date, INTERVAL 1 DAY), j.duration, j.amount, b.type, b.capacity, c.name, c.contact, j.journey_Id FROM route r "
			+ "JOIN journey j on r.route_id = j.route_id "
			+ "JOIN bus b on j.bus_no = b.bus_no "
			+ "JOIN company c on j.company_id = c.company_id "
			+ "where r.source_id = :sourceId and r.destination_id = :destinationId "
			+ "and j.date = :jdate and b.type = :btype", nativeQuery = true)
	public List<Object> getJourneyBySourceDestinationDateType(int sourceId, int destinationId, String jdate, String btype);

}
