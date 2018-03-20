package utilities;

import se.lexicon.model.Airplane;

public class ExampleAirlineManagerCreator {

	/*
	 * Returns an AirlineManager with some added airplanes.
	 */
	public static AirlineManager createExampleAirlineManager() {
        Airplane airplane1 = new Airplane(5, 5, "Stockholm", "Oslo", "Cessna Caravan");
        Airplane airplane2 = new Airplane(20, 10, "Berlin", "Paris", "SAAB 340");
        Airplane airplane3 = new Airplane(500, 100, "Kopenhagen", "London", "Airbuss");
        AirlineManager airlineManager = new AirlineManager();
        airlineManager.addAirplane(airplane1);
        airlineManager.addAirplane(airplane2);
        airlineManager.addAirplane(airplane3);
        
        return airlineManager;
	}
	
}
