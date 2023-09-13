package implementations;

import interfaces.Deque;

import java.util.Iterator;

public class ArrayDeque<E> implements Deque<E> {
    private final int INITIAL_CAPACITY = 7;

    private Object[] array;
    private int size;
    private int head;
    private int tail;

    public ArrayDeque() {
        this.array = new Object[INITIAL_CAPACITY];
        this.size = 0;
        int middle = INITIAL_CAPACITY / 2;
        head = tail = middle;
    }

    @Override
    public void add(E element) {
        addLast(element);
    }

    private Object[] grow() {
        int newSize = array.length * 2 + 1;

        Object[] newElements = new Object[newSize];
        int middle = newSize / 2;
        int begin = middle - size / 2;

        int index = head;

        for (int i = begin; tail >= index; i++) {
            newElements[i] = array[index++];
        }

        this.head = begin;
        tail = head + size - 1;

        return newElements;

    }

    @Override
    public void offer(E element) {
        addLast(element);
    }

    @Override
    public void addFirst(E element) {
        if (size == 0) {
            this.addLast(element);
        } else {
            if (head == 0) {
                this.array = grow();
            }
            this.array[--head] = element;
            size++;
        }
    }

    @Override
    public void addLast(E element) {
        if (size() == 0) {
            array[tail] = element;
        } else {
            if (tail == array.length - 1) {
                array = grow();
            }
            array[++tail] = element;
        }

        size++;
    }

    @Override
    public void push(E element) {
        addFirst(element);
    }

    @Override
    public void insert(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Out of bounds");
        }
        int realIndex = head + index;

        if (realIndex - head < tail - realIndex) {
            InsertAndShiftLeft(element, realIndex - 1);
        } else {
            InsertAndShiftRight(element, realIndex);
        }

    }

    private void InsertAndShiftRight(E element, int index) {
        E lastElement = (E) array[tail];
        for (int i = tail; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
        addLast(lastElement);
    }

    private void InsertAndShiftLeft(E element, int index) {
        E firstElement = (E) array[head];
        for (int i = head; i <= index; i++) {
            array[i] = array[i + 1];
        }
        array[index] = element;
        addFirst(firstElement);
    }

    @Override
    public void set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Out of bounds");
        }

        int realIndex = head + index;
        array[realIndex] = element;

    }

    @Override
    @SuppressWarnings("unchecked")
    public E peek() {
        if (size != 0) {
            return (E) array[head];
        }
        return null;
    }

    @Override
    public E poll() {
        return removeFirst();
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Out of bounds");
        }
        int realIndex = head + index;
        E element = (E) array[realIndex];
        return element;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(Object object) {
        for (int i = head; i <= tail; i++) {
            if (array[i].equals(object)) {
                return (E) array[i];
            }
        }

        return null;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Out of bounds");
        }
        int realIndex = head + index;
        E element = (E) array[realIndex];
        array[realIndex] = null;
        size--;
        head++;
        return element;
    }

    @Override
    public E remove(Object object) {
        if (isEmpty()) {
            return null;
        }

        for (int i = head; i <= tail; i++) {
            if (array[i].equals(object)) {
                E element = (E) array[i];
                array[i] = null;

                for (int j = i; j < tail; j++) {
                    array[j] = array[j + 1];
                }
                this.removeLast();
                return element;
            }
        }


        return null;
    }

    @Override
    public E removeFirst() {
        if (!isEmpty()) {
            E element = (E) array[head];
            array[head] = null;
            head++;
            size--;
            return element;
        }

        return null;
    }

    @Override
    public E removeLast() {
        if (!isEmpty()) {
            E element = (E) array[tail];
            array[tail] = null;
            tail--;
            size--;
            return element;
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return array.length;
    }

    @Override
    public void trimToSize() {
        Object[] newElements = new Object[size];

        int index = 0;
        for (int i = head; i <= tail; i++) {
            newElements[index++] = array[i];
        }
        array = newElements;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = head;

            @Override
            public boolean hasNext() {
                return index <= tail;
            }

            @Override
            public E next() {
                return (E) array[index++];
            }
        };
    }

}
