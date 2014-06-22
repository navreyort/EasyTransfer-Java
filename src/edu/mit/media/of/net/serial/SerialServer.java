package edu.mit.media.of.net.serial;

import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Hashtable;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import lombok.extern.java.Log;
import edu.mit.media.of.net.ClientServerModel;

@Log
public abstract class SerialServer implements ClientServerModel {

	private Hashtable<String, SerialIO> clientsByID; // coders keyed by id
	
	public SerialServer(){
		clientsByID = new Hashtable<String, SerialIO>();
	}
	
	public SerialServer(String port, int serialRate, String threadID){
		this();
		this.addSerialClient(port,serialRate);	
	}
	
	/**
	 * Initialize server
	 * @param port
	 */
	public final synchronized void addSerialClient(String port, int serialRate) {
		try {
			SerialPort serialPort = new SerialPort(port);
			serialPort.openPort();
			serialPort.setParams(serialRate,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
	
			SerialIO io =  new SerialIO(this, serialPort, port);
			this.clientsByID.put(port,io);
			io.start();
			
			log.info("Server listening to port: "+port);
		} catch (SerialPortException e) {
			log.warning("Could not listen on port: " +port);
			log.warning(e.getMessage());
			log.warning("Exiting application");
			System.exit(1);
		}
	}
	
	public final void close(String port) {
		SerialIO serialIO = this.clientsByID.remove(port);
		if (serialIO != null) {
			serialIO.close();
		}
		serialIO = null;
	}
	
	/**
	 * looks for serial ports and returns available ones.
	 * @return available ports
	 */
	public static String[] availablePorts(){
		return SerialPortList.getPortNames();
	}

	public boolean hasPort(String port){
		return this.clientsByID.containsKey(port);
	}
	
	@Override
	public final void run() {}

	@Override
	public final void start() {}

	@Override
	public final void close() {
		this.removeAllClients();
	}
	
	private final void removeAllClients() {
		for(Enumeration<SerialIO> e = this.getClients(); e.hasMoreElements();){
			SerialIO client = e.nextElement();
			client.close();
		}
		clientsByID.clear();
	}

	/**
	 * Get all clients
	 * 
	 * @return
	 */
	public final Enumeration<SerialIO> getClients() {
		return clientsByID.elements();
	}

	/**
	 * Send message to everyone whose connected
	 * 
	 * @param msg
	 */
	public final synchronized void broadCastData(ByteBuffer t) {
		synchronized (clientsByID) {
			for (Enumeration<SerialIO> e = getClients(); e.hasMoreElements();) {
				SerialIO client = (SerialIO) e.nextElement();
				client.pushOutput(t);
			}
		}
	}

	/**
	 * Send event to an individual client
	 * @param socket
	 * @param data
	 */
	public final synchronized void sendData(String port, ByteBuffer t) {
		SerialIO client = clientsByID.get(port);
		client.pushOutput(t);
	}

	/**
	 * Remove client connection
	 * 
	 * @param socket
	 */
	public final synchronized void removeClient(String port) {
		clientsByID.remove(port).close();		
		log.info("Socket closed.");
	}
	
	/**
	 * Factory method for creating data reader
	 */
	public abstract SerialDataReader generateDataReader(SerialIO client);
}
