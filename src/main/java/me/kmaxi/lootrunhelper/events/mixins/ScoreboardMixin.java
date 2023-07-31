package me.kmaxi.lootrunhelper.events.mixins;

import me.kmaxi.lootrunhelper.events.ScoreBoardUpdated;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ScoreboardPlayerUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ScoreboardMixin {


    @Inject(method = "onScoreboardPlayerUpdate", at = @At("HEAD"), cancellable = true)
    private void onScoreChanged(ScoreboardPlayerUpdateS2CPacket packet, CallbackInfo ci) {
        ScoreBoardUpdated.onScoreChanged(packet, ci);

    }
}