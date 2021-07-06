package beans;

import java.io.Serializable;
public class Ticket implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private int					ticketID;
	private String				ticketDate;
	private double				ticketPrice;
	private String				ticketEvent;
	private String 				categoryName;
	private String 				subCategory;
	

	public Ticket() {

	}

	public Ticket(int int1, String string, double double1, String string2, String string3, String string4, String string5) {
		super();
		this.ticketID = int1;
		this.ticketDate = string;
		this.ticketPrice = double1;
		this.ticketEvent = string3;
		this.categoryName = string4;
		this.subCategory = string5;
	}


	public int getTicketID() {
		return ticketID;
	}
	
	
	public String getTicketEvent() {
		return ticketEvent;
	}
	
	public void setTicketEvent(String ticketEvent) {
			this.ticketEvent = ticketEvent;
	}

	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}

	public String getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(String ticketDate) {
		this.ticketDate = ticketDate;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	
	
	public String getCategory() {
		return categoryName;
	}

	public void setCategory(String category) {
		this.categoryName = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}



	}

