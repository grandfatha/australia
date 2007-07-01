package org.australia.algorithm;

import java.util.Calendar;
import java.util.Observable;

public class Status extends Observable{

	private Calendar timeStarted;
	private Calendar timeStopped;
	
	private Individual currentBestIndividual;
	private Individual currentBestValidIndividual;
	
	
	private int currentIteration;
	

	
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


	public int getCurrentIteration() {
		return currentIteration;
	}


	public Calendar getTimeStarted() {
		return timeStarted;
	}


	public Calendar getTimeStopped() {
		return timeStopped;
	}


	public void setCurrentBestIndividual(Individual currentBestIndividual) {
		this.currentBestIndividual = currentBestIndividual;
		setChanged();
		notifyObservers();
	}


	public void setCurrentBestValidIndividual(Individual currentBestValidIndividual) {
		this.currentBestValidIndividual = currentBestValidIndividual;
		setChanged();
		notifyObservers();
	}


	public void setCurrentIteration(int currentIteration) {
		this.currentIteration = currentIteration;
		setChanged();
		notifyObservers();
	}


	public void setTimeStarted(Calendar timeStarted) {
		this.timeStarted = timeStarted;
		setChanged();
		notifyObservers();
	}


	public void setTimeStopped(Calendar timeStopped) {
		this.timeStopped = timeStopped;
	}
	
	
	

}