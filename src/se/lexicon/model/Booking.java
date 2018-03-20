package se.lexicon.model;

import java.util.HashMap;

public class Booking {
	private int id;
	private String passengerName;
	private String location;
	private String destination;
	private String airplaneName;
	private double cost;
	private int seatNo;
	private SeatType seatType;
	public static HashMap< FoodItem, Integer> item;
	
	public Booking(int id, String passengerName, String location, String destination, String airplaneName, double cost,
			int seatNo, SeatType seatType) {
		
		this.id = id;
		this.passengerName = passengerName;
		this.location = location;
		this.destination = destination;
		this.airplaneName = airplaneName;
		this.cost = cost;
		this.seatNo = seatNo;
		this.seatType = seatType;
		item = new HashMap<FoodItem, Integer>();
	}
	
	@Override
	public String toString() {
		return "Booking [id=" + id + ", passengerName=" + passengerName + ", location=" + location + ", destination="
				+ destination + ", airplaneName=" + airplaneName + ", cost=" + cost + ", seatNo=" + seatNo
				+ ", seatType=" + seatType + "]";
	}
	
	
	
}
