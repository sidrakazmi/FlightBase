package se.lexicon.model;

import utilities.SeatType;

/**
 * A class, the objects of which represents airplane.
 * 
 * @author Sidra Ali Kazmi
 * @author Linus Karlbom
 */
public class Airplane {

	private boolean isFlying;

	private int maxBusinessClassSeat;
	private int maxEconomyClassSeat;

	private int noOfReservedBussinessSeats;
	private int noOfReservedEconomySeats;

	private String planeName;
	private String location;
	private String destination;

	/**
	 * 
	 * Constructs an Airplane object using the provided parameters
	 * 
	 * @param maxBusinessClassSeat
	 *            the number of business class seats for the Airplane object.
	 * @param maxEconomyClassSeat
	 *            the number of economy class seats for the Airplane object.
	 * @param planeName
	 *            the name of the Airplane object.
	 * @param location
	 *            the location from which the Airplane object will fly.
	 * @param destination
	 *            the destination to which the Airplane object will fly.
	 */
	public Airplane(int maxBussinessClassSeat, int maxEconomyClassSeat, String planeName, String location,
			String destination) {

		this.maxBusinessClassSeat = maxBussinessClassSeat;
		this.maxEconomyClassSeat = maxEconomyClassSeat;
		this.planeName = planeName;
		this.location = location;
		this.destination = destination;
		noOfReservedBussinessSeats = 0;
		noOfReservedEconomySeats = 0;
		isFlying = false;
	}

	/**
	 * Checks if the Airplane object is flying.
	 * 
	 * @return true if the Airplane is flying, false otherwise.
	 */
	public boolean isIsflying() {
		return isFlying;
	}

	/**
	 * @return the name of the Airplane object.
	 */
	public String getPlaneName() {
		return planeName;
	}

	/**
	 * @return the location the Airplane object will fly from.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the destination of the Airplane object will fly to.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @return a String containing the name, location and destination of the
	 *         Airplane object.
	 */
	@Override
	public String toString() {
		return planeName + ": " + location + "->" + destination;
	}

	/**
	 * Calculates the number of available seats for the Airplane object.
	 * 
	 * @param seatType
	 *            the type of seat to check for. Can be either ECONOMY_SEAT or
	 *            BUSINESS_SEAT.
	 * @return the calculated number of available seats.
	 */
	public int getNumberOfAvaiableSeats(SeatType seatType) {
		if (seatType == SeatType.BUSINESS_SEAT) {
			int noOfAvaiableBussinessSeats = maxBusinessClassSeat - noOfReservedBussinessSeats;
			return noOfAvaiableBussinessSeats;
		} else {
			if (seatType == SeatType.ECONOMY_SEAT) {
				int noOfAvaiableEconomySeats = maxEconomyClassSeat - noOfReservedEconomySeats;
				return noOfAvaiableEconomySeats;
			} else
				return -1;
		}

	}

	/**
	 * Attempt to reserve a seat. Succeeds if an available seat of the right seat
	 * type exists.
	 * 
	 * @param the
	 *            type of seat to book for. Can be either ECONOMY_SEAT or
	 *            BUSINESS_SEAT.
	 * @return seat number(which is positive or zero) if the reservation was
	 *         successful. Returns a negative number if the reservation failed.
	 */
	public int bookSeat(SeatType seatType) {

		if (seatType == SeatType.BUSINESS_SEAT && noOfReservedBussinessSeats < maxBusinessClassSeat)

		{
			noOfReservedBussinessSeats++;
			takeFlightIfThePlaneIsFull();
			return noOfReservedBussinessSeats;
		}

		else if (seatType == SeatType.ECONOMY_SEAT && noOfReservedEconomySeats < maxEconomyClassSeat) {
			noOfReservedEconomySeats++;
			takeFlightIfThePlaneIsFull();
			return noOfReservedEconomySeats;
		}

		else
			return -1;

	}

	/**
	 * Attempts to remove a reservation. Succeeds if there is a reservation of the
	 * right seat type to remove.
	 * 
	 * @param seatType
	 *            the type of the reservation to remove. Can be either ECONOMY_SEAT
	 *            or BUSINESS_SEAT.
	 * @return true if a reservation was removed; false otherwise.
	 */
	public boolean removeReservation(SeatType seatType) {
		if (seatType == SeatType.BUSINESS_SEAT && noOfReservedBussinessSeats > 0)

		{
			noOfReservedBussinessSeats--;
			return true;
		}

		else if (seatType == SeatType.ECONOMY_SEAT && noOfReservedEconomySeats > 0) {
			noOfReservedEconomySeats--;
			return true;
		}

		else
			return false;

	}

	/**
	 * Checks if all the seats in Economy and Business class are reserved. If all of them are reserved, sends the
	 * Airplane off to flight for two minutes and prints some messages about this process. Removes all seat reservations after the two minutes have passed.
	 */
	public void takeFlightIfThePlaneIsFull() {
		if (noOfReservedBussinessSeats == maxBusinessClassSeat && noOfReservedEconomySeats == maxEconomyClassSeat) {
			isFlying = true;
			Airplane thisAirplane = this;

			Thread thread1 = new Thread(() -> {
				System.out.println(thisAirplane.getPlaneName() + " takes off");
				System.out.println(thisAirplane.getPlaneName() + " is flying");
				try {
					Thread.sleep(120000);
				} catch (InterruptedException e) {
					// Ignore
				}
				synchronized (thisAirplane) {
					thisAirplane.arrive();
				}
				System.out.println(thisAirplane.getPlaneName() + " has arrived");
				System.out.println(thisAirplane.getPlaneName() + " has refueled");
			});
			thread1.start();

		}

	}

	/**
	 * Removes all reservations for the Airplane object and sets its isFlying status to false.
	 */
	private void arrive() {
		noOfReservedBussinessSeats = 0;
		noOfReservedEconomySeats = 0;
		isFlying = false;
	}
}
