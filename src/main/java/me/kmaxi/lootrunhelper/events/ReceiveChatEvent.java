package me.kmaxi.lootrunhelper.events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ReceiveChatEvent {

    public static void receivedChat(String message){
        System.out.println("got message: " + message);

        if (!message.startsWith("\n" +
                "§7§r                         ÀÀ§6§lChoose a Beacon!")){
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(Text.of("Found start"));
    }

}
