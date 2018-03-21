package se.lexicon.model;

public class FoodItem {
	private String name;
	private double price;
	
	
	/**
	 * Constructor 
	 **/
	public FoodItem(String name, double price) {
		this.name = name;
		this.price = price;
	}

	
	/**
	 * Generates a readable name of a Food Item and its price
	 * @param is the food item for which we want to know name and price
	 **/
	public FoodItem(FoodItem foodItem) {
		name = foodItem.getName();
		price = foodItem.getPrice();
	}
	/**
	 * Returns the Name of Food
	 **/
	public String getName() {
		return name;
	}

	/**
	 * Returns the Price of Food
	 **/
	public double getPrice() {
		return price;
	}
	
}
