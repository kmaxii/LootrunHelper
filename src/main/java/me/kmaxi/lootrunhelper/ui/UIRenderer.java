package me.kmaxi.lootrunhelper.ui;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.BeaconDestinations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.joml.Vector2d;
import org.joml.Vector2i;
import org.joml.Vector3d;

public class UIRenderer {

    private static final int SPACING_BETWEEN_LINES = 2;
    private static UiPositions uiPositions = UiPositions.getInstance();

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

        int windowWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int windowHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        Vector2i destinationAdd = uiPositions.getVector2i(UiPositions.DESTINATION_KEY);
        int x = windowWidth + destinationAdd.x;
        int y = destinationAdd.y;

        String textToRender = BeaconDestinations.destinations;
        renderTextOnScreen(textToRender, x, y, 0xFFFFFF, TextRenderType.SHADOW);

        String secondTextToRender = BeaconChecker.activeDataSaver().getData();

        Vector2i beaconToAdd = uiPositions.getVector2i(UiPositions.BEACON_COUNTER_KEY);
        int x2 = windowWidth + beaconToAdd.x;
        int y2 = windowHeight - (windowHeight/ 5) * 2 + beaconToAdd.y;
        renderTextOnScreen(secondTextToRender, x2, y2, 0xFFFFFF, TextRenderType.OUTLINE);
    }
}
