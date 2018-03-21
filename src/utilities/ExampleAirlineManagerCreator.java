package utilities;

import se.lexicon.model.Airplane;

public class ExampleAirlineManagerCreator {

	/*
	 * Returns an AirlineManager with some added airplanes.
	 */
	public static AirlineManager createExampleAirlineManager() {
        Airplane airplane1 = new Airplane(5, 5, "Cessna Caravan", "Stockholm", "Oslo");
        Airplane airplane2 = new Airplane(20, 10, "SAAB 340", "Berlin", "Paris");
        Airplane airplane3 = new Airplane(500, 100, "Airbuss", "Kopenhagen", "London");
        AirlineManager airlineManager = new AirlineManager();
        airlineManager.addAirplane(airplane1);
        airlineManager.addAirplane(airplane2);
        airlineManager.addAirplane(airplane3);

        return airlineManager;
	}

}
