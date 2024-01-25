package com.tecpie.modbus.core.schedule;

import com.serotonin.modbus4j.ModbusMaster;
import com.tecpie.modbus.core.master.ModbusMasterConfig;
import com.tecpie.modbus.core.schedule.impl.MinuteSchedule;
import com.tecpie.modbus.core.schedule.impl.SecondSchedule;
import com.tecpie.modbus.entity.CacheTask;
import com.tecpie.modbus.entity.CacheTasksConfig;
import com.tecpie.modbus.toolkit.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        String secondPreFileName = systemConfig.get("secondPreFileName").toString();
        String minutePreFileName = systemConfig.get("minutePreFileName").toString();
        String fileExtension = systemConfig.get("fileExtension").toString();
        logger.info("host:{}, port:{},secondPeriod:{},minutePeriod:{}", host, port, secondPeriod, minutePeriod);
        logger.info("secondFilePath:{}, minuteFilePath:{}, secondPreFileName:{}, minutePreFileName:{}",
                secondFilePath, minuteFilePath, secondPreFileName, minutePreFileName);
        ModbusMaster modbusMaster = ModbusMasterConfig.getMaster(host, port);
        // 获取秒任务
        List<CacheTask> secondList = config.getSecondList();
        // 获取分任务
        List<CacheTask> minuteList = config.getMinuteList();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        // 使用scheduleAtFixedRate方法分别安排任务1和任务2
        AbstractSchedule secondSchedule = new SecondSchedule(secondFilePath, secondPreFileName, fileExtension);
        AbstractSchedule minuteSchedule = new MinuteSchedule(minuteFilePath, minutePreFileName, fileExtension);
        scheduler.scheduleAtFixedRate(minuteSchedule.run(minuteList, modbusMaster), 0, minutePeriod, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(secondSchedule.run(secondList, modbusMaster), 0, secondPeriod, TimeUnit.SECONDS);
    }

}
