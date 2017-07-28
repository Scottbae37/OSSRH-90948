package com.lge.sdet3;

class GarageState {
	private static final String OK_RESPONSE = "OK.";
	public static final String LED_OFF 		= "0";
	public static final String LED_GREEN 	= "G";
	public static final String LED_RED 		= "R";
	public static final String LED_SPOT_OFF = "0";
	public static final String LED_SPOT_ON 	= "1";
	private static final short PARKING_SPOT_CAPACITY = 4;
	
	private static GarageState garageInstance = null;
	private StringBuilder sb;
	private boolean isEntryOpened;
	private boolean isExitOpened;
	private boolean isCarDetectedEntry;
	private boolean isCarDetectedExit;
	private String entryLedState;
	private String exitLedState;
	
	private boolean parkingSpotStates[];
	private boolean parkingSpotLedStates[];
		
	private GarageState() {
		sb  = new StringBuilder(64);
		parkingSpotStates = new boolean[PARKING_SPOT_CAPACITY];
		parkingSpotLedStates = new boolean[PARKING_SPOT_CAPACITY];
		entryLedState = LED_OFF;
		exitLedState = LED_OFF;
	}

	synchronized static GarageState getInstance() {
		if (garageInstance == null) {
			garageInstance = new GarageState();
		}
		return garageInstance;
	}

	public void clearState(){
		sb.delete(0, sb.toString().length());
		isEntryOpened = false;
		isExitOpened = false;
		isCarDetectedEntry = false;
		isCarDetectedExit = false;
		entryLedState = LED_OFF;
		exitLedState = LED_OFF;

		for (int i = 0; i < PARKING_SPOT_CAPACITY; i++) {
			parkingSpotStates[i] = false;
			parkingSpotLedStates[i] = false;
		}
	}
	
	public boolean getSpotState(int index){
		return parkingSpotStates[index];
	}
	
	public void setSpotState(int index, boolean isOn){
		parkingSpotStates[index] = isOn;
	}
	
	public boolean getSpotLedState(int index){
		return parkingSpotLedStates[index];
	}
	
	public void setSpotLedState(int index, boolean isOn){
		parkingSpotLedStates[index] = isOn;
	}
	
	public boolean isEntryOpened() {
		return isEntryOpened;
	}

	public void setEntryOpened(boolean isEntryOpened) {
		this.isEntryOpened = isEntryOpened;
	}

	public boolean isExitOpened() {
		return isExitOpened;
	}

	public void setExitOpened(boolean isExitOpened) {
		this.isExitOpened = isExitOpened;
	}

	public boolean isCarDetectedEntry() {
		return isCarDetectedEntry;
	}

	public void setCarDetectedEntry(boolean isCarDetectedEntry) {
		this.isCarDetectedEntry = isCarDetectedEntry;
	}

	public boolean isCarDetectedExit() {
		return isCarDetectedExit;
	}

	public void setCarDetectedExit(boolean isCarDetectedExit) {
		this.isCarDetectedExit = isCarDetectedExit;
	}

	public String getEntryLedState() {
		return entryLedState;
	}

	public void setEntryLedState(String entryLedState) {
		this.entryLedState = entryLedState;
	}

	public String getExitLedState() {
		return exitLedState;
	}

	public void setExitLedState(String exitLedState) {
		this.exitLedState = exitLedState;
	}
	
	@Override
	public String toString() {
		String response = "SU:";
		sb.delete(0, sb.toString().length());
		sb.append(response);
		
		sb.append("NG=");
		if(isEntryOpened)
			sb.append("1;");
		else 
			sb.append("0;");
		
		sb.append("XG=");		
		if(isExitOpened)
			sb.append("1;");
		else 
			sb.append("0;");

		sb.append("NL=");	
		if(entryLedState == LED_GREEN)
			sb.append("G;");
		else if(entryLedState == LED_RED)
			sb.append("R;");
		else
			sb.append("0;");
		
		sb.append("NIR=");	
		if(isCarDetectedEntry)
			sb.append("1;");
		else 
			sb.append("0;");
		
		sb.append("XIR=");	
		if(isCarDetectedExit)
			sb.append("1;");
		else 
			sb.append("0;");
		
		sb.append("XL=");	
		if(exitLedState == LED_GREEN)
			sb.append("G;");
		else if(exitLedState == LED_RED)
			sb.append("R;");
		else
			sb.append("0;");
		
		sb.append("PO=[");
		for (int i = 0; i < PARKING_SPOT_CAPACITY; i++) {
			
			sb.append(i+1).append("=");
			if(parkingSpotStates[i] == true)
				sb.append("1");
			else
				sb.append("0");
			
			if(i < PARKING_SPOT_CAPACITY - 1)
				sb.append(",");
		}
		sb.append("];");
		
		sb.append("PL=[");
		for (int i = 0; i < PARKING_SPOT_CAPACITY; i++) {
			
			sb.append(i+1).append("=");
			if(parkingSpotLedStates[i] == true)
				sb.append("1");
			else
				sb.append("0");
			
			if(i < PARKING_SPOT_CAPACITY - 1)
				sb.append(",");
		}
		sb.append("].");
		
		return sb.toString();
	}

	
	
	/*
	 * 
	 * 
	 * String HandleGetgarageInstance() {
	 * 
	 * Serial.println("HandleGetgarageInstance");
	 * 
	 * String response = "SU:"; // garageInstance update
	 * 
	 * // Get entry gate garageInstance int ng = EntryGategarageInstance; if (ng
	 * == OPEN) { response += "NG=1"; } else { response += "NG=0"; } response +=
	 * ";";
	 * 
	 * // get exit gate garageInstance int xg = ExitGategarageInstance; if (xg
	 * == OPEN) { response += "XG=1"; } else { response += "XG=0"; } response +=
	 * ";";
	 * 
	 * // get exit gate IR beam int xir = GetExitIRgarageInstance(); if (xir ==
	 * ARRIVED) { response += "XIR=1"; } else { response += "XIR=0"; }
	 * 
	 * response += ";";
	 * 
	 * // get entry gate IR beam int nir = GetEntryIRgarageInstance(); if (nir
	 * == ARRIVED) { response += "NIR=1"; } else { response += "NIR=0"; }
	 * 
	 * response += ";";
	 * 
	 * int nl = EntryLightgarageInstance; if (nl == GREEN) { response += "NL=G";
	 * } else if (nl == RED) { response += "NL=R"; } else if (nl == OFF) {
	 * response += "NL=0"; }
	 * 
	 * response += ";";
	 * 
	 * int xl = ExitLightgarageInstance; if (xl == GREEN) { response += "XL=G";
	 * } else if (xl == RED) { response += "XL=R"; } else if (xl == OFF) {
	 * response += "XL=0"; }
	 * 
	 * response += ";";
	 * 
	 * // get the parking spot garageInstances. String ps = "PO=["; if
	 * (GetParkingStall1garageInstance() == OCCUPIED) { ps += "1=1,"; } else {
	 * ps += "1=0,"; } if (GetParkingStall2garageInstance() == OCCUPIED) { ps +=
	 * "2=1,"; } else { ps += "2=0,"; } if (GetParkingStall3garageInstance() ==
	 * OCCUPIED) { ps += "3=1,"; } else { ps += "3=0,"; } if
	 * (GetParkingStall4garageInstance() == OCCUPIED) { ps += "4=1"; } else { ps
	 * += "4=0"; } ps += "]";
	 * 
	 * response += ps + ";";
	 * 
	 * // get the parking light garageInstance
	 * 
	 * String pl = "PL=["; if (ParkingStall1LEDgarageInstance == ON) { pl +=
	 * "1=1,"; } else { pl += "1=0,"; } if (ParkingStall2LEDgarageInstance ==
	 * ON) { pl += "2=1,"; } else { pl += "2=0,"; } if
	 * (ParkingStall3LEDgarageInstance == ON) { pl += "3=1,"; } else { pl +=
	 * "3=0,"; } if (ParkingStall4LEDgarageInstance == ON) { pl += "4=1"; } else
	 * { pl += "4=0"; } pl += "]"; response += pl + ".";
	 * 
	 * Serial.println("HandleGetgarageInstance response: " + response);
	 * 
	 * return response; }
	 */
}
