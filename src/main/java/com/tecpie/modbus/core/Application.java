package com.tecpie.modbus.core;

import com.serotonin.modbus4j.ModbusMaster;
import com.tecpie.modbus.core.master.ModbusMasterConfig;
import com.tecpie.modbus.core.schedule.ScheduleRunner;
import com.tecpie.modbus.entity.CacheTasksConfig;
import com.tecpie.modbus.toolkit.ConfigReader;

public class Application {

    public static void main(String[] args) throws Exception {
        // 读取文件
        CacheTasksConfig cacheTasksConfig = ConfigReader.readCache();
        ScheduleRunner scheduleRunner = new ScheduleRunner();
        scheduleRunner.schedule(cacheTasksConfig);
    }

}
