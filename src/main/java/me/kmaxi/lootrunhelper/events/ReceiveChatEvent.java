package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.BeaconChecker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ReceiveChatEvent {

    public static void receivedChat(String message) {
        System.out.println("got message: " + message);

        if (message.startsWith("Select a character!")) {
            BeaconChecker.enabled = false;
            return;
        }

        if (!message.startsWith("\n" +
                "§7§r                         ÀÀ§6§lChoose a Beacon!")) {
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(Text.of("Found start"));
        BeaconChecker.enabled = true;
    }

}
