package org.academiadecodigo.concurrency.bqueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Blocking queue
 */
public class BQueue<T> {

    /**
     * Queue size
     **/
    private final int limit;

    /**
     * Container backing up the queue
     */
     private final Queue<T> queue;

    /**
     * @param limit the queue size
     */
    public BQueue(int limit) {
        this.limit = limit;
        queue = new LinkedList<>();
    }

    /**
     * Adds an item into the queue, blocking if the queue is full
     *
     * @param data the item to add
     */
    public void offer(T data) {

        synchronized (this) {

            // Make sure we block when the queue is full, even if interrupted
            while (queue.size() == limit) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Add element to the queue
            queue.offer(data);
            System.out.println("\n## ELEMENT ADDED, SIZE OF QUEUE IS NOW " + queue.size() + " ##");

            // Notify other threads
            notifyAll();

        }

    }

    /**
     * Removes an item from the queue, blocking if the queue is empty
     *
     * @return the removed item
     */
    public T poll() {

        synchronized (this) {

            // Make sure we block when the queue is empty, even if interrupted
            while (queue.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Remove item from the queue
            T data = queue.poll();
            System.out.println("\n## ELEMENT REMOVED, SIZE OF QUEUE IS NOW " + queue.size() + " ##");

            // Notify other threads
            notifyAll();

            return data;

        }

    }

    /**
     * Gets the number of elements in the queue
     *
     * @return the number of elements
     */
    public int getSize() {
        synchronized (this) {
            return queue.size();
        }
    }

    /**
     * Gets the maximum number of elements that can be present in the queue
     *
     * @return the maximum number of elements
     */
    public int getLimit() {
        return limit;
    }
}
