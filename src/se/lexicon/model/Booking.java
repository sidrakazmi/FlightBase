package se.lexicon.model;

import java.util.Map;

import utilities.SeatType;

public class Booking {
	private int id;
	private String passengerName;
	private String location;
	private String destination;
	private String airplaneName;
	private int seatNo;
	private SeatType seatType;
	
	private double cost;
	
	public static Map< FoodItem, Integer> foodItems;
	
	/**
	 * Constructor 
	 **/
	public Booking(int id, String passengerName, String location, String destination, String airplaneName, double cost,
			int seatNo, SeatType seatType, Map<FoodItem, Integer> foodMap) {
		
		this.id = id;
		this.passengerName = passengerName;
		this.location = location;
		this.destination = destination;
		this.airplaneName = airplaneName;
		this.cost = cost;
		this.seatNo = seatNo;
		this.seatType = seatType;
		foodItems = foodMap;
	}
	
	@Override
	public String toString() {
		return "Booking Details : Passenger id= " + id + ", PassengerName= " + passengerName + ", Location= " + location + ",-> Destination= "
				+ destination + ", AirplaneName= " + airplaneName + ", Cost=" + cost + ", Seat No= "  + seatNo
				+ ", Seat Type= " + seatType;
	}
	
	/**
	 * Returns the Cost of Booking
	 **/
	public double getCost() {
		return cost;
	}
	
}
