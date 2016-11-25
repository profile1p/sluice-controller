package com.llx.bean;

import gnu.io.SerialPort;

/**
 * @author Lilx
 * @since 2016
 */
public class SerialPortConfigBean {
    /**
     * 端口名（COM1、COM2....）
     */
    private String port;

    /**
     * 波特率
     */
    private int baudrate;

    /**
     * 数据位
     * 默认：8位
     */
    private int dataBit = SerialPort.DATABITS_8;

    /**
     * 停止位
     * 默认：1位
     */
    private int stopBit = SerialPort.STOPBITS_1;

    /**
     * 奇偶校验位（串口自带的校验方式，与工程内部实现的CRC16校验无关）
     * 默认：无
     */
    private int parityBit = SerialPort.PARITY_NONE;

    /**
     * 设备超时时间（单位：毫秒）
     * 默认：1000ms
     */
    private int timeout = 1000;

    /**
     * 重试次数
     * 默认：3次
     */
    private int retryTime = 3;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(int baudrate) {
        this.baudrate = baudrate;
    }

    public int getDataBit() {
        return dataBit;
    }

    public void setDataBit(int dataBit) {
        this.dataBit = dataBit;
    }

    public int getStopBit() {
        return stopBit;
    }

    public void setStopBit(int stopBit) {
        this.stopBit = stopBit;
    }

    public int getParityBit() {
        return parityBit;
    }

    public void setParityBit(int parityBit) {
        this.parityBit = parityBit;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
