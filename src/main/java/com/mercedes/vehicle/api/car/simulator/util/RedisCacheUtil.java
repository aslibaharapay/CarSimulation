package com.mercedes.vehicle.api.car.simulator.util;

import org.springframework.stereotype.Component;

@Component
public class RedisCacheUtil {
/*
    @Autowired(required = false)
    private static RedisTemplate<String, String> redisTemplate;

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void saveCache(T t, String key) throws JsonProcessingException {
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        String serializedObject = objectMapper.writeValueAsString(t);
        stringStringValueOperations.set(key, serializedObject);
    }

    public static <T> T getValueInCache(Class<T> tClass,String key) throws JsonProcessingException {
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        String deserializedObject = stringStringValueOperations.get(key);
        if (deserializedObject == null)return null;
        return  objectMapper.readValue(deserializedObject, tClass);
    }

    public static void deleteCacheValue(String key){
        redisTemplate.delete(key);
    }*/
}
