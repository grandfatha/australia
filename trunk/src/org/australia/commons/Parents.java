package org.australia.commons;

import org.australia.util.Utils;

public class Parents {
	
	
	private Individual mum;
	private Individual dad;
	
	public Parents(Individual mum, Individual dad) {
		
		this.mum = mum;
		this.dad = dad;
		
	}
	
	
	public Individual haveHotAndLongAndPossiblySweatySex(){
		
		
		int[] babygene = new int[mum.getGene().length];
		int[] mumgene = mum.getGene();
		int[] dadgene = dad.getGene();

		assert(babygene.length == mumgene.length  && babygene.length == dadgene.length);

		Individual baby = null;
		
		int counter=0;
		
		do{
			counter++;
			if(counter > 1000){
				System.out.println("Could not create a baby");
				break;
			}
			
			boolean[] pattern = Utils.getRandomPattern(mumgene.length);
		
			for (int i = 0; i < mumgene.length; i++) {
				
				if(pattern[i]){	// true at posistion i
					
					babygene[i] = mumgene[i];
					
				}else{
					
					babygene[i] = dadgene[i];
				}
			}
			
			// create baby via factory method
			// return null if invalid
			baby = Individual.createInstance(mum.getProblem(), babygene);

		}while(baby==null);
		

		if(baby==null){
			return dad;
		}
		
		return baby;
	}
	

}
