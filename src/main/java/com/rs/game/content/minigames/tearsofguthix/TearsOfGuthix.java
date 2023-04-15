package com.rs.game.content.minigames.tearsofguthix;

import com.rs.game.map.ChunkManager;
import com.rs.game.model.object.GameObject;
import com.rs.game.tasks.WorldTasks;
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

	private static boolean isLeftTear(GameObject well) {
		return isOneIntAway(Tear.ABSENTLEFT.id, well.getId());
	}

	private static boolean isRightTear(GameObject well) {
		return isOneIntAway(Tear.ABSENTRIGHT.id, well.getId());
	}

	private static boolean isBlue(GameObject well) {
		return (well.getId() == Tear.BLUELEFT.id || well.getId() == Tear.BLUERIGHT.id);
	}

	private static boolean isGreen(GameObject well) {
		return (well.getId() == Tear.GREENLEFT.id || well.getId() == Tear.GREENRIGHT.id);
	}

	private static boolean isAbsent(GameObject well) {
		return (well.getId() == Tear.ABSENTLEFT.id || well.getId() == Tear.ABSENTRIGHT.id);
	}

	private static void setTearBlue(GameObject well) {
		if(isLeftTear(well))
			well.setId(Tear.BLUELEFT.id);
		else
			well.setId(Tear.BLUERIGHT.id);
	}

	private static void setTearGreen(GameObject well) {
		if(isLeftTear(well))
			well.setId(Tear.GREENLEFT.id);
		else
			well.setId(Tear.GREENRIGHT.id);
	}

	private static void setTearAbsent(GameObject well) {
		if(isLeftTear(well))
			well.setId(Tear.ABSENTLEFT.id);
		else
			well.setId(Tear.ABSENTRIGHT.id);
	}

	private static int getTearColorIndex(GameObject well) {
		if(isBlue(well))
			return 0;
		else if(isGreen(well))
			return 1;
		else
			return 2;
	}

	private static void setTearByIndex(GameObject tear, int index) {
		if(index == 0)
			setTearBlue(tear);
		else if(index == 1)
			setTearGreen(tear);
		else
			setTearAbsent(tear);
	}

	public static int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
		int random = start + rnd.nextInt(end - start + 1 - exclude.length);
		for (int ex : exclude) {
			if (random < ex) {
				break;
			}
			random++;
		}
		return random;
	}

	@ServerStartupEvent(Priority.POST_PROCESS)
	public static void dripTears() {
		List<GameObject> tears = new ArrayList<>();
		for(int chunkId : new int[] {9223334, 9223333})
			for (GameObject obj : ChunkManager.getChunk(chunkId, true).getBaseObjects())
				if (getTearIds().contains(obj.getId()))
					tears.add(obj);
		Set<Integer> blueTearsChanged = new HashSet<>();
		Set<Integer> greenTearsChanged = new HashSet<>();
		Set<Integer> absentTearsChanged = new HashSet<>();
		WorldTasks.scheduleTimer(3, 5, (tick)-> {
			if(blueTearsChanged.size() >= 3) {
				blueTearsChanged.clear();
				greenTearsChanged.clear();
				absentTearsChanged.clear();
				List<GameObject> tearsBuffer = new ArrayList<>();
				for(GameObject tear : tears)
					if(isBlue(tear))
						tearsBuffer.add(tear);
				for(GameObject tear : tears)
					if(isGreen(tear))
						tearsBuffer.add(tear);
				for(GameObject tear : tears)
					if(isAbsent(tear))
						tearsBuffer.add(tear);
				tears.clear();
				tears.addAll(tearsBuffer);
			}

			int tear1Index = getRandomWithExclusion(new Random(), 0, 2, blueTearsChanged.stream().mapToInt(i->i).toArray());
			blueTearsChanged.add(tear1Index);
			int tear2Index = getRandomWithExclusion(new Random(), 3, 5, greenTearsChanged.stream().mapToInt(i->i).toArray());
			greenTearsChanged.add(tear2Index);
			int tear3Index = getRandomWithExclusion(new Random(), 6, 8, absentTearsChanged.stream().mapToInt(i->i).toArray());
			absentTearsChanged.add(tear3Index);

			int firstColor = getRandomWithExclusion(new Random(), 0, 2, getTearColorIndex(tears.get(tear1Index)));
			int[] exclude = new int[]{firstColor};
			Arrays.sort(exclude);
			int secondColor = getRandomWithExclusion(new Random(), 0, 2, exclude);
			exclude = new int[]{firstColor, secondColor};
			Arrays.sort(exclude);
			int thirdColor = getRandomWithExclusion(new Random(), 0, 2, exclude);

			setTearByIndex(tears.get(tear1Index), firstColor);
			setTearByIndex(tears.get(tear2Index), secondColor);
			setTearByIndex(tears.get(tear3Index), thirdColor);

			return true;
		});
	}
}
