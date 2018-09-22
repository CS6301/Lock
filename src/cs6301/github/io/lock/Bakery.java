package cs6301.github.io.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;

public class Bakery implements Lock {

    final private AtomicInteger id = new AtomicInteger(0);

    private ThreadLocal<Integer> THREAD_ID = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return id.getAndIncrement();
        }
    };

    private AtomicIntegerArray flag;
    private AtomicLongArray label;

    public Bakery(int n) {
        this.flag = new AtomicIntegerArray(n);
        this.label = new AtomicLongArray(n);
    }

    private boolean check(int id) {
        for (int i = 0; i < this.label.length(); i++) {
            if (this.flag.get(i) == 1 &&
                (this.label.get(i) < this.label.get(id) ||
                    (this.label.get(i) == this.label.get(id) && i < id)))
                return true;
        }
        return false;
    }

    private long max(AtomicLongArray atomicLongArray) {
        long max = 0, tmp;
        for (int i = 0; i < atomicLongArray.length(); i++) {
            tmp = atomicLongArray.get(i);
            if (tmp > max)
                max = tmp;
        }
        return max;
    }

    public void lock() {
        int id = THREAD_ID.get();
        if (id >= this.label.length()) {
            throw new IllegalThreadStateException(
                "number of threads trying to acquire lock exceed max support value");
        }
        this.flag.set(id, 1);
        this.label.set(id, max(label) + 1);
        while (this.check(id)) ;
    }

    public void unlock() {
        this.flag.set(THREAD_ID.get(), 0);
    }
}
