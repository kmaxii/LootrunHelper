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

        msg("§b"+
                "Walk Speed Curse: " + getEnemyWalkSpeedCurse() +"\n" +
                "Enemy Attackspeed Curse: " + getEnemyAttackSpeedCurse()+"\n" +
                "Enemy Health Curse: " + getEnemyHealthCurse()+"\n" +
                "Enemy Resistance Curse: " + getEnemyResistanceCurse()+"\n" +
                "Enemy Damage Curse: " + getEnemyDamageCurse() +"\n" +
                "Vibrant Count: " + getVibrantCount()+"\n" +
                "Rerolls count: " + getRerollsCount()+"\n" +
                "Pull count: "+ getPullsCount() +"\n" +
                "Challanges from White: "+ getChallengesFromWhiteCount()+"\n" +
                "Curse count: "+getCursesCount());

        return 1;

    }
}
