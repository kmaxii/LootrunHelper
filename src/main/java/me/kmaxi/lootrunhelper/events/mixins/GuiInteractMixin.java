package me.kmaxi.lootrunhelper.events.mixins;

import me.kmaxi.lootrunhelper.utils.ChosenCharacter;
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

        if (!ChosenCharacter.listenForNextClick)
            return;

        ChosenCharacter.listenForNextClick = false;
        if (slotId >= 1 && slotId <=5|| slotId >= 10 && slotId <=14||slotId >= 19 && slotId <=22) {
            ChosenCharacter.setChosenCharacter(slotId);
        }
    }
}
