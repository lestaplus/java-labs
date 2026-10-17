import java.util.concurrent.ThreadLocalRandom;
import java.util.Locale;

public class ParallelMonteCarloPi {

    private static class Worker extends Thread {
        private final long iterations;
        private long circleDotsCount = 0;

        public Worker(long iterations) {
            this.iterations = iterations;
        }

        @Override
        public void run() {
            long count = 0;
            for (long i = 0; i < iterations; i++) {
                double x = ThreadLocalRandom.current().nextDouble();
                double y = ThreadLocalRandom.current().nextDouble();

                if ((x * x) + (y * y) <= 1.0) {
                    count++;
                }
            }
            circleDotsCount = count;
        }

        public long getCircleDotsCount() {
            return circleDotsCount;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            System.err.println("Error: pass number of threads as argument!");
            return;
        }
        int threadsCount = Integer.parseInt(args[0]);
        long totalIterations = 1_000_000_000L;
        long iterationsPerThread = totalIterations / threadsCount;

        Worker[] workers = new Worker[threadsCount];

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadsCount; i++) {
            long iterations = (i == threadsCount - 1)
                    ? iterationsPerThread + (totalIterations % threadsCount)
                    : iterationsPerThread;

            workers[i] = new Worker(iterations);
            workers[i].start();
        }

        long totalCircleDots = 0;
        for (Worker worker : workers) {
            worker.join();
            totalCircleDots += worker.getCircleDotsCount();
        }

        long endTime = System.currentTimeMillis();
        double totalTime = endTime - startTime;

        double pi = 4.0 * totalCircleDots / totalIterations;

        System.out.printf(Locale.US, """
                PI is %.5f
                THREADS %d
                ITERATIONS %,d
                TIME %.2fms
                """, pi, threadsCount, totalIterations, totalTime);
    }
}
