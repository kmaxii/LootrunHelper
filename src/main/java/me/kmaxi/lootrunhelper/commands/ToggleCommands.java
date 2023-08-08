package me.kmaxi.lootrunhelper.commands;

import com.mojang.brigadier.context.CommandContext;
import me.kmaxi.lootrunhelper.data.BooleanHashMap;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class ToggleCommands {

    public static int toggleBeacons(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext){
        BooleanHashMap.setShowBeacons(!BooleanHashMap.showBeacons());
        return 1;
    }

    public static int toggleDestinations(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext) {

        BooleanHashMap.setShowDestinations(!BooleanHashMap.showDestinations());
        return 1;
    }
}
