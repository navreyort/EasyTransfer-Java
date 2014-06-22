package edu.mit.media.of.net.serial;

import java.nio.ByteBuffer;

import edu.mit.media.of.net.DataReader;
import edu.mit.media.of.net.serial.protocol.SerialTransmitData;

public abstract class SerialDataReader implements DataReader<ByteBuffer> {

	protected SerialIO client;
	protected SerialTransmitData rxData;
	
	public SerialDataReader(SerialIO client, SerialTransmitData rxData){
		this.client = client;
		this.rxData = rxData;
	}
	
	@Override
	public void processInput(ByteBuffer message){
		this.rxData.toData(message);
	}
	
	@Override
	public void run() {}

	@Override
	public void close() {}

	@Override
	public void start() {}

	@Override
	public boolean isRunning() {return false;}
	
	@Override
	public int getPacketSize(){
		return this.rxData.getPacketSize();
	}
	
	@Override
	public int getHeaderSize(){
		return this.rxData.getHeaderSize();
	}
	
	@Override
	public int getDataSize(){
		return (int) this.rxData.getDataSize();
	}
}
