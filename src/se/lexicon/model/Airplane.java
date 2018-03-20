package se.lexicon.model;

import utilities.SeatType;

public class Airplane{
	
	private boolean isFlying;
	
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
		isFlying = false;
	}

	
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

	
	public int getNumberOfAvaiableSeats(SeatType seatType) {
		if(seatType == SeatType.BUISNESS_SEAT) {
			noOfAvaiableBussinessSeats= maxBussinessClassSeat - noOfReservedBussinessSeats;
			return noOfAvaiableBussinessSeats;
		}
		else {
			if(seatType == SeatType.ECONOMY_SEAT) {
			noOfAvaiableEconomySeats= maxEconomyClassSeat - noOfReservedEconomySeats;
			return noOfAvaiableEconomySeats;
		}
			else return -1;
	}

	}

	public int bookSeat(SeatType seatType) {
		
		if(seatType == SeatType.BUISNESS_SEAT && noOfAvaiableBussinessSeats<maxBussinessClassSeat) 
		
	   {
			noOfReservedBussinessSeats++;
			takeFlightIfThePlaneIsFull();
			return noOfReservedBussinessSeats;
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
	

	
	public boolean removeSeat(SeatType seatType) {
		if(seatType == SeatType.BUISNESS_SEAT && noOfReservedBussinessSeats>0) 
			
		   {
				noOfReservedBussinessSeats--;
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
	
	
	/* Threading methods */
	public void takeFlightIfThePlaneIsFull() {
		if (noOfReservedBussinessSeats==maxBussinessClassSeat  &&  noOfReservedEconomySeats==maxEconomyClassSeat) {
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


	private void arrive() {
		noOfReservedBussinessSeats=0;
		noOfReservedEconomySeats=0;
		isFlying = false;
	}
}
