package me.kmaxi.lootrunhelper;

import me.kmaxi.lootrunhelper.commands.ListBeaconCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import static net.minecraft.server.command.CommandManager.literal;

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

        BeaconChecker beaconChecker = new BeaconChecker();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
           beaconChecker.onTick();
        });
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager
                    .literal("beacons")
                    .executes(ListBeaconCommand::run));
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("beacons")
                .executes(context -> {
                    // For versions below 1.19, replace "Text.literal" with "new LiteralText".

                    return 1;
                })));
    }
}
