package pl.training.concurrency.solution5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockBlockingQueue<T> {

    private final T[] array;
    private final Lock lock = new ReentrantLock();
    private final int capacity;
    private int size = 0;
    private int head = 0;
    private int tail = 0;

    @SuppressWarnings("unchecked")
    public LockBlockingQueue(int capacity) {
        array = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    public T dequeue() {
        T item = null;
        lock.lock();
        while (size == 0) {
            lock.unlock();
            lock.lock();
        }
        if (head == capacity) {
            head = 0;
        }
        item = array[head];
        array[head] = null;
        head++;
        size--;
        lock.unlock();
        return item;
    }

    public void enqueue(T item) {
        lock.lock();
        while (size == capacity) {
            lock.unlock();
            lock.lock();
        }
        if (tail == capacity) {
            tail = 0;
        }
        array[tail] = item;
        size++;
        tail++;
        lock.unlock();
    }

}
