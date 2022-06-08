package helper;

import io.restassured.path.json.JsonPath;

public class Helper {
    public static String payload(String address){
        return "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"Frontline house\",\n" +
                "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "  \"address\": \""+address+"\",\n" +
                "  \"types\": [\n" +
                "    \"shoe park\",\n" +
                "    \"shop\"\n" +
                "  ],\n" +
                "  \"website\": \"http://google.com\",\n" +
                "  \"language\": \"French-IN\"\n" +
                "}\n";
    }
    public static String payload(String place_id, String updateAddress){
        return "{\n" +
                "\"place_id\":\""+place_id+"\",\n" +
                "\"address\":\""+updateAddress+"\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}\n";
    }

    public static String deletePayload(String place_id){
        return "{\"place_id\": \""+place_id+"\"}";
    }

    public static JsonPath parseToJson(String strRes){
        JsonPath jsonObj = new JsonPath(strRes);
        return jsonObj;
    }
}
