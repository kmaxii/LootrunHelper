package me.kmaxi.lootrunhelper;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.commands.CommandsRegister;
import me.kmaxi.lootrunhelper.utils.FileUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

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
    }
}
