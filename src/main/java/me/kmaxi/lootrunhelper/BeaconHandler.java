package me.kmaxi.lootrunhelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class BeaconHandler {


    public static final HashSet<Item> beaconItems = new HashSet<>(Arrays.asList(Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL));

    public static HashSet<Beacon> getBeacons() {
        ClientWorld world = MinecraftClient.getInstance().world;


        if (world != null) {
            HashSet<Beacon> foundBeacons = new HashSet<>();


            ClientPlayerEntity user = MinecraftClient.getInstance().player;
            float boxSize = 10000;

            //  = world.getEntitiesByType(EntityType.ARMOR_STAND, new Box(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), (entity) -> true);
            List<ArmorStandEntity> armorStands = world.getEntitiesByType(EntityType.ARMOR_STAND, new Box(user.getX() - boxSize, user.getY() - boxSize, user.getZ() - boxSize, user.getX() + boxSize, user.getY() + boxSize, user.getZ() + boxSize), EntityPredicates.VALID_ENTITY);

            MinecraftClient.getInstance().player.sendMessage(Text.of("Armor stand amount:" + armorStands.size()), false);

            for (ArmorStandEntity armorStand : armorStands) {
                ItemStack helmet = armorStand.getEquippedStack(EquipmentSlot.HEAD);

                if (helmet.isEmpty())
                    continue;

                Item item = helmet.getItem();

                if (!beaconItems.contains(item))
                    continue;

                Beacon beacon = new Beacon(armorStand.getPos(), helmet);

                foundBeacons.add(beacon);

            }

            MinecraftClient.getInstance().player.sendMessage(Text.of("Found " + foundBeacons.size() + " beacons!"), false);


            return foundBeacons;
        }


        return null;
    }


}
