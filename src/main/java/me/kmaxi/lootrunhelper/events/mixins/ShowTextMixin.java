package me.kmaxi.lootrunhelper.events.mixins;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.BeaconDestinations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class ShowTextMixin {

    @Inject(method = "render", at = @At("TAIL"))
    public void render(CallbackInfo ci) {
        if (!BeaconChecker.isEnabled() || BeaconDestinations.destinations.equals(""))
            return;

        renderTwoTextBlocksOnScreen();

    }

    private static final int SPACING_BETWEEN_LINES = 2;

    public void renderTextOnScreen(String text, int x, int y, int color) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        MatrixStack matrixStack = new MatrixStack();

        String[] lines = text.split("\n");

        // Calculate the maximum width among all lines
        int maxWidth = 0;
        for (String line : lines) {
            int lineWidth = textRenderer.getWidth(line);
            maxWidth = Math.max(maxWidth, lineWidth);
        }

        // Adjust the starting x position to align the entire text block to the right edge
        x -= maxWidth;

        // Render the text
        for (String line : lines) {
            int textWidth = textRenderer.getWidth(line);
            textRenderer.draw(matrixStack, Text.of(line), x, y, color);
            y += textRenderer.fontHeight + SPACING_BETWEEN_LINES;
        }
    }

    public void renderTwoTextBlocksOnScreen() {
        if (!BeaconChecker.isEnabled())
            return;

        int x = MinecraftClient.getInstance().getWindow().getScaledWidth() - 10;
        int y = 10;

        String textToRender = BeaconDestinations.destinations;
        renderTextOnScreen(textToRender, x, y, 0xFFFFFF);

        String secondTextToRender = BeaconChecker.activeDataSaver().getData();

        // Calculate the total height of the first text block
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        String[] lines = textToRender.split("\n");
        int totalHeight = lines.length * (textRenderer.fontHeight + SPACING_BETWEEN_LINES);

        // Adjust y-coordinate for the second text to start below the first text
        y += totalHeight + SPACING_BETWEEN_LINES;
        int y2 = MinecraftClient.getInstance().getWindow().getScaledHeight() -(MinecraftClient.getInstance().getWindow().getScaledHeight()/5)*2;
        renderTextOnScreen(secondTextToRender, x, y2, 0xFFFFFF);
    }

}

