package comm;

import bean.SerialPortConfigBean;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

/**
 * @author Lilx
 * @since 2016
 */
public interface Controller {
    SerialPort open(SerialPortConfigBean config, String portName) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException;

    void close() throws IOException;

    void write(byte[] bytes) throws IOException;

    int read() throws IOException;

    int read(byte[] buffer) throws IOException;

    boolean isOpen();
}
