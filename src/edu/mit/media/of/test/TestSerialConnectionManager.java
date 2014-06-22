package edu.mit.media.of.test;


import edu.mit.media.of.lang.primitive.ThreadManager;
import edu.mit.media.of.net.serial.SerialServer;

public abstract class TestSerialConnectionManager {

	private SerialServer serialServer;
	
	public TestSerialConnectionManager(){

	}
	
	public void addClient(String port, int serialRate){
		if(!this.serialServer.hasPort(port)){
			this.serialServer.addSerialClient(port, serialRate);	//Connect to board
		}
		//TODO initialize the board
	}
	
	public abstract void ledOn(boolean b);

	//--------------------------- Main test ------------------------------------------
	public static void main(String[] args) {
		TestSerialConnectionManager manager = new TestSerialConnectionManager(){

			@Override
			public void ledOn(boolean b) {
				// TODO Auto-generated method stub
				
			}

			
		};
		
		manager.addClient(Constants.PORT_NAMES[0], Constants.SERIAL_RATES[1]);
		
		
		while(true){
			//byte rand = (byte)(Math.random()*50);
			manager.serialServer.sendData(Constants.PORT_NAMES[0], new TxData().toByteBuffer());
			ThreadManager.sleep(1000);
		}
	}
}
