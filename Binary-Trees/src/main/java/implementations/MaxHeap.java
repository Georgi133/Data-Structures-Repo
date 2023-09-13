package implementations;

import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxHeap<E extends Comparable<E>> implements Heap<E> {

    private List<E> elements;

    public MaxHeap() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(E element) {
        elements.add(element);
        hepifyUp(this.size() - 1);
    }

    private void hepifyUp(int index) {
        while(index > 0 && isLess(index,getParent(index))){
            Collections.swap(elements,index,getParent(index));
            index = getParent(index);
        }
    }

    private static int getParent(int index) {
        int indexParent = (index - 1) / 2;
        return indexParent;
    }

    private boolean isLess(int index,int indexParent) {
        return getElement(index).compareTo(getElement(indexParent)) > 0;
    }

    private E getElement(int index) {
      return  elements.get(index);
    }

    @Override
    public E peek() {
        if(this.size() == 0) {
            throw new IllegalStateException("Heap is empty upon peek attempt");
        }

        return getElement(0);
    }
}
