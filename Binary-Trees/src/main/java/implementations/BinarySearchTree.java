package implementations;

import interfaces.AbstractBinarySearchTree;

public class BinarySearchTree<E extends Comparable<E>> implements AbstractBinarySearchTree<E> {

    private Node<E> root;
    private Node<E> leftChild;
    private Node<E> rightChild;

    public BinarySearchTree (){

    }
    public BinarySearchTree (Node<E> other){
           copy(other);
    }

    private void copy(Node<E> node) {
        if(node != null) {
            this.insert(node.value);
            this.copy(node.leftChild);
            this.copy(node.rightChild);
        }
    }

    @Override
    public void insert(E element) {
        Node<E> newNode = new Node<>(element);

        if (this.getRoot() == null) {
            this.root = newNode;
        } else {
            Node<E> current = this.root;
            Node<E> prev = current;

            while (current != null) {
                prev = current;
                if (isElementSmaller(element, current)) {
                    current = current.leftChild;
                } else if (isGreater(element, current)) {
                    current = current.rightChild;
                } else if (areEqual(element, current)) {
                    return;
                }
            }


            if (isElementSmaller(element, prev)) {
                prev.leftChild = newNode;
            } else if (isGreater(element, prev)) {
                prev.rightChild = newNode;
            }

        }
    }

    private boolean isElementSmaller(E element, Node<E> current) {
        return element.compareTo(current.value) < 0;
    }

    private boolean isGreater(E element, Node<E> current) {
        return element.compareTo(current.value) > 0;
    }

    private boolean areEqual(E element, Node<E> current) {
        return element.compareTo(current.value) == 0;
    }

    @Override
    public boolean contains(E element) {
        Node<E> current = this.root;


        while (current != null) {
            if (isElementSmaller(element, current)) {
                current = current.leftChild;
            } else if (isGreater(element, current)) {
                current = current.rightChild;
            } else if (areEqual(element, current)) {
                return true;
            }
        }

         return false;

    }

    @Override
    public AbstractBinarySearchTree<E> search(E element) {
        AbstractBinarySearchTree<E> result = new BinarySearchTree<>();

        Node<E> current = this.root;


        while (current != null) {
            if (isElementSmaller(element, current)) {
                current = current.leftChild;
            } else if (isGreater(element, current)) {
                current = current.rightChild;
            } else if (areEqual(element, current)) {
                return new BinarySearchTree<>(current);
            }
        }

        return result;
    }

    @Override
    public Node<E> getRoot() {
        return this.root;
    }

    @Override
    public Node<E> getLeft() {
        return this.leftChild;
    }

    @Override
    public Node<E> getRight() {
        return this.rightChild;
    }

    @Override
    public E getValue() {
        return this.root.value;
    }
}
