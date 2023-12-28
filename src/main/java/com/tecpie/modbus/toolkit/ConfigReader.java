package com.tecpie.modbus.toolkit;

import com.tecpie.modbus.entity.CacheTasksConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigReader {

    private ConfigReader() {
    }

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

    public static Map<String, Object> readSystem() {
        Yaml yaml = new Yaml();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(SYSTEM_YML);
        try {
            return yaml.loadAs(inputStream, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
