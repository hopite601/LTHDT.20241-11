public class TreeTest {
    public static void main(String[] args) {
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        binaryTree.createTree();
        binaryTree.insertNode(null, 10); // Root node
        binaryTree.insertNode(10, 20);
        binaryTree.insertNode(10, 30);

        BalancedTree<Integer> balancedTree = new BalancedTree<>(2);
        balancedTree.createTree();
        balancedTree.insertNode(null, 50);
        balancedTree.insertNode(50, 60);
        balancedTree.insertNode(50, 70);

        BalancedBinaryTree<Integer> balancedBinaryTree = new BalancedBinaryTree<>(2);
        balancedBinaryTree.createTree();
        balancedBinaryTree.insertNode(null, 100);
        balancedBinaryTree.insertNode(100, 110);
        balancedBinaryTree.insertNode(100, 120);
    }
}