package se.lexicon.ui;


import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import se.lexicon.model.Booking;
import se.lexicon.model.FoodItem;
import utilities.AirlineManager;
import utilities.SeatType;



public class UI extends AirlineManager {



	static UI conAirHandler = new UI();


	protected int reservationID;
	protected int capacity = 0;
	static int response = 0;
	static String[] options = null;


	protected String name = null;
	protected String email = null;
	protected String adress = null;
	protected String airlineName = null;
	protected String theAirlineName = null;
	protected Booking reservation = null;
	protected AirlineManager airlineManager = null;




	public UI() {
	}


	public void callMainMenu(AirlineManager airlineManager)
	{

		boolean choice = true;
		this.airlineManager = airlineManager;
		String[] options = new String[] {"Reservation menu", "Close"};
		while(choice == true) {

			response = JOptionPane.showOptionDialog(null, "What would menu would you like to enter", "Main menu",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, options, options[0]);

			switch (response) {
			// Open reservation menu
			case 0: callReservationMenu();
			break;
			// Exit application
			case 1: choice = false;
			}
		}


	}


	/**
	 * Reservation menu method
	 */
	public void callReservationMenu()
	{

		boolean choice = true;
		options = new String[] {"Reserve seat", "Remove seat", "TEMP: List passengers", "Get profit", "Go back"};

		while(choice == true) {


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
			case 2:	System.out.println("The total profit is: " + airlineManager.getProfit());
			break;
			// Back to main menu
			case 3:	choice = false;
			break;
			}
		}
	}












	@Override
	public double getProfit() {

		return capacity;
	}
	/**
	 * Prompts the user for inputs and removes the passenger with the specified name
	 */
	public void removeReservation()
	{
		reservationID = Integer.parseInt(JOptionPane.showInputDialog("What's the ID of the passenger you would like to remove?"));
		Boolean isBooked = super.removeReservation(reservationID);


		if (isBooked == false) {
			JOptionPane.showMessageDialog(null, "Reservation was not found");
		}
		else {
			JOptionPane.showMessageDialog(null, "Reservation removed");
		}

	}

	/**
	 * Prompts the user for inputs and creates reserves a seat
	 */
	public void addReservation()
	{


			JTextField name = new JTextField();
			ArrayList<String> locationList = null;
			SeatType seatType = null;
			// Name, Location, Destination in combobox
			ConcurrentSkipListMap<FoodItem, Integer> foodMap;
			boolean choice = true;
			String[] availableLocations = locationList.toArray(new String[locationList.size()]);
			String[] currentClass={"Business","Economy"};
			String selectedFlights;
			JComboBox cmbClass = new JComboBox(currentClass);
			JComboBox cmbAvailableFlights = new JComboBox(availableLocations);


			// name: Stockholm->Oslo



			for(String airplane : airlineManager.getAvailibleFlights())
			{
				//	locationList.add( airplane.getName() + ": " + airplane.getLocation() + "->" + airplane.getDestination() );
				locationList.add( airplane.toString() );
			}




			Object[] message = {
					"Name:", name,
					"Flights:", cmbAvailableFlights,
					"Seat type:", cmbClass
			};
			while (choice == true) {
				int option = JOptionPane.showConfirmDialog(null, message, "Reservation", JOptionPane.OK_CANCEL_OPTION);
				if (option == 0) {
					// Asks if you would like to order food if yes do nothing and continue with the method
					try {
						// If name or selected flights box is empty throw an expection and return to the menu
						if(name.getText() == null || cmbAvailableFlights.getSelectedItem() == null)
						{
							throw new NullPointerException();
						}
						else {
							choice = false;
						}
					}
					catch (NullPointerException e)
					{
						JOptionPane.showMessageDialog(null, "Please input a correct value");
					}
				}
				else if (option == 1) {
					// Returns to menu
					return;
				}
				else {
					// Returns to menu
					return;
				}

			}






			// Do you want to order food? YES NO?

			int reply = JOptionPane.showConfirmDialog(null, "Do you want to order food?", "Order food", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				// Open food menu based on what class the seat was selected
				if (String.valueOf(cmbClass.getSelectedItem()).toUpperCase(Locale.ENGLISH).equals("BUSINESS")) {
					seatType = SeatType.BUISNESS_SEAT;
				}
				else if(String.valueOf(cmbClass.getSelectedItem()).toUpperCase(Locale.ENGLISH).equals("ECONOMY")) {
					seatType = SeatType.ECONOMY_SEAT;
				}
				foodMap = orderFood(seatType);
			}
			else {
				// Finish reservation without ordering
				// TODO if user does not want to order food add reservation with empty foodMap
				return;
			}

			selectedFlights = String.valueOf(cmbAvailableFlights.getSelectedItem());

			// airlineManager.getSeatPrice(SeatType.valueOf(cmbClass.getSelectedItem().toUpperCase(Locale.ENGLISH)))
			seatType = SeatType.valueOf(String.valueOf(cmbClass.getSelectedItem()));





			// Adds reservation

			int canBeReserved = super.addReservation(selectedFlights.substring(0, selectedFlights.indexOf(' ')), name.getText(), seatType, foodMap);


			if (canBeReserved < 0) {
				JOptionPane.showMessageDialog(null, "The airline is full");
				return;
			}
			if (canBeReserved >= 0) {
				JOptionPane.showMessageDialog(null, "Reservation was added");
				return;
			}


	}


	public ConcurrentSkipListMap<FoodItem, Integer> orderFood(SeatType seatType) {

		// Creates text fields
		boolean choice = true;
		FoodItem[] foodArray = airlineManager.getMenu(seatType);

		Object[] message = new Object[foodArray.length * 2];
		JTextField[] foodFieldNames = new JTextField[foodArray.length];
		for (int i = 0; i < message.length; i+=2){
			// Divides the index used for foodFieldNames with two because the for loop increments with two each time.
			foodFieldNames[i/2]  = new JTextField();
			message[i] = foodArray[i].getName() + " , " + foodArray[i].getPrice() + " SEK";
			message[i + 1] = i;
		}


		// Loop will run forever until the user gives valid input.
		while (choice == true) {

			int option = JOptionPane.showConfirmDialog(null, message, "Order food", JOptionPane.OK_CANCEL_OPTION);
			//TODO check that 0 really is this
			if (option == 0) {
				ConcurrentSkipListMap<FoodItem, Integer> foodMap = new ConcurrentSkipListMap<>();
				try {
					addFoodItemsToMap(foodMap, message, foodArray);
				}
				catch ( NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Must input a valid number");
				}
				return foodMap;

			}

		}
		return null;




		// For each textfield add to map
		// Map<FoodItem, Integer> foodMap




	}


	public void addFoodItemsToMap(ConcurrentSkipListMap<FoodItem, Integer> foodMap, Object[] message, FoodItem[] foodArray) throws NumberFormatException {


		for (int i = 0; i < message.length; i+=2){
			// Goes through each textfield created and adds value to map
			String quantityString = String.valueOf(((JComboBox) message[i + 1]).getSelectedItem() );
			if (quantityString == null) {
				quantityString = "0";
			}
			Integer quantity = Integer.parseInt(quantityString);

			if ( quantity < 0 ) {
				throw new NumberFormatException();
			}

			foodMap.put(foodArray[i],  quantity);


		}



	}





















}
