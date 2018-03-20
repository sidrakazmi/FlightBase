package se.lexicon.model;

public class FoodMenu {
		
	public void foodMenuBussiness() {
		
		String [] food = {"Burger", "drink", "fries", "dessert", "coffee", "water"};
		int [] price = {35, 25, 15, 12, 30, 16};
		
		int total= 20000;
		System.out.println("Food Menu for Bussiness Class : ");
		for (int i =0; i<food.length; i++) {
			System.out.println(food[i]+ '\t' +price[i]);
			total = total+ price[i];
		}
		System.out.println("Total for Bussiness Class is: "+total);
		System.out.println();
	}
	
	public void foodMenuEconomy() {
		
		String [] food = {"Lasagne", "drink", "bread", "jam", "dessert", "coffee"};
		int [] price = {30, 15, 5, 2, 10, 6};
		
		int total= 5000;
		System.out.println("Food Menu for Economy Class : ");
		for (int i =0; i<food.length; i++) {
			System.out.println(food[i]+ '\t' +price[i]);
			total = total+ price[i];
		}
		System.out.println("Total for Economy Class is: "+total);
		System.out.println();
	
	}
	
	public static void main (String[] args) {
		
		FoodMenu m= new FoodMenu();
		m.foodMenuBussiness();
		m.foodMenuEconomy();
	}

}
