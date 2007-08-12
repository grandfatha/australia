package org.australia.algorithm2;

import java.util.ArrayList;

public class FacilityMutator {
	
	public static ArrayList<Integer> mutateFacilites(ArrayList<Integer> givenFacilities, double maxFacilities){
		
		ArrayList<Integer> facilities = (ArrayList<Integer>) givenFacilities.clone();
		
		
		// get random nr
		int nr = (int)(facilities.size()*Math.random());
		
		// get random facility
		int randomFacility = (int)(maxFacilities*Math.random());
		
		// set random
		facilities.remove(nr);
		
		facilities.add(randomFacility);
		
		return facilities;
	}

}
