package com.tecpie.modbus.enums;

import com.serotonin.modbus4j.code.DataType;
import lombok.Getter;

@Getter
public enum DataTypeEnums {
    BINARY("binary", DataType.BINARY),
    TWO_BYTE_INT_UNSIGNED("twoByteIntUnsigned", DataType.TWO_BYTE_INT_UNSIGNED),
    TWO_BYTE_INT_SIGNED("twoByteIntSigned", DataType.TWO_BYTE_INT_SIGNED),
    FOUR_BYTE_INT_UNSIGNED("fourByteIntUnsigned", DataType.FOUR_BYTE_INT_UNSIGNED),
    FOUR_BYTE_INT_SIGNED("fourByteIntSigned", DataType.FOUR_BYTE_INT_SIGNED),
    FOUR_BYTE_FLOAT("fourByteFloat", DataType.FOUR_BYTE_FLOAT),
    EIGHT_BYTE_INT_UNSIGNED("eightByteIntUnsigned", DataType.EIGHT_BYTE_INT_UNSIGNED),
    EIGHT_BYTE_INT_SIGNED("eightByteIntSigned", DataType.EIGHT_BYTE_INT_SIGNED),
    EIGHT_BYTE_FLOAT("eightByteFloat", DataType.EIGHT_BYTE_FLOAT),
    ;

    private final String code;

    private final int dataType;

    DataTypeEnums(String code, int dataType) {
        this.code = code;
        this.dataType = dataType;
    }

    public static int getType(String type){
        for (DataTypeEnums dataTypeEnums : DataTypeEnums.values()){
            if (dataTypeEnums.getCode().equals(type)){
                return dataTypeEnums.getDataType();
            }
        }
        return -1;
    }
}
