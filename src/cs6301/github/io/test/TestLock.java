package cs6301.github.io.test;

import cs6301.github.io.lock.Lock;

public class TestLock implements Runnable {

    public static volatile int shared = 0;

    public static void reset() {
        shared = 0;
    }

    private Lock lock;
    private int id;
    private int iteration;

    public TestLock(Lock lock, int id, int iteration) {
        this.lock = lock;
        this.id = id;
        this.iteration = iteration;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.iteration; i++) {
            this.lock.lock();
            shared++;
//            System.out.printf("thread %s increment shared var to %d.\n", id, shared);
//            try {
//                Thread.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            this.lock.unlock();
        }
    }
}
