package me.kmaxi.lootrunhelper.commands;

import com.mojang.brigadier.context.CommandContext;
import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.BeaconDestinations;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import static me.kmaxi.lootrunhelper.utils.CodingUtils.msg;

public class ListBeaconDestinations {

    public static int run(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext) {
        if (BeaconChecker.getLastBeacons() == null){
            msg("§4No current beacons active.");
            return 0;
        }else {


            System.out.println("Executing list command");
            BeaconDestinations.updateDestinations();

            MinecraftClient.getInstance().player.sendMessage(Text.of(BeaconDestinations.destinations), false);

            return 1;
        }
    }


}
