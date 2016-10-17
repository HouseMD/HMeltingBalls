package com.house.hmeltingballs.util;

public enum State {
	
	UNNOTE_BARS("Un-noting bars."), SMITH_BALLS("Smithing cannonballs.");
	
	String name;
	
	State(String name) {
		this.name= name;
	}
	
	public String toString() {
		return name;
	}

}
