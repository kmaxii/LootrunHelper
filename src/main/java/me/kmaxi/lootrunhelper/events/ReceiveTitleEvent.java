package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.BeaconChecker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ReceiveTitleEvent {
    public static void receivedTitle(String title) {
        title = title.toLowerCase().replaceAll("[^abcdefghijklmnopqrstuvwxyz?.! /]", "");

        System.out.println("Title: " + title);

        if (!title.equalsIgnoreCase("spelunk!")
                && !title.equalsIgnoreCase("defend!")
                && !title.equalsIgnoreCase("slay target!")
                && !title.equalsIgnoreCase("destroy")) {
            return;
        }


        MinecraftClient.getInstance().player.sendMessage(Text.of("Started Challange"));
        BeaconChecker.enabled = false;
    }
}