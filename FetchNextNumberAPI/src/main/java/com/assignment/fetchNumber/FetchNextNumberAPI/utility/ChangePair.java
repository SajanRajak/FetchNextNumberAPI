package com.assignment.fetchNumber.FetchNextNumberAPI.utility;

//Utility Class to hold and pass the pair of OldValue and NewValue as mentioned in question
public class ChangePair {
	private long oldValue;
	private long newValue;
	
	public ChangePair(long oldValue, long newValue) {
		super();
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public ChangePair() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getOldValue() {
		return oldValue;
	}

	public void setOldValue(long oldValue) {
		this.oldValue = oldValue;
	}

	public long getNewValue() {
		return newValue;
	}

	public void setNewValue(long newValue) {
		this.newValue = newValue;
	}

	@Override
	public String toString() {
		return "ChangePair [oldValue=" + oldValue + ", newValue=" + newValue + "]";
	}
	
	
}
