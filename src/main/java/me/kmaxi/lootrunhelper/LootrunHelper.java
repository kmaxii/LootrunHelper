package me.kmaxi.lootrunhelper;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.BeaconDestinations;
import me.kmaxi.lootrunhelper.commands.CommandsRegister;
import me.kmaxi.lootrunhelper.utils.FileUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import static me.kmaxi.lootrunhelper.UIRenderer.renderTwoTextBlocksOnScreen;
import static me.kmaxi.lootrunhelper.utils.CodingUtils.msg;

public class LootrunHelper implements ModInitializer {
    public static KeyBinding cordsChecker;

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
                System.out.println("Beacon location: " + BeaconChecker.closestBeacon.position);
                msg("§6Beacon location §clogged");
            }
        });

        HudRenderCallback.EVENT.register(((matrixStack, tickDelta) -> {
            renderTwoTextBlocksOnScreen();
        }));
    }

}
