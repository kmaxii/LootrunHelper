package me.kmaxi.lootrunhelper.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class CodingUtils {
    public static void msg(String msg){
        MinecraftClient.getInstance().player.sendMessage(Text.of(msg));
    }
    public static ClientPlayerEntity player = MinecraftClient.getInstance().player;
}
