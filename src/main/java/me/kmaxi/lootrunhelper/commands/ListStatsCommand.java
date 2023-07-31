package me.kmaxi.lootrunhelper.commands;

import com.mojang.brigadier.context.CommandContext;
import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.BeaconDestinations;
import me.kmaxi.lootrunhelper.data.CurrentData;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import static me.kmaxi.lootrunhelper.data.CurrentData.*;
import static me.kmaxi.lootrunhelper.utils.CodingUtils.msg;

public class ListStatsCommand {


    public static int run(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext) {

        msg("\n                                              §5§lCurses\n" +
                "   §5Curse count: §f" + getCursesCount()+ "                       §5Walk Speed Curse: §f" + getEnemyWalkSpeedCurse() + "§7%\n" +
                "   §5Attackspeed Curse: §f" + getEnemyAttackSpeedCurse() + "§7%" +"                       §5Health Curse: §f" + getEnemyHealthCurse() + "§7\n" +
                "   §5Resistance Curse: §f" + getEnemyResistanceCurse() + "§7%" +"                       §5Damage Curse: §f" + getEnemyDamageCurse() + "§7%\n" +
                "\n                                              §6§lRolls\n" +
                "   §6Pull count: §f" + getPullsCount() + "                       §6Rerolls count: §f" + getRerollsCount() + "\n" +
                "                       §6Effective pulls: §f" + getPullsCount()+getPullsCount()*getRerollsCount() + "\n" +
                "\n                                              §3§lChallenge info\n" +
                "   §bVibrant Count: §f" + getVibrantCount()  +"                       §bChallanges from White: §f" + getChallengesFromWhiteCount()+"\n" +
                "\n                                              §4§lMobs Buffs\n" +
                "   §cHealth from challanges: §f" + CurrentData.getEnemyHealthChallenge() +"                       §cDamage from challanges: §f" + CurrentData.getEnemyDamageChallenge() + "\n");

        return 1;

    }
}
