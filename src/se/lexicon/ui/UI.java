package se.lexicon.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import se.lexicon.model.Booking;
import se.lexicon.model.FoodItem;
import utilities.AirlineManager;
import utilities.SeatType;



public class UI {


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

	/**
	 * Reservation menu method
	 */
	public void callMainMenu(AirlineManager airlineManager)
	{

		this.airlineManager = airlineManager;
		boolean choice = true;
		options = new String[] {"Reserve seat", "Remove reservation", "Get income", "Get profit", "Exit"};

		while(choice == true) {


			response = JOptionPane.showOptionDialog(null, "What would you like to do?", "Main menu",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, options, options[0]);

			switch (response) {
			// Calls the reserve method
			case 0: addReservation();
			break;
			// Remove specific reservation
			case 1: removeReservation();
			break;
			// Calls and displays the airlines income method
			case 2:JOptionPane.showMessageDialog(null, "The total income is: " + airlineManager.getIncome() + " SEK", "Income", JOptionPane.INFORMATION_MESSAGE);
			break;
			// Calls and displays the airlines profit method
			case 3:	JOptionPane.showMessageDialog(null, "The total profit is: " + airlineManager.getProfit() + " SEK", "Profit", JOptionPane.INFORMATION_MESSAGE);
			break;
			// Back to main menu
			case 4: choice = false;
			break;
			default: return;
			}
		}
	}


	/**
	 * Prompts the user for inputs and removes the passenger with the specified name
	 */
	public void removeReservation()
	{
		boolean choice = true;

		while (choice == true) {
			String option = JOptionPane.showInputDialog(null, "What's the ID of the passenger you would like to remove?", "Remove Reservation", JOptionPane.INFORMATION_MESSAGE);

			if (option == null) {
				//Leave without doing anything if cancel is pressed.
				choice = false;
			}
			else if(option.equals("")) {
				JOptionPane.showMessageDialog(null, "Please input a valid number.");
			}
			else{
				try {
					reservationID = Integer.parseInt(option);
					reservationID = Integer.parseInt(option);
					Boolean isBooked = airlineManager.removeReservation(reservationID);


					if (isBooked == false) {
						JOptionPane.showMessageDialog(null, "Reservation was not found");
					}
					else {
						JOptionPane.showMessageDialog(null, "Reservation removed");
					}
					choice = false;
				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Please input a valid number.");
				}
			}
		}
	}

	/**
	 * Prompts the user for inputs and creates reserves a seat
	 */
	public void addReservation()
	{


			JTextField name = new JTextField();
			ArrayList<String> locationList = new ArrayList<>();
			SeatType seatType = null;
			// Name, Location, Destination in combobox
			HashMap<FoodItem, Integer> foodMap = null;
			boolean choice = true;
			String[] currentClass={"Business","Economy"};
			String selectedFlights;
			JComboBox<String> cmbClass = new JComboBox<>(currentClass);
			int option;


			for(String airplane : airlineManager.getAvailibleFlights())
			{
				//	locationList.add( airplane.getName() + ": " + airplane.getLocation() + "->" + airplane.getDestination() );
				locationList.add( airplane.toString() );
			}

			String[] availableLocations = locationList.toArray(new String[locationList.size()]);
			JComboBox<String> cmbAvailableFlights = new JComboBox<>(availableLocations);


			Object[] message = {
					"Name:", name,
					"Flights:", cmbAvailableFlights,
					"Seat type:", cmbClass
			};
			while (choice == true) {
				option = JOptionPane.showConfirmDialog(null, message, "Reservation", JOptionPane.OK_CANCEL_OPTION);
				if (option == 0) {
					try {
						// If name or selected flights box is empty throw an expection and return to the menu
						if(name.getText().equals(""))
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

			if (String.valueOf(cmbClass.getSelectedItem()).toUpperCase(Locale.ENGLISH).equals("BUSINESS")) {
				seatType = SeatType.BUSINESS_SEAT;
			}
			else if(String.valueOf(cmbClass.getSelectedItem()).toUpperCase(Locale.ENGLISH).equals("ECONOMY")) {
				seatType = SeatType.ECONOMY_SEAT;
			}


			//Check if the user wants to switch seats if the current seat type is full.
			selectedFlights = String.valueOf(cmbAvailableFlights.getSelectedItem());
			if (!airlineManager.hasAvailibleSeat(selectedFlights, seatType)) {
				if (seatType == SeatType.BUSINESS_SEAT) {
					option = JOptionPane.showConfirmDialog(null, "The airplane has no free business seats. Would you like to book an economy seat instead?", "No free business seats", JOptionPane.OK_CANCEL_OPTION);
					seatType = SeatType.ECONOMY_SEAT;
				}
				else {
					option = JOptionPane.showConfirmDialog(null, "The airplane has no free economy seats. Would you like to book an business seat instead?", "No free economy seats", JOptionPane.OK_CANCEL_OPTION);
					seatType = SeatType.BUSINESS_SEAT;
				}
				if (option != 0) {
					return;
				}
			}


			// Do you want to order food? YES NO?

			int reply = JOptionPane.showConfirmDialog(null, "Do you want to order food?", "Order food", JOptionPane.YES_NO_OPTION);

			if (reply == JOptionPane.YES_OPTION) {
				// Open food menu based on what class the seat was selected
				foodMap = orderFood(seatType);
				//orderFood returns null when the user is supposed to go back to the main menu
				if (foodMap == null) {
					return;
				}
			}
			else if (reply == JOptionPane.NO_OPTION) {
				// Finish reservation without ordering food
				foodMap = new HashMap<FoodItem, Integer>();
			}
			else {
				return;
			}


			// Confirmation screen
			String confirmationMessage = "Name: " + name.getText() + " \n" + "Flight: " + selectedFlights + " \n" + "Seat type: " + seatType.toString() + " \n";
			double reservationCost = airlineManager.getSeatPrice(seatType);
			for(FoodItem foodItem : foodMap.keySet()) {
				if(foodMap.get(foodItem) > 0) {
					confirmationMessage += foodMap.get(foodItem) + " " + foodItem.getName() + " \n";
					reservationCost += foodMap.get(foodItem) * foodItem.getPrice();
				}
			}
			confirmationMessage += "Cost: " + reservationCost;

			option = JOptionPane.showConfirmDialog(null, confirmationMessage, "Confirmation", JOptionPane.OK_CANCEL_OPTION);



			if (option == 0) {
				// If the user presses yes, do nothing
			}
			else {
				// If the user presses something else, return to menu
				return;
			}



			// Adds reservation
			int canBeReserved = airlineManager.addReservation(selectedFlights, name.getText(), seatType, foodMap);


			JOptionPane.showMessageDialog(null, "Reservation was added\nThe ID number for your reservation is: " + canBeReserved + "\nCost: " + reservationCost + " SEK", "Receipt", JOptionPane.INFORMATION_MESSAGE);
			return;


	}

/**
 * Returns null when the user is supposed to go back to the main menu
 * @param seatType
 * @return
 */
	public HashMap<FoodItem, Integer> orderFood(SeatType seatType) {

		// Creates text fields
		boolean choice = true;
		FoodItem[] foodArray = airlineManager.getMenu(seatType);

		Object[] message = new Object[foodArray.length * 2];
		for (int i = 0; i < message.length; i+=2){
			// Divides the index used for foodArray with two because the for loop increments with two each time.
			message[i] = foodArray[i/2].getName() + " , " + foodArray[i/2].getPrice() + " SEK";
			message[i + 1] = new JTextField();
		}


		// Loop will run forever until the user gives valid input.
		HashMap<FoodItem, Integer> foodMap = new HashMap<>();
		while (choice == true) {

			int option = JOptionPane.showConfirmDialog(null, message, "Order food", JOptionPane.OK_CANCEL_OPTION);
			if (option == 0) {
				try {
					addFoodItemsToMap(foodMap, message, foodArray);
					choice = false;
				}
				catch ( NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Must input a valid number");
				}
			}
			else {
				return null;
			}
		}
		return foodMap;
	}


	public void addFoodItemsToMap(HashMap<FoodItem, Integer> foodMap, Object[] message, FoodItem[] foodArray) throws NumberFormatException {


		for (int i = 0; i < message.length; i+=2){
			// Goes through each textfield created and adds value to map
			String quantityString = ((JTextField) message[i + 1]).getText();
			if (quantityString.equals("")) {
				quantityString = "0";
			}
			Integer quantity = Integer.parseInt(quantityString);

			if ( quantity < 0 ) {
				throw new NumberFormatException();
			}

			foodMap.put(foodArray[i/2],  quantity);


		}



	}





















}
