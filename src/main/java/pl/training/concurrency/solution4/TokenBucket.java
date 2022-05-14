package pl.training.concurrency.solution4;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenBucket {

    private final int capacity;
    private final long frequencyInMilliseconds;

    private long lastRequestTime = System.currentTimeMillis();
    private long tokensCount = 0;

    public synchronized void getToken() throws InterruptedException {
        tokensCount += (System.currentTimeMillis() - lastRequestTime) / frequencyInMilliseconds;
        if (tokensCount > capacity) {
            tokensCount = capacity;
        }
        if (tokensCount == 0) {
            Thread.sleep(frequencyInMilliseconds);
        } else {
            tokensCount--;
        }
        lastRequestTime = System.currentTimeMillis();
    }

}
