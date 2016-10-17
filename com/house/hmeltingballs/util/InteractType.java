package com.house.hmeltingballs.util;

import java.util.concurrent.ThreadLocalRandom;

public enum InteractType {

	MOUSE(0), KEYBOARD(1);

	int id;

	InteractType(int id) {
		this.id = id;
	}

	public static InteractType getRandomType() {
		int rand = ThreadLocalRandom.current().nextInt(values().length);
		return values()[rand];
	}
}
