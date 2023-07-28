package me.kmaxi.lootrunhelper.commands;

import com.mojang.brigadier.context.CommandContext;
import me.kmaxi.lootrunhelper.beacon.BeaconDestinations;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ListBeaconDestinations {

    public static int run(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext) {
        System.out.println("Executing list command");

        BeaconDestinations.updateDestinations();

        MinecraftClient.getInstance().player.sendMessage(Text.of(BeaconDestinations.destinations), false);

        return 1;
    }


}
