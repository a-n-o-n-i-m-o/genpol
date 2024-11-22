package com.tsoft.bot.both.utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonUtils {
    public static JSONObject stringToJson(String source)
    {
        JSONObject jsonObject;
        try {
            source = source.replace("\n","");
            source = source.replace(" ","");
            jsonObject = new JSONObject(source);
        }catch (JSONException err) {
            err.printStackTrace();
            jsonObject = null;
        }
        return jsonObject;
    }
    public static JSONObject fileToJson(String path)
    {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            Object obj = parser.parse(new FileReader(path));
            jsonObject = new JSONObject(obj.toString());
        }catch (JSONException | IOException | ParseException e) {
            e.printStackTrace();
            jsonObject = null;
        }
        return jsonObject;
    }
}
