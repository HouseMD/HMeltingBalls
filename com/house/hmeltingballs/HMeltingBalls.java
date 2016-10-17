package com.house.hmeltingballs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.osbot.rs07.script.ScriptManifest;

import com.house.hmeltingballs.tasks.SmithBallsTask;
import com.house.hmeltingballs.tasks.UnNoteTask;
import com.house.hmeltingballs.util.State;

@ScriptManifest(name = "HMeltingBalls", author = "House", version = 1.0, info = "", logo = "")
public class HMeltingBalls extends TaskScript {

	private static State current_state;
	public static UnNoteTask unnote_task;
	public static SmithBallsTask smith_balls_task;

	public void onStart() {
		unnote_task = new UnNoteTask(this);
		smith_balls_task = new SmithBallsTask(this);

		current_state = State.UNNOTE_BARS;

		addTask(unnote_task);
		addTask(smith_balls_task);
	}

	public void onExit() {
		logger.error("Stopped| State=" + current_state.toString());
	}

	public static State getState() {
		return current_state;
	}

	public static void setState(State new_state) {
		current_state = new_state;
	}

	private final Color text_color = new Color(255, 255, 255);

	private final Font font = new Font("Arial", 0, 12);

	private long startTime = System.currentTimeMillis();

	public void onPaint(Graphics2D g) {

		long millis = System.currentTimeMillis() - startTime;

		long hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		long minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		long seconds = millis / 1000;

		g.setColor(text_color);
		g.setFont(font);

		g.drawString("Runtime [" + hours + ":" + minutes + ":" + seconds + "]", 10, 315);
		g.drawString("State: " + current_state, 10, 330);
	}

}
