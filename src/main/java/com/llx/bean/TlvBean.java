package com.llx.bean;

/**
 * Type-Length-Value Bean
 * @author Lilx
 * @since 2016
 */
public class TlvBean implements Sendable {
    /**
     * 类型码（占用1字节）
     */
    private int type;
    /**
     * 长度（占用2字节）
     */
    private int length;
    /**
     * 值（长度不超过65535）
     */
    private String value;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int length() {
        return 1 + 2 + value.length();
    }
}
