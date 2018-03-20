package utilities;

import java.util.ArrayList;
import java.util.Map;

import se.lexicon.model.Airplane;
import se.lexicon.model.Booking;
import se.lexicon.model.FoodItem;

public class AirlineManager {

	ArrayList<Airplane> airplanes;
	ArrayList<Booking> bookings;

	/*
	 * Adds an airplane to the airline's list of airplanes.
	 */
	public void addAirplane(Airplane airplane) {
		airplanes.add(airplane);
	}

	/*
	 * Removes the airplane corresponding to airplaneIndex from the airline's list
	 * of airplanes. Returns true if successful and returns false if no plane
	 * corresponding to airplaneIndex was found.
	 */
	public boolean removeAirplane(int airplaneIndex) {
		try {
			airplanes.remove(airplaneIndex);
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	/*
	 * Returns true if an airplane (for which the toString method returns a String
	 * equal to the given airplane argument) has at least one available seat of
	 * seatType. Returns false if no such free seat exists or if no such airplane is
	 * found.
	 */
	public boolean hasAvailibleSeat(String airplane, SeatType seatType) {

		for (int i = 0; i < airplanes.size(); i++) {
			if (airplane.equals(airplanes.get(i))) {
				if (airplanes.get(i).getNumberOfAvaiableSeats(SeatType.BUISNESS_SEAT) > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Returns an ArrayList of toStrings for each available airplane
	 */
	public ArrayList<String> getAvailibleFlights() {
		ArrayList<String> availibleFlightsList = new ArrayList<String>();
		for (int i = 0; i < airplanes.size(); i++) {
			if (airplanes.get(i).getNumberOfAvaiableSeats(SeatType.BUISNESS_SEAT) > 0
					&& airplanes.get(i).getNumberOfAvaiableSeats(SeatType.ECONOMY_SEAT) > 0) {
				availibleFlightsList.add(airplanes.get(i).toString());
			}
		}
		return availibleFlightsList;
	}

	/*
	 * Attempts to add a reservation for one airplanes for which toString returns a
	 * String equal to the given first String argument. Returns Booking ID if
	 * successful. Returns a negative number if the reservation failed.
	 */
	public int addReservation(String airplane, String name, SeatType seatType, Map<FoodItem, Integer> foodMap) {
		int bookedSeat;
		for (int i = 0; i < airplanes.size(); i++) {
			if (airplane.equals(airplanes.get(i))) {
				bookedSeat = airplanes.get(i).bookSeat(seatType);

				// bookedSeat being negative means that there was no such available seat on the
				// plane
				if (bookedSeat < 0) {
					return -1;
				} else {
					double cost = getSeatPrice(seatType);
					for(Map.Entry<FoodItem, Integer> entry : foodMap.entrySet()) {
						cost += entry.getKey().getPrice() * entry.getValue();
					}
					
					Booking newBooking = new Booking(bookings.size(), name, airplanes.get(i).getLocation(),
							airplanes.get(i).getDestination(), airplanes.get(i).getPlaneName(), cost, bookedSeat, seatType, foodMap);
					bookings.add(newBooking);
					return bookings.size() - 1; // -1 because a new element is added to bookings in the row above
				}
			}
		}
		return -1;
	}

	/*
	 * Removes a reservation corresponding to the booking ID. Returns true if the
	 * reservation was found and removed. Returns false if the reservation wasn't
	 * found.
	 */
	public boolean removeReservation(int bookingID) {
		//booking ID is one larger than the index for the booking.
		if(bookingID < 0 || bookingID > bookings.size() + 1) {
			return false;
		}
		bookings.remove(bookingID - 1);
		return true;
	}

	/*
	 * Returns an array of the FoodItems for the menu corresponding to seatType.
	 */
	public FoodItem[] getMenu(SeatType seatType) {
		if(seatType == SeatType.BUISNESS_SEAT) {
			return Menus.getBusinessMenuCopy();
		}
		else {
			return Menus.getEconomyMenuCopy();
		}
	}

	/*
	 * Returns the total income of the airline.
	 */
	public double getIncome() {
		double income = 0;
		for(Booking booking : bookings) {
			income += booking.getCost();
		}
		return income;
	}
	
	/*
	 * Returns the profit made by the airline.
	 */
	public double getProfit() {
		double profit = getIncome();
		profit = profit * 0.3; //30% of the income is profit
		return profit;
	}

	/*
	 * Returns the price of a seat corresponding to seatType.
	 */
	public double getSeatPrice(SeatType seatType) {
		if (seatType == SeatType.BUISNESS_SEAT) {
			return 20000;
		} else {
			return 5000;
		}
	}

}
