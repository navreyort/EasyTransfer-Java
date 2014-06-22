package edu.mit.media.of.net.serial;

import java.nio.ByteBuffer;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import lombok.Getter;
import lombok.extern.java.Log;
import edu.mit.media.of.net.AbstractClientModel;
import edu.mit.media.of.net.serial.protocol.SerialTransmitData;

/**
 * Takes care of serial i/o. It is the lowest level of serial communication class that is exposed through SerialServer and SerialDataReader. 
 * @author n4v
 *
 */
@Log
public final class SerialIO extends AbstractClientModel<ByteBuffer> implements SerialPortEventListener {

	@Getter private SerialPort serialPort;

	public SerialIO(SerialServer server, SerialPort serialPort, String threadID) throws SerialPortException{
		super(server, threadID);
		this.serialPort = serialPort;
		this.serialPort.addEventListener(this);
		this.readHandler = server.generateDataReader(this);
	}

	@Override
	public void serialEvent(SerialPortEvent portEvent) {
		ByteBuffer buffer = this.read();
		if(buffer != null){
			this.pushInput(buffer);
		}
	}

	@Override
	public ByteBuffer read() {
		try {
			byte[] dataCheck = this.serialPort.readBytes(this.readHandler.getHeaderSize());
			if(dataCheck.length > 0 
					&& dataCheck[0] == SerialTransmitData.SERIAL_INIT1
					&& dataCheck[1] == SerialTransmitData.SERIAL_INIT2
					&& dataCheck[2] == this.readHandler.getDataSize()){
				byte[] data = new byte[this.readHandler.getPacketSize()];
				byte[] otherData = this.serialPort.readBytes(this.readHandler.getPacketSize()-this.readHandler.getHeaderSize());
				System.arraycopy(dataCheck, 0, data, 0, dataCheck.length);
				System.arraycopy(otherData, 0, data, dataCheck.length, otherData.length);
				for (int i = 0; i < data.length; i++) {
					System.out.print(data[i]);System.out.print(" ");
				}
				System.out.println();
				return ByteBuffer.wrap(data);
			}
			else {
				return null;
			}
		} catch (SerialPortException e) {
			log.warning(e.getMessage());
		}
		return null;
	}

	@Override
	public void write(ByteBuffer byteBuffer) {
		try {
			this.serialPort.writeBytes(byteBuffer.array());
		} catch (SerialPortException e) {
			log.warning(e.getMessage());
		}
	}

	@Override
	public void close(){
		super.close();		
		try {
			this.serialPort.closePort();
		} catch (SerialPortException e) {
			log.warning(e.getMessage());
		}
	}
}
