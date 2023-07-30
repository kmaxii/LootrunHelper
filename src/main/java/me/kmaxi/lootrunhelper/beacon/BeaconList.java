package me.kmaxi.lootrunhelper.beacon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.kmaxi.lootrunhelper.utils.ChosenCharacter;
import me.kmaxi.lootrunhelper.utils.Config;
import me.kmaxi.lootrunhelper.utils.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static me.kmaxi.lootrunhelper.utils.FileUtils.getBeaconListFileName;

public class BeaconList implements Iterable<Beacon> {
    private List<Beacon> beacons;

    public BeaconList(HashSet<Beacon> beaconHashSet) {
        beacons = new ArrayList<>();
        loadFromJsonFile();
        if (beacons.size() > 0)
            return;

        beacons.addAll(beaconHashSet);
        saveToJsonFile();
    }

    public BeaconList() {
        beacons = new ArrayList<>();
        loadFromJsonFile();
    }

    public boolean contains(Beacon beacon) {
        return beacons.contains(beacon);
    }

    public void add(Beacon beacon) {
        beacons.add(beacon);
        saveToJsonFile();
    }

    public void sort() {
        beacons.sort(Comparator.comparingInt(beacon -> beacon.beaconType.ordinal()));
    }

    public void replace(HashSet<Beacon> beaconHashSet) {
        beacons.clear();
        beacons.addAll(beaconHashSet);
        sort();
        saveToJsonFile();
    }

    public void remove(Beacon beacon) {
        beacons.remove(beacon);
        saveToJsonFile();
    }

    public void clear() {
        beacons.clear();
        saveToJsonFile();
    }

    public int size() {

        return beacons.size();
    }

    public void saveToJsonFile() {
        String fileName = getBeaconListFileName();
        FileUtils.createDirectory(fileName);
        try (FileWriter writer = new FileWriter(fileName)) {
            Gson gson = new Gson();
            gson.toJson(beacons, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromJsonFile() {
        String fileName = getBeaconListFileName();
        try (FileReader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            beacons = gson.fromJson(reader, new TypeToken<List<Beacon>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /* public static void main(String[] args) {
         BeaconList beaconList = new BeaconList();
         beaconList.add(new Beacon(new Vec3d(1, 2, 3), BeaconType.RED));
         beaconList.add(new Beacon(new Vec3d(4, 5, 6), BeaconType.GREEN));
         beaconList.saveToJsonFile();

         BeaconList loadedBeaconList = new BeaconList();
         loadedBeaconList.loadFromJsonFile();
         System.out.println(loadedBeaconList.beacons);
     }
 */
    @NotNull
    @Override
    public Iterator<Beacon> iterator() {
        return beacons.iterator();
    }
}
