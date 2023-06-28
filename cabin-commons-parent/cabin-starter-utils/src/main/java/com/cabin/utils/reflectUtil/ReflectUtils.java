package com.cabin.utils.reflectUtil;

import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 发射工具箱
 *
 * @author 伍六七
 * @date 2023/6/28 14:54
 */
public class ReflectUtils {
    // 定义一个方法，接受 Class<T> 参数并返回 TypeReference<List<T>> 实例

    /**
     * @param clazz Job.class
     * @return TypeReference<List < T>>
     */
    public static <T> TypeReference<List<T>> getListTypeRef(Class<T> clazz) {
        return new TypeReference<List<T>>() {
            @Override
            public Type getType() {
                return new ParameterizedType() {
                    @Override
                    public Type[] getActualTypeArguments() {
                        return new Type[]{clazz};
                    }

                    @Override
                    public Type getRawType() {
                        return List.class;
                    }

                    @Override
                    public Type getOwnerType() {
                        return null;
                    }
                };
            }
        };
    }
}
