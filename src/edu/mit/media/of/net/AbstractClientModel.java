package edu.mit.media.of.net;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

/**
 * Abstract client model based on blocking queue processing.
 * @author akito
 *
 * @param <T>
 */
@Log
public abstract class AbstractClientModel<T> implements ClientModel<T> {

	private Thread thread;
	private BlockingQueue<T> inQueue;
	private BlockingQueue<T> outQueue;
	@Getter private boolean isRunning;
	@Getter @Setter private boolean lock;
	@Setter protected DataReader<T> readHandler;
	@Getter private ClientServerModel clientServerModel;
	
	public AbstractClientModel(ClientServerModel server, String threadID) {
		this.clientServerModel = server;
		this.thread = new Thread(this,threadID);
		inQueue = new BlockingQueue<T>();
		outQueue = new BlockingQueue<T>();
		isRunning = false;
		lock = false;
	}

	/**
	 * Starts client process
	 */
	public void start(){
		thread.start();
		readHandler.start();
	}

	/**
	 * Runs client process.
	 */
	public final void run(){
		this.isRunning = true;
		while(this.isRunning){
			if(!this.lock){
				try {
					if(!this.inQueue.isEmpty()){
						this.readHandler.processInput(this.inQueue.pop());
					}

					if(!this.outQueue.isEmpty()){
						this.write(this.outQueue.pop());
					}
				} catch (InterruptedException e) {
					log.warning(e.getMessage());
				}
			}
		}
	}

	/**
	 * Closes connection
	 */
	public void close() {
		isRunning = false;
		if(readHandler.isRunning()){
			readHandler.close();
		}
		thread.interrupt();
	}

	/**
	 * Pushes data to queue.
	 * Call this method when data needs to be sent.
	 * @param t
	 */
	public final synchronized void pushOutput(T t){
		outQueue.push(t);
	}

	/**
	 * Pushes data to queue.
	 * @param t
	 */
	public final synchronized void pushInput(T t){
		inQueue.push(t);
	}

	@Override
	public final DataReader<T> getDataReader() {
		return readHandler;
	}

	/**
	 * Returns true if client still has data to process
	 * @return
	 */
	public final boolean dataExists(){
		return !inQueue.isEmpty() || !outQueue.isEmpty();
	}
}
