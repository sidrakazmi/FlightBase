package utilities;

import se.lexicon.model.FoodItem;

/*
 * Two hard coded menus for business and economy class.
 */
public class Menus {
	private final static FoodItem[] businessMenu = {new FoodItem("Wine", 200), new FoodItem("Steak", 300), new FoodItem("Salad", 200)};
	private final static FoodItem[] economyMenu = {new FoodItem("Glass of water", 20), new FoodItem("Bread", 30), new FoodItem("Boiled Potatoes", 40)};
	
	/*
	 * Returns a copy of the businessMenu array
	 */
	public static FoodItem[] getBusinessMenuCopy() {
		FoodItem[] businessMenuCopy = new FoodItem[businessMenu.length];
		for (int i = 0; i < businessMenu.length; i++) {
			businessMenuCopy[i] = new FoodItem(businessMenu[i]);
		}
		return businessMenuCopy;
	}
	
	/*
	 * Returns a copy of the economyMenu array
	 */
	public static FoodItem[] getEconomyMenuCopy() {
		FoodItem[] economyMenuCopy = new FoodItem[economyMenu.length];
		for (int i = 0; i < economyMenu.length; i++) {
			economyMenuCopy[i] = new FoodItem(economyMenu[i]);
		}
		return economyMenuCopy;
	}
	
}
