// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright (C) 2021 Trenton Kress
//  This file is part of project: Darkan
//
package com.rs.game.content.minigames.tearsofguthix;

import com.rs.engine.dialogue.Dialogue;
import com.rs.engine.dialogue.HeadE;
import com.rs.engine.dialogue.Options;
import com.rs.engine.quest.QuestManager;
import com.rs.engine.quest.data.QuestInformation;
import com.rs.game.content.minigames.pyramidplunder.PyramidPlunder;
import com.rs.game.model.entity.player.Controller;
import com.rs.game.model.entity.player.Equipment;
import com.rs.game.model.object.GameObject;
import com.rs.game.tasks.WorldTasks;
import com.rs.lib.game.Item;
import com.rs.lib.game.Rights;
import com.rs.lib.game.Tile;
import com.rs.lib.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TearsOfGuthixController extends Controller {
	final static int TOG_INTERFACE = 4;
	private float TICK_DURATION = 156; //4 minutes 22 seconds -> 30 seconds(18 ticks)
	private float TICKS_LEFT = 0;
	private float TEARS_GATHERED = 0;
	@Override
	public void start() {
		player.getEquipment().setSlot(Equipment.WEAPON, new Item(4704));
		player.getAppearance().generateAppearanceData();
		float qpratio = player.getQuestManager().getQuestPoints() / QuestManager.MAX_QUESTPOINTS;
		TICK_DURATION = (float)Math.ceil(TICK_DURATION * qpratio);
		TICKS_LEFT = TICK_DURATION;
//		player.save("TimeLastTOG", System.currentTimeMillis());
	}

	@Override
	public void process() {
		TICKS_LEFT--;
		if (TICKS_LEFT <= 0) {
			player.save("TimeLastTOG", System.currentTimeMillis());
			stopMinigame();
			return;
		}
		updateTOGInterface();
	}

	private void stopMinigame() {
		player.setNextTile(Tile.of(3251, 9516, 2));
		forceClose();
	}

	private void updateTOGInterface() {
		player.getInterfaceManager().sendInventoryInterface(TOG_INTERFACE);
		player.getVars().setVar(449, (int)(1280 * (TICKS_LEFT / TICK_DURATION)));
	}

	@Override
	public boolean login() {
		forceClose();
		return false;
	}

	@Override
	public boolean logout() {
		return false;
	}

	@Override
	public boolean sendDeath() {
		forceClose();
		return true;
	}

	@Override
	public void magicTeleported(int type) {
		forceClose();
	}

	@Override
	public void forceClose() {
		player.getInterfaceManager().removeInventoryInterface();
		player.getEquipment().deleteSlot(Equipment.WEAPON);
		player.getAppearance().generateAppearanceData();
		removeController();
	}
}
