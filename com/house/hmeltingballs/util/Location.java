package com.house.hmeltingballs.util;

public enum Location {
	
	EDGEVILLE("Edgeville"),//
	FALADOR("Falador");
	
	String name;
	Location(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}

}
