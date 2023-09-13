package solutions;

import interfaces.Decrease;
import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MinHeap<E extends Comparable<E> & Decrease<E>> implements Heap<E> {

    private List<E> data;

    public MinHeap() {
        this.data = new ArrayList<>();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public void add(E element) {
        this.data.add(element);
        this.heapifyUp(data.size() - 1);
    }

    private void heapifyUp(int index) {
        int parentIndex = this.getParentIndexFor(index);

        while (index > 0 && isLess(index,parentIndex)){
            Collections.swap(this.data,index , parentIndex);
            index = parentIndex;
            parentIndex = this.getParentIndexFor(index);
        }


    }


    @Override
    public E peek() {
        ensureNonEmpty();
        return this.data.get(0);
    }

    @Override
    public E poll() {
        ensureNonEmpty();

        Collections.swap(data,0,data.size() - 1);
        E element = data.get(data.size() - 1);
        data.remove(data.size() - 1);

        heapifyDown(0);

        return element;
    }

    private void heapifyDown(int index) {
        int leftChildIndex = leftChild(index);

        while (leftChildIndex < this.data.size()){
            int swapIndex = leftChildIndex;

            int rightChildIndex = rightChild(index);

            if(rightChildIndex < this.data.size() && isLess(rightChildIndex,leftChildIndex)){
                swapIndex = rightChildIndex;
            }

            if(!isBigger(index,swapIndex)) {
                return;
            }

            Collections.swap(data, index, swapIndex);
            index = swapIndex;
            leftChildIndex = leftChild(index);

        }

    }

    @Override
    public void decrease(E element) {
        int elementIndex = data.indexOf(element);

        E heapElement = data.get(elementIndex);

        heapElement.decrease();

        heapifyUp(elementIndex);

    }

    private boolean isBigger(int firstIndex, int secondIndex) {
        return this.data.get(firstIndex).compareTo(this.data.get(secondIndex)) > 0;
    }
    private int rightChild(int index) {
        return index * 2 + 2;
    }

    private int leftChild(int index) {
        return index * 2 + 1;
    }

    private void ensureNonEmpty() {
        if(this.size() == 0){
            throw new IllegalStateException();
        }
    }

    private boolean isLess(int firstIndex, int secondIndex) {
        return this.data.get(firstIndex).compareTo(this.data.get(secondIndex)) < 0;
    }

    private int getParentIndexFor(int index) {
        return (index - 1) / 2;
    }
}
