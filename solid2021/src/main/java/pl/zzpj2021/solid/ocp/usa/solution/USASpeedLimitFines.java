package pl.zzpj2021.solid.ocp.usa.solution;

import java.util.HashMap;

public class USASpeedLimitFines {

	private HashMap<String, Integer> SpeedLimits = new HashMap<>();

	public void addStateLimit(String stateCode, int speed){
		SpeedLimits.merge(stateCode, speed, (oldValue, newValue) -> newValue);
	}
	public double calcualateSpeedLimitFine(String stateCode, int speed) {
		double fine = 0;
		if(SpeedLimits.get(stateCode)<speed){
			// calculate...
		}
		return fine;
	}




}
