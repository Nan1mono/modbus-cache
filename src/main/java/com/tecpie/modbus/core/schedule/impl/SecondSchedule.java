package com.tecpie.modbus.core.schedule.impl;

import com.serotonin.modbus4j.ModbusMaster;
import com.tecpie.modbus.entity.CacheTask;

import java.util.List;

public class SecondSchedule {

    public static Runnable run(List<CacheTask> cacheTaskList, ModbusMaster modbusMaster) {
        return () -> {
            cacheTaskList.forEach(System.out::println);
        };
    }

}
