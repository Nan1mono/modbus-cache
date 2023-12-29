package com.tecpie.modbus.entity;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CachePoint {

    private Integer offset;

    private String name;

    private String plant;

}
