package common;

import com.sun.org.apache.bcel.internal.generic.RET;

import java.util.zip.Checksum;

/**
 * @author Lilx
 * @since 2016
 */
public class CRC16 implements Checksum {
    /**
     * MODBUS CRC16算法使用的多项式（16X + 15X + 13X + 1）0x1A001的简化
     */
    private static final long POLYNOMIAL = 0xA001L;
    /**
     * CRC16值
     */
    private long value;

    public CRC16() {
        reset();
    }

    @Override
    public void update(int b) {
        b = b & 0xFF;
        value = value ^ b;
        long checkBit;
        for (int i = 0; i < 8; ++i) {
            checkBit = value & 1;
            value = value >>> 1;
            if (1 == checkBit) {
                value = value ^ POLYNOMIAL;
            }
        }
    }

    public void update(byte... b) {
        update(b, 0, b.length);
    }

    @Override
    public void update(byte[] bytes, int off, int len) {
        if (null == bytes) {
            throw new NullPointerException();
        }
        if (off < 0 || len < 0 || off > bytes.length - len) {
            throw new ArrayIndexOutOfBoundsException();
        }
        for (int i = off; i < len; ++i) {
            update(bytes[i]);
        }
    }

    public void update(int... b) {
        update(b, 0, b.length);
    }

    public void update(int[] bytes, int off, int len) {
        if (null == bytes) {
            throw new NullPointerException();
        }
        if (off < 0 || len < 0 || off > bytes.length - len) {
            throw new ArrayIndexOutOfBoundsException();
        }
        for (int i = off; i < len; ++i) {
            update(bytes[i]);
        }
    }
    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void reset() {
        this.value = 0xFFFFL;
    }

    public boolean equals(CRC16 crc16) {
        return crc16.value == value;
    }

    public long getHighByte() {
        return (value >>> 8 & 0xFF);
    }

    public long getLowByte() {
        return (value & 0xFF);
    }
}
