package com.tecpie.modbus.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("offset-list")
    private List<CachePoint> offsetList;

}
