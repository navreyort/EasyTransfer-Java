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
		
		//XXX Do stuff with RxData here. Probably want connectionManager to access other objects		
		System.out.println("Byte: "+((RxData)this.rxData).getB());
		System.out.println("Short: "+((RxData)this.rxData).getS());
		System.out.println("Int: "+((RxData)this.rxData).getI());
		System.out.println("Long: "+((RxData)this.rxData).getL());
		System.out.println("Char: "+((RxData)this.rxData).getC());
		System.out.println("Boolean: "+((RxData)this.rxData).getBo());
		System.out.println("================================");
	}
}
