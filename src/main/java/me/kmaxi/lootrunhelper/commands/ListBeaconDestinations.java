package me.kmaxi.lootrunhelper.commands;

import com.mojang.brigadier.context.CommandContext;
import me.kmaxi.lootrunhelper.beacon.Beacon;
import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.challenges.Challenge;
import me.kmaxi.lootrunhelper.challenges.ChallengeList;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class ListBeaconDestinations {

    public static int run(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext) {
        System.out.println("Executing list command");

        List<Challenge> challenges = ChallengeList.getChallenges("locations/locations.json");


        for (Beacon beacon : BeaconChecker.getLastBeacons()) {
            Challenge challengeItLeadsTo = beacon.findChallengeItLeadsTo(challenges);
            MinecraftClient.getInstance().player.sendMessage(Text.of(
                    beacon.getBeaconName()
                    + "leads to §l" +
                    challengeItLeadsTo.getChallengeName() + "§r of type " + challengeItLeadsTo.getType()
                    + " and is §n" + getChallengeDistance(challengeItLeadsTo) + "§r blocks away"), false);
            System.out.println(beacon.beaconType + " leads to " + challengeItLeadsTo.getChallengeName() + " of type " + challengeItLeadsTo.getType());
        }
        return 1;
    }

    private static int getChallengeDistance(Challenge challenge) {
        Vec3d challengePos = new Vec3d(challenge.getX(), 50, challenge.getZ());
        Vec3d playerPos = MinecraftClient.getInstance().player.getPos();
        return (int) challengePos.distanceTo(playerPos);
    }
}
