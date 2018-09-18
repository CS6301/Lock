package cs6301.github.io.lock;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import static cs6301.github.io.math.IntegerMath.log2ceiling;

public class TournamentPetersonLock implements Lock {

    final private AtomicInteger id = new AtomicInteger(0);

    private ThreadLocal<Integer> THREADID = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return id.getAndIncrement();
        }
    };

    /**
     * Internal peterson lock implementation based on standard algorithm, replaced the THREAD_ID to
     * use the outer thread id to generate peterson lock's internal 0/1 id.
     */
    private class PetersonLockWithLevel extends PetersonLock {
        // level of the peterson lock.
        private int level;

        private PetersonLockWithLevel(int l) {
            this.level = l;

            // Compute id based on outer thread id and level, return 0 or 1 based on thread outer id
            super.THREAD_ID = new ThreadLocal<Integer>() {
                @Override
                protected Integer initialValue() {
                    int div = 1;
                    for (int i = 0; i < totalLevel - level; i++) {
                        div *= 2;
                    }
                    return THREADID.get() / div % 2;
                }
            };
        }
    }

    // total number of level.
    private int totalLevel;

    // List of Peterson locks array. Array of index 0 represent the top level Peterson locks,
    // which consist of only one lock instance.
    private List<PetersonLockWithLevel[]> petersonLocks;

    /**
     * If number of thread unspecified, create TournamentPetersonLock support two threads by default.
     */
    public TournamentPetersonLock() {
        this(2);
    }

    /**
     * Create TournamentPetersonLock support {@code num} of threads.
     *
     * @param num total number of threads supported.
     */
    public TournamentPetersonLock(int num) {
        // computer number of levels needed to support num threads.
        this.totalLevel = log2ceiling(num);

        // instantiate the lock arrays.
        // first level has 1 lock instance, and rest levels each contains two times lock instances of the previous level.
        this.petersonLocks = new LinkedList<>();
        for (int level = 1, lockNumPerLevel = 1; level <= totalLevel; level++, lockNumPerLevel *= 2) {
            PetersonLockWithLevel[] curLevel = new PetersonLockWithLevel[lockNumPerLevel];
            for (int j = 0; j < lockNumPerLevel; j++) {
                curLevel[j] = new PetersonLockWithLevel(level);
            }
            this.petersonLocks.add(curLevel);
        }
    }

    @Override
    public void lock() throws IllegalThreadStateException {
        int id = THREADID.get();
        if (id >= (1 << totalLevel)) {
            throw new IllegalThreadStateException("number of threads trying to acquire lock exceed max support value");
        }

        // acquire lock level by level from bottom to top.
        for (int i = totalLevel - 1; i >= 0; i--) {
            this.petersonLocks.get(i)[id / (1 << (totalLevel - i))].lock();
        }

    }

    @Override
    public void unlock() {
        int id = THREADID.get();
        for (int i = 0; i < totalLevel; i++) {
            this.petersonLocks.get(i)[id / (1 << (totalLevel - i))].unlock();
        }
    }
}
