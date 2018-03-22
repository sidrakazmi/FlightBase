package se.lexicon.model;

import java.util.Map;

import se.lexicon.utilities.SeatType;

/**
 * A class, the objects of which represents bookings.
 * 
 * @author Sidra Ali Kazmi
 * @author Linus Karlbom
 */
public class Booking {
	private int id;
	private String passengerName;
	private String location;
	private String destination;
	private String airplaneName;
	private double cost;
	private int seatNo;
	private SeatType seatType;
	public static Map<FoodItem, Integer> foodItems;

	/**
	 * Constructs a Booking object using the provided parameters
	 * 
	 * @param id
	 *            the ID of the booking.
	 * @param passengerName
	 *            the name of the booking's corresponding passenger.
	 * @param location
	 *            the location from where the booking's corresponding flight starts.
	 * @param destination
	 *            the destination of the booking's corresponding flight.
	 * @param airplaneName
	 *            the name of the booking's corresponding airplane.
	 * @param cost
	 *            the cost of the booking.
	 * @param seatNo
	 *            the seat number for the booking.
	 * @param seatType
	 *            the seat type of the booking. Can be either ECONOMY_SEAT or
	 *            BUSINESS_SEAT.
	 * @param foodMap
	 *            a map between the ordered food items and the ordered quantity of
	 *            each item.
	 */
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

	/**
	 * @return a String describing the Booking object.
	 */
	@Override
	public String toString() {
		return "Booking [id=" + id + ", passengerName=" + passengerName + ", location=" + location + ", destination="
				+ destination + ", airplaneName=" + airplaneName + ", cost=" + cost + ", seatNo=" + seatNo
				+ ", seatType=" + seatType + "]";
	}

	/**
	 * @return the cost of the Booking object.
	 */
	public double getCost() {
		return cost;
	}

}
