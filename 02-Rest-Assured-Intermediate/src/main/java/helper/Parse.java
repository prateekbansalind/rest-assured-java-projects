package helper;

import io.restassured.path.json.JsonPath;

public class Parse {

    public static JsonPath parseToJson(String resInStr){
        JsonPath resInJson = new JsonPath(resInStr);
        return resInJson;
    }
}
