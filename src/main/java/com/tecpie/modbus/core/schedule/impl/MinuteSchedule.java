package com.tecpie.modbus.core.schedule.impl;

public class MinuteSchedule {

    public static Runnable run(){
        return () -> {
            System.out.println("Task 1: Executing every minute");
        };
    }

}
