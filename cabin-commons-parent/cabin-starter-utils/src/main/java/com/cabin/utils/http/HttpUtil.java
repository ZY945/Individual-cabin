package com.cabin.utils.http;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 伍六七
 * @date 2023/5/17 9:39
 */
public class HttpUtil {

    public static JSONObject getJsonObject(JSONObject jsonObject, HttpURLConnection con) throws IOException {
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            jsonObject = new org.json.JSONObject(response.toString());
        }
        return jsonObject;
    }

    public static String getStrResponse(String url) {
        StringBuilder response = new StringBuilder();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            // 发送 HTTP 请求
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                InputStream inStream = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.toString();
    }

    public static JSONArray getJsonArrayResponse(String url) {
        JSONArray jsonArray = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            // 发送 HTTP 请求
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                InputStream inStream = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 解析 JSON 数据
                jsonArray = new JSONArray(response);
            }
            if (jsonArray == null) {
                throw new RuntimeException("没有获取到jsonArray");
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonArray;
    }

    public static JSONObject getJSONObjectResponse(String url) {
        JSONObject jsonObject = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            // 发送 HTTP 请求
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                InputStream inStream = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 解析 JSON 数据
                jsonObject = new JSONObject(String.valueOf(response));
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }
}
