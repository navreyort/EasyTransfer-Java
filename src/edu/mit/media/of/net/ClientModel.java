package edu.mit.media.of.net;



/**
 * Server side model of client
 * @author akito
 *
 * @param <T>
 */
public interface ClientModel<T> extends ClientServerModel {

	/**
	 * Write data to a client
	 * @param t
	 */
	public void write(T t);
	
	/**
	 * Read data from a client
	 * @return
	 */
	public T read();
	
	/**
	 * Get client data reader
	 * @return
	 */
	public DataReader<T> getDataReader();
}
