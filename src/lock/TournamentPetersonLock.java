package lock;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TournamentPetersonLock implements Lock {

    final private AtomicInteger id = new AtomicInteger(0);

    private ThreadLocal<Integer> THREADID = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return id.getAndIncrement();
        }
    };

    class PetersonLockWithLevel extends PetersonLock {
        int level;

        public PetersonLockWithLevel(int l) {
            this.level = l;
            super.THREAD_ID = new ThreadLocal<Integer>() {
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

    private int totalLevel;


    private List<PetersonLockWithLevel[]> petersonLocks;

    public TournamentPetersonLock() {
        this(1);
    }

    public TournamentPetersonLock(int totalLevel) {
        this.totalLevel = totalLevel;
        this.petersonLocks = new LinkedList<PetersonLockWithLevel[]>();
        for (int level = 1, lockNumPerLevel = 1; level <= totalLevel; level++, lockNumPerLevel *= 2) {

            PetersonLockWithLevel[] curLevel = new PetersonLockWithLevel[lockNumPerLevel];

            for (int j = 0; j < lockNumPerLevel; j++) {
                curLevel[j] = new PetersonLockWithLevel(level);
            }

            this.petersonLocks.add(curLevel);
        }
    }

    @Override
    public void lock() {
        int id = THREADID.get();
//        if (id >= (1 << totalLevel)) {
//            while (true) ;
//        }

        for (int i = totalLevel - 1; i >= 0; i--) {
//            System.out.println("node " + id + " try to acquire lock[" + i + "][" + id / (1 << (totalLevel - i)) + "]");
            this.petersonLocks.get(i)[id / (1 << (totalLevel - i))].lock();
//            System.out.println("node " + id + " acquired lock[" + i + "][" + id / (1 << (totalLevel - i)) + "]");
        }

    }

    @Override
    public void unlock() {
        int id = THREADID.get();
        for (int i = 0; i < totalLevel; i++) {
//            System.out.println("node " + id + " unlock[" + i + "][" + id / (1 << (totalLevel - i)) + "]");
            this.petersonLocks.get(i)[id / (1 << (totalLevel - i))].unlock();
        }
    }
}
