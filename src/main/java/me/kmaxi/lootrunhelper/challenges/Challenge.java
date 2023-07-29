package me.kmaxi.lootrunhelper.challenges;

import com.google.gson.Gson;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Challenge {
    String challengeName;
    double x;
    double y;
    double z;
    String type;
    public Challenge(String challengeName, double x, double y, double z, String type) {
        this.challengeName = challengeName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
    }

    public Challenge() {
    }

    public String getChallengeName() {
        return challengeName;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "challengeName='" + challengeName + '\'' +
                ", x=" + x +
                ", z=" + z +
                ", type='" + type + '\'' +
                '}';
    }

    public static void main(String[] args) {
        // Read the JSON file and parse it into a list of Challenge objects
        try {
            Gson gson = new Gson();
            Map<String, Map<String, Object>> challengeMap = gson.fromJson(
                    new FileReader("src/main/resources/locations.json"),
                    new com.google.gson.reflect.TypeToken<Map<String, Map<String, Object>>>() {}.getType()
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
