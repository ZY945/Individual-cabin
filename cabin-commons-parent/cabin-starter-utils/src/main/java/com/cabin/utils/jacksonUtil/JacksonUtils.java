package com.cabin.utils.jacksonUtil;

import com.cabin.utils.reflectUtil.ReflectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;

import java.util.List;

public class JacksonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 参数1:object<br/>
     * 参数2:实体类.class<br/>
     * 返回:List<实体类><br/>
     *
     * @param fromValue   object
     * @param toValueType Job.class
     * @return Job
     */
    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return objectMapper.convertValue(fromValue, toValueType);
    }

    public static <T> T convertValue(Object fromValue, TypeReference<T> toValueTypeRef) {
        return objectMapper.convertValue(fromValue, toValueTypeRef);
    }

    /**
     * 参数1:json数组<br/>
     * 参数2:实体类.class<br/>
     * 返回:List<实体类><br/>
     *
     * @param jsonArray jsonArray
     * @param tClass    Job.class
     * @return List<Job>
     */
    public static <T> List<T> convertValue(JSONArray jsonArray, Class<T> tClass) {
        TypeReference<List<T>> listTypeRef = ReflectUtils.getListTypeRef(tClass);
        return objectMapper.convertValue(jsonArray.toList(), listTypeRef);
    }
}