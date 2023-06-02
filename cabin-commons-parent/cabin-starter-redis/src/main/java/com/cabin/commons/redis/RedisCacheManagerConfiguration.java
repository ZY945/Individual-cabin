package com.cabin.commons.redis;

import com.cabin.utils.dateUtil.DateUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Redis 缓存管理器 配置
 */
@Configuration
public class RedisCacheManagerConfiguration {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * Redis 缓存管理器
     *
     * @param redisTemplate Redis 模板
     * @return 返回 Redis 缓存管理器
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate<?, ?> redisTemplate) {

        // 从 RedisTemplate 中获取连接
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();

        // 检查 RedisConnectionFactory 是否为 null
        RedisConnectionFactory redisConnectionFactory = Objects.requireNonNull(connectionFactory);

        // 检查 RedisConnectionFactory 是否为 null
        // 创建新的无锁 RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

        // 获取 RedisTemplate 的序列化
        RedisSerializer<?> valueSerializer = redisTemplate.getValueSerializer();

        // 序列化对
        RedisSerializationContext.SerializationPair<?> serializationPair = RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer);

        // 获取默认缓存配置
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();

        // 设置序列化
        RedisCacheConfiguration redisCacheConfigurationSerialize = redisCacheConfiguration.serializeValuesWith(serializationPair);

        // 创建并返回 Redis 缓存管理
        return new RedisCacheManager(redisCacheWriter, redisCacheConfigurationSerialize);
    }

    /**
     * 注意：如果要使用注解 {@link Autowired} 管理 {@link RedisTemplate}， 则需要将 {@link RedisTemplate} 的
     * {@link Bean} 缺省泛型
     *
     * @return 返回 Redis 模板
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {

        // Helper类简化了 Redis 数据访问代码
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 设置连接工厂。
        template.setConnectionFactory(redisConnectionFactory);

        // 可以使用读写JSON
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

//        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//        ObjectMapper build = builder.build();

        //https://docs.spring.io/spring-data/redis/docs/3.0.2/reference/html/#redis:serializer
        //TODO 需要手动去反序列化
        //在get获取后,用Object封装,Object进行封装成指定类
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper());
//         springboot 3.0.2 'setObjectMapper(com.fasterxml.jackson.databind.ObjectMapper)' is deprecated and marked for removal

        // Redis 字符串：键、值序列化
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);

        // Redis Hash：键、值序列化
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();

        return template;
    }

    public static ObjectMapper objectMapper() {
        // ObjectMapper 提供了从基本 POJO（普通旧Java对象）或从通用 JSON 树模型（{@link JsonNode}）读取和写入 JSON
        // 的功能，
        // 以及执行转换的相关功能。
        ObjectMapper objectMapper = new ObjectMapper();

        // 枚举，定义影响Java对象序列化方式的简单开/关功能。
        // 默认情况下启用功能，因此默认情况下日期/时间序列化为时间戳。
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 如果启用，上下文<code> TimeZone </
        // code>将基本上覆盖任何其他TimeZone信息;如果禁用，则仅在值本身不包含任何TimeZone信息时使用。
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        // 注册使用Jackson核心序列化{@code java.time}对象的功能的类。
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // 添加序列化
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateUtil.DateTimeForMatter));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateUtil.DateFormatter));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateUtil.TimeFormatter));

        // 添加反序列化
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateUtil.DateTimeForMatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateUtil.DateFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateUtil.TimeFormatter));

        // 用于注册可以扩展该映射器提供的功能的模块的方法; 例如，通过添加自定义序列化程序和反序列化程序的提供程序。
        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }

}
