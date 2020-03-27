package dal.cloud.tourism.TicketBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dal.cloud.tourism.TicketBooking.model.Booking;
import dal.cloud.tourism.TicketBooking.model.BookingAudit;
import dal.cloud.tourism.TicketBooking.model.Booking;
import dal.cloud.tourism.TicketBooking.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
	
	@Query(value = "SELECT * FROM booking b "
			+ "where b.booking_Id = :bookingId ", 
			nativeQuery = true) 
	public Booking getBookingById(int bookingId);

	@Query(value = "SELECT * FROM booking b "
			+ "where b.user_id = :userId",
			nativeQuery = true)
	public List<Booking> getBookingsByUserId(String userId);
	
	@Query(value = "SELECT b.booking_Id, b.transaction_mode, b.amount, b. timestamp, b.total_seats, "
			+ "j.date, j.duration, "
			+ "bu.type, "
			+ "c.name as company, c.contact as contact, "
			+ "ci.name AS source, ci2.name AS destination "
			+ "FROM booking b "
			+ "JOIN journey j on b.journey_Id = j.journey_Id "
			+ "JOIN bus bu on j.bus_no = bu.bus_no "
			+ "JOIN company c on j.company_id = c.company_id "
			+ "JOIN route r on j.route_id = r.route_id "
			+ "JOIN city ci on r.source_id = ci.city_Id " 
			+ "JOIN city ci2 on r.destination_id = ci2.city_Id "
			+ "where b.user_id = :userId",
			nativeQuery = true)
	public List<Object> getBookingInfoByUserId(String userId);

	@Query(value = "SELECT seats_available from booking_audit "
			+ "where journey_id = :journeyId", 
			nativeQuery = true)
	public int getBookingAuditInfo(int journeyId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE booking_audit b set b.seats_available = b.seats_available - :totalSeats "
			+ "where journey_Id = :journeyId", 
			nativeQuery = true)
	public void updateBookingAuditInfo(int journeyId, int totalSeats);
	
}
