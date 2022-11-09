package com.rs.game.content.minigames.tearsofguthix;

import com.rs.game.World;
import com.rs.game.model.object.GameObject;
import com.rs.game.region.Region;
import com.rs.game.tasks.WorldTasks;
import com.rs.lib.game.WorldTile;
import com.rs.lib.util.Utils;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.annotations.ServerStartupEvent;
import com.rs.plugin.annotations.ServerStartupEvent.Priority;


import java.util.*;

@PluginEventHandler
public class TearsOfGuthix {
	private enum Tear {
		ABSENTLEFT(6663),
		ABSENTRIGHT(6667),
		BLUELEFT(6661),
		BLUERIGHT(6665),
		GREENLEFT(6662),
		GREENRIGHT(6666);
		private final int id;
		Tear(int id) {
			this.id = id;
		}
	}
	private static boolean isOneIntAway(int middle, int away) {
		if(Math.abs(away - middle) <= 1)
			return true;
		return false;
	}

	public static List<Integer> getTearIds() {
		return Arrays.asList(6661, 6662, 6663, 6665, 6666, 6667);
	}

	private static boolean isLeftWell(GameObject well) {
		return isOneIntAway(Tear.ABSENTLEFT.id, well.getId());
	}

	private static boolean isRightWell(GameObject well) {
		return isOneIntAway(Tear.ABSENTRIGHT.id, well.getId());
	}

	public static int getRandomLeftTear() {
		return Utils.random(6661, 6664);
	}
	public static int getRandomRightTear() {
		return Utils.random(6665, 6668);
	}

	@ServerStartupEvent(Priority.POST_PROCESS)
	public static void dripTears() {
		Region junasLair = World.getRegion(12948, true);
		List<GameObject> tears = new ArrayList<>();
		for(GameObject obj : junasLair.getAllObjects())
			if(getTearIds().contains(obj.getId()))
				tears.add(obj);
		WorldTasks.scheduleTimer(3, 3, (tick)-> {
			if(tick % 3 == 0) {
				GameObject well = tears.get(Utils.random(tears.size()));
				if(isLeftWell(well))
					well.setId(getRandomLeftTear());
				if(isRightWell(well))
					well.setId(getRandomRightTear());
			}
			return true;
		});
	}
}
