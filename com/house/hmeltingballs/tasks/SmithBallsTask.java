package com.house.hmeltingballs.tasks;

import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.Mouse;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;

import com.house.hmeltingballs.HMeltingBalls;
import com.house.hmeltingballs.util.HHumanSimulator;
import com.house.hmeltingballs.util.State;

public class SmithBallsTask extends Task {

	public SmithBallsTask(Script script) {
		super(script);
	}

	private final Area furnace_area = new Area(3105, 3501, 3110, 3496);
	private final Area furnace_destiantion_area = new Area(3106, 3500, 3109, 3497);
	private Position furnace_destiantion;

	private long max_animation_interval = 3000;
	private long last_animation = System.currentTimeMillis() - max_animation_interval;

	@Override
	public void update() {
		furnace_destiantion = furnace_destiantion_area.getRandomPosition();
		if (furnace_destiantion.getX() == 3109 && furnace_destiantion.getY() == 3500) {
			furnace_destiantion = null;
		}
	}

	@Override
	public boolean validate() {
		return HMeltingBalls.getState() == State.SMITH_BALLS;
	}

	@Override
	public void process() {

		Inventory inventory = getScript().getInventory();

		if (!inventory.contains(steel_bar_id)) {
			if (HHumanSimulator.check(5, 15)) {
				HMeltingBalls.unnote_task.update();
				HMeltingBalls.setState(State.UNNOTE_BARS);
			}
			return;
		}

		if (furnace_destiantion == null) {
			update();
			return;
		}

		if (!furnace_area.contains(getScript().myPlayer())) {
			walkToPosition(furnace_destiantion, 0, furnace_area);
			return;
		}

		if (getScript().myPlayer().isAnimating())
			last_animation = System.currentTimeMillis();

		if (System.currentTimeMillis() - last_animation < max_animation_interval) {
			Mouse mouse = getScript().getMouse();
			if (mouse.isOnScreen())
				mouse.moveOutsideScreen();
			return;
		}

		RS2Widget smith_all = getScript().getWidgets().get(309, 7);

		if (smith_all == null || !smith_all.isVisible()) {

			if (inventory.getSelectedItemName() == null || !inventory.getSelectedItemName().equals("Steel bar")) {

				if (inventory.getSelectedItemName() != null) {
					getScript().getMouse().click(false);
					return;
				}

				if (!inventory.contains(steel_bar_id))
					return;

				Item steel_bars = inventory.getItem(steel_bar_id);
				if (steel_bars == null)
					return;

				steel_bars.interact("Use");
				sleepTill(2000, () -> inventory.getSelectedItemName().equals("Steel bar"));
				return;

			}

			RS2Object furnace = getScript().getObjects().closest("Furnace");

			if (furnace != null) {
				furnace.interact("Use");
				sleepTill(2000, () -> getScript().getWidgets().get(309, 7) != null && getScript().getWidgets().get(309, 7).isVisible());
				return;
			}

		}

		smith_all.interact("Continue");
		sleepTill(2000, () -> getScript().myPlayer().isAnimating());

	}

}
