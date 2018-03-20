package utilities;

import java.util.ArrayList;

import se.lexicon.model.Airplane;
import se.lexicon.model.Booking;

public class AirlineManager {
	
	ArrayList<Airplane> airplanes;
	ArrayList<Booking> bookings;
	
	public void addAirplane(Airplane airplane) {
		airplanes.add(airplane);
	}
}
