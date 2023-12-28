package com.tecpie.modbus.core.schedule.impl;

import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.tecpie.modbus.core.master.ModbusMasterConfig;
import com.tecpie.modbus.entity.CachePoint;
import com.tecpie.modbus.entity.CacheTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SecondSchedule {

    private static final Logger logger = LoggerFactory.getLogger(SecondSchedule.class);

    public static Runnable run(List<CacheTask> cacheTaskList, ModbusMaster modbusMaster) {
        return () -> {
            cacheTaskList.forEach(t -> {
                // 从寄存器id
                Integer slaveId = t.getSlaveId();
                // 函数类型
                Integer function = t.getFunction();
                // 数据类型
                String dataType = t.getDataType();
                // offset
                List<CachePoint> offsetList = t.getOffsetList();
                    // 获取该配置下的offset集合，并读取其中的数
                    offsetList.forEach(point -> {
                        String name = point.getName();
                        Integer offset = point.getOffset();
                        Boolean value = null;
                        try {
                            value = ModbusMasterConfig.readCoilStatus(modbusMaster, slaveId, offset);
                            logger.info("Second Task ---> point:{},value:{}", name, value);
                        } catch (ModbusInitException | ErrorResponseException | ModbusTransportException e) {
                            logger.error(e.getLocalizedMessage());
                        }
                    });
            });
        };
    }

}
