package me.kmaxi.lootrunhelper.challenges;

import com.google.gson.Gson;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChallengeList {


    public static List<Challenge> getChallenges(String fileName){
        try {
            Gson gson = new Gson();
            Map<String, Map<String, Object>> challengeMap = gson.fromJson(
                    new FileReader(fileName),
                    new com.google.gson.reflect.TypeToken<Map<String, Map<String, Object>>>() {}.getType()
            );

            List<Challenge> challenges = new ArrayList<>();
            for (Map.Entry<String, Map<String, Object>> entry : challengeMap.entrySet()) {
                String challengeName = entry.getKey();
                Map<String, Object> challengeDetails = entry.getValue();
                double x = (Double) challengeDetails.get("x");
                double z = (Double) challengeDetails.get("z");
                String type = (String) challengeDetails.get("type");

                Challenge challenge = new Challenge();
                challenge.challengeName = challengeName;
                challenge.x = x;
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
}
