package se.lexicon.utilities;

/**
 * Provides a static method for getting seat prices
 * 
 * @author Linus Karlbom
 *
 */
public class SeatPrices {

	/**
	 * Returns a double containing the price for a seat. The price is 20000 for
	 * business seats and 5000 for economy seats.
	 * 
	 * @param seatType
	 *            The seat type for which to check the price. Can be either
	 *            ECONOMY_SEAT or BUSINESS_SEAT.
	 * @return the price for the specified seat type.
	 */
	public static double getPricesForSeatType(SeatType seatType) {
		if (seatType == SeatType.BUSINESS_SEAT) {
			return 20000;
		} else {
			return 5000;
		}
	}
}
