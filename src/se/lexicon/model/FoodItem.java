package se.lexicon.model;

/**
 * An object representing food items which have both names and prices.
 * 
 * @author Linus Karlbom
 * @author Sidra Ali Kazmi
 *
 */
public class FoodItem {
	private String name;
	private double price;

	/**
	 * Constructs a FoodItem using parameter's corresponding to the fields of
	 * FoodItem.
	 * 
	 * @param name
	 *            the name for the created FoodItem
	 * @param price
	 *            the price for the created FoodItem
	 */
	public FoodItem(String name, double price) {
		this.name = name;
		this.price = price;
	}

	/**
	 * Constructs a FoodItem with fields equal to those of another FoodItem.
	 * 
	 * @param foodItem
	 *            the FoodItem which the created FoodItem will have the same fields
	 *            as.
	 */
	public FoodItem(FoodItem foodItem) {
		name = foodItem.getName();
		price = foodItem.getPrice();
	}

	/**
	 * @return the name for the FoodItem
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the price for the FoodItem
	 */
	public double getPrice() {
		return price;
	}

}
