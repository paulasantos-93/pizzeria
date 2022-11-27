package org.academiadecodigo.concurrency;

import org.academiadecodigo.concurrency.bqueue.BQueue;
import org.academiadecodigo.concurrency.bqueue.Pizza;

public class Producer implements Runnable {

    private final BQueue<Pizza> queue;
    private int elementsToProduce;

    public Producer(BQueue queue, int elementsToProduce) {
        this.queue = queue;
        this.elementsToProduce = elementsToProduce;
    }


    @Override
    public void run() {

        while (elementsToProduce > 0) {

           Pizza pizza = new Pizza();

            synchronized (queue) {

                System.out.println(Thread.currentThread().getName() + " will add the " + pizza + " to the queue");

                // Block until element is added to queue
                queue.offer(pizza);

                if (queue.getSize() == queue.getLimit()) {

                    System.out.println(Thread.currentThread().getName() + " has left the queue full! Mamma mia");

                }

            }

            --elementsToProduce;

            try {
                Thread.sleep((int) (Math.random() * 3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
