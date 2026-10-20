package Task2;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadCreator {

    private static class ThreadStringGenerator implements Runnable {
        private final int id;
        private static final String CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
        private final CircularBuffer<String> cb;

        public ThreadStringGenerator(int id, CircularBuffer<String> cb) {
            this.id = id;
            this.cb = cb;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                int randLength = ThreadLocalRandom.current().nextInt(6, 11);
                String data = generateData(randLength);
                try {
                    String newStr = "Thread №" + id + " generated a message '" + data + "'";
                    cb.put(newStr);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread № " + id + " interrupted");
                }
            }
        }

        private String generateData(int length) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < length; i++) {
                int randIndex = ThreadLocalRandom.current().nextInt(0, CHARS.length());
                sb.append(CHARS.charAt(randIndex));
            }

            return sb.toString();
        }
    }

    private static class ThreadStringMover implements Runnable {
        private final int id;
        private final CircularBuffer<String> firstCb;
        private final CircularBuffer<String> secondCb;

        public ThreadStringMover(int id, CircularBuffer<String> firstCb, CircularBuffer<String> secondCb) {
            this.id = id;
            this.firstCb = firstCb;
            this.secondCb = secondCb;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String str = firstCb.take();
                    String strForMove = "Thread №" + id + " moved a message '" + str + "'";
                    secondCb.put(strForMove);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread № " + id + " interrupted");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CircularBuffer<String> firstCb = new CircularBuffer<>(10);
        CircularBuffer<String> secondCb = new CircularBuffer<>(10);

        ThreadFactory threadFactory = r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        };

        for (int i = 0; i < 5; i++) {
            threadFactory.newThread(new ThreadStringGenerator(i + 1, firstCb)).start();
        }
        for (int i = 0; i < 2; i++) {
            threadFactory.newThread(new ThreadStringMover(i + 1, firstCb, secondCb)).start();
        }

        for (int i = 0; i < 100; i++) {
            String data = secondCb.take();
            System.out.println((i + 1) + ": " + data);
        }
    }
}
