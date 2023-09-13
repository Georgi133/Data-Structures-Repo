import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.function.Consumer;

import java.util.List;

public class BinarySearchTree<E extends Comparable<E>> {
    private Node<E> root;


    public BinarySearchTree() {
    }

    public BinarySearchTree(E element) {
        this.root = new Node<>(element);
    }

    public BinarySearchTree(Node<E> otherRoot) {
        this.root = new Node<>(otherRoot);
    }

    public static class Node<E> {

        private E value;
        private Node<E> leftChild;
        private Node<E> rightChild;
        private int count;

        public Node(Node<E> other) {
            this.value = other.value;
            this.count = other.count;
            if (other.getLeft() != null) {
                this.leftChild = new Node<>(other.getLeft());
            }
            if (other.getRight() != null) {
                this.rightChild = new Node<>(other.getRight());
            }

        }

        public Node(E value) {
            this.value = value;
            this.count = 1;
        }

        public Node<E> getLeft() {
            return this.leftChild;
        }

        public Node<E> getRight() {
            return this.rightChild;
        }

        public E getValue() {
            return this.value;
        }
    }

    public void eachInOrder(Consumer<E> consumer) {
        nodeInOrder(this.root, consumer);
    }

    private void nodeInOrder(Node<E> root, Consumer<E> consumer) {
        if (root == null) {
            return;
        }

        nodeInOrder(root.getLeft(), consumer);
        consumer.accept(root.getValue());
        nodeInOrder(root.getRight(), consumer);
    }

    public Node<E> getRoot() {
        return this.root;
    }

    public void insert(E element) {

        if (this.root == null) {
            this.root = new Node<>(element);
        } else {
            insertInto(this.root, element);
        }

    }

    private void insertInto(Node<E> root, E element) {

        if (isGreater(element, root)) {
            if (root.getRight() == null) {
                root.rightChild = new Node<>(element);
            } else {
                insertInto(root.rightChild, element);
            }
        } else if (isLess(element, root)) {
            if (root.getLeft() == null) {
                root.leftChild = new Node<>(element);
            } else {
                insertInto(root.leftChild, element);
            }
        }
        root.count++;
    }


    private boolean isLess(E element, Node<E> current) {
        return element.compareTo(current.value) < 0;
    }

    private boolean isGreater(E element, Node<E> current) {
        return element.compareTo(current.value) > 0;
    }

    private boolean isEqual(E element, Node<E> current) {
        return element.compareTo(current.value) == 0;
    }

    public boolean contains(E element) {
        boolean isTrueOrFalse = false;
        return checkIfThereIsSuchElementRecursively(this.root, element, isTrueOrFalse);
    }

    private boolean checkIfThereIsSuchElementRecursively(Node<E> root, E element, boolean isTrueOrFalse) {

        if (root == null) {
            return false;
        }

        if (isLess(element, root)) {
            isTrueOrFalse |= checkIfThereIsSuchElementRecursively(root.leftChild, element, isTrueOrFalse);
        } else if (isGreater(element, root)) {
            isTrueOrFalse |= checkIfThereIsSuchElementRecursively(root.rightChild, element, isTrueOrFalse);
        } else if (isEqual(element, root)) {
            isTrueOrFalse = true;
            return isTrueOrFalse;
        }

        return isTrueOrFalse;
    }

    public BinarySearchTree<E> search(E element) {

        Node<E> theNode = checkIfThereIsSuchElement(this.root, element);

        BinarySearchTree<E> tree = null;
        if (theNode != null) {
            tree = new BinarySearchTree<E>(theNode);
        }

        return theNode == null ? null : tree;

    }

    private Node<E> checkIfThereIsSuchElement(Node<E> root, E element) {
        while (root != null) {
            if (isLess(element, root)) {
                root = root.leftChild;
            } else if (isGreater(element, root)) {
                root = root.rightChild;
            } else if (isEqual(element, root)) {
                return root;
            }
        }
        return null;
    }

    public List<E> range(E lower, E upper) {
        List<E> result = new ArrayList<>();
        if (this.root == null) {
            return result;
        }

        Deque<Node<E>> queue = new ArrayDeque<>();

        queue.offer(this.root);

        while (!queue.isEmpty()) {

            Node<E> current = queue.poll();

            if (current.getLeft() != null) {
                queue.offer(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.offer(current.getRight());
            }

            if (isLess(lower, current) && isGreater(upper, current)) {
                result.add(current.value);
            } else if (isEqual(lower, current) || isEqual(upper, current)) {
                result.add(current.getValue());
            }
        }

        return result;
    }

    public void deleteMin() {
        if (this.root == null) {
            throw new IllegalStateException("No elements");
        }
        Node<E> current = this.root;

        if (this.root.getLeft() == null) {
            this.root = this.root.getRight();
            return;
        }

        while (current.leftChild.leftChild != null) {
            current.count--;
            current = current.leftChild;
        }

        current.count--;
        current.leftChild = current.getLeft().getRight();
    }

    public void deleteMax() {
        if (this.root == null) {
            throw new IllegalStateException("No elements");
        }
        Node<E> current = this.root;

        if (this.root.getRight() == null) {
            this.root = this.root.getLeft();
            return;
        }

        while (current.rightChild.getRight() != null) {
            current.count--;
            current = current.rightChild;
        }

        current.count--;
        current.rightChild = current.getRight().getLeft();
    }

    public int count() {

        return this.root.count;
    }


    public int rank(E element) {

        return recursion(this.root, element);
    }

    private int recursion(Node<E> root, E element) {
        if (root == null) {
            return 0;
        }
        if (isLess(element, root)) {
            return recursion(root.getLeft(), element);
        } else if (isEqual(element, root)) {
            return getNodeCount(root.getLeft()) + 1;
        }

        return getNodeCount(root.getLeft()) + 1 + recursion(root.getRight(), element);


    }

    private int getNodeCount(Node<E> root) {
        return root.getLeft() == null ? 0 : root.count;
    }

    public E ceil(E element) {
        if(this.root == null) {
            return null;
        }
        Node<E> current = this.root;
        Node<E> nearestBigger = null;

        while (current != null) {
            if(isLess(element,current)){
                nearestBigger = current;
                current = current.getLeft();
            } else if (isGreater(element,current)) {
                current = current.getRight();
            } else {
                Node <E> right = current.getRight();
                if(right != null && nearestBigger != null) {
                    nearestBigger = isLess(right.getValue(), nearestBigger) ? right : nearestBigger;
                } else if (nearestBigger == null) {
                    nearestBigger = right;
                }
                break;
            }


        }

        return nearestBigger == null ? null : nearestBigger.getValue();
    }

    public E floor(E element) {
        if (this.root == null) {
            return null;
        }
        Node<E> current = this.root;

        Node<E> nearestSmaller = null;

        while (current != null) {
            if (isGreater(element, current)) {
                nearestSmaller = current;
                current = current.getRight();
            } else if (isLess(element, current)) {
                current = current.getLeft();
            }else {
                Node <E> left = current.getLeft();
                if(left != null && nearestSmaller != null) {
                    nearestSmaller = isGreater(left.getValue(),nearestSmaller) ? left : nearestSmaller;
                } else if(nearestSmaller == null) {
                    nearestSmaller = left;
                }
                break;
            }

        }

        return nearestSmaller == null ? null : nearestSmaller.getValue();
    }
}
