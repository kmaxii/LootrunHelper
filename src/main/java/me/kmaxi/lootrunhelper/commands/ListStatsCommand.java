package me.kmaxi.lootrunhelper.commands;

import com.mojang.brigadier.context.CommandContext;
import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.data.CurrentData;
import me.kmaxi.lootrunhelper.utils.CodingUtils;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import static me.kmaxi.lootrunhelper.data.CurrentData.*;
import static me.kmaxi.lootrunhelper.utils.message.CenteredTextSender.*;

public class ListStatsCommand {



    public static int run(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext) {

        CurrentData.loadFromFile();
        StringBuilder toSend = new StringBuilder();

        toSend.append(getCenteredMessage("§a§lBeacons"));
        toSend.append(newLine);
        toSend.append(BeaconChecker.activeDataSaver().getBeaconDataCentered());

        toSend.append(newLine);
        toSend.append(getCenteredMessage("§5§lCurses"));
        toSend.append(newLine);
        toSend.append(getCenteredMessage("§5Curse count: §d" + getCursesCount(), "§5Walk Speed Curse: §d" + getEnemyWalkSpeedCurse() + "§7%"));
        toSend.append(newLine);
        toSend.append(getCenteredMessage("§5Attackspeed Curse: §d" + getEnemyAttackSpeedCurse() + "§7%"
                , "§5Health Curse: §d" + getEnemyHealthCurse() + "§7%"));
        toSend.append(newLine);
        toSend.append(getCenteredMessage("§5Resistance Curse: §d" + getEnemyResistanceCurse() + "§7%"
                , "§5Damage Curse: §d" + getEnemyDamageCurse() + "§7%"));
        toSend.append(newLine);
        toSend.append(getCenteredMessage("§4§lMob Buffs"));
        toSend.append(newLine);
        toSend.append(getCenteredMessage("§cHealth: §f+" + CurrentData.getEnemyHealthChallenge() + "§7%"
                , "§cDamage: §f+" + CurrentData.getEnemyDamageChallenge() + "§7%"));
        toSend.append(newLine);
        toSend.append(getCenteredMessage("§3§lChallenge info"));
        toSend.append(newLine);
        toSend.append(getCenteredMessage("§bVibrants taken: §f" + getVibrantCount(), "§bChallanges from White: §f" + getChallengesFromWhiteCount()));
        toSend.append(newLine);
        toSend.append(getCenteredMessage("§bChallenges failed: §f" + getChallengesFailedCount(), "§bChallanges completed: §f" + getFinishedChallengesCount()));
        toSend.append(newLine);
        toSend.append(getCenteredMessage("§bBoons collected: §f" + getBoonPickedCount(), "§bBoons offered: §f" + getBoonOfferedCount()));
        toSend.append(newLine);
        toSend.append(getCenteredMessage("§6§lRolls"));
        toSend.append(newLine);
        toSend.append(getCenteredMessage("§6Pull count: §f" + getPullsCount(), "§6Rerolls count: §f" + getRerollsCount()));
        toSend.append(newLine);
        toSend.append(getCenteredMessage("§6Effective pulls: §f" + getEffectivePulls()));
        CodingUtils.msg(toSend.toString());
        return 1;
    }
}
