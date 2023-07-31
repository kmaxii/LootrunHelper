package me.kmaxi.lootrunhelper.beacon;

import me.kmaxi.lootrunhelper.beacon.Beacon;
import me.kmaxi.lootrunhelper.utils.CodingUtils;
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
import net.minecraft.util.math.Vec3d;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class BeaconHandler {


    public static final HashSet<Item> beaconItems = new HashSet<>(Arrays.asList(Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL));
    private static final double BOX_HEIGHT = 10;

    public static HashSet<Beacon> getBeacons() {
        ClientWorld world = MinecraftClient.getInstance().world;


        if (world != null) {
            HashSet<Beacon> foundBeacons = new HashSet<>();


            ClientPlayerEntity user = MinecraftClient.getInstance().player;
            float boxSize = 10000;

            //  = world.getEntitiesByType(EntityType.ARMOR_STAND, new Box(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), (entity) -> true);
            List<ArmorStandEntity> armorStands = world.getEntitiesByType(EntityType.ARMOR_STAND, new Box(user.getX() - boxSize, 0, user.getZ() - boxSize, user.getX() + boxSize, BOX_HEIGHT, user.getZ() + boxSize), EntityPredicates.VALID_ENTITY);

            double lowestYCord = Integer.MAX_VALUE;

            for (ArmorStandEntity armorStand : armorStands) {
                ItemStack helmet = armorStand.getEquippedStack(EquipmentSlot.HEAD);

                if (helmet.isEmpty())
                    continue;

                Item item = helmet.getItem();

                if (!beaconItems.contains(item))
                    continue;

                BeaconType beaconType = Beacon.getBeaconType(helmet.getItem(), helmet.getDamage());
                if (beaconType == null)
                    continue;

                if (armorStand.getPos().y < lowestYCord)
                    lowestYCord = (armorStand.getPos().y);

                Vec3d beaconPos = armorStand.getPos();

                Beacon beacon = new Beacon(beaconPos, beaconType);

                if (endsWithPointFive(beaconPos.x) && endsWithPointFive(beaconPos.z)){
                    BeaconDestinations.beaconDestinationClose(beacon, beaconPos);
                }

                foundBeacons.add(beacon);

            }

            return foundBeacons;
        }


        return null;
    }


    public static boolean endsWithPointFive(double number) {

        number = Math.abs(number);

        double decimalPart = number % 1.0; // Get the decimal part of the number

        // Check if the decimal part is exactly 0.5 (within a small epsilon for floating-point comparison)
        return Math.abs(decimalPart - 0.5) < 1e-6;
    }

}
