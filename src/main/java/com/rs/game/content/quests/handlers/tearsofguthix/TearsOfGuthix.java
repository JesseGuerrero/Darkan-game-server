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

import com.rs.game.content.dialogue.Conversation;
import com.rs.game.content.dialogue.HeadE;
import com.rs.game.content.quests.Quest;
import com.rs.game.content.quests.QuestHandler;
import com.rs.game.content.quests.QuestOutline;
import com.rs.game.model.entity.player.Player;
import com.rs.lib.Constants;
import com.rs.lib.game.Item;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.events.NPCClickEvent;
import com.rs.plugin.handlers.NPCClickHandler;

import java.util.ArrayList;

@QuestHandler(Quest.TEARS_OF_GUTHIX)
@PluginEventHandler
public class TearsOfGuthix extends QuestOutline {
	public final static int NOT_STARTED = 0;
	public final static int TALK_TO_JUNA = 1;
	public final static int TEARS = 2;
	public final static int REPORT_TO_JUNA = 3;
	public final static int QUEST_COMPLETE = 4;
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
				lines.add("the tears shed by Guthix when it saw the destruction Saradomin and ");
				lines.add("Zamorak had caused with their wars flow from the very walls. These ");
				lines.add("tears are said to have magical properties to help players gain deeper");
				lines.add("understanding of the world. However, the cave is guarded by a loyal ");
				lines.add("serpent named Juna who blocks passage from everyone. However, she has ");
				lines.add("grown bored from three thousand years of sitting in the dark cave and");
				lines.add("wishes to hear stories of life above. Maybe you could come to some sort");
				lines.add("of arrangement...?");
				lines.add("");
			}
			case TALK_TO_JUNA -> {
				lines.add("");
				lines.add("");
			}
			case TEARS -> {
				lines.add("");
				lines.add("");
			}
			case REPORT_TO_JUNA -> {
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

	@Override
	public void complete(Player player) {
		player.getSkills().addXpQuest(Constants.COOKING, 300);
		getQuest().sendQuestCompleteInterface(player, 1891, "300 Cooking XP");
	}
}
