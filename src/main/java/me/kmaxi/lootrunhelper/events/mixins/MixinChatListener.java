package me.kmaxi.lootrunhelper.events.mixins;

import me.kmaxi.lootrunhelper.events.ReceiveChatEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientPlayNetworkHandler.class)
public class MixinChatListener {
    @Inject(method = "onGameMessage", at = @At("HEAD"))
    private void onGameMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
        // Your code here
        ReceiveChatEvent.receivedChat(packet.content().getString());
        ReceiveChatEvent.finalMessage(packet.content().getString());
    }
}
