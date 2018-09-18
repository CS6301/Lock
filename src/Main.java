import cs6301.github.io.lock.Lock;
import cs6301.github.io.lock.TournamentPetersonLock;
import cs6301.github.io.test.TestLock;

public class Main {

    public static void main(String[] args) {

        int threadNum = Integer.parseInt(args[0]);

        Lock lock = new TournamentPetersonLock(threadNum);

        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(new TestLock(lock, i, 500 * i));
        }
        for (Thread t : threads)
            t.start();
    }
}
