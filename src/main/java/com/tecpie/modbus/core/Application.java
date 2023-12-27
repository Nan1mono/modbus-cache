package com.tecpie.modbus.core;

import com.serotonin.modbus4j.ModbusMaster;
import com.tecpie.modbus.core.master.ModbusMasterConfig;
import com.tecpie.modbus.core.schedule.ScheduleRunner;
import com.tecpie.modbus.entity.CacheTasksConfig;
import com.tecpie.modbus.toolkit.CacheTaskReader;

public class Application {

    public static void main(String[] args) throws Exception {
        // 读取文件
        CacheTasksConfig cacheTasksConfig = CacheTaskReader.readTask();
        // 获取链接
        ModbusMaster master = ModbusMasterConfig.getMaster();
        ScheduleRunner scheduleRunner = new ScheduleRunner();
        scheduleRunner.schedule(cacheTasksConfig, master);
    }

}
