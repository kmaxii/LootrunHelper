package me.kmaxi.lootrunhelper.events.mixins;

import me.kmaxi.lootrunhelper.events.SentCommandEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayNetworkHandler.class)

public abstract class SendMessageMixin {
    @Shadow
    public abstract void sendChatMessage(String content);

    @Inject(method = "sendChatCommand", at = @At("HEAD"))
    private void onGameMessage(String command, CallbackInfo ci) {
        SentCommandEvent.sentCommand(command);
    }
    @Inject(method = "sendCommand", at = @At("HEAD"))
    private void onGameMessage(String command, CallbackInfoReturnable<Boolean> cir) {
        SentCommandEvent.sentCommand(command);
    }

}
