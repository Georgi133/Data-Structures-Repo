import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinarySearchTreeTest {


    private BinarySearchTree <Integer> bst;
    private BinarySearchTree <Integer> bstLeft;
    private BinarySearchTree <Integer> bstMoreLeft;
    private BinarySearchTree <Integer> bstRight;

    @Before
    public void setUp() {
        bst = new BinarySearchTree<>(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(6);
        bst.insert(1);
        bst.insert(17);
    }

    @Test
    public void testLeftSideBST() {

        BinarySearchTree.Node<Integer> root = bst.getRoot();

        assertEquals(Integer.valueOf(12), root.getValue());

        BinarySearchTree.Node<Integer> left = root.getLeft();

        assertEquals(Integer.valueOf(8), left.getValue());

        BinarySearchTree.Node<Integer> left_left = left.getLeft();
        BinarySearchTree.Node<Integer> left_right = left.getRight();

        assertEquals(Integer.valueOf(1), left_left.getValue());
        assertEquals(Integer.valueOf(9), left_right.getValue());
    }

    @Test
    public void testEachInOrder() {
        List<Integer> elements = new ArrayList<>();

        bst.eachInOrder(e -> elements.add(e));

        List<Integer> expected = new ArrayList<>(
                Arrays.asList(1, 8, 9, 12)
        );

        assertEquals(expected.size(),elements.size());

        for (int i = 0; i <expected.size() ; i++) {
            assertEquals(expected.get(i),elements.get(i));
        }
    }

    @Test
    public void testContains() {
        assertTrue(bst.contains(8));
        assertTrue(bst.contains(1));
        assertTrue(bst.contains(12));

    }
    @Test
    public void search() {
        assertEquals(bst.search(8).getRoot().getValue(),Integer.valueOf(8));
        assertEquals(bst.search(8).getRoot().getLeft().getValue(),Integer.valueOf(1));
        assertEquals(bst.search(8).getRoot().getRight().getValue(),Integer.valueOf(9));
        assertNull(bst.search(33));

    }

    @Test
    public void testRange() {
        List<Integer> range = bst.range(8, 12);
        List<Integer> expected = Arrays.asList(8, 9, 12);

        assertEquals(3,range.size());
        for (int i = 0; i < range.size(); i++) {
            assertTrue(range.contains(expected.get(i)));
        }

    }

    @Test
    public void testDeleteMin() {

        bst.deleteMin();

        assertNull(bst.getRoot().getLeft().getLeft());

    }
    @Test
    public void testDeleteMax() {
        assertEquals(Integer.valueOf(14),bst.getRoot().getRight().getValue());

        bst.deleteMax();

        assertEquals(Integer.valueOf(13),bst.getRoot().getRight().getValue());

    }

    @Test
    public void testCount () {
        assertEquals(6,bst.count());
        bst.deleteMin();
        assertEquals(5,bst.count());
    }

    @Test
    public void testRank () {
        assertEquals(4,bst.rank(7));
    }

    @Test
    public void testFloor () {
        assertEquals(Integer.valueOf(6),bst.floor(7));
    }

    @Test
    public void testEmptyFloor () {
        assertNull(bst.floor(-1));
    }

    @Test
    public void testCeil () {
        assertEquals(Integer.valueOf(7),bst.ceil(6));
    }







}
