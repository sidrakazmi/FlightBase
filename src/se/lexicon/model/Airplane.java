package se.lexicon.model;

import utilities.SeatType;

public class Airplane{
	
	private boolean isFlying;
	
	private int maxBusinessClassSeat;
	private int maxEconomyClassSeat;
	
	private int noOfReservedBusinessSeats;
	private int noOfReservedEconomySeats;
	
	private int noOfAvaiableBusinessSeats;
	private int noOfAvaiableEconomySeats;
	
	private String planeName;
	private String location;
	private String destination;
	
	/**
	 * Constructor 
	 **/
	public Airplane(int maxBussinessClassSeat, int maxEconomyClassSeat, String planeName, String location,
			String destination) {
	
		this.maxBusinessClassSeat = maxBussinessClassSeat;
		this.maxEconomyClassSeat = maxEconomyClassSeat;
		this.planeName = planeName;
		this.location = location;
		this.destination = destination;
		noOfReservedBusinessSeats=0;
		noOfReservedEconomySeats=0;
		isFlying = false;
	}

	/**
	 * Returns the status of a Flight
	 **/
	public boolean isIsflying() {
		return isFlying;
	}

	public String getPlaneName() {
		return planeName;
	}
	
	public String getLocation() {
		return location;
	}

	public String getDestination() {
		return destination;
	}

	@Override
	public String toString() {
		return planeName + ": " + location + "->" + destination;
	}

	/**
	 * Calculates and returns the number of Available seats in Economy/Business class 
	 * @param Seat Type: Economy or Business
	 **/
	public int getNumberOfAvaiableSeats(SeatType seatType) {
		if(seatType == SeatType.BUISNESS_SEAT) {
			noOfAvaiableBusinessSeats= maxBusinessClassSeat - noOfReservedBusinessSeats;
			return noOfAvaiableBusinessSeats;
		}
		else {
			if(seatType == SeatType.ECONOMY_SEAT) {
			noOfAvaiableEconomySeats= maxEconomyClassSeat - noOfReservedEconomySeats;
			return noOfAvaiableEconomySeats;
		}
			else return -1;
	}

	}
	
	/**
	 * Reserves a seat in Economy/Business class 
	 * Checks the seat type and if any seats available to book
	 * @param Seat Type: Economy or Business
	 **/
	public int bookSeat(SeatType seatType) {
		
		if(seatType == SeatType.BUISNESS_SEAT && noOfAvaiableBusinessSeats<maxBusinessClassSeat) 
		
	   {
			noOfReservedBusinessSeats++;
			takeFlightIfThePlaneIsFull();
			return noOfReservedBusinessSeats;
		}
		
		
		else 
			if(seatType == SeatType.ECONOMY_SEAT && noOfAvaiableEconomySeats<maxEconomyClassSeat) 
			{
					noOfReservedEconomySeats++;
					takeFlightIfThePlaneIsFull();
					return noOfReservedEconomySeats;
			}
			

		else return -1;
		
	}
	
	/**
	 * Cancels a booking in Economy/Business class 
	 * Checks the seat type and if any reservation available to remove
	 * @param Seat Type: Economy or Business
	 **/	
	public boolean removeSeat(SeatType seatType) {
		if(seatType == SeatType.BUISNESS_SEAT && noOfReservedBusinessSeats>0) 
			
		   {
				noOfReservedBusinessSeats--;
				return true;
			}
			
			
			else 
				if(seatType == SeatType.ECONOMY_SEAT && noOfReservedEconomySeats>0) 
				{
						noOfReservedEconomySeats--;
						return true;
				}
				
		
			else return false;
			
		}
	
	/**
	 * Checks if all the seats in Economy and Business class are reserved 
	 * Sends the Airplane off to flight
	 * Waits for 2 minutes before sending the next plane to Flight
	 **/	
	public void takeFlightIfThePlaneIsFull() {
		if (noOfReservedBusinessSeats==maxBusinessClassSeat  &&  noOfReservedEconomySeats==maxEconomyClassSeat) {
	        isFlying = true;
	        Airplane thisAirplane = this;
	       
	        Thread thread1 = new Thread(() -> {
	            System.out.println(thisAirplane.getPlaneName() + " takes off");
	            System.out.println(thisAirplane.getPlaneName() + " is flying");
	            try {
					Thread.sleep(120000);
				} catch (InterruptedException e) {
					// Ignore
				}
	            synchronized(thisAirplane) {
	            	thisAirplane.arrive();
	            }
	            System.out.println(thisAirplane.getPlaneName() + " has arrived");
	            System.out.println(thisAirplane.getPlaneName() + " has refueled");
	            });
	        thread1.start();
			
			
		}
		
	}

	/**
	 * When a plane arrives resets its status 
	 **/	
	private void arrive() {
		noOfReservedBusinessSeats=0;
		noOfReservedEconomySeats=0;
		isFlying = false;
	}
}
