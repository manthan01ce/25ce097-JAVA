/**
 * Practical 10 — Part A, Program 2
 * Producer-consumer coordinated with wait() / notify().
 * One thread adds numbers to a small shared buffer;
 * another removes them. A fixed count of items is produced/consumed in order with none lost.
 */
public class ProducerConsumer {

    static final int   CAPACITY = 5;
    static final int   ITEMS    = 20;
    static final int[] buffer   = new int[CAPACITY];
    static int         count    = 0;   // items currently in buffer
    static int         head     = 0;   // next slot to read
    static int         tail     = 0;   // next slot to write

    static final Object monitor = new Object();

    static class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= ITEMS; i++) {
                synchronized (monitor) {
                    while (count == CAPACITY) {
                        try { monitor.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    }
                    buffer[tail] = i;
                    tail = (tail + 1) % CAPACITY;
                    count++;
                    System.out.println("Produced: " + i + "  buffer=" + count);
                    monitor.notifyAll();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < ITEMS; i++) {
                synchronized (monitor) {
                    while (count == 0) {
                        try { monitor.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    }
                    int val = buffer[head];
                    head = (head + 1) % CAPACITY;
                    count--;
                    System.out.println("Consumed: " + val + "  buffer=" + count);
                    monitor.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Thread(new Producer(), "Producer");
        Thread consumer = new Thread(new Consumer(), "Consumer");
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("Producer-consumer complete. All " + ITEMS + " items transferred.");
    }
}
