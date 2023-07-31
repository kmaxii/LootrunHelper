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

        msg("\n                                 §5§lCurses\n" +
                "      §5Curse count: §d" + getCursesCount()+ "                    §5Walk Speed Curse: §d" + getEnemyWalkSpeedCurse() + "§7%\n" +
                "   §5Attackspeed Curse: §d" + getEnemyAttackSpeedCurse() + "§7%" +"                 §5Health Curse: §d" + getEnemyHealthCurse() + "§7%\n" +
                "    §5Resistance Curse: §d" + getEnemyResistanceCurse() + "§7%" +"                 §5Damage Curse: §d" + getEnemyDamageCurse() + "§7%\n" +
                "\n                               §4§lMob Buffs\n" +
                "        §cHealth: §f" + CurrentData.getEnemyHealthChallenge() +"§7%                                §cDamage: §f" + CurrentData.getEnemyDamageChallenge() + "§7%" +

                "\n                                  §6§lRolls\n" +
                "        §6Pull count: §f" + getPullsCount() + "                       §6Rerolls count: §f" + getRerollsCount() + "\n" +
                "      §6Effective pulls: §f" + getEffectivePulls() + "\n" +
                "\n                            §3§lChallenge info\n" +
                "   §bVibrants taken: §f" + getVibrantCount()  +"                     §bChallanges from White: §f" + getChallengesFromWhiteCount()+"\n");

        return 1;

    }
}
