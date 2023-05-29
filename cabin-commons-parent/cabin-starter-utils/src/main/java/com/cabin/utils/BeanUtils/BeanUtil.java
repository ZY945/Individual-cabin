package com.cabin.utils.BeanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/5/29 15:38
 */
public class BeanUtil {

    /**
     * JsonArray转List
     *
     * @param jsonArray 提供的jsonArray
     * @param clazz    列表类型.class
     * @param <T>
     * @return List<T>
     */
    @SuppressWarnings("目前不知道是否有阻塞,慎用")
    public static <T> List<T> getListByJsonArray(JSONArray jsonArray, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            return objectMapper.readValue(jsonArray.toString(), type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> fromJsonStr(String json, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
        return objectMapper.readValue(json, type);
    }

}
