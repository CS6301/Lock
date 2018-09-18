package cs6301.github.io.test;

import cs6301.github.io.lock.Lock;

public class TestLock implements Runnable {

    static int shared = 0;

    private Lock lock;
    private int id;
    private int iteration;

    public TestLock(Lock lock, int id, int iterations) {
        this.lock = lock;
        this.id = id;
        this.iteration = iterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.iteration; i++) {
            this.lock.lock();
            shared++;
            System.out.println(this.id + " increment shared value to " + shared);
            this.lock.unlock();
        }
    }
}
