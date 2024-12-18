package testTree;

import tree.BinaryTree;
import tree.Node;

public class BinaryTreeTest {
	public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.createTree(1);  // Tạo cây với root có giá trị 1

        Node root = tree.getRoot();
        tree.insertNode(root, 2);  // Thêm node 2 vào root
        tree.insertNode(root, 3);  // Thêm node 3 vào root
        tree.insertNode(root, 11);  // Thêm node 3 vào root
        
        Node node2 = tree.search(2);;
        tree.insertNode(node2, 4);  // Thêm node 4 vào node 2

        // Duyệt cây theo DFS và BFS
        System.out.println("DFS Traversal:");
        for (Node node : tree.traverse("DFS")) {
            System.out.print(node.getValue() + " ");
        }
        System.out.println("\nBFS Traversal:");
        for (Node node : tree.traverse("BFS")) {
            System.out.print(node.getValue() + " ");
        }

        // Cập nhật node
        tree.updateNode(2, 5);
        System.out.println("\nAfter updating node 2 to 5:");
        for (Node node : tree.traverse("BFS")) {
            System.out.print(node.getValue() + " ");
        }

        // Xóa node
        tree.deleteNode(5);
        System.out.println("\nAfter deleting node 5:");
        for (Node node : tree.traverse("BFS")) {
            System.out.print(node.getValue() + " ");
        }
    }
}
