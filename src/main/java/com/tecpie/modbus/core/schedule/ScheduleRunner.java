package com.tecpie.modbus.core.schedule;

import com.tecpie.modbus.core.schedule.impl.MinuteSchedule;
import com.tecpie.modbus.core.schedule.impl.SecondSchedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleRunner {

    private Integer secondPeriod = 1;
    private Integer minutePeriod = 1;

    public void schedule() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        // 使用scheduleAtFixedRate方法分别安排任务1和任务2
        scheduler.scheduleAtFixedRate(MinuteSchedule.run(), 0, minutePeriod, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(SecondSchedule.run(), 0, secondPeriod, TimeUnit.SECONDS);
    }

}
