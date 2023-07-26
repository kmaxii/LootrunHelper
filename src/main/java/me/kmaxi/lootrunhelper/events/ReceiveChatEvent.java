package me.kmaxi.lootrunhelper.events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ReceiveChatEvent {

    public static void receivedChat(String message){
        if (!message.equals("§7§r                         ÀÀ§6§lChoose a Beacon!")){
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(Text.of("Found start"));
    }
}
