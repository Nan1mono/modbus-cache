package com.tecpie.modbus.toolkit;

import com.tecpie.modbus.entity.CacheTasksConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;

public class CacheTaskReader {

    // 指定YAML文件的路径
    private static final String yamlFilePath = "cache.yml";

    public static CacheTasksConfig readTask() throws Exception {
        Yaml yaml = new Yaml();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(yamlFilePath);
        Map<String, Object> config = null;
        try {
            CacheTasksConfig cacheTasksConfig = yaml.loadAs(inputStream, CacheTasksConfig.class);
            return cacheTasksConfig;
//            config = (Map<String, Object>) yamlMap.get("cache");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T mapToObj(Map<String, Object> source,Class<T> target) throws Exception {
        Field[] fields = target.getDeclaredFields();
        T o = target.newInstance();
        for (Field field : fields) {
            Object val;
            if ((val = source.get(field.getName())) != null) {
                field.setAccessible(true);
                field.set(o, val);
            }
        }
        return o;
    }

}
