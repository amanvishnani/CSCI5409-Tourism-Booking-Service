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

}
