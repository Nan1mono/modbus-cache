package com.tecpie.modbus.core.schedule.impl;

import com.serotonin.modbus4j.BatchResults;
import com.serotonin.modbus4j.ModbusMaster;
import com.tecpie.modbus.core.master.ModbusMasterConfig;
import com.tecpie.modbus.core.schedule.AbstractSchedule;
import com.tecpie.modbus.entity.CachePoint;
import com.tecpie.modbus.entity.CacheTask;

import java.util.List;

public class SecondSchedule extends AbstractSchedule {

    public SecondSchedule(String filePath, String preFileName, String fileExtension) {
        super(filePath, preFileName, fileExtension);
    }

    @Override
    protected Runnable run(List<CacheTask> cacheTaskList, ModbusMaster modbusMaster) {
        return () -> cacheTaskList.forEach(t -> {
            // offset
            List<CachePoint> offsetList = t.getOffsetList();
            // 批量读取点位信息
            BatchResults<Integer> results = ModbusMasterConfig.readBatch(modbusMaster, t);
            initAndWrite(t, offsetList, results, "yyyyMMdd HHmmss");
        });
    }
}
