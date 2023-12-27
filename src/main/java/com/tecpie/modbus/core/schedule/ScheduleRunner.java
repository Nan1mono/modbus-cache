package com.tecpie.modbus.core.schedule;

import com.serotonin.modbus4j.ModbusMaster;
import com.tecpie.modbus.core.schedule.impl.MinuteSchedule;
import com.tecpie.modbus.core.schedule.impl.SecondSchedule;
import com.tecpie.modbus.entity.CacheTask;
import com.tecpie.modbus.entity.CacheTasksConfig;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleRunner {

    private Integer secondPeriod = 1;
    private Integer minutePeriod = 1;

    public void schedule(CacheTasksConfig config, ModbusMaster modbusMaster) {
        // 获取秒任务
        List<CacheTask> secondList = config.getSecondList();
        // 获取分任务
        List<CacheTask> minuteList = config.getMinuteList();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        // 使用scheduleAtFixedRate方法分别安排任务1和任务2
        scheduler.scheduleAtFixedRate(MinuteSchedule.run(secondList, modbusMaster), 0, minutePeriod, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(SecondSchedule.run(minuteList, modbusMaster), 0, secondPeriod, TimeUnit.SECONDS);
    }

}
