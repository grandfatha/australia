package org.australia.algorithm;

import java.util.Calendar;
import java.util.Observable;


/**
 * Status is used to collect status-messages and information about the current ga
 * @author jochen
 * @see GA
 */
public class Status extends Observable{

	private Calendar timeStarted;
	private Calendar timeStopped;
	
	private Individual currentBestIndividual;
	private Individual currentBestValidIndividual;
	
	
	private int currentGeneration;
	private int lastImprovedGeneration;
	

	
	public Status() {
		timeStarted = Calendar.getInstance();

	}

	
	public Long getDuration(){
		if(timeStopped==null){
			return null;
		}
		return timeStopped.getTimeInMillis() - timeStarted.getTimeInMillis();
	}
	
	// GETTER SETTER //////////////////////////////////////////////////////////////////////////

	public Individual getCurrentBestIndividual() {
		return currentBestIndividual;
	}


	public Individual getCurrentBestValidIndividual() {
		return currentBestValidIndividual;
	}


	public int getCurrentGeneration() {
		return currentGeneration;
	}


	public Calendar getTimeStarted() {
		return timeStarted;
	}


	public Calendar getTimeStopped() {
		return timeStopped;
	}


	public void setCurrentBestIndividual(Individual currentBestIndividual) {
		this.currentBestIndividual = currentBestIndividual;
		this.lastImprovedGeneration = currentGeneration;
		setChanged();
		notifyObservers();
	}


	public void setCurrentBestValidIndividual(Individual currentBestValidIndividual) {
		this.currentBestValidIndividual = currentBestValidIndividual;
		setChanged();
		notifyObservers();
	}

	public void setCurrentGeneration(int currentGeneration) {
		this.currentGeneration = currentGeneration;
		setChanged();
		
		if(this.currentGeneration%100==0){
			notifyObservers();
		}
	}


	public void setTimeStarted(Calendar timeStarted) {
		this.timeStarted = timeStarted;
		setChanged();
		notifyObservers();
	}


	public void setTimeStopped(Calendar timeStopped) {
		this.timeStopped = timeStopped;
		setChanged();
		notifyObservers();
	}


	public int getLastImprovedGeneration() {
		return lastImprovedGeneration;
	}
	
	
	

}
