package se.lexicon.application;

import se.lexicon.ui.UI;
import utilities.ExampleAirlineManagerCreator;

public class Main {

    public static void main(String[] args) {
        
        //Starting the user interface
        UI ui = new UI();
        ui.callMainMenu(ExampleAirlineManagerCreator.createExampleAirlineManager());

    }
}
