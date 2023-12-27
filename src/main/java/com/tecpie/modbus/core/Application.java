package com.tecpie.modbus.core;

import com.tecpie.modbus.entity.CacheTasksConfig;
import com.tecpie.modbus.toolkit.CacheTaskReader;

public class Application {

    public static void main(String[] args) throws Exception {
        CacheTasksConfig cacheTasksConfig = CacheTaskReader.readTask();
        System.out.println(cacheTasksConfig);
//        ScheduleRunner scheduleRunner = new ScheduleRunner();
//        scheduleRunner.schedule();
    }

}
