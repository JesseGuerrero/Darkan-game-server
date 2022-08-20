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
package com.rs.game.content.quests.handlers.tearsofguthix;

import com.rs.game.content.dialogue.Dialogue;
import com.rs.game.content.dialogue.HeadE;
import com.rs.game.content.quests.Quest;
import com.rs.game.content.quests.QuestHandler;
import com.rs.game.content.quests.QuestOutline;
import com.rs.game.model.entity.player.Equipment;
import com.rs.game.model.entity.player.Player;
import com.rs.lib.Constants;
import com.rs.lib.game.Item;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.events.ItemClickEvent;
import com.rs.plugin.handlers.ItemClickHandler;

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

	public static ItemClickHandler handleMagicStone = new ItemClickHandler(new Object[]{4703}, new String[]{"Craft"}) {
		@Override
		public void handle(ItemClickEvent e) {
			if (e.getPlayer().getQuestManager().getStage(Quest.TEARS_OF_GUTHIX) != GET_BOWL) {
				return;
			}
			e.getPlayer().getInventory().replace(new Item(4703, 1), new Item(4704, 1));
		}
	};

	protected static void tearsOfGuthix(Player p) {
		p.sendMessage("you are doing it...");
		p.save("TimeLastTOG", System.currentTimeMillis());
	}

	@Override
	public void complete(Player player) {
		player.getSkills().addXpQuest(Constants.CRAFTING, 1000);
		getQuest().sendQuestCompleteInterface(player, 4704, "1,000 Crafting XP", "Access to the Tears of Guthix");
	}
}
