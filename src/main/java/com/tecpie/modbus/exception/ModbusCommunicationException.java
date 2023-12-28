package com.tecpie.modbus.exception;

public class ModbusCommunicationException extends RuntimeException {

    public ModbusCommunicationException(Exception e) {
        super(e);
    }

}
