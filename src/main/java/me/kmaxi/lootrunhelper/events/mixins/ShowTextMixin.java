package me.kmaxi.lootrunhelper.events.mixins;

import me.kmaxi.lootrunhelper.BeaconChecker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class ShowTextMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "render", at = @At("TAIL"))
    public void render(CallbackInfo ci) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        MatrixStack matrixStack = new MatrixStack();
        textRenderer.draw(matrixStack, Text.of("Closest beacon: " + BeaconChecker.closestBeacon2 + " At: " + BeaconChecker.closestBeaconPos), 250, 250, 0xFFFFFF);
    }
}

