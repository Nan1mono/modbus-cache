# DataType数据类型

所有数据类型引自com.serotonin.modbus4j.code.DataType

| 编号                 | 类型                    | 说明                                  |
| -------------------- | ----------------------- | ------------------------------------- |
| binary               | BINARY                  | 二进制，对应 Modbus 数据类型 01       |
| twoByteIntUnsigned   | TWO_BYTE_INT_UNSIGNED   | 无符号短整型，对应 Modbus 数据类型 03 |
| twoByteIntSigned     | TWO_BYTE_INT_SIGNED     | 有符号短整型，对应 Modbus 数据类型 03 |
| fourByteIntUnsigned  | FOUR_BYTE_INT_UNSIGNED  | 无符号整型，对应 Modbus 数据类型 06   |
| fourByteIntSigned    | FOUR_BYTE_INT_SIGNED    | 有符号整型，对应 Modbus 数据类型 06   |
| fourByteFloat        | FOUR_BYTE_FLOAT         | 浮点型，对应 Modbus 数据类型 06       |
| eightByteIntUnsigned | EIGHT_BYTE_INT_UNSIGNED | 无符号长整型，对应 Modbus 数据类型 16 |
| eightByteIntSigned   | EIGHT_BYTE_INT_SIGNED   | 有符号长整型，对应 Modbus 数据类型 16 |
| eightByteFloat       | EIGHT_BYTE_FLOAT        | 双精度浮点型，对应 Modbus 数据类型 16 |

# Function类型

| 编号 | 对应寄存器函数   |
| ---- | ---------------- |
| 01   | Coil Status      |
| 02   | Input Status     |
| 03   | Holding Register |
| 04   | Input Registers  |