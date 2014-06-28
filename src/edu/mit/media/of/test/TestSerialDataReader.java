package edu.mit.media.of.test;

import java.nio.ByteBuffer;

import edu.mit.media.of.net.serial.SerialDataReader;
import edu.mit.media.of.net.serial.SerialIO;


public class TestSerialDataReader extends SerialDataReader {

	//XXX Convenient reference to do further processing on receiving something from client
	private TestSerialConnectionManager connectionManager;
	
	public TestSerialDataReader(SerialIO client, TestSerialConnectionManager connectionManager) {
		super(client, new RxData());
		this.connectionManager = connectionManager;
	}

	@Override
	public void processInput(ByteBuffer message) {
		super.processInput(message);
		this.connectionManager.dumpMessage((RxData) this.rxData);		
	}
}
