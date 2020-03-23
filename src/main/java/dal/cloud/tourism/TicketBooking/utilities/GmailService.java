package dal.cloud.tourism.TicketBooking.utilities;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestParam;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import dal.cloud.tourism.TicketBooking.model.Journey;

public class GmailService {

	Properties properties = null;
	Session session = null;
	Message msg = null;
	
	String userId;
	String amt;
	String timestamp;
	int totalSeats;
	String cardNumber;
	Journey journey;
	String source;
	String destination;
	
	public GmailService(String userId, double amount, Timestamp timestamp, int totalSeats, String cardNumber, Journey journey, String source, String destination) {
		this.userId = userId;
		this.amt = amount+"";
		this.timestamp = timestamp+"";
		this.totalSeats = totalSeats;
		this.cardNumber = cardNumber;
		this.journey = journey;
		this.source = source;
		this.destination = destination;
	}

	public void sendMail() {

		properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("cloud.tourism.app@gmail.com", "Tourism@123");
			}
		});
		
		ByteArrayOutputStream outputStream = null;
		try {

			outputStream = new ByteArrayOutputStream();
			writePdf(outputStream);
			byte[] bytes = outputStream.toByteArray();

			DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
			MimeBodyPart pdfBodyPart = new MimeBodyPart();
			pdfBodyPart.setDataHandler(new DataHandler(dataSource));
			pdfBodyPart.setFileName(journey.date+".pdf");

			MimeMultipart mimeMultipart = new MimeMultipart();
			mimeMultipart.addBodyPart(pdfBodyPart);

			msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("cloud.tourism.app@gmail.com", false));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("cloud.tourism.app@gmail.com"));
			msg.setSubject("Booking Invoice for booking made on: "+ timestamp);
			msg.setText("Dear customer,\nPlease find the booking invoice for your jouney from "+source+" to "+destination+" on "+ journey.date+".\nHAVE A SAFE JOURNEY!");
			msg.setContent(mimeMultipart);
			
			Transport.send(msg);

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex);
		}

	}

	public void writePdf(ByteArrayOutputStream outputStream) throws Exception {
		Document document = new Document();
		PdfWriter.getInstance(document, outputStream);
		document.open();
		Paragraph lines = new Paragraph(new Chunk("\n------------------------------------------------------------------------------------\n",FontFactory.getFont(FontFactory.HELVETICA, 15)));
		Paragraph header = new Paragraph(new Chunk("Tourism Canada",FontFactory.getFont(FontFactory.HELVETICA, 30)));
		Paragraph topic = new Paragraph(new Chunk("Booking Invoice",FontFactory.getFont(FontFactory.HELVETICA, 20)));
		Paragraph bdate = new Paragraph(new Chunk("\nBooking Date: "+ timestamp));
		Paragraph userid = new Paragraph(new Chunk("User Id: "+ userId));
		Paragraph city = new Paragraph(new Chunk("Source: "+ source + "\nDestination: "+destination));
		Paragraph jdate = new Paragraph(new Chunk("Journey Date: "+ journey.date));
		Paragraph duration = new Paragraph(new Chunk("Duration: "+ journey.duration + " hours"));
		Paragraph seats = new Paragraph(new Chunk("Seats: "+ totalSeats));
		Paragraph amount = new Paragraph(new Chunk("Amount Paid: $" + amt));
		Paragraph card = new Paragraph(new Chunk("Card No: "+ cardNumber));
		Paragraph greeting = new Paragraph(new Chunk("HAVE A SAFE JOURNEY!", FontFactory.getFont(FontFactory.HELVETICA, 20)));
		
		document.add(header);
		document.add(lines);
		document.add(topic);
		document.add(bdate);
		document.add(userid);
		document.add(city);
		document.add(jdate);
		document.add(duration);
		document.add(seats);
		document.add(amount);
		document.add(card);
		document.add(lines);
		document.add(greeting);
		document.close();
	}
}