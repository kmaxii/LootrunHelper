package me.kmaxi.lootrunhelper.data;

import java.util.HashMap;

public class JsonConverter {
    public static String toJson(HashMap<String, Integer> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (String key : map.keySet()) {
            if (!first) {
                sb.append(",");
            }
            sb.append("\"").append(key).append("\":").append(map.get(key));
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    public static HashMap<String, Integer> fromJson(String json) {
        HashMap<String, Integer> map = new HashMap<>();
        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
            String[] keyValuePairs = json.split(",");
            for (String pair : keyValuePairs) {
                String[] parts = pair.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim().replaceAll("\"", "");
                    int value = Integer.parseInt(parts[1].trim());
                    map.put(key, value);
                }
            }
        }
        return map;
    }
}
