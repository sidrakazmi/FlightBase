package se.lexicon.ui;


import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import se.lexicon.exception.AirlineIsFullException;
import se.lexicon.exception.AirlineNotFoundException;
import se.lexicon.model.Booking;
import utilities.AirlineManager;



public class UI extends AirlineManager {



	static UI conAirHandler = new UI();


	protected int reservationID = null;
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
	protected AirlineManager airlineManager = null;

	protected Main menu = new Main();


	public UI() {
	}


	public void callMainMenu(AirlineManager airlineManager)
	{
		this.airlineManager = airlineManager;
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












	@Override
	public double getProfit() {

		System.out.println("The total profit is: " + airlineManager.getProfit());
	}
	/**
	 * Prompts the user for inputs and removes the passenger with the specified name
	 */
	public void removeReservation()
	{
			reservationID = Integer.parseInt(JOptionPane.showInputDialog("What's the ID of the passenger you would like to remove?"));
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
			ArrayList<String> locationList;
			ArrayList<String> destinationList;

			// Name, Location, Destination in combobox

			ArrayList<String> availableFlights = airlineManager.getAvailibleFlights();




			// name: Stockholm->Oslo



			for(String airplane : airlineManager.getAvailibleFlights())
			{
				//	locationList.add( airplane.getName() + ": " + airplane.getLocation() + "->" + airplane.getDestination() );
				locationList.add( airplane.toString() );
			}


			String[] availableLocations = locationList.toArray(new String[locationList.size()]);
			JTextField theAirlineName = new JTextField();
			String[] currentClass={"Business","Economy"};
		    JComboBox cmbClass = new JComboBox(currentClass);
		    JComboBox cmbAvailableFlights = new JComboBox(availableLocations);



		    // Open food menu based on what class the seat is
		    // public FoodItem[] getMenu(SeatType seatType)





			Object[] message = {
				    "Name:", name,
				    "Flights:", cmbAvailableFlights,
				    "Seat type:", cmbClass
				};
			if(name.getText() == null || cmbAvailableFlights.getSelectedItem() == null)
			{
				throw new NullPointerException();
			}
		//	int option = new JOptionPane().showConfirmDialog(null, message, "Seat", JOptionPane.OK_CANCEL_OPTION);
			int option = JOptionPane.showConfirmDialog(null, message, "Reserve seat", JOptionPane.OK_CANCEL_OPTION);
		//	reservation = new Booking(name.getText(), cmbLocations.getSelectedItem(), cmbDestinations.getSelectedItem(), cmbClass.getSelectedItem(), cost , map);
			super.addReservation(name.getText(), cmbAvailableFlights.getSelectedItem(), cmbDestinations.getSelectedItem(), cmbClass.getSelectedItem(), airlineManager.getSeatPrice(SeatType.valueOf(cmbClass.getSelectedItem().toUpperCase(Locale.ENGLISH)) ) , map);
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

		String[] foodArray = airlineManager.getFoodArray();
		JTextField foodName = new JTextField();
		JTextField foodQuantity = new JTextField();
		String foodString;
			for (Food food : airlineManager.getFoodMap().values()) {
		//		JTextField food.getName() = new JTextField;



				Object[] message = {



					    food.getName(), foodName,
					};
			}





			Object[] message = new Object[airlineManager.getFoodArray().length];

			int i = 0;
			for (int i = 0; i < airlineManager.getFoodArray().length; i++){
				foodString  = food.getName();
				message[i] = airlineManager.getFoodArray()[i].getName() + "," + " " + foodString;
			}


		// Leta i food collection sedan lägg till ett text fält för varje item i listan

		// Map<FoodItem, Integer>

		int option = JOptionPane.showConfirmDialog(null, message, "Food menu", JOptionPane.OK_CANCEL_OPTION);

	}

























}
