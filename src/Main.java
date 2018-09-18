import lock.Lock;
import lock.PetersonLock;
import lock.TournamentPetersonLock;
import test.TestLock;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Lock lock = new TournamentPetersonLock(5);

        int count = 32;
        Thread[] threads = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(new TestLock(lock, i));
        }
        for (Thread t : threads)
            t.start();
    }
}
