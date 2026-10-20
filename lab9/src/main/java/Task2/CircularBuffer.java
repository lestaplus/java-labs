package Task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CircularBuffer<T> {

    private static class Node<T> {
        private T data;
        private Node<T> next;
    }

    private Node<T> head;
    private Node<T> tail;

    private int size = 0;
    private final int capacity;

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public CircularBuffer(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException();

        this.capacity = capacity;

        Node<T> current = new Node<>();
        head = current;
        tail = current;

        for (int i = 1; i < capacity; i++) {
            Node<T> nextNode = new Node<>();
            current.next = nextNode;
            current = nextNode;
        }
        current.next = head;
    }

    public void put(T data) throws InterruptedException {
        if (data == null) return;

        lock.lock();
        try {
            while (size == capacity) {
                notFull.await();
            }

            tail.data = data;
            tail = tail.next;
            size++;

            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (size == 0) {
                notEmpty.await();
            }

            T data = head.data;
            head.data = null;
            head = head.next;
            size--;

            notFull.signalAll();
            return data;
        } finally {
            lock.unlock();
        }
    }
}
