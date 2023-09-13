package implementations;

import java.util.Iterator;

public class ReversedList<E> implements Iterable<E>{

    private final int INITIAL_CAPACITY = 7;

    private Object[] array;
    private int size;

    private int head;

    private int tail;


    public ReversedList() {
        this.array = new Object[INITIAL_CAPACITY];
        this.size = 0;
        tail = head = array.length / 2;
    }

    public void add(E element) {
        if (tail == array.length - 1) {
            grow();
            array[tail++] = element;
            size++;
        } else {
            array[tail++] = element;
            size++;
        }
    }

    public int size(){
        return size;
    }

    public int capacity (){
        return array.length;
    }

    public E get(int index){
        int realIndex = tail - index;
        E element = (E) array[realIndex];

        return element;
    }
    public void removeAt(int index){
        int begin = tail - (index + 1);
        for (int i = begin; i <= tail - 1 ; i++) {
            array[i] = array[i + 1];
        }
        tail--;

    }


    public Iterator<E> iterator() {
        return new Iterator<E>() {

            int begin = tail - 1;
            @Override
            public boolean hasNext() {
                return begin >= head;
            }

            @Override
            public E next() {
                E element = (E)array[begin--];
                return element;
            }
        };
    }

    private void grow() {
        int newSize = array.length * 2;
        Object[] newArray = new Object[newSize];

        int middle = newSize / 2;
        int begin = middle - (size / 2);

        int index = head;

        for (int i = begin; index <= tail ; i++) {
            newArray[i] = array[index++];
        }

        head = begin;
        tail = head + size;

        array = newArray;
    }



}
