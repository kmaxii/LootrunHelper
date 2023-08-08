package me.kmaxi.lootrunhelper.commands;

import com.mojang.brigadier.context.CommandContext;
import me.kmaxi.lootrunhelper.beacon.AquaType;
import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.data.BooleanHashMap;
import me.kmaxi.lootrunhelper.utils.CodingUtils;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class SendAquaInformation {

    public static int sendInfo(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext){
        AquaType aquaType = BeaconChecker.activeDataSaver().aquaInfo();
        switch (aquaType) {
            case BAD -> CodingUtils.msg("§4Should not take §baqua§4. Can not get second aqua after");
            case GOOD -> CodingUtils.msg("§bAqua §ais good to take. Can get second aqua after");
        }
        return 1;
    }
}
