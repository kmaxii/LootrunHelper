package me.kmaxi.lootrunhelper;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.commands.CommandsRegister;
import me.kmaxi.lootrunhelper.utils.FileUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

import static me.kmaxi.lootrunhelper.ui.UIRenderer.renderTwoTextBlocksOnScreen;
import static me.kmaxi.lootrunhelper.utils.CodingUtils.msg;

public class LootrunHelper implements ModInitializer {
    public static KeyBinding cordsChecker;

    public static boolean scoreboardPacketInterupt;
    @Override
    public void onInitialize() {
        FileUtils.copyLootrunFiles();
        register();
    }

    private static void register() {
        CommandsRegister.registerCommands();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            BeaconChecker.onTick();
        });
        cordsChecker = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.yourmod.yourkeybinding", // Translation key for display name
                GLFW.GLFW_KEY_X, // Default GLFW key code (change to your desired key)
                "category.yourmod" // Translation key for keybinding category (optional)
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (cordsChecker.wasPressed()) {
                scoreboardPacketInterupt = !scoreboardPacketInterupt;
                msg("Scoreboard Packet Interupt: " + scoreboardPacketInterupt);

            }
        });

        HudRenderCallback.EVENT.register(((matrixStack, tickDelta) -> {
            renderTwoTextBlocksOnScreen();
        }));
    }

}
