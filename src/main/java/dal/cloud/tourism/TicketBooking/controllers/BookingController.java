package dal.cloud.tourism.TicketBooking.controllers;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.codec.Base64.OutputStream;

import dal.cloud.tourism.TicketBooking.model.Booking;
import dal.cloud.tourism.TicketBooking.model.Journey;
import dal.cloud.tourism.TicketBooking.repository.BookingRepository;
import dal.cloud.tourism.TicketBooking.repository.JourneyRepository;
import dal.cloud.tourism.TicketBooking.repository.RouteRepository;
import dal.cloud.tourism.utilities.GmailService;

@RestController
@RequestMapping("booking")
public class BookingController {

	@Autowired
	BookingRepository bookingRepository;
	@Autowired
	JourneyRepository journeyRepository;
	@Autowired
	RouteRepository routeRepository;

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
	public List<Booking> getBookingByUserId(@RequestParam("userId") int userId) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println(currentPrincipalName);
		
		List<Booking> list = new ArrayList<Booking>();
		list = bookingRepository.getBookingsByUserId(userId);
		return list;
	}
	
	@RequestMapping("/bookingInfoByUserId")
	public List<Object> getBookingInfoByUserId(@RequestParam("userId") int userId) {
		List<Object> list = new ArrayList<Object>();
		list = bookingRepository.getBookingInfoByUserId(userId);
		return list;
	}

	@RequestMapping("/getBookingConfirmation")
	public String getBookingConfirmation(@RequestParam("journeyId") int journeyId, @RequestParam("totalSeats") int totalSeats) {
		if(journeyId==-1) {
			return "Error";
		}
		int seatsLeft = bookingRepository.getBookingAuditInfo(journeyId);			
		return seatsLeft+"";
	}
		
	@RequestMapping("/addBooking")
	public String addBookingInformation(@RequestParam("userId") String userId, @RequestParam("journeyId") int journeyId,
			@RequestParam("transactionMode") String transactionMode, @RequestParam("amount") double amount,
			@RequestParam("totalSeats") int totalSeats, @RequestParam("cardNumber") String cardNumber, 
			@RequestParam("holderName") String holderName, @RequestParam("mm") String mm, @RequestParam("yy") String yy, 
			@RequestParam("cvv") String cvv) {

		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		
		if(!cardNumber.equals("1111-1111-1111-1111") 
				|| holderName.length() == 0 
				|| mm.length() < 2 || yy.length() < 2 || cvv.length() < 3  
				|| Integer.parseInt(mm) < 0 || Integer.parseInt(mm) > 12 
				|| (Integer.parseInt(mm) < month+1 && Integer.parseInt(yy)<=20)
				|| Integer.parseInt(yy) < 20 || Integer.parseInt(yy) > 25) {
			
			return "Invalid Card Details. Please try again!";
		}
		
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
			bookingRepository.updateBookingAuditInfo(journeyId, totalSeats);
			
			Journey journey = journeyRepository.getJourneyById(journeyId);
			String source = routeRepository.getSourceNameByRouteById(journey.route_id);
			String destination = routeRepository.getDestinationNameByRouteById(journey.route_id);
			
			GmailService gmail = new GmailService(userId, (amount*1.15), timestamp, totalSeats, cardNumber, journey, source, destination);
			gmail.sendMail();
			
			return "Booking Successful!";
		} else {
			return "No seats available! Please try again later.";
		}
	}
}
