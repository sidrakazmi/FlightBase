package se.lexicon.utilities;

import java.util.ArrayList;
import java.util.Map;

import se.lexicon.model.Airplane;
import se.lexicon.model.Booking;
import se.lexicon.model.FoodItem;

/**
 * A class, the objects of which manages lists of Airplane and Booking objects.
 * 
 * @author Linus Karlbom
 * @author Daniel Axelsson
 * @author Sidra Ali Kazmi
 */
public class AirlineManager {

	private ArrayList<Airplane> airplanes = new ArrayList<>();
	private ArrayList<Booking> bookings = new ArrayList<>();

	/**
	 * Adds an Airplane object to the AirlineManager's list of airplanes.
	 * 
	 * @param airplane
	 *            the Airplane object to add.
	 */
	public void addAirplane(Airplane airplane) {
		airplanes.add(airplane);
	}

	/**
	 * Removes the airplane corresponding to the parameter airplaneIndex from the
	 * AirlineManager's list of airplanes. Returns true if successful and returns
	 * false if no plane corresponding to airplaneIndex was found.
	 * 
	 * @param airplaneIndex
	 *            the index corresponding to the Airplane to remove.
	 * @return true if successful and false if no plane corresponding to
	 *         airplaneIndex was found.
	 */
	public boolean removeAirplane(int airplaneIndex) {
		try {
			airplanes.remove(airplaneIndex);
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * Checks if any Airplane (for which the toString method returns a String equal
	 * to the given airplane argument) has at least one available seat of seatType.
	 * 
	 * @param airplane
	 *            a String matching the toString result of the desired Airplane object.
	 * @param seatType
	 *            The seat type of the checked for seat. Can be either ECONOMY_SEAT
	 *            or BUSINESS_SEAT.
	 * @return true if a matching Airplane has available seats and false otherwise.
	 */
	public boolean hasAvailibleSeat(String airplane, SeatType seatType) {

		for (int i = 0; i < airplanes.size(); i++) {
			if (airplane.equals(airplanes.get(i).toString())) {
				if (airplanes.get(i).getNumberOfAvaiableSeats(seatType) > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks which Airplane objects are available for flight.
	 * 
	 * @return an ArrayList of toString results for each available airplane.
	 */
	public ArrayList<String> getAvailibleFlights() {
		ArrayList<String> availibleFlightsList = new ArrayList<String>();
		for (int i = 0; i < airplanes.size(); i++) {
			if (airplanes.get(i).getNumberOfAvaiableSeats(SeatType.BUSINESS_SEAT) > 0
					|| airplanes.get(i).getNumberOfAvaiableSeats(SeatType.ECONOMY_SEAT) > 0) {
				availibleFlightsList.add(airplanes.get(i).toString());
			}
		}
		return availibleFlightsList;
	}

	/**
	 * Attempts to add a reservation to one Airplane.The foodMap parameter should
	 * consist of a map between FoodItems and the desired number of that FoodItem.
	 * Returns Booking ID if successful. Returns a negative number if the
	 * reservation failed.
	 * 
	 * @param airplane
	 *            a String equal to the toString result for the desired Airplane object.
	 * @param name
	 *            the name of the one making the reservation.
	 * @param seatType
	 *            the seat type of the reservation. Can be either ECONOMY_SEAT or
	 *            BUSINESS_SEAT.
	 * @param foodMap
	 *            a map between FoodItems and the ordered number of that FoodItem.
	 * @return the booking ID (which is a positive integer) if successful and a
	 *         negative number if the reservation failed.
	 */
	public int addReservation(String airplane, String name, SeatType seatType, Map<FoodItem, Integer> foodMap) {
		int bookedSeat;
		for (int i = 0; i < airplanes.size(); i++) {
			if (airplane.equals(airplanes.get(i).toString())) {
				bookedSeat = airplanes.get(i).bookSeat(seatType);

				// bookedSeat being negative means that there was no such available seat on the
				// plane
				if (bookedSeat < 0) {
					return -1;
				} else {
					double cost = getSeatPrice(seatType);
					for (Map.Entry<FoodItem, Integer> entry : foodMap.entrySet()) {
						cost += entry.getKey().getPrice() * entry.getValue();
					}

					Booking newBooking = new Booking(bookings.size(), name, airplanes.get(i).getLocation(),
							airplanes.get(i).getDestination(), airplanes.get(i).getPlaneName(), cost, bookedSeat,
							seatType, foodMap);
					bookings.add(newBooking);
					return bookings.size() - 1; // -1 because a new element is added to bookings in the row above
				}
			}
		}
		return -1;
	}

	/**
	 * Attempts to remove a reservation.
	 * 
	 * @param bookingID
	 *            the booking ID of the reservation to be removed.
	 * @return true if the reservation was found and removed. False if the
	 *         reservation wasn't found.
	 */
	public boolean removeReservation(int bookingID) {
		// booking ID is one larger than the index for the booking.
		if (bookingID < 0 || bookingID > bookings.size() - 1) {
			return false;
		}
		bookings.remove(bookingID);
		return true;
	}

	/**
	 * Returns a menu corresponding to a seat type.
	 * 
	 * @param seatType
	 *            the seat type for the sought menu. Can be either ECONOMY_SEAT or
	 *            BUSINESS_SEAT.
	 * @return an array of FoodItems.
	 */
	public FoodItem[] getMenu(SeatType seatType) {
		return Menus.getMenuCopy(seatType);
	}

	/**
	 * Calculates the total income of the AirlineManager.
	 * 
	 * @return a double containing the calculated income.
	 */
	public double getIncome() {
		double income = 0;
		for (Booking booking : bookings) {
			income += booking.getCost();
		}
		return income;
	}

	/**
	 * Calculates the total profit of the AirlineManager. The profit is equal to 30%
	 * of the AirlineManager's income.
	 * 
	 * @return a double containing the calculated profit.
	 */
	public double getProfit() {
		double profit = getIncome();
		profit = profit * 0.3; // 30% of the income is profit
		return profit;
	}

	/**
	 * Returns the price of a seat.
	 * @param seatType the seat type for which to get the price. Can be either ECONOMY_SEAT or
	 *            BUSINESS_SEAT.
	 * @return a double containing the price of the seat.
	 */
	public double getSeatPrice(SeatType seatType) {
		return SeatPrices.getPricesForSeatType(seatType);
	}

}
