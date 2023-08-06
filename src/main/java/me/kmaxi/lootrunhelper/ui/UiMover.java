package me.kmaxi.lootrunhelper.ui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import net.minecraft.resource.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class UiMover extends LightweightGuiDescription {


    private final Identifier image = new Identifier("lootrunhelper", "icon.png");

    public UiMover(){
        WGridPanel root = new WGridPanel();
        AddButtons(root);
    }

    private void AddButtons(WGridPanel root) {
        WButton beaconDestinations = new WButton(Text.of("None"));


    }
}
