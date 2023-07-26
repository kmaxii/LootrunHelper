package me.kmaxi.lootrunhelper.events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.MinecartCommandBlockScreen;
import net.minecraft.text.Text;

public class ReceiveTitleEvent {
    public static void receivedTitle(String title){
        title.toLowerCase().replaceAll("[^abcdefghijklmnopqrstuvwxyz?.!0123456789/]", "");

        if (!title.equalsIgnoreCase("spelunk!")||!title.equalsIgnoreCase("defend!")||!title.equalsIgnoreCase("slay target!")||!title.equalsIgnoreCase("destroy")) {
            return;
        }


        MinecraftClient.getInstance().player.sendMessage(Text.of("Started Challange"));
    }
}
