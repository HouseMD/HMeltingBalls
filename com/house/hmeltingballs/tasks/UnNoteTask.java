package com.house.hmeltingballs.tasks;

import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;

import com.house.hmeltingballs.HMeltingBalls;
import com.house.hmeltingballs.util.InteractType;
import com.house.hmeltingballs.util.State;

public class UnNoteTask extends Task {

	private final Area bank_area = new Area(3091, 3499, 3098, 3492);
	private final Area bank_destiantion_area = new Area(3095, 3494, 3098, 3497);

	private InteractType interactType;
	private Position bank_destiantion;

	public UnNoteTask(Script script) {
		super(script);
	}

	@Override
	public void update() {
		interactType = InteractType.getRandomType();
		bank_destiantion = bank_destiantion_area.getRandomPosition();
	}

	@Override
	public boolean validate() {
		return HMeltingBalls.getState() == State.UNNOTE_BARS;
	}

	@Override
	public void process() {
		if (interactType == null)
			interactType = InteractType.getRandomType();

		if (bank_destiantion == null)
			bank_destiantion = bank_destiantion_area.getRandomPosition();

		Inventory inventory = getScript().getInventory();
		if (inventory.contains(steel_bar_id)) {
			HMeltingBalls.smith_balls_task.update();
			HMeltingBalls.setState(State.SMITH_BALLS);
			return;
		}

		if (!bank_area.contains(getScript().myPlayer())) {
			if(bank_destiantion != null)
			walkToPosition(bank_destiantion, 0, bank_area);
			update();
			return;
		}

		RS2Widget unnote_widget_yes = getScript().getWidgets().get(219, 0, 1);

		if (unnote_widget_yes == null || !unnote_widget_yes.isVisible()) {

			if (inventory.getSelectedItemName() == null || !inventory.getSelectedItemName().equals("Steel bar")) {
				if (inventory.getSelectedItemName() != null) {
					getScript().getMouse().click(false);
					return;
				}
				if (!inventory.contains(noted_steel_bar_id)) {
					getScript().logger.error("No more noted Steel bars in inventory!");
					getScript().stop();
					return;
				}
				Item noted_steel_bars = inventory.getItem(noted_steel_bar_id);
				if (noted_steel_bars == null)
					return;
				noted_steel_bars.interact("Use");
				sleepTill(2000, () -> inventory.getSelectedItemName().equals("Steel bar"));
				return;

			}

			RS2Object bank_booth = getScript().getObjects().closest("Bank booth");

			if (bank_booth != null) {
				bank_booth.interact("Use");
				sleepTill(2000, () -> getScript().getWidgets().get(219, 0, 1) != null && getScript().getWidgets().get(219, 0, 1).isVisible());
				return;
			}

		}

		if (interactType == InteractType.MOUSE) {
			unnote_widget_yes.interact("Continue");
			return;
		} else if (interactType == InteractType.KEYBOARD) {
			getScript().getKeyboard().typeKey("1".toCharArray()[0]);
			return;
		}

	}
	
	public Area getBankDestinationArea() {
		return bank_destiantion_area;
	}

}
