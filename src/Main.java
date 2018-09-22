import cs6301.github.io.lock.*;
import cs6301.github.io.test.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int threadNum = 2;
        int iteration = 100;
        String LockType = "Peterson";
        boolean verbose = false;
        if (args.length >= 1)
            threadNum = Integer.parseInt(args[0]);
        if (args.length >= 2)
            iteration = Integer.parseInt(args[1]);
        if (args.length >= 3)
            LockType = args[2];

        if (args[args.length - 1].endsWith("verbose"))
            verbose = true;

        Lock lock;
        switch (LockType) {
            case "Tournament":
                lock = new TournamentPetersonLock(threadNum);
                break;
            case "Bakery":
                lock = new Bakery(threadNum);
                break;
            default:
                lock = new PetersonLock();
                threadNum = 2;
        }

        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(new TestLock(lock, i, iteration));
        }
        if (verbose) {
            System.out.printf("Running %d thread with %d iteration.\n", threadNum, iteration);
        }
        TestLock.reset();
        Timer timer = new Timer();
        for (Thread t : threads)
            t.start();

        for (Thread t : threads)
            t.join();
        System.out.println(timer.end());
        if (verbose) {
            System.out.printf("Shared value after execution: %d", TestLock.shared);
        }
    }
}
