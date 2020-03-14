package dal.cloud.tourism.TicketBooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "booking_audit")
public class BookingAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int journeyId;

	@Column
	public int seat_capacity;

	@Column
	public int seats_available;

	public int getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

	public int getSeat_capacity() {
		return seat_capacity;
	}

	public void setSeat_capacity(int seat_capacity) {
		this.seat_capacity = seat_capacity;
	}

	public int getSeats_available() {
		return seats_available;
	}

	public void setSeats_available(int seats_available) {
		this.seats_available = seats_available;
	}
	
}
