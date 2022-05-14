package pl.training.concurrency.ex004_monitor;

import java.util.Queue;
import java.util.Random;

public class Printer implements Runnable {

    private static final int MAX_SLEEP_TIME = 200;

    private final Queue<String> printingQueue;
    private final Random random = new Random();

    public Printer(Queue<String> printingQueue) {
        this.printingQueue = printingQueue;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (printingQueue) {
                waitIfQueueIsEmpty();
                printDocument();
                try {
                    printingQueue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void printDocument() {
        try {
            Thread.sleep(random.nextInt(MAX_SLEEP_TIME));
        } catch (InterruptedException e) {
            System.out.println("Printing was interrupted...");
        }
        String text = printingQueue.poll();
        System.out.printf("Printing: %s\n", text);
        printingQueue.notifyAll();
    }

    private void waitIfQueueIsEmpty() {
        while (printingQueue.isEmpty()) {
            try {
                System.out.println("Queue is empty...");
                printingQueue.wait();
            } catch (InterruptedException e) {
                System.out.println("Printing was interrupted...");
            }
        }
    }

}
