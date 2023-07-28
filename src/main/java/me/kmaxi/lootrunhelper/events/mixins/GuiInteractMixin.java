package me.kmaxi.lootrunhelper.events.mixins;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class GuiInteractMixin {

    @Inject(method = "clickSlot", at = @At("HEAD"))
    private void onItemClick(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        System.out.println("Clicked button " + button + " on slot " + slotId + " with action type " + actionType);
    }
}
