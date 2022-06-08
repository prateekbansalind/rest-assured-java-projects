package helper;

public class Payload {

    public static String payload(String isbn, String aisle){
        return "{\n" +
                "\"name\":\"Learn TypeScript\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"Mosh Hamedani\"\n" +
                "}\n";
    }

    public static String deletePayload(String id){
        return "{\n" +
                " \n" +
                "\"ID\" : \""+id+"\"\n" +
                " \n" +
                "} \n";
    }

}
