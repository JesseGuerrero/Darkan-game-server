package com.rs.game.content.quests.handlers.tearsofguthix;

import com.rs.game.content.dialogue.Conversation;
import com.rs.game.content.dialogue.Dialogue;
import com.rs.game.content.dialogue.HeadE;
import com.rs.game.content.quests.Quest;
import com.rs.game.model.entity.player.Player;
import com.rs.game.tasks.WorldTasks;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.events.ObjectClickEvent;
import com.rs.plugin.handlers.ObjectClickHandler;

import static com.rs.game.content.quests.handlers.familycrest.FamilyCrest.*;
import static com.rs.game.content.quests.handlers.tearsofguthix.TearsOfGuthix.GET_BOWL;

@PluginEventHandler
public class JunaTearsOfGuthix extends Conversation {
	private static final int NPC = 2023;
	public JunaTearsOfGuthix(Player player) {
		this(player, false);
	}
	public JunaTearsOfGuthix(Player p, boolean tellingStory) {
		super(p);
		switch(p.getQuestManager().getStage(Quest.FAMILY_CREST)) {
		case NOT_STARTED -> {
			addNPC(NPC, HeadE.CAT_CALM_TALK, "Tell me... a story...");
			addOptions("Choose an option:", ops -> {
				ops.add("You tell me a story.", new Dialogue()
						.addPlayer(HeadE.HAPPY_TALKING, "You tell me a story.")
						.addNPC(NPC, HeadE.CAT_CALM_TALK, "The Third Age of the world was a time of great conflict, of destruction never seen before or since, " +
								"when all the gods save Guthix warred for control. The colossal wyrms, of whom today's dragons are a pale reflection, ")
						.addNPC(NPC, HeadE.CAT_CALM_TALK, "turned all the sky to fire, while on the ground armies of foot soldiers – goblins and trolls and " +
								"humans – filled the valleys and plains with blood. In time, the noise of conflict woke Guthix from His deep slumber, and")
						.addNPC(NPC, HeadE.CAT_CALM_TALK, "He rose and stood in the centre of the battlefield so that the splendour of His wrath filled the world, " +
								"and He called for the conflict to cease! Silence fell, for the gods knew that none could challenge the power of the mighty Guthix; ")
						.addNPC(NPC, HeadE.CAT_CALM_TALK, "for His power is that of nature itself, to which all other things are subject, in the end. Guthix " +
								"reclaimed that which had been stolen from Him, and went back underground to His sleep and continue to draw the world's power into ")
						.addNPC(NPC, HeadE.CAT_CALM_TALK, "Himself. But on His way into the depths of the earth He sat and rested in this cave; and, thinking of " +
								"the battle-scarred desert that now streched from one side of His world to the other, He wept. And so great was His sorrow, and so great")
						.addNPC(NPC, HeadE.CAT_CALM_TALK, "was His life-giving power, that the rocks themselves began to weep with Him. Later, Guthix noticed " +
								"that the rocks continued to weep, and that their tears was infused with a small part of His power. So He set me, His servant, ")
						.addNPC(NPC, HeadE.CAT_CALM_TALK, "to guard the cave, and He entrusted me the task of judging who was and was not worthy to access the tears.")
						.addPlayer(HeadE.CALM_TALK, "I see...")
						.addPlayer(HeadE.HAPPY_TALKING, "Good story!")
						.addNPC(NPC, HeadE.CAT_CALM_TALK, "Thank you.")
				);
				ops.add("A story?", new Dialogue()
						.addPlayer(HeadE.HAPPY_TALKING, "A story?")
						.addNPC(NPC, HeadE.CAT_CALM_TALK, "I have been waiting here for three thousand years, guarding the Tears of Guthix. I serve my master " +
								"faithfully, but I am bored. An adventurer such as yourself must have many tales to tell.")
						.addNPC(NPC, HeadE.CAT_CALM_TALK, "If you can entertain me, I will let you in the cave for a time. The more I enjoy your story, " +
								"the more time I will give you in the cave. Then you can drink the power of balance, which will make you stronger in whatever area you are weakest.")
						.addPlayer(HeadE.HAPPY_TALKING, "That sounds like a good deal!")
						.addNPC(NPC, HeadE.CAT_CALM_TALK, "It is.")
				);
				ops.add("Okay...", new Dialogue()
						.addSimple("You tell Juna about some stories...")
						.addNext(()->{
							p.lock(3);
							p.getInterfaceManager().fadeOut();
							WorldTasks.delay(3,()->{
								p.getInterfaceManager().fadeIn();
								p.getQuestManager().setStage(Quest.TEARS_OF_GUTHIX, GET_BOWL);
								p.startConversation(new JunaTearsOfGuthix(p, true).getStart());
							});
						})
				);
			});

		}
		case GET_BOWL -> {
			if(tellingStory) {
				addPlayer(HeadE.CALM_TALK, "And, that's what happened...");
				addPlayer(HeadE.HAPPY_TALKING, "Amazing right?");
				addNPC(NPC, HeadE.CAT_CALM_TALK, "Yes, you have entertained me");
			}
			if(p.getInventory().containsItem(4704)) {
				//p.getQuestManager().setStage(Quest.TEARS_OF_GUTHIX, GET_BOWL);
				return;
			}
			addNPC(NPC, HeadE.CAT_CALM_TALK, "Before you can collect the Tears of Guthix you must make a bowl out of the stone " +
							"in the cave on the south of the chasm.");
			addPlayer(HeadE.HAPPY_TALKING, "But, how do I get there?");
			addNPC(NPC, HeadE.CAT_CALM_TALK, "Use the light creatures...");
			addPlayer(HeadE.HAPPY_TALKING, "What?");
			addNPC(NPC, HeadE.CAT_CALM_TALK, "Yes, the light creatures..");
			addPlayer(HeadE.HAPPY_TALKING, "Umm...");
			addNPC(NPC, HeadE.CAT_CALM_TALK, "Sssssst!");
			addPlayer(HeadE.HAPPY_TALKING, "Okay, I'll do it!");
		}
		case TALK_TO_GEM_TRADER -> {

		}
		case TALK_TO_AVAN -> {

		}
		case TALK_TO_BOOT -> {

		}
		case QUEST_COMPLETE ->  {

		}
		}
	}


    public static ObjectClickHandler handleDialogue = new ObjectClickHandler(new Object[]{6657}) {
        @Override
        public void handle(ObjectClickEvent e) {
            e.getPlayer().startConversation(new JunaTearsOfGuthix(e.getPlayer()).getStart());
        }
    };
}
