package utilities;

public class SeatPrices {

	public static double getPricesForSeatType(SeatType seatType) {
		if (seatType == SeatType.BUISNESS_SEAT) {
			return 20000;
		} else {
			return 5000;
		}
	}
}
