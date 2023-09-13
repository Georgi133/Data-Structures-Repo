package implementations;

import interfaces.AbstractTree;

import java.util.*;

public class Tree<E> implements AbstractTree<E> {

    private E value;
    private Tree<E> root;
    private List<Tree<E>> children;

    public Tree(E value, Tree<E>... subtrees) {

        this.value = value;
        root = null;
        children = new ArrayList<>();

        for (Tree<E> subtree : subtrees) {
            children.add(subtree);
            subtree.root = this;
        }
    }

    @Override
    public List<E> orderBfs() {
        List<E> result = new ArrayList<>();

        if(this.value == null){
            return result;
        }

        ArrayDeque<Tree> childrenQueue = new ArrayDeque<>();

        childrenQueue.offer(this);

        while (!childrenQueue.isEmpty()) {
            Tree<E> current = childrenQueue.poll();

            result.add(current.value);

            for (Tree<E> child : current.children) {
                childrenQueue.offer(child);
            }


        }
        return result;
    }

    @Override
    public List<E> orderDfs() {
        List<E> result = new ArrayList<>();

        //doDfs(this,result);

        Deque<Tree<E>> toTraverse = new ArrayDeque<>();
        toTraverse.push(this);

        while (!toTraverse.isEmpty()) {

            Tree<E> current = toTraverse.pop();

            for (Tree<E> node : toTraverse) {
                toTraverse.push(node);
            }
        }

        return result;
    }

    private void doDfs(Tree<E> eTree, List<E> result) {


        for (Tree<E> children : eTree.children) {
            doDfs(children, result);
        }
        result.add(eTree.value);


    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {

        Tree<E> search = find(parentKey, this);

        if (search == null) {
            throw new IllegalArgumentException();
        }

        search.children.add(child);
        child.root = search;

    }

    private Tree<E> find(E parentKey, Tree<E> eTree) {

        if (eTree.value.equals(parentKey)) {
            return eTree;
        }

        for (Tree<E> children : eTree.children) {
            Tree<E> found = find(parentKey, children);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    @Override
    public void removeNode(E nodeKey) {

        Tree<E> search = remove(nodeKey, this);

        if (search == null) {
            throw new IllegalArgumentException();
        }

        for (Tree<E> eTree : search.children) {
            eTree.root = null;
        }

        search.children.clear();
        Tree<E> parent = search.root;

        if (parent != null) {
            parent.children.remove(search);
        }

        search.value = null;

    }

    private Tree<E> remove(E nodeKey, Tree<E> eTree) {

        if (eTree.value.equals(nodeKey)) {
            return eTree;
        }

        for (Tree<E> children : eTree.children) {
            Tree<E> found = remove(nodeKey, children);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> first = findThem(firstKey,this);
        Tree<E> second = findThem(secondKey,this);

        if(first == null || second == null) {
            throw new IllegalArgumentException();
        }


        Tree<E> firstParent = first.root;
        Tree<E> secondParent = second.root;

        if(firstParent == null){
            swapRoot(second);
            return;
        } else if (secondParent == null) {
            swapRoot(first);
            return;
        }


        first.root = secondParent;
        second.root = firstParent;


        int firstIndex = firstParent.children.indexOf(first);
        int secondIndex = secondParent.children.indexOf(second);

        firstParent.children.set(firstIndex,second);
        secondParent.children.set(secondIndex,first);

    }

    private void swapRoot(Tree<E> node) {
        this.value = node.value;
        this.root = null;
        this.children = node.children;
        node.root = null;
    }

    private Tree<E> findThem(E nodeKey, Tree<E> eTree) {

        if (eTree.value.equals(nodeKey)) {
            return eTree;
        }

        for (Tree<E> children : eTree.children) {
            Tree<E> found = findThem(nodeKey, children);
            if (found != null) {
                return found;
            }
        }

        return null;
    }


}



