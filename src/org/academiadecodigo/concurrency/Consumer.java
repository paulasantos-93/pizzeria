package org.academiadecodigo.concurrency;

import org.academiadecodigo.concurrency.bqueue.BQueue;
import org.academiadecodigo.concurrency.bqueue.Pizza;

public class Consumer implements Runnable {

    private final BQueue<Pizza> queue;
    private int consumedElements;

    public Consumer(BQueue queue, int consumedElements) {
        this.queue = queue;
        this.consumedElements = consumedElements;
    }

    @Override
    public void run() {

        while (consumedElements > 0) {

            synchronized (queue) {

                // Block until element is removed from queue
                Pizza pizza = queue.poll();

                System.out.println(Thread.currentThread().getName() + " has consumed " + pizza + " from the queue");

                if (queue.getSize() == 0) {
                    System.out.println(Thread.currentThread().getName() + " has left the queue empty. Radical!");
                }

            }

            --consumedElements;

            try {
                Thread.sleep((int) (Math.random() * 3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
