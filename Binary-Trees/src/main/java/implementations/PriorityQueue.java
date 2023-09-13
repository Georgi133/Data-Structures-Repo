package implementations;

import interfaces.AbstractQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {

    private List<E> elements;

    public PriorityQueue() {
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

        while(index > 0 && isLess(getParent(index),index)){
            Collections.swap(elements,index,getParent(index));
            index = getParent(index);
        }
    }

    private static int getParent(int index) {
        int indexParent = (index - 1) / 2;
        return indexParent;
    }

    private boolean isLess(int first,int second) {
        return getElement(first).compareTo(getElement(second)) < 0;
    }

    private E getElement(int index) {
        return  elements.get(index);
    }

    @Override
    public E peek() {
        ensureNotEmpty();

        return getElement(0);
    }

    private void ensureNotEmpty() {
        if(this.size() == 0) {
            throw new IllegalStateException("Heap is empty upon peek attempt");
        }
    }

    @Override
    public E poll() {
        ensureNotEmpty();
        E element = getElement(0);
        Collections.swap(elements,0 , elements.size() - 1);
        elements.remove(elements.size() - 1);
        heapifyDown(0);
        return element;
    }

    private void heapifyDown(int index) {

        while (getLeftChildIndex(index) < elements.size() && isLess(index,getLeftChildIndex(index))) {

            int current = getLeftChildIndex(index);
            int rightChildIndex = getRightChildIndex(index);

            if(rightChildIndex < this.size() && isLess(current,rightChildIndex)) {
               current = rightChildIndex;
            }

            Collections.swap(elements,current,index);
            index = current;

        }
    }

   private E getLeftChild (int index) {
        return this.elements.get(this.getLeftChildIndex(index));
   }
    private E getRightChild (int index) {
        return this.elements.get(this.getRightChildIndex(index));
    }
   private int getLeftChildIndex(int index) {
        return index * 2 + 1;
   }

   private int getRightChildIndex (int index) {
       return index * 2 + 2;
   }

}
