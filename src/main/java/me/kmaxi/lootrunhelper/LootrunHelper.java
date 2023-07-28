package me.kmaxi.lootrunhelper;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.commands.CommandsRegister;
import me.kmaxi.lootrunhelper.utils.FileUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static me.kmaxi.lootrunhelper.utils.CodingUtils.player;
import static me.kmaxi.lootrunhelper.utils.CodingUtils.msg;

public class LootrunHelper implements ModInitializer {

    @Override
    public void onInitialize() {
        FileUtils.copyLootrunFiles();
        register();
    }

    private static void register() {
        CommandsRegister.registerCommands();

        BeaconChecker beaconChecker = new BeaconChecker();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            beaconChecker.onTick();
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player != null) {
                if (player.getInventory().getDisplayName() != null){
                    System.out.println(player.getInventory().getDisplayName());
                    msg("Logged");
                }else {
                    System.out.println("Custom name is null");
                }
                if (client.player.currentScreenHandler != null) {
                    /*if (!player.currentScreenHandler.getCursorStack().getItem().equals(Items.AIR) && !player.getInventory().equals(null)&&player.getInventory().getName().toString().equalsIgnoreCase("")) {
                    }*/
                    if (!player.currentScreenHandler.getCursorStack().getItem().equals(Items.AIR)){
                        System.out.println(player.getInventory().getCustomName().toString());
                        msg("Logged");
                    }
                }
            }
        });
    }
}
