package pl.training.concurrency.solution4;

import java.util.Date;

import static pl.training.concurrency.common.ThreadUtils.asyncRun;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        TokenBucket tokenBucket = new TokenBucket(5, 1_000);
        Thread.sleep(2_000);
        for (int index = 0; index < 12; index++) {
            asyncRun(() -> {
                tokenBucket.getToken();
                System.out.println(Thread.currentThread().getName() + " received token at " + new Date());
            }).start();
        }
        Thread.sleep(10_000);
    }

}
