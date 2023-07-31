package me.kmaxi.lootrunhelper.challenges;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.kmaxi.lootrunhelper.data.CurrentData;
import me.kmaxi.lootrunhelper.utils.Config;
import me.kmaxi.lootrunhelper.utils.FileUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChallengesLoader {
    private static List<Challenge> challenges;

    public static void clearSavedList(){
        challenges = null;
    }

    public static List<Challenge> loadRightChallenges() {
        if (challenges != null) {
            return challenges;
        }

        int currentLrIndex = CurrentData.getCurrentLootrunIndex();
        if (currentLrIndex != Integer.MAX_VALUE){
            challenges = getChallenges(FileUtils.lootrunIndexes.get(currentLrIndex));
            return challenges;
        }

        String fileName = findClosestLocationName(MinecraftClient.getInstance().player.getPos()) + ".json";
        CurrentData.setCurrentLootrunIndex(FileUtils.lootrunIndexes.indexOf(fileName));

        challenges = getChallenges(fileName);
        return challenges;
    }

    private static String findClosestLocationName(Vec3d playerPos) {
        String filePath = "config/lootrunHelper/locations/starts.json";
        double minDistance = Double.MAX_VALUE;
        String closestLocationName = null;


        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
            Gson gson = new Gson();
            JsonObject jsonRoot = gson.fromJson(jsonContent, JsonObject.class);

            for (String locationName : jsonRoot.keySet()) {
                JsonObject location = jsonRoot.getAsJsonObject(locationName);
                double locX = location.get("x").getAsDouble();
                double locY = location.get("y").getAsDouble();
                double locZ = location.get("z").getAsDouble();
                Vec3d challengeLoc = new Vec3d(locX, locY, locZ);

                double distance = playerPos.distanceTo(challengeLoc);

                if (distance < minDistance) {
                    minDistance = distance;
                    closestLocationName = locationName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return closestLocationName;
    }

    private static List<Challenge> getChallenges(String fileName) {
        try {
            Gson gson = new Gson();
            Map<String, Map<String, Object>> challengeMap = gson.fromJson(
                    new FileReader(Config.LOCATIONS_DIRS + fileName),
                    new com.google.gson.reflect.TypeToken<Map<String, Map<String, Object>>>() {
                    }.getType()
            );

            List<Challenge> challenges = new ArrayList<>();
            for (Map.Entry<String, Map<String, Object>> entry : challengeMap.entrySet()) {
                String challengeName = entry.getKey();
                Map<String, Object> challengeDetails = entry.getValue();
                double x = (Double) challengeDetails.get("x");
                double y = (Double) challengeDetails.get("y");
                double z = (Double) challengeDetails.get("z");
                String type = (String) challengeDetails.get("type");

                Challenge challenge = new Challenge();
                challenge.challengeName = challengeName;
                challenge.x = x;
                challenge.y = y;
                challenge.z = z;
                challenge.type = type;

                challenges.add(challenge);
            }

            for (Challenge challenge : challenges) {
                System.out.println(challenge);
            }

            return challenges;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {

        String closestLocationName = findClosestLocationName(new Vec3d(0, 0, 0));
        System.out.println("Closest location name: " + closestLocationName);
    }
}
