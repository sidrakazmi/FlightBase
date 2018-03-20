package se.lexicon.model;

public class FoodItem {
	private String name;
	private double price;
	
	public FoodItem(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public FoodItem(FoodItem foodItem) {
		name = foodItem.getName();
		price = foodItem.getPrice();
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}
	
}
