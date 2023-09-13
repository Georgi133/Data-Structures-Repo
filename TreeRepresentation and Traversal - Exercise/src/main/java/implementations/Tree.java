package implementations;

import interfaces.AbstractTree;

import java.util.*;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {

    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key) {
        this.key = key;
        this.children = new ArrayList<>();


//        for (int i = 0; i <children.length ; i++) {
//            children[i].setParent(this);
//        }

    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public String getAsString() {
        StringBuilder sb = new StringBuilder();
        dfs(0, this, sb);

        System.out.println(sb.toString());
        return sb.toString().trim();
    }

    private void dfs(int level, Tree<E> eTree, StringBuilder sb) {
        sb.append(spacesToAdd(level)).
                append(eTree.key)
                .append(System.lineSeparator());

        for (Tree<E> child : eTree.children) {
            dfs(level + 2, child, sb);
        }

    }

    private String spacesToAdd(int level) {

        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < level; i++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }

    @Override
    public List<E> getLeafKeys() {
        List<E> save = new ArrayList<>();
        dfsLeaf(this, save);


        return save;

    }

    private void dfsLeaf(Tree<E> eTree, List<E> save) {
        if (eTree.children.size() == 0) {
            save.add(eTree.key);
        }

        for (Tree<E> child : eTree.children) {
            dfsLeaf(child, save);
        }
    }

    @Override
    public List<E> getMiddleKeys() {
        List<E> save = new ArrayList<>();
        List<Tree<E>> all = new ArrayList<>();

        List<Tree<E>> trees = dfsKeys(this, save, all);


        return trees.stream().filter(e -> e.parent != null && e.children.size() != 0)
                .map(e -> e.getKey()).collect(Collectors.toList());
    }


    private List<Tree<E>> dfsKeys(Tree<E> eTree, List<E> save, List<Tree<E>> all) {

        all.add(eTree);

        for (Tree<E> child : eTree.children) {
            dfsKeys(child, save, all);
        }

        return all;
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        List<Tree<E>> saver = new ArrayList<>();
        List<Tree<E>> save = deepestNode(this, saver);

        int counter = 0;

        Tree<E> theTree = null;

        for (Tree<E> tree : save) {
            int current = getStepsFromLeafToRoout(tree);
            if (current > counter) {
                counter = current;
                theTree = tree;
            }
        }

        return theTree;
    }

    public List<Tree<E>> deepestNode(Tree<E> eTree, List<Tree<E>> save) {

        if (eTree.children.size() == 0) {
            save.add(eTree);
        }

        for (Tree<E> child : eTree.children) {
            deepestNode(child, save);
        }

        return save;

    }


    private int getStepsFromLeafToRoout(Tree<E> tree) {

        int counter = 0;
        Tree<E> current = tree;

        while (current.parent != null) {
            counter++;
            current = current.parent;
        }
        return counter;
    }

    private boolean isLeaf() {
        return this.parent != null && this.children.size() == 0;
    }

    @Override
    public List<E> getLongestPath() {
        List<Tree<E>> save = new ArrayList<>();

        List<Tree<E>> longestPath = longestNode(this, save);
        List<Tree<E>> list = new ArrayList<>();
        int max = 0;

        for (Tree<E> tree : longestPath) {
            int current = getStepsFromLeafToRoout(tree);
            if (current > max) {
                max = current;
                while (tree != null) {
                    list.add(0, tree);
                    tree = tree.parent;
                }
            }
        }


        return list.stream().map(e -> e.getKey()).collect(Collectors.toList());
    }

    public List<Tree<E>> longestNode(Tree<E> eTree, List<Tree<E>> save) {

        if (eTree.children.size() == 0) {
            save.add(eTree);
        }

        for (Tree<E> child : eTree.children) {
            deepestNode(child, save);
        }

        return save;

    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        List<List<E>> list = new ArrayList<>();
        int[] result = new int[1];
        List<Tree<E>> listToSaveEveryOne = new ArrayList<>();
        int[] counter = new int[1];
        dfsNew(sum, this, list, result, counter, listToSaveEveryOne);

        return list;
    }

    private void dfsNew(int sum, Tree<E> eTree, List<List<E>> list, int[] result, int[] counter, List<Tree<E>> listToSaveEveryOne) {

        result[0] += (int) eTree.key;
        listToSaveEveryOne.add(eTree);

        if (result[0] == sum && listToSaveEveryOne.size() > 0) {
            addList(list, listToSaveEveryOne, counter);
            counter[0]++;
            return;
        }

        for (Tree<E> child : eTree.children) {
            dfsNew(sum, child, list, result, counter, listToSaveEveryOne);
            result[0] -= (int) child.key;
            listToSaveEveryOne.remove(child);
        }

    }

    private void addList(List<List<E>> list, List<Tree<E>> listToSaveEveryOne, int[] counter) {
        list.add(new ArrayList<>());
        for (int i = 0; i < listToSaveEveryOne.size(); i++) {
            list.get(counter[0]).add(listToSaveEveryOne.get(i).key);
        }
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        int[] result = new int[1];
        List<Tree<E>> save = new ArrayList<>();
        List<Tree<E>> saveAll = new ArrayList<>();
        Tree<E> currentParent = null;

        List<Tree<E>> trees = dfsLast(sum, this, result, save, saveAll, currentParent);

        Tree<E> theOne = trees.get(0);

        List<Tree<E>> last = new ArrayList<>();
        last.add(theOne);
        return last;

    }

    private List<Tree<E>> dfsLast(int sum, Tree<E> eTree, int[] result, List<Tree<E>> save, List<Tree<E>> saveAll, Tree<E> currentParent) {

        if (eTree.parent != null) {
            if (result[0] == 0) {
                currentParent = eTree;
            }
            result[0] += (int) eTree.key;
            save.add(eTree);

        }

        if (result[0] == sum && save.size() > 0) {
            saveAll.addAll(save);
            return saveAll;
        }

        for (Tree<E> child : eTree.children) {
            dfsLast(sum, child, result, save, saveAll, currentParent);
        }
        if(eTree.parent != currentParent) {
            result[0] = 0;
            save.clear();
        }

        return saveAll;
    }

}



