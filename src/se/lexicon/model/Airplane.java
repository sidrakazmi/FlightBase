package se.lexicon.model;

public class Airplane {
	private boolean isflying;
	private int bussinessClassSize;
	private int economyClassSize;
	private String planeName;
	private int noOfReservedBussinessSeats;
	private int noOfReservedEconomySeats;
	private String location;
	private String destination;
	
	
	public Airplane(int bussinessClassSize, int economyClassSize, String planeName, String location,
			String destination) {
	
		this.bussinessClassSize = bussinessClassSize;
		this.economyClassSize = economyClassSize;
		this.planeName = planeName;
		this.location = location;
		this.destination = destination;
		noOfReservedBussinessSeats=0;
		noOfReservedEconomySeats=0;
	}


	
	public boolean isIsflying() {
		return isflying;
	}


	public int getNoOfReservedBussinessSeats() {
		return noOfReservedBussinessSeats;
	}


	public int getNoOfReservedEconomySeats() {
		return noOfReservedEconomySeats;
	}



	
	@Override
	public String toString() {
		return "Airplane [planeName=" + planeName + ", location=" + location + ", destination=" + destination + "]";
	}
	
	public int bookBussinessSeat() {
		if() {
			
		}
		else return -1;
		
	}
	
	public int bookEconomySeat() {
		if() {
			
		}
		else return -1;
		
	}
	
	public boolean removeBussinessSeat() {
		if() {
			
		}
		else return false //with a message;
		
	}
	public boolean removeEconomySeat() {
		if() {
			
		}
		else return false //with a message;
		
	}
	
	
}
