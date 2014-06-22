package edu.mit.media.of.net;

public interface ClientServerModel extends Runnable {

	/**
	 * Starts server process
	 */
	public void start();
	/**
	 * Shuts down a server
	 */
	public void close();
}
