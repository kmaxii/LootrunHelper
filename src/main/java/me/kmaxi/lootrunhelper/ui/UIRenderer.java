package me.kmaxi.lootrunhelper.ui;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.BeaconDestinations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class UIRenderer {

    private static final int SPACING_BETWEEN_LINES = 2;

    private static void renderTextOnScreen(String text, int x, int y, int color, TextRenderType textRenderType) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        MatrixStack matrixStack = new MatrixStack();

        String[] lines = text.split("\n");

        // Calculate the maximum width among all lines
        int maxWidth = 0;
        for (String line : lines) {
            int lineWidth = textRenderer.getWidth(line);
            maxWidth = Math.max(maxWidth, lineWidth);
        }

        // Adjust the starting x position to center the entire text block horizontally
        x -= maxWidth;

        VertexConsumerProvider.Immediate vertexConsumers = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

        // Render the text
        for (String line : lines) {
            int textWidth = textRenderer.getWidth(line);
            int xOffset = maxWidth - textWidth; // Calculate the offset to center the line horizontally

            switch (textRenderType) {
                case NONE -> textRenderer.draw(matrixStack, Text.of(line), x + xOffset, y, color);
                case SHADOW -> textRenderer.drawWithShadow(matrixStack, Text.of(line), x + xOffset, y, color);
                case OUTLINE ->
                        textRenderer.drawWithOutline(Text.of(line).asOrderedText(), x + xOffset, y, color, 0x000000, matrixStack.peek().getPositionMatrix(), vertexConsumers, 255);
            }

            y += textRenderer.fontHeight + SPACING_BETWEEN_LINES;
        }
        textRenderer.drawWithOutline(Text.of("\ne").asOrderedText(), x + 10000, y, color, 0x000000, matrixStack.peek().getPositionMatrix(), vertexConsumers, 255);

    }

    public static void renderTwoTextBlocksOnScreen() {
        if (!BeaconChecker.isEnabled())
            return;

        int x = MinecraftClient.getInstance().getWindow().getScaledWidth() - 10;
        int y = 10;

        String textToRender = BeaconDestinations.destinations;
        renderTextOnScreen(textToRender, x, y, 0xFFFFFF, TextRenderType.SHADOW);

        String secondTextToRender = BeaconChecker.activeDataSaver().getData();

        // Calculate the total height of the first text block
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        String[] lines = textToRender.split("\n");
        int totalHeight = lines.length * (textRenderer.fontHeight + SPACING_BETWEEN_LINES);

        // Adjust y-coordinate for the second text to start below the first text
        y += totalHeight + SPACING_BETWEEN_LINES;
        int y2 = MinecraftClient.getInstance().getWindow().getScaledHeight() - (MinecraftClient.getInstance().getWindow().getScaledHeight() / 5) * 2;
        renderTextOnScreen(secondTextToRender, x, y2, 0xFFFFFF, TextRenderType.OUTLINE);
    }
}
