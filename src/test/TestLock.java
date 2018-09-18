package test;

import lock.Lock;

public class TestLock implements Runnable {

    static int shared = 0;

    private Lock lock;
    private int id;

    public TestLock(Lock lock, int id) {
        this.lock = lock;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            this.lock.lock();
            shared ++;
            System.out.println(this.id + " increment shared value to " + shared);
            this.lock.unlock();
        }
    }
}
