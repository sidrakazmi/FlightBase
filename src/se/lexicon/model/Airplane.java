package se.lexicon.model;

import java.util.concurrent.Callable;

public class Airplane implements Callable {
	
	private boolean isflying;
	
	private int maxBussinessClassSeat;
	private int maxEconomyClassSeat;
	
	private int noOfReservedBussinessSeats;
	private int noOfReservedEconomySeats;
	
	private int noOfAvaiableBussinessSeats;
	private int noOfAvaiableEconomySeats;
	
	private String planeName;
	private String location;
	private String destination;
	
	
	public Airplane(int maxBussinessClassSeat, int maxEconomyClassSeat, String planeName, String location,
			String destination) {
	
		this.maxBussinessClassSeat = maxBussinessClassSeat;
		this.maxEconomyClassSeat = maxEconomyClassSeat;
		this.planeName = planeName;
		this.location = location;
		this.destination = destination;
		noOfReservedBussinessSeats=0;
		noOfReservedEconomySeats=0;
	}

	
	public boolean isIsflying() {
		return isflying;
	}

	
	@Override
	public String toString() {
		return "Airplane [planeName=" + planeName + ", location=" + location + ", destination=" + destination + "]";
	}

	
	public int AvaiableSeats(SeatType seatType) {
		if(seatType == SeatType.Business_Seat) {
			noOfAvaiableBussinessSeats= maxBussinessClassSeat - noOfReservedBussinessSeats;
			return noOfAvaiableBussinessSeats;
		}
		else {
			if(seatType == SeatType.Economy_Seat) {
			noOfAvaiableEconomySeats= maxEconomyClassSeat - noOfReservedEconomySeats;
			return noOfAvaiableEconomySeats;
		}
			else return -1;
	}

	}

	public int bookSeat(SeatType seatType) {
		
		if(seatType == SeatType.Business_Seat && noOfAvaiableBussinessSeats<maxBussinessClassSeat) 
		
	   {
			noOfReservedBussinessSeats++;
			return noOfReservedBussinessSeats;
		}
		
		
		else 
			if(seatType == SeatType.Economy_Seat && noOfAvaiableEconomySeats<maxEconomyClassSeat) 
			{
					noOfReservedEconomySeats++;
					return noOfReservedEconomySeats;
			}
			
	
		else return -1;
		
	}
	

	
	public boolean removeSeat(SeatType seatType) {
		if(seatType == SeatType.Business_Seat && noOfReservedBussinessSeats>0) 
			
		   {
				noOfReservedBussinessSeats--;
				return true;
			}
			
			
			else 
				if(seatType == SeatType.Economy_Seat && noOfReservedEconomySeats>0) 
				{
						noOfReservedEconomySeats--;
						return true;
				}
				
		
			else return false;
			
		}
	
	
	/* Threading methods */
	public void takeFlight() {
		
	}

	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
