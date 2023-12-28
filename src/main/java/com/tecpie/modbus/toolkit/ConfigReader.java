package com.tecpie.modbus.toolkit;

import com.tecpie.modbus.entity.CacheTasksConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;

public class ConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

    // 指定YAML文件的路径
    private static final String CACHE_YML = "cache.yml";

    public static final String SYSTEM_YML = "system.yml";

    public static CacheTasksConfig readCache() {
        Yaml yaml = new Yaml();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(CACHE_YML);
        try {
            return yaml.loadAs(inputStream, CacheTasksConfig.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> readSystem(){
        Yaml yaml = new Yaml();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(SYSTEM_YML);
        try {
            return yaml.loadAs(inputStream, Map.class);
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
