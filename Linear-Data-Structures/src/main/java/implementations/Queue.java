package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {
    private Node<E> head;
    private int size;

    private Node<E> tail;

    private static class Node<E> {
        private E element;
        private Node<E> next;

        private Node(E element) {
            this.element = element;
        }
    }

    public Queue() {
        this.size = 0;
    }

    @Override
    public void offer(E element) {
        Node <E> toInsert = new Node<>(element);

        if(isEmpty()){
            tail = toInsert;
            head = tail;
            size++;
            return;
        }
        tail.next = toInsert;
        tail = toInsert;

        size++;
    }

    @Override
    public E poll() {
        ensureNonEmpty();

        E element = this.head.element;

        if (this.size == 1) {
            this.head = null;
            tail = head;
        } else {
            Node<E> next = this.head.next;
            this.head = next;
        }
        this.size--;
        return element;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.head.element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public E next() {
                E element = this.current.element;
                this.current = this.current.next;
                return element;
            }
        };
    }

    private void ensureNonEmpty() {
        if (this.size == 0) {
            throw new IllegalStateException("Illegal operation on empty stack");
        }
    }
}
