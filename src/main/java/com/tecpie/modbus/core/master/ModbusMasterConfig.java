package com.tecpie.modbus.core.master;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.BaseLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModbusMasterConfig {

    private static final Logger logger = LoggerFactory.getLogger(ModbusMasterConfig.class);

    static ModbusFactory modbusFactory;

    static {
        modbusFactory = new ModbusFactory();
    }

    public static ModbusMaster getMaster() throws ModbusInitException {
        IpParameters params = new IpParameters();
        params.setHost("192.168.66.128");
        params.setPort(502);
        // 这个属性确定了协议帧是否是通过tcp封装的RTU结构，采用modbus tcp/ip时，要设为false, 采用modbus rtu over tcp/ip时，要设为true
        params.setEncapsulated(false);
        // modbusFactory.createRtuMaster(wapper); //RTU 协议
        // modbusFactory.createUdpMaster(params);//UDP 协议
        // modbusFactory.createAsciiMaster(wrapper);//ASCII 协议
        ModbusMaster master = modbusFactory.createTcpMaster(params, false);
        master.init();
        return master;
    }

    /**
     * 读取线圈状态 F=01
     *
     * @param slaveId 从属 ID
     * @param offset  抵消
     * @return {@link Boolean}
     * @throws ModbusInitException      Modbus 初始化异常
     * @throws ErrorResponseException   错误响应异常
     * @throws ModbusTransportException Modbus 传输异常
     */
    public static Boolean readCoilStatus(int slaveId, int offset) throws ModbusInitException, ErrorResponseException, ModbusTransportException {
        BaseLocator<Boolean> loc = BaseLocator.coilStatus(slaveId, offset);
        return getMaster().getValue(loc);
    }

    /**
     * 读取[02 Input Status 1x]类型 开关数据
     *
     * @param slaveId 从站编号
     * @param offset  偏移位
     */
    public static Boolean readInputStatus(int slaveId, int offset)
            throws ModbusTransportException, ErrorResponseException, ModbusInitException {
        // 02 Input Status
        BaseLocator<Boolean> loc = BaseLocator.inputStatus(slaveId, offset);
        return getMaster().getValue(loc);
    }

    /**
     * 读取[03 Holding Register类型 2x]模拟量数据
     *
     * @param slaveId  从寄存器id
     * @param offset   位置
     * @param dataType 数据类型,来自com.serotonin.modbus4j.code.DataType
     */
    public static Number readHoldingRegister(int slaveId, int offset, int dataType)
            throws ModbusTransportException, ErrorResponseException, ModbusInitException {
        // 03 Holding Register类型数据读取
        BaseLocator<Number> loc = BaseLocator.holdingRegister(slaveId, offset, dataType);
        return getMaster().getValue(loc);
    }


    /**
     * 读取[04 Input Registers 3x]类型 模拟量数据
     */
    public static Number readInputRegisters(int slaveId, int offset, int dataType)
            throws ModbusTransportException, ErrorResponseException, ModbusInitException {
        // 04 Input Registers类型数据读取
        BaseLocator<Number> loc = BaseLocator.inputRegister(slaveId, offset, dataType);
        return getMaster().getValue(loc);
    }

}
