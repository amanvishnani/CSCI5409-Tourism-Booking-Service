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
			+ "where b.booking_id = :bookingId ", 
			nativeQuery = true) 
	public Booking getBookingById(int bookingId);

	@Query(value = "SELECT * FROM booking b "
			+ "where b.user_id = :userId",
			nativeQuery = true)
	public List<Booking> getBookingsByUserId(int userId);

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
