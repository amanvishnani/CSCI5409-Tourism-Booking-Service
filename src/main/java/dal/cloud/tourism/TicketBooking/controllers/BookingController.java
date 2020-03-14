package dal.cloud.tourism.TicketBooking.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dal.cloud.tourism.TicketBooking.model.Booking;
import dal.cloud.tourism.TicketBooking.repository.BookingRepository;
import dal.cloud.tourism.TicketBooking.repository.BookingRepository;

@RestController
@RequestMapping("booking")
public class BookingController {

	@Autowired
	BookingRepository bookingRepository;

	@RequestMapping("/all")
	public List<Booking> getBookings() {
		List<Booking> list = new ArrayList<Booking>();
		list = bookingRepository.findAll();
		return list;
	}

	@RequestMapping("/bookingById")
	public Booking getBookingById(@RequestParam("bookingId") int bookingId) {
		Booking journey = bookingRepository.getBookingById(bookingId);
		return journey;
	}

	@RequestMapping("/bookingByUserId")
	public List<Booking> getBookingByRouteId(@RequestParam("userId") int userId) {
		List<Booking> list = new ArrayList<Booking>();
		list = bookingRepository.getBookingsByUserId(userId);
		return list;
	}

	@RequestMapping("/getBookingConfirmation")
	public Booking getBookingConfirmation(@RequestParam("userId") int userId, @RequestParam("journeyId") int journeyId,
			@RequestParam("transactionMode") String transactionMode, @RequestParam("amount") int amount,
			@RequestParam("totalSeats") int totalSeats) {

		int seatsLeft = bookingRepository.getBookingAuditInfo(journeyId);
		Booking booking = new Booking();

		if (seatsLeft - totalSeats >= 0) {

			Date date = new Date();
			long time = date.getTime();
			Timestamp timestamp = new Timestamp(time);

			booking.setBookingId(0);
			booking.setUserId(userId);
			booking.setJourneyId(journeyId);
			booking.setTransactionMode(transactionMode);
			booking.setAmount(amount*totalSeats);
			booking.setTimestamp(timestamp + "");
			booking.setTotalSeats(totalSeats);
		}			
		return booking;
	}
	
	@RequestMapping("/addBooking")
	public String addBookingInformation(@RequestParam("userId") int userId, @RequestParam("journeyId") int journeyId,
			@RequestParam("transactionMode") String transactionMode, @RequestParam("amount") int amount,
			@RequestParam("totalSeats") int totalSeats) {

		int seatsLeft = bookingRepository.getBookingAuditInfo(journeyId);

		if (seatsLeft - totalSeats >= 0) {

			Date date = new Date();
			long time = date.getTime();
			Timestamp timestamp = new Timestamp(time);

			Booking booking = new Booking();
			booking.setBookingId(0);
			booking.setUserId(userId);
			booking.setJourneyId(journeyId);
			booking.setTransactionMode(transactionMode);
			booking.setAmount(amount);
			booking.setTimestamp(timestamp + "");
			booking.setTotalSeats(totalSeats);
			bookingRepository.save(booking);

			return "Booking Successful!";
		} else {
			return "No seats available! Please try again later.";
		}
	}
}
