package comm.protocol;

import bean.SensorMsgBean;
import bean.SerialPortConfigBean;
import common.CRC16;
import comm.SerialController;
import exception.Crc16CheckFailedException;
import exception.PortStreamClosedException;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Lilx
 * @since 2016
 */
@Service
public class SensorProtocol {
    /**
     * 功能码：读取传感器寄存器
     */
    public static final int FUNCTION_READ_SENSOR = 0x03;
    /**
     * 功能码：修改传感器地址
     */
    public static final int FUNCTION_CHANGE_ADDRESS = 0x06;

    @Resource(name = "sensorPortConfig")
    private SerialPortConfigBean config;
    private SerialController sensor;

    public synchronized void open()
            throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException {
        if (null == sensor) {
            sensor = new SerialController();
            sensor.open(config, "SensorController");
        }
    }

    public synchronized void close() throws IOException {
        if (null != sensor) {
            sensor.close();
            sensor = null;
        }
    }

    public boolean isOpen() {
        return sensor.isOpen();
    }


    public synchronized void write(SensorMsgBean msg) throws IOException {
        byte[] buffer = sensorMsgBean2ByteArray(msg);
        sensor.write(buffer);
    }

    private byte[] sensorMsgBean2ByteArray(SensorMsgBean msg) {
        byte[] b = new byte[msg.length()];
        int index = 0;
        b[index++] = number2Byte(msg.getAddress());
        b[index++] = number2Byte(msg.getFunction());
        switch (msg.getFunction()) {
            case FUNCTION_READ_SENSOR:
                // 与修改地址逻辑一直
            case FUNCTION_CHANGE_ADDRESS:
                b[index++] = number2Byte(msg.getDataPrefix()[0]);
                b[index++] = number2Byte(msg.getDataPrefix()[1]);
                b[index++] = number2Byte(msg.getData()[0]);
                b[index++] = number2Byte(msg.getData()[1]);
                break;
            default:
        }
        CRC16 crc16 = new CRC16();
        crc16.update(b);
        b[index++] = number2Byte(crc16.getLowByte());
        b[index] = number2Byte(crc16.getHighByte());
        return b;
    }

    private byte number2Byte(Number num) {
        return num.byteValue();
    }

    public synchronized SensorMsgBean read() throws IOException, PortStreamClosedException, Crc16CheckFailedException {
        SensorMsgBean msg = new SensorMsgBean();
        msg.setAddress(sensor.read());
        msg.setFunction(sensor.read());
        checkPortStreamOpen(msg.getAddress(), msg.getFunction());
        readData(msg);
        int[] readCrc16 = readBytes(2);
        CRC16 currentCrc16 = calcCrc16(msg);
        if (currentCrc16.getLowByte() != readCrc16[0] || currentCrc16.getHighByte() != readCrc16[1]) {
            throw new Crc16CheckFailedException();
        }
        msg.setCrc16(currentCrc16);
        return msg;
    }

    private void readData(SensorMsgBean msg) throws IOException, PortStreamClosedException {
        switch (msg.getFunction()) {
            case FUNCTION_READ_SENSOR:
                int[] dataSize = readBytes(1);
                msg.setDataPrefix(dataSize);
                msg.setData(readBytes(dataSize[0]));
                break;
            case FUNCTION_CHANGE_ADDRESS:
                msg.setDataPrefix(readBytes(2));
                msg.setData(readBytes(2));
                break;
            default:
        }
    }

    private int[] readBytes(int length) throws IOException, PortStreamClosedException {
        int[] result = new int[length];
        for (int i = 0; i < length; ++i) {
            result[i] = sensor.read();
        }
        checkPortStreamOpen(result);
        return result;
    }

    private void checkPortStreamOpen(int... array) throws PortStreamClosedException {
        for (int i : array) {
            if (-1 == i) {
                throw new PortStreamClosedException();
            }
        }
    }

    private CRC16 calcCrc16(SensorMsgBean msg) {
        CRC16 crc16 = new CRC16();
        crc16.update(msg.getAddress(), msg.getFunction());
        crc16.update(msg.getDataPrefix());
        crc16.update(msg.getData());
        return crc16;
    }
}
