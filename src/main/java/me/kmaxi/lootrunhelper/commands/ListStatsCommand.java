package me.kmaxi.lootrunhelper.commands;

import com.mojang.brigadier.context.CommandContext;
import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.data.CurrentData;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import static me.kmaxi.lootrunhelper.data.CurrentData.*;
import static me.kmaxi.lootrunhelper.utils.message.CenteredTextSender.sendCenteredMessage;
import static me.kmaxi.lootrunhelper.utils.message.CenteredTextSender.sendLeftCenteredMessage;

public class ListStatsCommand {


    public static int run(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext) {

        CurrentData.loadFromFile();

        sendCenteredMessage("§a§lBeacons");
        BeaconChecker.activeDataSaver().sendDataToChat();


        sendCenteredMessage("§5§lCurses");
        sendCenteredMessage("§5Curse count: §d" + getCursesCount(), "§5Walk Speed Curse: §d" + getEnemyWalkSpeedCurse() + "§7%");
        sendCenteredMessage("§5Attackspeed Curse: §d" + getEnemyAttackSpeedCurse() + "§7%"
                , "§5Health Curse: §d" + getEnemyHealthCurse() + "§7%");
        sendCenteredMessage("§5Resistance Curse: §d" + getEnemyResistanceCurse() + "§7%"
                , "§5Damage Curse: §d" + getEnemyDamageCurse() + "§7%");
        sendCenteredMessage("§4§lMob Buffs");
        sendCenteredMessage("§cHealth: §f" + CurrentData.getEnemyHealthChallenge() +"§7%"
                , "§cDamage: §f" + CurrentData.getEnemyDamageChallenge() + "§7%");
        sendCenteredMessage("§3§lChallenge info");
        sendCenteredMessage("§bVibrants taken: §f" + getVibrantCount(), "§bChallanges from White: §f" + getChallengesFromWhiteCount());
        sendCenteredMessage("§bChallenges failed: §f" + getChallengesFailedCount(), "§bChallanges completed: §f" + getFinishedChallengesCount());
        sendCenteredMessage("§bBoons collected: §f" + getBoonPickedCount(), "§bBoons offered: §f" + getBoonOfferedCount());
        sendCenteredMessage("§6§lRolls");
        sendCenteredMessage("§6Pull count: §f" + getPullsCount(), "§6Rerolls count: §f" + getRerollsCount());
        sendLeftCenteredMessage("§6Effective pulls: §f" + getEffectivePulls());
        return 1;
    }
}
