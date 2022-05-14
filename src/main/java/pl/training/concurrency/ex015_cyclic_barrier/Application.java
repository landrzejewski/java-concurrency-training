package pl.training.concurrency.ex015_cyclic_barrier;

import java.util.concurrent.CyclicBarrier;

public class Application {

    private static final int THREADS_COUNT = 10;

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for (int threadIndex = 0; threadIndex < THREADS_COUNT; threadIndex++) {
            new Thread(new Service(cyclicBarrier)).start();
            Thread.sleep(3_000);
        }
    }

}
