package com.llx.comm;

import com.llx.bean.SerialPortConfigBean;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.*;

/**
 * @author Lilx
 * @since 2016
 */
public class SerialController implements Controller {
    private volatile boolean isOpen = false;
    private SerialPort serialPort;
    private BufferedOutputStream out;
    private BufferedInputStream in;

    public SerialController() {
    }

    @Override
    public synchronized SerialPort open(SerialPortConfigBean config, String portName)
            throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException {
        if (null == portName) {
            portName = config.getPort();
        }
        if (!isOpen) {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(config.getPort());
            CommPort commPort = portIdentifier.open(portName, config.getTimeout());
            if (commPort instanceof SerialPort) {
                this.serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(config.getBaudrate(), config.getDataBit(), config.getStopBit(), config.getParityBit());
                System.out.println("Open " + portName + " on " + config.getPort() + " successful!");
                this.in = new BufferedInputStream(new DataInputStream(serialPort.getInputStream()));
                this.out = new BufferedOutputStream(new DataOutputStream(serialPort.getOutputStream()));
                this.isOpen = true;
                return this.serialPort;
            } else {
                System.out.println("ERROR:Open" + portName + " failed.Cause " + config.getPort() + " is not a serial port!");
                commPort.close();
            }
        } else {
            System.out.println(portName + " is already open!");
        }
        return this.serialPort;
    }

    @Override
    public synchronized void close() throws IOException {
        if (isOpen) {
            in.close();
            out.close();
            serialPort.close();
            this.isOpen = false;
        }
    }

    @Override
    public synchronized void write(byte[] bytes) throws IOException {
        if (isOpen) {
            out.write(bytes);
            out.flush();
        } else {
            throw new IOException("Serial port is already closed!");
        }
    }

    @Override
    public synchronized int read() throws IOException {
        return in.read();
    }

    @Override
    public synchronized int read(byte[] buffer) throws IOException {
        int length = 0;
        while (in.available() > 0 && length < buffer.length) {
            length += in.read(buffer, length, buffer.length - length);
        }
        return length;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }
}
