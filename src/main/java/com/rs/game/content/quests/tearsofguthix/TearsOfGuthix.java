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
package com.rs.game.content.quests.tearsofguthix;

import com.rs.engine.quest.Quest;
import com.rs.engine.quest.QuestHandler;
import com.rs.engine.quest.QuestOutline;
import com.rs.game.content.minigames.tearsofguthix.TearsOfGuthixController;
import com.rs.game.model.entity.player.Player;
import com.rs.game.model.object.GameObject;
import com.rs.game.tasks.WorldTasks;
import com.rs.lib.Constants;
import com.rs.lib.game.Animation;
import com.rs.lib.game.Item;
import com.rs.lib.game.Tile;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.events.ItemClickEvent;
import com.rs.plugin.events.ItemOnItemEvent;
import com.rs.plugin.handlers.ItemClickHandler;
import com.rs.plugin.handlers.ItemOnItemHandler;

import java.util.ArrayList;

@QuestHandler(Quest.TEARS_OF_GUTHIX)
@PluginEventHandler
public class TearsOfGuthix extends QuestOutline {
	public final static int NOT_STARTED = 0;
	public final static int GET_BOWL = 1;
	public final static int QUEST_COMPLETE = 2;

	@Override
	public int getCompletedStage() {
		return QUEST_COMPLETE;
	}

	@Override
	public ArrayList<String> getJournalLines(Player player, int stage) {
		ArrayList<String> lines = new ArrayList<>();
		switch(stage) {
			case NOT_STARTED -> {
				lines.add("Deep in the caves in the Lumbridge Swamp is an enchanted place where");
				lines.add("the tears shed by Guthix are said to have magical properties. However,");
				lines.add("the cave is guarded by a loyal serpent named Juna...");
				lines.add("");
			}
			case GET_BOWL -> {
				lines.add("I must get a bowl to be able to fill with the tears.");
				lines.add("");
			}
			case QUEST_COMPLETE -> {
				lines.add("");
				lines.add("");
				lines.add("QUEST COMPLETE!");
			}
			default -> {
				lines.add("Invalid quest stage. Report this to an administrator.");
			}
		}
		return lines;
	}

	public static ItemClickHandler handleMagicStone = new ItemClickHandler(new Object[]{4703}, new String[]{"Craft"}, e-> {
		if (e.getPlayer().getQuestManager().getStage(Quest.TEARS_OF_GUTHIX) != GET_BOWL) {
			return;
		}
		e.getPlayer().getInventory().replace(new Item(4703, 1), new Item(4704, 1));
	});


	public static ItemOnItemHandler handleLensToBullsEye = new ItemOnItemHandler(4542, new int[]{4544}, e -> {
		e.getPlayer().getInventory().deleteItem(e.getItem1().getId(), 1);
		e.getPlayer().getInventory().deleteItem(e.getItem2().getId(), 1);
		e.getPlayer().getInventory().addItem(4546, 1);
	});

	public static void tearsOfGuthix(Player p, GameObject juna) {
		p.lock(3);
		p.walkToAndExecute(Tile.of(3251, 9516, 2), () ->{
			juna.animate(new Animation(2055));
			p.blockRun();
			p.addWalkSteps(3253, 9516, -1, false);
			WorldTasks.schedule(2, () -> {
				p.unblockRun();
				p.getControllerManager().startController(new TearsOfGuthixController());
			});
		});
	}

	@Override
	public void complete(Player player) {
		player.getSkills().addXpQuest(Constants.CRAFTING, 1000);
		getQuest().sendQuestCompleteInterface(player, 4704, "1,000 Crafting XP"/*, "Access to the Tears of Guthix"*/);
	}
}
