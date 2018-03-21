package utilities;

public class SeatPrices {

	public static double getPricesForSeatType(SeatType seatType) {
		if (seatType == SeatType.BUSINESS_SEAT) {
			return 20000;
		} else {
			return 5000;
		}
	}
}
