package se.lexicon.application;

import se.lexicon.ui.UI;
import se.lexicon.utilities.ExampleAirlineManagerCreator;

/**
 * Provides an entry point for the FlightBase program.
 * 
 * @author Linus Karlbom
 * @author Daniel Axelsson
 *
 */
public class Main {

	/**
	 * Creates an UI object and calls its callMainMenu with a created
	 * AirlineManager.
	 * 
	 * @param args
	 *            are not used.
	 */
	public static void main(String[] args) {

		// Starting the user interface
		UI ui = new UI();
		ui.callMainMenu(ExampleAirlineManagerCreator.createExampleAirlineManager());

	}
}
