package edu.mit.media.of.test;


import edu.mit.media.of.lang.primitive.ThreadManager;
import edu.mit.media.of.net.serial.SerialDataReader;
import edu.mit.media.of.net.serial.SerialIO;
import edu.mit.media.of.net.serial.SerialServer;

public abstract class TestSerialConnectionManager {

	private SerialServer serialServer;
	
	public TestSerialConnectionManager(){
		this.serialServer = new SerialServer() {
			@Override
			public SerialDataReader generateDataReader(SerialIO client) {
				return new TestSerialDataReader(client, TestSerialConnectionManager.this);
			}
		};
	}
	
	public void addClient(String port, int serialRate){
		if(!this.serialServer.hasPort(port)){
			this.serialServer.addSerialClient(port, serialRate);	//Connect to board
		}
		//TODO initialize the board
	}
	
	public abstract void dumpMessage(RxData rxData);

	//--------------------------- Main test ------------------------------------------
	public static void main(String[] args) {
		TestSerialConnectionManager manager = new TestSerialConnectionManager(){
			@Override
			public void dumpMessage(RxData rxData) {
				//XXX Do stuff with RxData here. Probably want connectionManager to access other objects		
				System.out.println("Incoming ================================");	
				System.out.println("Byte: "+((RxData)rxData).getB());
				System.out.println("Short: "+((RxData)rxData).getS());
				System.out.println("Int: "+((RxData)rxData).getI());
				System.out.println("Long: "+((RxData)rxData).getL());
				System.out.println("Char: "+((RxData)rxData).getC());
				System.out.println("Boolean: "+((RxData)rxData).getBo());	
			}
		};
		
		manager.addClient(Constants.PORT_NAMES[Constants.DEFAULT_PORT_NAMES_INDEX], Constants.SERIAL_RATES[Constants.DEFAULT_SERIAL_RATE_INDEX]);
		
		while(true){
			TxData txData = new TxData();
			
			System.out.println("Outgoing ================================");	
			System.out.println("Byte: "+(txData).getB());
			System.out.println("Short: "+(txData).getS());
			System.out.println("Int: "+(txData).getI());
			System.out.println("Long: "+(txData).getL());
			System.out.println("Char: "+(txData).getC());
			System.out.println("Boolean: "+(txData).getBo());
			manager.serialServer.sendData(Constants.PORT_NAMES[0], new TxData().toByteBuffer());
			ThreadManager.sleep(1000);
		}
	}
}
