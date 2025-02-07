package pl.training.concurrency.ex001_creating_threads;

public class Application {

    public static void main(String[] args) {
        System.out.printf("Current thread: %s\n", Thread.currentThread().getName());

        PrintTime printTime = new PrintTime();
        Thread threadOne = new Thread(printTime);
        threadOne.start();

        Thread threadTwo = new Thread(printTime);
        threadTwo.start();

        Thread threadThree = new PrintTimeThread();
        threadThree.start();
    }

}
