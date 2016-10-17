package com.house.hmeltingballs.util;

import java.util.concurrent.ThreadLocalRandom;

public class HHumanSimulator {

	//Default time in seconds to wait.
	private static final int default_min_reaction_time = 5;
	private static final int default_max_reaction_time = 15;
	//While active the script will be idle.
	private static boolean active = false;
	//Time is saved in milliseconds in here every time a new simulation start.
	private static long start_time;
	//The randomly generated wait time in milliseconds in stored here.
	private static long current_reaction_time;

	//Poll before you want to check if you can do the task.
	public static boolean check() {
		if (!active) {
			start_time = System.currentTimeMillis();
			current_reaction_time = randomReactionTime(default_min_reaction_time, default_max_reaction_time);
			active = true;
		}
		if (System.currentTimeMillis() - start_time > current_reaction_time)
			active = false;
		return !active;
	}
	
	//Same as above however this method lets you add your own custom times rather than default.
	public static boolean check(int new_min_reaction_time, int new_max_reaction_time) {
		if (!active) {
			start_time = System.currentTimeMillis();
			current_reaction_time = randomReactionTime(new_min_reaction_time, new_max_reaction_time);
			active = true;
		}
		if (System.currentTimeMillis() - start_time > current_reaction_time)
			active = false;
		return !active;
	}

	//Returns the time left till simulation is done.
	public static long debugTimeLeft() {
		return current_reaction_time - (System.currentTimeMillis() - start_time);
	}

	//Generates a random long between two values.
	private static long randomReactionTime(int min, int max) {
		int random_reaction_time = ThreadLocalRandom.current().nextInt((max - min) + 1) + min;
		return random_reaction_time * 1000L;
	}

}
