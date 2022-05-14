package pl.training.concurrency.ex023_fork_join;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Application {

    private static final int PRODUCTS_COUNT = 100_000_000;
    private static final int CHUNK_SIZE = 10_000_000;
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Product> products = new ArrayList<>();
        for (int index = 0;  index < PRODUCTS_COUNT; index++) {
            products.add(new Product(1));
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ChangePriceTask changePriceTask = new ChangePriceTask(products, 1, 0, products.size() - 1, CHUNK_SIZE);
        System.out.println("Starting...");
        long startTime = System.nanoTime();
        forkJoinPool.execute(changePriceTask);
        changePriceTask.get();
        System.out.println("Total time: " + NUMBER_FORMAT.format(System.nanoTime() - startTime) + " ns");
    }

}
