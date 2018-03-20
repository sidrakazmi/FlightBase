package se.lexicon.application;

import se.lexicon.ui.SystemUI;
import utilities.AirlineManager;

public class Main {

    public static void main(String[] args) {

    	// Hard coded airplanes for the AirlineManager.
        Airplane airplane1 = new Airplane(5, 5, "Stockholm", "Oslo", "Cessna Caravan");
        Airplane airplane2 = new Airplane(20, 10, "Berlin", "Paris", "SAAB 340");
        Airplane airplane3 = new Airplane(500, 100, "Kopenhagen", "London", "Airbuss");
        AirlineManager airlineManager = new AirlineManager();
        airlineManager.addAirplane(airplane1);
        airlineManager.addAirplane(airplane2);
        airlineManager.addAirplane(airplane3);
        
        //Starting the user interface
        UI ui = new UI();
        ui.start(airlineManager);

    }
}
