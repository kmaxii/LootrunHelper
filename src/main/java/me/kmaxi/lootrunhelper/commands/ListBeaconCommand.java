package me.kmaxi.lootrunhelper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import me.kmaxi.lootrunhelper.BeaconChecker;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class ListBeaconCommand {
    public static int run(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext) {
        System.out.println("Executing list beacon command");
        BeaconChecker.activeDataSaver().sendDataToChat();
        return 1;
    }
}
