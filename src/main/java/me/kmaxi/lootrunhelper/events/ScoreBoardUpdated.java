package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.data.CurrentData;
import me.kmaxi.lootrunhelper.utils.CodingUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.ScoreboardPlayerUpdateS2CPacket;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.ServerScoreboard;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

public class ScoreBoardUpdated {

    private static HashMap<String, String> realToMyMessage = new HashMap<>();

    public static void reset() {
        realToMyMessage.clear();
    }

    public static void onScoreChanged(ScoreboardPlayerUpdateS2CPacket packet, CallbackInfo ci) {
        String playerName = packet.getPlayerName();

        if (!playerName.toLowerCase().contains("challenges:"))
            return;
        
        String challengeNumber =  playerName.substring(playerName.indexOf(": ") + 2, playerName.indexOf('/'));
        challengeNumber = challengeNumber.replaceAll("\\D", "");
        int challengeNumberInt = Integer.parseInt(challengeNumber);
        if (challengeNumberInt == 0 && CurrentData.hasFinishedLootrun() && packet.getUpdateMode() == ServerScoreboard.UpdateMode.CHANGE){
            Events.lootrunStarted();
        }

        boolean hasChangeThisName = realToMyMessage.containsKey(playerName);
        int activeReds = CurrentData.getRedChallengeCount();
        if (activeReds == 0 && !hasChangeThisName
        || !hasChangeThisName && packet.getUpdateMode() == ServerScoreboard.UpdateMode.REMOVE ) //|| playerName.contains("§c("
            return;

        ci.cancel();

        if (realToMyMessage.containsKey(playerName)) {
            playerName = realToMyMessage.get(playerName);
        } else if (packet.getUpdateMode() == ServerScoreboard.UpdateMode.REMOVE) {
            playerName = packet.getPlayerName();
        } else {
            String newPlayerName = playerName + " §c(" + activeReds + ")";
            realToMyMessage.put(playerName, newPlayerName);
            playerName = newPlayerName;
        }

        CodingUtils.msg("New player name: " + playerName + " | Mode: " + packet.getUpdateMode() + " | Score: " + packet.getScore());


        assert MinecraftClient.getInstance().player != null;
        NetworkThreadUtils.forceMainThread(packet, MinecraftClient.getInstance().player.networkHandler, MinecraftClient.getInstance());
        Scoreboard scoreboard = MinecraftClient.getInstance().world.getScoreboard();

        String objectiveName = packet.getObjectiveName();
        switch (packet.getUpdateMode()) {
            case CHANGE: {
                ScoreboardObjective scoreboardObjective = scoreboard.getObjective(objectiveName);
                ScoreboardPlayerScore scoreboardPlayerScore = scoreboard.getPlayerScore(playerName, scoreboardObjective);
                scoreboardPlayerScore.setScore(packet.getScore());
                break;
            }
            case REMOVE: {
                scoreboard.resetPlayerScore(playerName, scoreboard.getNullableObjective(objectiveName));
            }
        }
    }


}
