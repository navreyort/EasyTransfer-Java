package edu.mit.media.of.net;

import java.util.LinkedList;

/**
 * Implementation of Blocking Queue. This is simply a queue data structure that
 * has a blocking pop() method. The running thread blocks until an element is
 * avaible in the queue.
 * 
 * @author akito
 * 
 */
public class BlockingQueue<T> {

	// Linked list treated as queue
	private LinkedList<T> queue;

	/**
	 * Constructor: initialize linked list
	 */
	public BlockingQueue() {
		queue = new LinkedList<T>();
	}

	/**
	 * Push message
	 * Pushes in new event to queue and notifies all threads that
	 * are waiting for this object's monitor.
	 * @param event
	 */
	public synchronized void push(T data) {
		queue.addLast(data);
		notifyAll();
	}

	/**
	 * Pops message as soon as message is pushed in synchronized manner
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized T pop() throws InterruptedException {
		while (isEmpty())
			wait();

		return queue.removeFirst();
	}

	/**
	 * Immediately pops without waiting and checking content
	 * @return
	 */
	public synchronized T popNow() {
		return queue.removeFirst();
	}
	
	/**
	 * Removes all messages.
	 */
	public synchronized void clear(){
		queue.clear();
	}
	
	/**
	 * Retrun size of queue
	 * 
	 * @return
	 */
	public synchronized int size() {
		return queue.size();
	}
	
	public synchronized boolean isEmpty(){
		return queue.isEmpty();
	}
}
