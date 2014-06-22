package edu.mit.media.of.lang.primitive;

/**
 * A collection of static methods to manipulate Thread objects.
 * @author n4v
 *
 */
public class ThreadManager {

	/**
	 * Puts the current Thread to sleep for a number of milliseconds.
	 * @param millis
	 * @return Returns false if InterruptedException was thrown.
	 */
	@SuppressWarnings("static-access")
	public static boolean  sleep (long  millis) {
		try {
			Thread.currentThread().sleep(millis);
		}
		catch (InterruptedException  ex) {
			return false;
		}
		return true;
	}
}
