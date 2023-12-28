package com.tecpie.modbus.core.master;

import com.serotonin.modbus4j.BatchRead;
import com.serotonin.modbus4j.BatchResults;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.tecpie.modbus.entity.CachePoint;
import com.tecpie.modbus.entity.CacheTask;
import com.tecpie.modbus.enums.DataTypeEnums;
import com.tecpie.modbus.exception.ModbusCommunicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ModbusMasterConfig {

    private ModbusMasterConfig() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ModbusMasterConfig.class);

    static ModbusFactory modbusFactory;

    static {
        modbusFactory = new ModbusFactory();
    }

    public static ModbusMaster getMaster(String host, int port) {
        IpParameters params = new IpParameters();
        params.setHost(host);
        params.setPort(port);
        // 这个属性确定了协议帧是否是通过tcp封装的RTU结构，采用modbus tcp/ip时，要设为false, 采用modbus rtu over tcp/ip时，要设为true
        params.setEncapsulated(false);
        // modbusFactory.createRtuMaster(wapper); //RTU 协议
        // modbusFactory.createUdpMaster(params);//UDP 协议
        // modbusFactory.createAsciiMaster(wrapper);//ASCII 协议
        ModbusMaster master = modbusFactory.createTcpMaster(params, true);
        try {
            master.init();
        } catch (ModbusInitException e) {
            logger.error(e.getLocalizedMessage());
        }
        return master;
    }

    /**
     * 读取线圈状态 F=01
     *
     * @param slaveId 从属 ID
     * @param offset  抵消
     * @return {@link Boolean}
     * @throws ErrorResponseException   错误响应异常
     * @throws ModbusTransportException Modbus 传输异常
     */
    public static Boolean readCoilStatus(ModbusMaster master, int slaveId, int offset) throws ErrorResponseException, ModbusTransportException {
        BaseLocator<Boolean> loc = BaseLocator.coilStatus(slaveId, offset);
        return master.getValue(loc);
    }

    /**
     * 读取[02 Input Status 1x]类型 开关数据
     *
     * @param slaveId 从站编号
     * @param offset  偏移位
     */
    public static Boolean readInputStatus(ModbusMaster master, int slaveId, int offset)
            throws ModbusTransportException, ErrorResponseException {
        // 02 Input Status
        BaseLocator<Boolean> loc = BaseLocator.inputStatus(slaveId, offset);
        return master.getValue(loc);
    }

    /**
     * 读取[03 Holding Register类型 2x]模拟量数据
     *
     * @param slaveId  从寄存器id
     * @param offset   位置
     * @param dataType 数据类型,来自com.serotonin.modbus4j.code.DataType
     */
    public static Number readHoldingRegister(ModbusMaster master, int slaveId, int offset, int dataType) throws ModbusTransportException, ErrorResponseException {
        // 03 Holding Register类型数据读取
        BaseLocator<Number> loc = BaseLocator.holdingRegister(slaveId, offset, dataType);
        return master.getValue(loc);
    }


    /**
     * 读取[04 Input Registers 3x]类型 模拟量数据
     */
    public static Number readInputRegisters(ModbusMaster master, int slaveId, int offset, int dataType)
            throws ModbusTransportException, ErrorResponseException {
        // 04 Input Registers类型数据读取
        BaseLocator<Number> loc = BaseLocator.inputRegister(slaveId, offset, dataType);
        return master.getValue(loc);
    }

    public static BatchResults<Integer> readBatch(ModbusMaster modbusMaster, CacheTask cacheTask, List<CachePoint> pointList) {
        Integer slaveId = cacheTask.getSlaveId();
        Integer function = cacheTask.getFunction();
        String dataType = cacheTask.getDataType();
        BatchRead<Integer> batch = new BatchRead<>();
        for (CachePoint point : pointList) {
            Integer offset = point.getOffset();
            if (function == 1) {
                batch.addLocator(offset, BaseLocator.coilStatus(slaveId, offset));
            } else if (function == 2) {
                batch.addLocator(offset, BaseLocator.inputStatus(slaveId, offset));
            } else if (function == 3) {
                batch.addLocator(offset, BaseLocator.holdingRegister(slaveId, offset, DataTypeEnums.getType(dataType)));
            } else if (function == 4) {
                batch.addLocator(offset, BaseLocator.inputRegister(slaveId, offset, DataTypeEnums.getType(dataType)));
            }
        }
        try {
            batch.setContiguousRequests(true);
            return modbusMaster.send(batch);
        } catch (ModbusTransportException | ErrorResponseException e) {
            throw new ModbusCommunicationException(e);
        }
    }

    public static Object switchRead(ModbusMaster modbusMaster, int function, int slaveId, String dataType, CachePoint point) {
        Integer offset = point.getOffset();
        Object value = null;
        try {
            if (function == 1) {
                value = ModbusMasterConfig.readCoilStatus(modbusMaster, slaveId, offset);
            } else if (function == 2) {
                value = ModbusMasterConfig.readInputStatus(modbusMaster, slaveId, offset);
            } else if (function == 3) {
                value = ModbusMasterConfig.readHoldingRegister(modbusMaster, slaveId, offset, DataTypeEnums.getType(dataType));
            } else if (function == 4) {
                value = ModbusMasterConfig.readInputRegisters(modbusMaster, slaveId, offset, DataTypeEnums.getType(dataType));
            }
        } catch (ErrorResponseException | ModbusTransportException e) {
            logger.error(e.getLocalizedMessage());
        }
        return value;
    }

}
