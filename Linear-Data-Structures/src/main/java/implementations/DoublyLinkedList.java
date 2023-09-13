package implementations;

import interfaces.LinkedList;

import java.util.Iterator;

public class DoublyLinkedList<E> implements LinkedList<E> {
    private Node<E> tail;
    private Node<E> head;
    private int size;

    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> prev;
    }

    public DoublyLinkedList() {
        this.size = 0;
    }

    @Override
    public void addFirst(E element) {
        Node<E> toInsert = new Node<>();
        toInsert.element = element;

        if (size == 0) {
            tail = head = toInsert;
            size++;
            return;
        }

        toInsert.next = head;
        head.prev = toInsert;
        head = toInsert;
        size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> toInsert = new Node<>();
        toInsert.element = element;

        if (isEmpty()) {
            addFirst(element);
            return;
        }
        toInsert.prev = tail;
        tail.next = toInsert;
        tail = toInsert;

        size++;

    }

    @Override
    public E removeFirst() {
        if (size == 0) {
            throw new IllegalStateException("No elements");
        }

        Node<E> node = head;
        E currentElement = node.element;

        if(size == 1){
            head = tail = null;
        }else {
            head.next.prev = null;
            head = node.next;
        }

        size--;
        return currentElement;
    }

    @Override
    public E removeLast() {
        if (size == 0) {
            throw new IllegalStateException("No elements");
        }

        if (size == 1) {
            E element = head.element;
            head = tail = null;
            size--;
            return element;
        }

        E element = tail.element;

        tail.prev.next = null;
        this.tail = tail.prev;


        size--;

        return element;
    }

    @Override
    public E getFirst() {
        if (size == 0) {
            throw new IllegalStateException("No elements");
        }
        return head.element;
    }

    @Override
    public E getLast() {
        if (isEmpty()) {
            throw new IllegalStateException("No elements");
        }

        return tail.element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> node = head;


            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public E next() {
                E element = node.element;
                node = node.next;
                return element;
            }
        };
    }
}
