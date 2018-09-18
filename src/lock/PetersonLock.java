package lock;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class PetersonLock implements Lock {

    private boolean[] flag = new boolean[2];
    private volatile int victim;
    final private AtomicInteger id = new AtomicInteger(0);

    ThreadLocal<Integer> THREAD_ID = new ThreadLocal<Integer>() {
        protected Integer initialValue() {
            return id.getAndIncrement();
        }
    };

    @Override
    public void lock() {
        int i = this.THREAD_ID.get(); // 0 or 1
        int j = 1 - i;
        this.flag[i] = true;
        this.victim = i;
        while (flag[j] && victim == i) ;
    }

    @Override
    public void unlock() {
        int i = this.THREAD_ID.get();
        this.flag[i] = false;
    }
}
