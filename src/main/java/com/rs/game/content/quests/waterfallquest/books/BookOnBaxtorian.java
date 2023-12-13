package com.rs.game.content.quests.waterfallquest.books;

import com.rs.engine.book.Book;
import com.rs.engine.quest.Quest;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.handlers.ItemClickHandler;

@PluginEventHandler
public class BookOnBaxtorian extends Book {
    public BookOnBaxtorian() {
        super("Book on Baxtorian", """
                <u>The Missing Relics</u>
                Many artefacts of elven history were lost after the Fourth Age, following the departure of the elves from these lands. The greatest loss to our collections of elf history were the hidden treasures of Baxtorian.
                                
                Some believe these treasures are still unclaimed, but it is more commonly believed that dwarf miners recovered them early in the Fifth Age.
                                
                Another great loss was Glarial's pebble, a key which allowed her family to visit her tomb. The pebble was taken by a gnome family over a century ago. It is hoped that descendants of that gnome may still have the pebble hidden in their cave under the Tree Gnome Village.
                                
                <u>The Sonnet of Baxtorian</u>
                The love between Baxtorian and Glarial was said to have lasted over a century. They lived a peaceful life learning and teaching the laws of nature.
                                
                When trouble hit their home in the west, Baxtorian left on a great campaign. He returned to find his people slaughtered and his wife taken by the enemy.
                                
                After years of searching for his love, he finally gave up and returned to the home he had made for Glarial under the Baxtorian Falls. Once he entered, he never returned.
                                
                Only he and Glarial had the power to enter the waterfall. Since Baxtorian entered, no one else has been able to get in. It's as if the powers of nature still work to protect him.
                                
                <u>The Power of Nature</u>
                Glarial and Baxtorian were masters of nature. Trees would grow, hills form and rivers flood on their command.
                                
                Baxtorian in particular had perfected rune lore. It was said that he could use the stones to control water, earth and air.
                                
                <u>Ode to Eternity</u>
                A short piece written by Baxtorian himself.
                                
                What care I for this mortal coil,
                where treasures are yet so frail,
                for it is you that is my life blood,
                the wine to my holy grail,
                and if I see the judgement day,
                when gods fill the air with dust,
                I'll happily choke on your memory,
                as my kingdom turns to rust.
                """);
    }

    public static ItemClickHandler read = new ItemClickHandler(new Object[] { 292 }, new String[] { "Read" }, e -> {
        if (e.getPlayer().getQuestStage(Quest.WATERFALL_QUEST) == 2) {
            e.getPlayer().sendMessage("You read the book and find that a gnome named Golrie may be able to help find a way into the falls.");
            e.getPlayer().setQuestStage(Quest.WATERFALL_QUEST, 3);
        }
        e.getPlayer().openBook(new BookOnBaxtorian());
    });
}
