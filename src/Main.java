import cs6301.github.io.lock.*;
import cs6301.github.io.test.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int coreNum = 2;
        int iteration = 10000;
        if (args.length >= 1)
            coreNum = Integer.parseInt(args[0]);
        if (args.length >= 2)
            iteration = Integer.parseInt(args[1]);

        for (int threadNum = 2; threadNum <= coreNum; threadNum++) {
            System.out.printf("(%d,%d)", threadNum, test(new TournamentPetersonLock(threadNum), threadNum, iteration));
        }
        System.out.println();
        for (int threadNum = 2; threadNum <= coreNum; threadNum++) {
            System.out.printf("(%d,%d)", threadNum, test(new Bakery(threadNum), threadNum, iteration));
        }
    }

    static long test(Lock lock, int threadNum, int iteration) throws InterruptedException {
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(new TestLock(lock, i, iteration));
        }
        TestLock.reset();
        Timer timer = new Timer();
        for (Thread t : threads)
            t.start();

        for (Thread t : threads)
            t.join();
        assert TestLock.shared == threadNum * iteration;
        return timer.end().getElapsedTime();
    }
}
