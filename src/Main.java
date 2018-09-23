import cs6301.github.io.lock.*;
import cs6301.github.io.test.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int coreNum = 2;
        int iteration = 10000;
        int experimentNum = 10;
        if (args.length >= 1)
            coreNum = Integer.parseInt(args[0]);
        if (args.length >= 2)
            iteration = Integer.parseInt(args[1]);
        if (args.length >= 3)
            experimentNum = Integer.parseInt(args[2]);

        double[] tournament = new double[coreNum + 2];
        double[] bakery = new double[coreNum + 2];

        System.out.println("Tournament lock benchmark:");
        for (int threadNum = 2; threadNum <= coreNum; threadNum++) {
            System.out.printf("%d threads:\t", threadNum);
            for (int experiment = 0; experiment < experimentNum; experiment++) {
                long res = test(new TournamentPetersonLock(threadNum), threadNum, iteration);
                tournament[threadNum] += res;
                System.out.printf("%d \t", res);
            }
            tournament[threadNum] = tournament[threadNum] / experimentNum;
            System.out.printf("%.2f \n", tournament[threadNum]);
        }
        printResult(tournament, "Tournament lock", coreNum);
        System.out.println();
        System.out.println("Bakery lock benchmark:");
        for (int threadNum = 2; threadNum <= coreNum; threadNum++) {
            System.out.printf("%d threads:\t", threadNum);
            for (int experiment = 0; experiment < experimentNum; experiment++) {
                long res = test(new Bakery(threadNum), threadNum, iteration);
                bakery[threadNum] += res;
                System.out.printf("%d \t", res);
            }
            bakery[threadNum] = bakery[threadNum] / experimentNum;
            System.out.printf("%.2f \n", bakery[threadNum]);
        }
        printResult(bakery, "Bakery lock", coreNum);

        printResult(tournament, bakery, coreNum);
    }

    private static long test(Lock lock, int threadNum, int iteration) throws InterruptedException {
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

    private static void printResult(double[] res, String lock, int coreNum) {
        System.out.printf("Final result for %s:\n", lock);

        for (int i = 2; i <= coreNum; i++) {
            System.out.printf("(%d,%.2f)\n", i, res[i]);
        }

        int half = coreNum / 2;
        for (int i = 2; i <= half; i++) {
            System.out.printf("%d & %.2f & %d & %.2f\n", i, res[i], i + half, res[i + half]);
        }
        System.out.println();
    }

    private static void printResult(double[] res1, double[] res2, int coreNum) {
        System.out.println("Final result");

        int half = coreNum / 2;
        for (int i = 2; i <= half; i++) {
            System.out.printf("%d & %.2f & %.2f & %d & %.2f & %.2f \\\\\n", i, res1[i], res2[i], i + half, res1[i + half], res2[i + half]);
        }
        System.out.println();
    }
}
