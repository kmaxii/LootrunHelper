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
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class BeaconHandler {


    public static final HashSet<Item> beaconItems = new HashSet<>(Arrays.asList(Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL));

    public static void checkArmorStandHelmets() {
        ClientWorld world = MinecraftClient.getInstance().world;



        if (world != null) {

            ClientPlayerEntity user = MinecraftClient.getInstance().player;
            float boxSize = 10000;

            //  = world.getEntitiesByType(EntityType.ARMOR_STAND, new Box(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), (entity) -> true);
            List<ArmorStandEntity> armorStands = world.getEntitiesByType(EntityType.ARMOR_STAND, new Box(user.getX() - boxSize, user.getY() - boxSize, user.getZ() - boxSize, user.getX() + boxSize, user.getY() + boxSize, user.getZ() + boxSize), EntityPredicates.VALID_ENTITY);

            System.out.println("Armor stand amount:" + armorStands.size());
            for (ArmorStandEntity armorStand : armorStands) {
                ItemStack helmet = armorStand.getEquippedStack(EquipmentSlot.HEAD);

                if (helmet.isEmpty())
                    continue;

                Item item = helmet.getItem();

                if (!beaconItems.contains(item))
                    return;

                System.out.println("Armor stand at " + armorStand.getBlockPos() + " is wearing a " + item.getName().getString() + " Durability: " + helmet.getDamage());
                Vec3d
            }

        }
    }

    public BeaconType getBeaconType(Item item, int damage) {
        if (beaconItems.contains(stack.getItem())) {
            return BeaconType.BEACON;
        } else {
            return BeaconType.NONE;
        }
    }
}
