package bean;

import common.CRC16;

/**
 * @author Lilx
 * @since 2016
 */
public class SensorMsgBean implements Sendable {
    /**
     * 设备地址码
     */
    int address;
    /**
     * 命令功能码
     */
    int function;
    /**
     * 前置数据（如开始地址、数据大小等）
     */
    int[] dataPrefix;
    /**
     * 数据内容
     */
    int[] data;
    /**
     * CRC16校验码
     */
    CRC16 crc16;

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public int[] getDataPrefix() {
        return dataPrefix;
    }

    public void setDataPrefix(int... dataPrefix) {
        this.dataPrefix = dataPrefix;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int... data) {
        this.data = data;
    }

    public CRC16 getCrc16() {
        return crc16;
    }

    public void setCrc16(CRC16 crc16) {
        this.crc16 = crc16;
    }

    @Override
    public int length() {
        return 1 + 1 + dataPrefix.length + data.length + 2;
    }
}
