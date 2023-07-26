package me.kmaxi.lootrunhelper;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class LootrunHelper  implements ModInitializer {
    private static KeyBinding keyBinding;

    @Override
    public void onInitialize() {

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.LootrunHelper.test", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K, // Default key to trigger event (K here)
                "category.my_mod.my_key_category" // The translation key of the keybinding's category.
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                BeaconHandler.getBeacons();
            }
        });
    }
}
