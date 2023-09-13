package implementations;

import interfaces.AbstractBinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BinaryTree<E> implements AbstractBinaryTree<E> {

    private E key;
    private BinaryTree<E> left;
    private BinaryTree<E> right;


    public BinaryTree(E key, BinaryTree<E> left, BinaryTree<E> right) {
        this.setKey(key);
        this.left = left;
        this.right = right;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public AbstractBinaryTree<E> getLeft() {
        return this.left;
    }

    @Override
    public AbstractBinaryTree<E> getRight() {
        return this.right;
    }

    @Override
    public void setKey(E key) {
        this.key = key;
    }

    
    @Override
    public String asIndentedPreOrder(int indent) {
        StringBuilder sb = new StringBuilder();
        String padding = createPadding(indent) + this.getKey();
        sb.append(padding);


        if(this.getLeft() != null) {
            String preOrder = this.getLeft().asIndentedPreOrder(indent + 2);
            sb.append(System.lineSeparator()).append(preOrder);
        }

        if(this.getRight() != null) {
            String preOrder = this.getRight().asIndentedPreOrder(indent + 2);
            sb.append(System.lineSeparator()).append(preOrder);
        }

        return sb.toString();
    }

    private String createPadding(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <indent ; i++) {
            sb.append(" ");
        }

       return sb.toString();
    }


    @Override
    public List<AbstractBinaryTree<E>> preOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();

        result = preOrderThis(this,result);


        return result;
    }

    @Override
    public List<AbstractBinaryTree<E>> inOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();

        result = inOrderThis(this,result);


        return result;
    }

    @Override
    public List<AbstractBinaryTree<E>> postOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();

        result = postOrderThis(this,result);


        return result;
    }

    @Override
    public void forEachInOrder(Consumer<E> consumer) {
        if(this.getLeft() != null){
            this.getLeft().forEachInOrder(consumer);
        }
        consumer.accept(this.getKey());

        if(this.getRight() != null){
            this.getRight().forEachInOrder(consumer);
        }

    }

    private List<AbstractBinaryTree<E>> preOrderThis(BinaryTree<E> tree, List<AbstractBinaryTree<E>> result) {

        if(tree != null){
            result.add(tree);
            preOrderThis(tree.left, result);
            preOrderThis(tree.right, result);
        }

        return result;
    }
    private List<AbstractBinaryTree<E>> inOrderThis(BinaryTree<E> tree, List<AbstractBinaryTree<E>> result) {

        if(tree != null){
            inOrderThis(tree.left, result);
            result.add(tree);
            inOrderThis(tree.right, result);
        }

        return result;
    }
    private List<AbstractBinaryTree<E>> postOrderThis(BinaryTree<E> tree, List<AbstractBinaryTree<E>> result) {

        if(tree != null){
            postOrderThis(tree.left, result);
            postOrderThis(tree.right, result);
            result.add(tree);
        }

        return result;
    }
}
