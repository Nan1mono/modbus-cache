package com.tecpie.modbus.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CacheTasksConfig {

    private List<CacheTask> minuteList;

    private List<CacheTask> secondList;

}
