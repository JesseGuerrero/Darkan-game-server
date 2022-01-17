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
//  Copyright © 2021 Trenton Kress
//  This file is part of project: Darkan
//
package com.rs.game.player.content.commands.normal;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.db.WorldDB;
import com.rs.game.World;
import com.rs.game.ge.Offer;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.content.commands.Command;
import com.rs.game.player.content.commands.Commands;
import com.rs.game.region.ClipFlag;
import com.rs.game.region.RenderFlag;
import com.rs.lib.game.Rights;
import com.rs.lib.util.Utils;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.annotations.ServerStartupEvent;

import java.util.Arrays;

@PluginEventHandler
public class Shennanigans {

	@ServerStartupEvent
	public static void loadCommands() {
        Commands.add(Rights.PLAYER, "bank", "Opens the bank.", (p, args) -> {
            p.getBank().open();
        });

        Commands.add(Rights.PLAYER, "coords,getpos,mypos,pos,loc", "Gets the coordinates for the tile.", (p, args) -> {
            p.sendMessage("Coords: " + p.getX() + "," + p.getY() + "," + p.getPlane() + ", regionId: " + p.getRegionId() + ", chunkX: " + p.getChunkX() + ", chunkY: " + p.getChunkY());
            p.sendMessage("JagCoords: " + p.getPlane() + ","+p.getRegionX()+","+p.getRegionY()+","+p.getXInScene(p.getSceneBaseChunkId())+","+p.getYInScene(p.getSceneBaseChunkId()));
        });

        Commands.add(Rights.PLAYER, "search,si,itemid [item name]", "Searches for items containing the words searched.", (p, args) -> {
            p.getPackets().sendDevConsoleMessage("Searching for items containing: " + Arrays.toString(args));
            for (int i = 0; i < Utils.getItemDefinitionsSize(); i++) {
                boolean contains = true;
                for (int idx = 0; idx < args.length; idx++) {
                    if (!ItemDefinitions.getDefs(i).getName().toLowerCase().contains(args[idx].toLowerCase()) || ItemDefinitions.getDefs(i).isLended()) {
                        contains = false;
                        continue;
                    }
                }
                if (contains)
                    p.getPackets().sendDevConsoleMessage("Result found: " + i + " - " + ItemDefinitions.getDefs(i).getName() + " " + (ItemDefinitions.getDefs(i).isNoted() ? "(noted)" : "") + "" + (ItemDefinitions.getDefs(i).isLended() ? "(lent)" : ""));
            }
        });


        Commands.add(Rights.PLAYER, "pestp", "Gives pest control points", (p, args) -> {
            p.setPestPoints(p.getPestPoints() + 200);
            p.sendMessage("Pestp points");
        });

        Commands.add(Rights.PLAYER, "tileflag", "Get the tile flags for the tile you're standing on.", (p, args) -> {
            p.sendMessage("" + ClipFlag.getFlags(World.getClipFlags(p.getPlane(), p.getX(), p.getY())) + " - " + RenderFlag.getFlags(World.getRenderFlags(p.getPlane(), p.getX(), p.getY())));
        });



        Commands.add(Rights.PLAYER, "onlinep", "diaplays all players", (p, args) -> {
            for(Player player : World.getPlayers())
                p.sendMessage("On World" + player.getUsername());
        });

        Commands.add(Rights.PLAYER, "resett", "Displays all the commands the player has permission to use.", (p, args) -> {
            p.getSlayer().removeTask();
            p.updateSlayerTask();
        });

        Commands.add(Rights.PLAYER, "tokenator", "Gives the specified player dungeoneering tokens.", (p, args) -> {
            p.getDungManager().addTokens(200000);
            p.sendMessage("Successfully gave tokens..");
        });

        Commands.add(Rights.PLAYER, "addslayer", "More points", (p, args) -> {
            p.addSlayerPoints(300);
        });
	}

}
