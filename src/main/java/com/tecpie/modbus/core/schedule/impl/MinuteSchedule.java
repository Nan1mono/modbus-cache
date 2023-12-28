package com.tecpie.modbus.core.schedule.impl;

import com.serotonin.modbus4j.ModbusMaster;
import com.tecpie.modbus.entity.CacheTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MinuteSchedule {

    private static final Logger logger = LoggerFactory.getLogger(MinuteSchedule.class);

    public static Runnable run(List<CacheTask> cacheTaskList, ModbusMaster modbusMaster) {
        return () -> {
            cacheTaskList.forEach(t -> {
                // 从寄存器id
                Integer slaveId = t.getSlaveId();
                // 函数类型
                Integer function = t.getFunction();
                // 数据类型
                String dataType = t.getDataType();
            });
        };
    }

}
