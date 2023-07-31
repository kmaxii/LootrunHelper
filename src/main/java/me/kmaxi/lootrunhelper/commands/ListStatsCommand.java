package me.kmaxi.lootrunhelper.commands;

import com.mojang.brigadier.context.CommandContext;
import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.BeaconDestinations;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import static me.kmaxi.lootrunhelper.data.CurrentData.*;
import static me.kmaxi.lootrunhelper.utils.CodingUtils.msg;

public class ListStatsCommand {


    public static int run(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext) {

        msg("§5§lCurses: " + "\n" +
                "   §4§l!!!Curse stats currently not updating!!!" +"\n"+
                "   §5Walk Speed Curse: §f" + getEnemyWalkSpeedCurse() + "§7%\n" +
                "   §5Enemy Attackspeed Curse: §f" + getEnemyAttackSpeedCurse() + "§7%\n" +
                "   §5Enemy Health Curse: §f" + getEnemyHealthCurse() + "§7\n" +
                "   §5Enemy Resistance Curse: §f" + getEnemyResistanceCurse() + "§7%\n" +
                "   §5Enemy Damage Curse: §f" + getEnemyDamageCurse() + "§7%\n" +
                "   §5Curse count: §f" + getCursesCount()  + "\n"+
                "   §eVibrant Count: §f" + getVibrantCount() + "\n" +
                "   §ePull count: §f" + getPullsCount() + "\n" +
                "   §8Rerolls count: §f" + getRerollsCount() + "\n" +
                "   §f§lChallanges from White: §r§f" + getChallengesFromWhiteCount());

        return 1;

    }
}