package me.kmaxi.lootrunhelper.events.mixins;

import me.kmaxi.lootrunhelper.events.ReceiveTitleEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinTitleListener {
    @Inject(method = "onTitle", at = @At("HEAD"))
    private void onTitle(TitleS2CPacket packet, CallbackInfo ci) {
        // Your code here
        ReceiveTitleEvent.receivedTitle(packet.getTitle().getString());
    }
}
