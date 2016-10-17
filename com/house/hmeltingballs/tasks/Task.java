package com.house.hmeltingballs.tasks;

import java.util.function.BooleanSupplier;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.Condition;
import org.osbot.rs07.utility.ConditionalSleep;

public abstract class Task {

	final int steel_bar_id = 2353;
	final int noted_steel_bar_id = 2354;

	protected Script script;

	public Task(Script script) {
		this.script = script;
	}

	public abstract void update();

	public abstract boolean validate();

	public abstract void process();

	public Script getScript() {
		return script;
	}

	public void sleepTill(int check, BooleanSupplier condition) {
		new ConditionalSleep(check) {
			@Override
			public boolean condition() throws InterruptedException {
				return condition.getAsBoolean();
			}
		}.sleep();
	}

	public void walkToPosition(Position position, int threshold, Area area) {
		WalkingEvent walk = new WalkingEvent(position);
		walk.setMinDistanceThreshold(threshold);
		walk.setMiniMapDistanceThreshold(threshold);
		walk.setBreakCondition(new Condition() {
			@Override
			public boolean evaluate() {
				return area.contains(script.myPosition());
			}
		});
		getScript().execute(walk);
	}

}