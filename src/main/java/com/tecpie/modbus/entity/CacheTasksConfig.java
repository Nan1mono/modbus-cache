package com.tecpie.modbus.entity;

import java.util.List;

public class CacheTasksConfig {

    private List<CacheTask> minuteList;

    private List<CacheTask> secondList;

    public List<CacheTask> getMinuteList() {
        return minuteList;
    }

    public void setMinuteList(List<CacheTask> minuteList) {
        this.minuteList = minuteList;
    }

    public List<CacheTask> getSecondList() {
        return secondList;
    }

    public void setSecondList(List<CacheTask> secondList) {
        this.secondList = secondList;
    }

}
