package com.house.hmeltingballs;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.osbot.rs07.script.Script;

import com.house.hmeltingballs.tasks.Task;

public class TaskScript extends Script {

	ArrayList<Task> tasks = new ArrayList<Task>();

	@Override
	public int onLoop() throws InterruptedException {

		for (Task task : tasks) {

			if (task.validate())
				task.process();

		}

		return ThreadLocalRandom.current().nextInt((400 - 300) + 1) + 300;
	}

	public void addTask(Task task) {
		tasks.add(task);
	}

}
