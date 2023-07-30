package me.kmaxi.lootrunhelper.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class CodingUtils {
    public static void msg(String msg){
        if (MinecraftClient.getInstance() != null && MinecraftClient.getInstance().player != null)
            MinecraftClient.getInstance().player.sendMessage(Text.of(msg));
    }
   // public static ClientPlayerEntity player = MinecraftClient.getInstance().player;


    public static String removeColorCodes(String input) {
        // Define the regex pattern to match color codes (§ followed by a color code character)
        String regex = "§[0-9A-Fa-fK-Ok-oRr]";

        // Replace all occurrences of color codes with an empty string
        return input.replaceAll(regex, "");
    }
}
