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

/**
 * JOptionPane GUI for the FlightBase application
 *
 * @author Daniel Axelsson
 * @author Linus Karlbom
 *
 */
public class UI {

	protected int reservationID;
	protected int capacity = 0;
	static int response = 0;
	static String[] options = null;
	protected String name = null;
	protected String email = null;
	protected String adress = null;
	protected String airlineName = null;
	protected Booking reservation = null;
	protected AirlineManager airlineManager = null;

	public UI() {
	}

	/**
	 *
	 * @param airlineManager
	 *            AirlineManager class
	 */
	public void callMainMenu(AirlineManager airlineManager) {

		this.airlineManager = airlineManager;
		boolean choice = true;
		options = new String[] { "Reserve seat", "Remove reservation", "Get income", "Get profit", "Exit" };

		while (choice == true) {

			response = JOptionPane.showOptionDialog(null, "What would you like to do?", "Main menu",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

			switch (response) {
			// Calls the reserve method
			case 0:
				addReservation();
				break;
			// Remove specific reservation
			case 1:
				removeReservation();
				break;
			// Calls and displays the airlines income method
			case 2:
				JOptionPane.showMessageDialog(null, "The total income is: " + airlineManager.getIncome() + " SEK",
						"Income", JOptionPane.INFORMATION_MESSAGE);
				break;
			// Calls and displays the airlines profit method
			case 3:
				JOptionPane.showMessageDialog(null, "The total profit is: " + airlineManager.getProfit() + " SEK",
						"Profit", JOptionPane.INFORMATION_MESSAGE);
				break;
			// Back to main menu
			case 4:
				choice = false;
				break;
			default:
				return;
			}
		}
	}

	/**
	 * Prompts the user for inputs and removes the passenger with the specified ID
	 */
	public void removeReservation() {
		boolean choice = true;

		while (choice == true) {
			String option = JOptionPane.showInputDialog(null,
					"What's the ID of the passenger you would like to remove?", "Remove Reservation",
					JOptionPane.INFORMATION_MESSAGE);

			if (option == null) {
				// Leave without doing anything if cancel is pressed.
				choice = false;
			} else if (option.equals("")) {
				JOptionPane.showMessageDialog(null, "Please input a valid number.");
			} else {
				try {
					reservationID = Integer.parseInt(option);
					reservationID = Integer.parseInt(option);
					Boolean isBooked = airlineManager.removeReservation(reservationID);
					if (isBooked == false) {
						JOptionPane.showMessageDialog(null, "Reservation was not found");
					} else {
						JOptionPane.showMessageDialog(null, "Reservation removed");
					}
					choice = false;
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Please input a valid number.");
				}
			}
		}
	}

	/**
	 * Calls the reservation menu
	 */
	public void addReservation() {
		JTextField name = new JTextField();
		ArrayList<String> locationList = new ArrayList<>();
		SeatType seatType = null;
		HashMap<FoodItem, Integer> foodMap = null;
		String[] currentClass = { "Business", "Economy" };
		String selectedFlights;
		JComboBox<String> cmbClass = new JComboBox<>(currentClass);

		// Add each not full plane to locationlist
		for (String airplane : airlineManager.getAvailibleFlights()) {
			locationList.add(airplane.toString());
		}

		// Adds every location to an array which is then used to construct the combobox
		String[] availableLocations = locationList.toArray(new String[locationList.size()]);
		JComboBox<String> cmbAvailableFlights = new JComboBox<>(availableLocations);
		Object[] message = { "Name:", name, "Flights:", cmbAvailableFlights, "Seat type:", cmbClass };

		if (availableLocations == null) {
			JOptionPane.showMessageDialog(null, "No flights available, please check back later");
			return;
		}

		// Opens the flight menu
		int r = 0;
		while (r == 0) {
			r = chooseFlightMenu(name, message);
		}
		// If the user closes the window return to menu
		if (r == 2) {
			return;
		}

		// Gets selected flights
		if (String.valueOf(cmbClass.getSelectedItem()).toUpperCase(Locale.ENGLISH).equals("BUSINESS")) {
			seatType = SeatType.BUSINESS_SEAT;
		} else if (String.valueOf(cmbClass.getSelectedItem()).toUpperCase(Locale.ENGLISH).equals("ECONOMY")) {
			seatType = SeatType.ECONOMY_SEAT;
		}
		selectedFlights = String.valueOf(cmbAvailableFlights.getSelectedItem());


		// Check if the user wants to switch seats if the current seat type is full.
		seatType = checkIfSeatIsFull(seatType, selectedFlights);
		if (seatType == null) {
			return;
		}

		// Asks if the user wants to order food and then adds the items to foodMap
		foodMap = orderFoodOption(seatType, foodMap);

		if (foodMap == null) {
			return;
		}

		// Confirmation screen where the reservation is added
		confirmReservation(name, selectedFlights, seatType, foodMap);
		return;
	}

	/**
	 * Opens the flight menu where you input name, flight and what type of seat you
	 * would like to reserve.
	 *
	 * @param name
	 *            Name of the reservation
	 * @param message
	 *            Object array with JTextFields
	 * @return r State of what button was pressed. 1 = OK, 2 = Cancel, 0 = User did not write in a name when pressing OK
	 */
	public int chooseFlightMenu(JTextField name, Object[] message) {
		int option = JOptionPane.showConfirmDialog(null, message, "Reservation", JOptionPane.OK_CANCEL_OPTION);
		int r = 0;
		try {
			if (option == 0) {
				// If name or selected flights box is empty throw an expection and return to the
				// menu
				if (name.getText().equals("")) {
					throw new NullPointerException();
				} else {
					// Continue
					r = 1;
				}
			} else if (option == 1) {
				// Returns to menu
				r = 2;
			} else {
				// Returns to menu
				r = 2;
			}
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Please input a correct value");
		}
		return r;

	}

	/**
	 * Checks if seat is full on selected flight and asks if they would like to
	 * change type of seat
	 *
	 * @param seatType
	 *            The seat type of the reservation. Can be either ECONOMY_SEAT or
	 *            BUSINESS_SEAT
	 * @param selectedFlights
	 * @return seatType The seat type of the reservation. Can be either ECONOMY_SEAT
	 *         or BUSINESS_SEAT
	 */
	public SeatType checkIfSeatIsFull(SeatType seatType, String selectedFlights) {

		if (!airlineManager.hasAvailibleSeat(selectedFlights, seatType)) {
			int option;
			if (seatType == SeatType.BUSINESS_SEAT) {
				option = JOptionPane.showConfirmDialog(null,
						"The airplane has no free business seats. Would you like to book an economy seat instead?",
						"No free business seats", JOptionPane.OK_CANCEL_OPTION);
				seatType = SeatType.ECONOMY_SEAT;
			} else {
				option = JOptionPane.showConfirmDialog(null,
						"The airplane has no free economy seats. Would you like to book an business seat instead?",
						"No free economy seats", JOptionPane.OK_CANCEL_OPTION);
				seatType = SeatType.BUSINESS_SEAT;
			}
			if (option != 0) {
				seatType = null;
			}
		}
		return seatType;

	}

	/**
	 * Opens the order food menu
	 *
	 * @param seatType
	 *            The seat type of the reservation. Can be either ECONOMY_SEAT or
	 *            BUSINESS_SEAT
	 * @param foodMap
	 *            A map between FoodItems and the ordered number of that FoodItem.
	 * @return foodMap A map between FoodItems and the ordered number of that
	 *         FoodItem.
	 */
	public HashMap<FoodItem, Integer> orderFoodOption(SeatType seatType, HashMap<FoodItem, Integer> foodMap) {

		int reply = JOptionPane.showConfirmDialog(null, "Do you want to order food?", "Order food",
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			// Open food menu based on what class the seat was selected
			foodMap = orderFood(seatType);
			// orderFood returns null when the user is supposed to go back to the main menu
			if (foodMap == null) {
				return foodMap;
			}
			return foodMap;
		} else if (reply == JOptionPane.NO_OPTION) {
			// Finish reservation without ordering food
			foodMap = new HashMap<FoodItem, Integer>();
			return foodMap;
		} else {
			return null;
		}

	}

	/**
	 * Opens the confirmation menu and adds the reservation if the user confirms the
	 * reservation
	 *
	 * @param name
	 *            Name of the reservation
	 * @param selectedFlights
	 *            The selected Airplane
	 * @param seatType
	 *            The seat type of the reservation. Can be either ECONOMY_SEAT or
	 *            BUSINESS_SEAT
	 * @param foodMap
	 *            A map between FoodItems and the ordered number of that FoodItem.
	 */
	public void confirmReservation(JTextField name, String selectedFlights, SeatType seatType,
			HashMap<FoodItem, Integer> foodMap) {

		int option;
		String confirmationMessage = "Name: " + name.getText() + " \n" + "Flight: " + selectedFlights + " \n"
				+ "Seat type: " + seatType.toString() + " \n";
		double reservationCost = airlineManager.getSeatPrice(seatType);
		for (FoodItem foodItem : foodMap.keySet()) {
			if (foodMap.get(foodItem) > 0) {
				confirmationMessage += foodMap.get(foodItem) + " " + foodItem.getName() + " \n";
				reservationCost += foodMap.get(foodItem) * foodItem.getPrice();
			}
		}
		confirmationMessage += "Cost: " + reservationCost;

		option = JOptionPane.showConfirmDialog(null, confirmationMessage, "Confirmation", JOptionPane.OK_CANCEL_OPTION);

		if (option == 0) {
			// If the user presses yes, do nothing
		} else {
			// If the user presses something else, return to menu
			return;
		}

		// Adds reservation
		int canBeReserved = airlineManager.addReservation(selectedFlights, name.getText(), seatType, foodMap);
		JOptionPane.showMessageDialog(null, "Reservation was added\nThe ID number for your reservation is: "
				+ canBeReserved + "\nCost: " + reservationCost + " SEK", "Receipt", JOptionPane.INFORMATION_MESSAGE);

	}

	/**
	 * Opens the order food menu
	 *
	 * @param seatType
	 *            The seat type of the reservation. Can be either ECONOMY_SEAT or
	 *            BUSINESS_SEAT
	 * @return foodMap A map between FoodItems and the ordered number of that
	 *         FoodItem.
	 */
	public HashMap<FoodItem, Integer> orderFood(SeatType seatType) {

		// Creates text fields
		boolean choice = true;
		FoodItem[] foodArray = airlineManager.getMenu(seatType);
		Object[] message = new Object[foodArray.length * 2];
		for (int i = 0; i < message.length; i += 2) {
			// Divides the index used for foodArray with two because the for loop increments
			// with two each time.
			message[i] = foodArray[i / 2].getName() + " , " + foodArray[i / 2].getPrice() + " SEK";
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
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Must input a valid number");
				}
			} else {
				return null;
			}
		}
		return foodMap;
	}

	/**
	 * Adds the food items to the collection
	 *
	 * @param foodMap
	 *            A map between FoodItems and the ordered number of that FoodItem.
	 * @param message
	 *            Object array with JTextFields
	 * @param foodArray
	 *            Array with fooditems
	 * @throws NumberFormatException
	 */
	public void addFoodItemsToMap(HashMap<FoodItem, Integer> foodMap, Object[] message, FoodItem[] foodArray)
			throws NumberFormatException {

		for (int i = 0; i < message.length; i += 2) {
			// Goes through each textfield created and adds value to map
			String quantityString = ((JTextField) message[i + 1]).getText();
			if (quantityString.equals("")) {
				quantityString = "0";
			}
			Integer quantity = Integer.parseInt(quantityString);

			if (quantity < 0) {
				throw new NumberFormatException();
			}

			foodMap.put(foodArray[i / 2], quantity);

		}

	}

}
