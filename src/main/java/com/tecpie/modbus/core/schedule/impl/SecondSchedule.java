package com.tecpie.modbus.core.schedule.impl;

public class SecondSchedule {

    public static Runnable run(){
        return () -> {
            System.out.println("Task 1: Executing every second");
        };
    }

}
