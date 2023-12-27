package com.tecpie.modbus.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheTask {

    private Integer slaveId;

    private Integer function;

    private String dataType;

    private List<CachePoint> offsetList;

}
