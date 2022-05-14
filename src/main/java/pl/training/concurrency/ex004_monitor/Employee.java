package pl.training.concurrency.ex004_monitor;

import java.util.Queue;
import java.util.Random;
import java.util.UUID;

public class Employee implements Runnable {

    private static final int MAX_SLEEP_TIME = 300;

    private final Queue<String> printingQueue;
    private final int tasksLimit;
    private final Random random = new Random();

    public Employee(Queue<String> printingQueue, int tasksLimit) {
        this.printingQueue = printingQueue;
        this.tasksLimit = tasksLimit;
    }
    @Override
    public void run() {
        while (true) {
            synchronized (printingQueue) {
                waitIfQueueIsFull();
                addDocument();
                try {
                    printingQueue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void addDocument() {
        try {
            Thread.sleep(random.nextInt(MAX_SLEEP_TIME));
        } catch (InterruptedException e) {
            System.out.println("Employee was interrupted...");
        }
        String text = UUID.randomUUID().toString();
        printingQueue.add(text);
        System.out.printf("New document added to printing queue: %s\n", text);
        printingQueue.notifyAll();
    }

    private void waitIfQueueIsFull() {
        while (tasksLimit == printingQueue.size()) {
            try {
                System.out.println("Queue limit reached...");
                printingQueue.wait();
            } catch (InterruptedException e) {
                System.out.println("Employee was interrupted...");
            }
        }
    }

}
