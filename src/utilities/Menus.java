package utilities;

import se.lexicon.model.FoodItem;

/**
 * Provides two methods which returns hard coded menus for business and economy
 * class.
 * 
 * @author Linus Karlbom
 *
 */
public class Menus {
	private final static FoodItem[] businessMenu = { new FoodItem("Wine", 200), new FoodItem("Steak", 300),
			new FoodItem("Salad", 200) };
	private final static FoodItem[] economyMenu = { new FoodItem("Glass of water", 20), new FoodItem("Bread", 30),
			new FoodItem("Boiled Potatoes", 40) };

	/**
	 * Creates an array containing a copy of a menu of this class.
	 * 
	 * @param seatType
	 *            the SeatType corresponding to the sought menu. Can be either
	 *            ECONOMY_SEAT or BUSINESS_SEAT.
	 * @return the copied array of FoodItems
	 */
	public static FoodItem[] getMenuCopy(SeatType seatType) {
		FoodItem[] menuCopy;
		if (seatType == SeatType.ECONOMY_SEAT) {
			menuCopy = new FoodItem[economyMenu.length];
			for (int i = 0; i < economyMenu.length; i++) {
				menuCopy[i] = new FoodItem(economyMenu[i]);
			}
		} else {
			menuCopy = new FoodItem[businessMenu.length];
			for (int i = 0; i < businessMenu.length; i++) {
				menuCopy[i] = new FoodItem(businessMenu[i]);
			}
		}
		return menuCopy;
	}

}
