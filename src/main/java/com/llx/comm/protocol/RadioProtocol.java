package com.llx.comm.protocol;

import com.llx.bean.RadioMsgBean;
import com.llx.bean.SerialPortConfigBean;
import com.google.gson.Gson;
import com.llx.common.CRC16;
import com.llx.comm.SerialController;
import com.llx.exception.Crc16CheckFailedException;
import com.llx.exception.PortStreamClosedException;
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
public class RadioProtocol {
    /**
     * 识别符号：帧起始符
     */
    public static final byte SYMBOL_START = 0x02;
    /**
     * 识别符号：帧结束符
     */
    public static final byte SYMBOL_END = 0x03;

    @Resource(name = "radioPortConfig")
    private SerialPortConfigBean config;
    private SerialController radio;
    private Gson gson = new Gson();

    public synchronized void open()
            throws PortInUseException, IOException, NoSuchPortException, UnsupportedCommOperationException {
        if (null == radio) {
            radio = new SerialController();
            radio.open(config, "RadioController");
        }
    }

    public synchronized void close() throws IOException {
        if (null != radio) {
            radio.close();
            radio = null;
        }
    }

    public boolean isOpen() {
        return radio.isOpen();
    }

    public synchronized void write(RadioMsgBean msg) throws IOException {
        byte[] buffer = radioMsgBean2ByteArray(msg);
        radio.write(buffer);
    }

    private byte[] radioMsgBean2ByteArray(RadioMsgBean msg) {
        return gson.toJson(msg).getBytes();
    }

    public synchronized RadioMsgBean read() throws IOException, PortStreamClosedException, Crc16CheckFailedException {
        int readByte;
        StringBuilder dataStringBuilder = new StringBuilder();
        String dataString;
        do {
            readByte = radio.read();
        } while (SYMBOL_START == readByte);
        while (SYMBOL_END != (readByte = radio.read())) {
            if (readByte >= 0 && readByte < Byte.MAX_VALUE) {
                dataStringBuilder.append((char) readByte);
            } else if (-1 == readByte) {
                throw new PortStreamClosedException();
            }/* else {
                //TODO:ask 非ADCii码处理方式，是抛弃还是仍然处理
            }*/
        }
        dataString = dataStringBuilder.toString();
        int readCrc16LowByte = radio.read();
        int readCrc16HighByte = radio.read();
        CRC16 crc16 = calcCrc16(dataString);
        if (crc16.getLowByte() != readCrc16LowByte || crc16.getHighByte() != readCrc16HighByte) {
            throw new Crc16CheckFailedException();
        }
        return gson.fromJson(dataString, RadioMsgBean.class);
    }

    private CRC16 calcCrc16(String str) {
        CRC16 crc16 = new CRC16();
        crc16.update(str.getBytes());
        return crc16;
    }
}
