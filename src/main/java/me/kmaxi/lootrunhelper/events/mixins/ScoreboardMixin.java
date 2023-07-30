package me.kmaxi.lootrunhelper.events.mixins;

import me.kmaxi.lootrunhelper.data.CurrentData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.ScoreboardPlayerUpdateS2CPacket;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ScoreboardMixin {
    @Inject(method = "onScoreboardPlayerUpdate", at = @At("HEAD"), cancellable = true)
    private void onScoreChanged(ScoreboardPlayerUpdateS2CPacket packet, CallbackInfo ci) {

        String playerName = packet.getPlayerName();

        int activeReds = CurrentData.getRedChallengeCount();
        if (activeReds == 0 || !playerName.contains("Challenges:"))
            return;

        String objectiveName = packet.getObjectiveName();

        ci.cancel();
        playerName += "§c(" + activeReds + ")";

        packet = new ScoreboardPlayerUpdateS2CPacket(packet.getUpdateMode(), objectiveName, playerName, packet.getScore());

        NetworkThreadUtils.forceMainThread(packet, MinecraftClient.getInstance().player.networkHandler, MinecraftClient.getInstance());
        Scoreboard scoreboard = MinecraftClient.getInstance().world.getScoreboard();
        String string = packet.getObjectiveName();
        switch (packet.getUpdateMode()) {
            case CHANGE:
                ScoreboardObjective scoreboardObjective = scoreboard.getObjective(string);
                ScoreboardPlayerScore scoreboardPlayerScore = scoreboard.getPlayerScore(packet.getPlayerName(), scoreboardObjective);
                scoreboardPlayerScore.setScore(packet.getScore());
                break;
            case REMOVE:
                scoreboard.resetPlayerScore(packet.getPlayerName(), scoreboard.getNullableObjective(string));
        }


    }
}