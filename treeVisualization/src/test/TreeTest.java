package test;

import tree.BalancedBinaryTree;
import tree.BalancedTree;
import tree.Node;

public class TestTree {

    public static void main(String[] args) {
        // Test for BalancedTree
        System.out.println("Testing BalancedTree:");
        BalancedTree balancedTree = new BalancedTree(1);
        balancedTree.createTree(10); // Create root node with value 10

        Node root = balancedTree.getRoot();
        balancedTree.insertNode(root, 20); // Insert child node 20
        balancedTree.insertNode(root, 30); // Insert child node 30

        Node child = root.getChildren().get(0); // Get the first child (value 20)
        balancedTree.insertNode(child, 40); // Insert a child to node 20

        System.out.println("DFS Traversal of BalancedTree:");
        balancedTree.traverse("DFS").forEach(node -> System.out.print(node.getValue() + " "));
        System.out.println("\n");

        // Test for BalancedBinaryTree
        System.out.println("Testing BalancedBinaryTree:");
        BalancedBinaryTree balancedBinaryTree = new BalancedBinaryTree(1);
        balancedBinaryTree.createTree(50); // Create root node with value 50

        root = balancedBinaryTree.getRoot();
        balancedBinaryTree.insertNode(root, 60); // Insert left child 60
        balancedBinaryTree.insertNode(root, 70); // Insert right child 70

        child = root.getChildren().get(0); // Get the left child (value 60)
        balancedBinaryTree.insertNode(child, 80); // Insert a child to node 60

        System.out.println("DFS Traversal of BalancedBinaryTree:");
        balancedBinaryTree.traverse("DFS").forEach(node -> System.out.print(node.getValue() + " "));
        System.out.println("\n");

        // Test for unbalanced insertion
        System.out.println("Attempting to insert unbalanced node:");
        balancedBinaryTree.insertNode(root, 90); // Should fail as it would unbalance the tree
    }
}

