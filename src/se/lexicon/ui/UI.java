package se.lexicon.ui;


import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import se.lexicon.exception.AirlineAlreadyExistsException;
import se.lexicon.exception.AirlineIsFullException;
import se.lexicon.exception.AirlineNotFoundException;
import se.lexicon.model.FlightManager;



public class UI extends AirlineManager {



	static UI conAirHandler = new UI();
	static FlightManager flightManager = new FlightManager();

	protected String reservationID = null;
	protected int capacity = 0;
	static int response = 0;
	static String[] options = null;
	boolean choice = true;

	protected String name = null;
	protected String email = null;
	protected String adress = null;
	protected String airlineName = null;
	protected String theAirlineName = null;
	protected Booking reservation = null;
	protected Airline airline = null;
	protected Main menu = new Main();

	public UI() {
	}


	public void callMainMenu()
	{
		String[] options = new String[] {"Reservation menu", "Food menu", "Close"};
		while(choice = true) {

			response = JOptionPane.showOptionDialog(null, "What would menu would you like to enter", "Main menu",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, options, options[0]);

			switch (response) {
			// Open airline menu
			case 0: callReservationMenu();
			break;
			// Open reservation menu
			case 1: callFoodMenu();
			break;
			// Exit application
			case 2: return;
			break;
			}
		}


	}


	public void callFoodMenu() {
		conAirHandler.orderFood();

	}

	/**
	 * Reservation menu method
	 */
	public void callReservationMenu()
	{
		options = new String[] {"Reserve seat", "Remove seat", "TEMP: List passengers", "Get profit", "Go back"};

		while(choice = true) {


			response = JOptionPane.showOptionDialog(null, "What would you like to do?", "Reservation menu",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, options, options[0]);

			switch (response) {
			// Calls the reserve method
			case 0: conAirHandler.addReservation();
			break;
			// Remove specific reservation
			case 1: conAirHandler.removeReservation();
			break;
			// Calls the print airlines profit method
			case 2:	conAirHandler.getProfit();
			break;
			// Back to main menu
			case 3:	return;
			break;
			}
		}
	}












	private void getProfit() {

		System.out.println("The total profit is: " + airline.getProfit());
	}


	/**
	 * Prompts the user for inputs and adds an airline
	 */

	public void addAirplane()
	{
		try
		{
			theAirlineName = JOptionPane.showInputDialog("What's the name of the airline?");
			//capacity = 10 - 1;

			for(Airplane airplane : airline.getAirplaneList())
			{
				if(airplane.getName().equalsIgnoreCase(theAirlineName)) {

					airline.getAirplaneList().indexOf(airplane);
				}
			}


		}
		catch(AirlineAlreadyExistsException e)
		{
			System.out.println("airline already exists");
		}
	}
	/**
	 * Prompts the user for inputs and removes an airline
	 */
	public void removeAirline()
	{

			theAirlineName = JOptionPane.showInputDialog("What's the name of the airline you would like to remove?");
			//capacity = 10 - 1;

			for(Airplane airplane : airline.getAirplaneList())
			{
				if(airplane.getName().equalsIgnoreCase(theAirlineName)) {
					boolean isRemoved = airline.removeAirplane( airline.getAirplaneList().indexOf(airplane) );
					if (isRemoved = false) {
						System.out.println("Airline does not exist");
					}
					else {
						System.out.println("Airline was removed");
					}

				}
			}

	}
	/**
	 * Prompts the user for inputs and removes the passenger with the specified name
	 */
	public void removeReservation()
	{
			reservationID = JOptionPane.showInputDialog("What's the ID of the passenger you would like to remove?");
			Boolean isBooked = super.removeReservation(reservationID);

			if (isBooked = false) {
				System.out.println("Reservation was not found");
			}
			else {
				System.out.println("Reservation removed");
			}

	}

	/**
	 * Prompts the user for inputs and creates reserves a seat
	 */
	public void addReservation()
	{
		try {

			JTextField name = new JTextField();

			// destination , location, seattype, cost, map extras name
			//ArrayList<String> availableLocation = getAvailibleFlights();
			ArrayList<String> locationList;
			ArrayList<String> destinationList;

			// Name, Location, Destination in combobox

			ArrayList<String> availableFlights = airline.getAvailibleFlights();


			for(Airplane airplane : airline.getAirplaneList())
			{
				if (airplane.flying = false) {

					locationList.add( airplane.getName() + " " + airplane.getLocation() + " " + airplane.getDestination() );

					destinationList.add(airplane.getDestination());
				}
			}


			String[] availableLocations = locationList.toArray(new String[locationList.size()]);
			String[] availableDestinations = destinationList.toArray(new String[destinationList.size()]);
			JTextField theAirlineName = new JTextField();
			String[] currentClass={"Business","Economy"};
		    JComboBox cmbClass = new JComboBox(currentClass);
		    JComboBox cmbLocations = new JComboBox(availableLocations);
		    JComboBox cmbDestinations = new JComboBox(availableDestinations);




		    // TODO: Fix so that locations and destinations appear based on which flight they select


			Object[] message = {
				    "Name:", name,
				    "Location:", cmbLocations,
				    "Destination:", cmbDestinations,
				    "Airline:", theAirlineName,
				    "Seat type:", cmbClass
				};
			if(name.getText() == null || cmbLocations.getSelectedItem() == null || cmbDestinations.getSelectedItem() == null)
			{
				throw new NullPointerException();
			}
		//	int option = new JOptionPane().showConfirmDialog(null, message, "Seat", JOptionPane.OK_CANCEL_OPTION);
			int option = JOptionPane.showConfirmDialog(null, message, "Reserve seat", JOptionPane.OK_CANCEL_OPTION);
		//	reservation = new Booking(name.getText(), cmbLocations.getSelectedItem(), cmbDestinations.getSelectedItem(), cmbClass.getSelectedItem(), cost , map);
			super.addReservation(name.getText(), cmbLocations.getSelectedItem(), cmbDestinations.getSelectedItem(), cmbClass.getSelectedItem(), airline.getSeatPrice(SeatType.valueOf(cmbClass.getSelectedItem().toUpperCase(Locale.ENGLISH)) ) , map);
		//	super.reserveSeat(reservation, theAirlineName.getText());
		}
		catch (AirlineIsFullException e)
		{
			System.out.println("\n" +  "The airline is full \n ");
			return;
		}
		catch (NullPointerException e)
		{
			System.out.println("\n" + "Please input a correct value \n");
			return;
		}
		catch (AirlineNotFoundException e)
		{
			System.out.println("\n" + "Airline was not found \n");
			return;
		}
	}

	public void orderFood() {

		String[] foodArray = airline.getFoodArray();
		JTextField foodName = new JTextField();
		JTextField foodQuantity = new JTextField();
		String foodString;
			for (Food food : airline.getFoodMap().values()) {
		//		JTextField food.getName() = new JTextField;



				Object[] message = {



					    food.getName(), foodName,
					};
			}





			Object[] message = new Object[airline.getFoodArray().length];

			int i = 0;
			for (int i = 0; i < airline.getFoodArray().length; i++){
				foodString  = food.getName();
				message[i] = airline.getFoodArray()[i].getName() + "," + " " + foodString;
			}


		// Leta i food collection sedan lägg till ett text fält för varje item i listan

		// Map<FoodItem, Integer>

		int option = JOptionPane.showConfirmDialog(null, message, "Food menu", JOptionPane.OK_CANCEL_OPTION);

	}

























}
