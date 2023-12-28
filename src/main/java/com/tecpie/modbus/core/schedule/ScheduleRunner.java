package com.tecpie.modbus.core.schedule;

import com.serotonin.modbus4j.BatchResults;
import com.serotonin.modbus4j.ModbusMaster;
import com.tecpie.modbus.core.master.ModbusMasterConfig;
import com.tecpie.modbus.entity.CachePoint;
import com.tecpie.modbus.entity.CacheTask;
import com.tecpie.modbus.entity.CacheTasksConfig;
import com.tecpie.modbus.exception.ModbusCommunicationException;
import com.tecpie.modbus.toolkit.ConfigReader;
import com.tecpie.modbus.toolkit.DateTimeUtil;
import com.tecpie.modbus.toolkit.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleRunner {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleRunner.class);

    public void schedule(CacheTasksConfig config) {
        // 获取系统运行配置
        Map<String, Object> systemConfig = ConfigReader.readSystem();
        String host = systemConfig.get("host").toString();
        int port = Integer.parseInt(systemConfig.get("port").toString());
        int secondPeriod = Integer.parseInt(systemConfig.get("secondPeriod").toString());
        int minutePeriod = Integer.parseInt(systemConfig.get("minutePeriod").toString());
        String secondFilePath = systemConfig.get("secondFilePath").toString();
        String minuteFilePath = systemConfig.get("minuteFilePath").toString();
        logger.info("host:{}, port:{},secondPeriod:{},minutePeriod:{}", host, port, secondPeriod, minutePeriod);
        logger.info("secondFilePath:{}, minuteFilePath:{}", secondFilePath, minuteFilePath);
        ModbusMaster modbusMaster = ModbusMasterConfig.getMaster(host, port);
        // 获取秒任务
        List<CacheTask> secondList = config.getSecondList();
        // 获取分任务
        List<CacheTask> minuteList = config.getMinuteList();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        // 使用scheduleAtFixedRate方法分别安排任务1和任务2
        scheduler.scheduleAtFixedRate(run(minuteList, modbusMaster, minuteFilePath), 0, minutePeriod, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(run(secondList, modbusMaster, secondFilePath), 0, secondPeriod, TimeUnit.SECONDS);
    }

    public static Runnable run(List<CacheTask> cacheTaskList, ModbusMaster modbusMaster, String filePath) {
        return () -> cacheTaskList.forEach(t -> {
            // offset
            List<CachePoint> offsetList = t.getOffsetList();
            // 批量读取点位信息
            BatchResults<Integer> results = ModbusMasterConfig.readBatch(modbusMaster, t);
            List<String> lineList = new ArrayList<>();
            // 生成写入文件
            String format = DateTimeUtil.format(LocalDateTime.now(), "yyyyMMdd HHmm");
            String fileName = format + ".txt";
            File file = new File(filePath + fileName);
            offsetList.forEach(point -> {
                String value = results.getValue(point.getOffset()).toString();
                logger.info("{} ---> function:{},point:{},value:{}", filePath + fileName, t.getFunction(), point.getName(), value);
                lineList.add(String.format("%s,%s,%s,%s,%s", format, point.getOffset(), value, point.getPlant(), point.getDesc()));
            });
            try {
                FileUtil.writeLines(file, StandardCharsets.UTF_8.toString(), lineList, "\n", true);
            } catch (IOException e) {
                throw new ModbusCommunicationException(e);
            }
        });
    }

}
