package com.tecpie.modbus.toolkit;

import com.tecpie.modbus.entity.CacheTask;
import com.tecpie.modbus.entity.CacheTasksConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheTaskReader {

    // 指定YAML文件的路径
    private static final String yamlFilePath = "cache.yml";

    public static Map<String, List<CacheTask>> readTask() {
        Yaml yaml = new Yaml();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(yamlFilePath);
        CacheTasksConfig cacheTasksConfig = yaml.loadAs(inputStream, CacheTasksConfig.class);
        List<CacheTask> minuteList = cacheTasksConfig.getMinuteList();
        for (CacheTask task : minuteList){
            System.out.println(task);
        }
        return  new HashMap<>();
    }

}
