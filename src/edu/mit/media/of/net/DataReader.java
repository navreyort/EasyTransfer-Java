package edu.mit.media.of.net;

public interface DataReader<T> extends Runnable {

	/**
	 * Closes data reader
	 */
	public void close();
	
	/**
	 * Start reading client input
	 */
	public void start();
	
	/**
	 * Check if the reader is running
	 * @return
	 */
	public boolean isRunning();
	
	/**
	 * Process input data
	 * @param t data from client/server
	 */
	public void processInput(T t);
	
	/**
	 * Packet Size to transmit
	 */
	public int getPacketSize();
	public int getHeaderSize();
	public int getDataSize();
}
