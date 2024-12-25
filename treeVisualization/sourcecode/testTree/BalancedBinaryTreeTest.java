package testTree;

import java.util.List;

import tree.BalancedBinaryTree;
import tree.Node;

public class BalancedBinaryTreeTest {
    public static void main(String[] args) {
        // Khởi tạo cây nhị phân cân bằng với độ chênh lệch chiều cao tối đa là 1
        BalancedBinaryTree balancedBinaryTree = new BalancedBinaryTree(1);

        // Tạo cây với giá trị root = 10
        balancedBinaryTree.createTree(10);
        System.out.println("Tree created with root value: 10");

        // Thử thêm các node vào cây
        Node root = balancedBinaryTree.getRoot();

        // Chèn node 5 vào cây
        balancedBinaryTree.insertNode(root, 5);  // Node 5 sẽ được thêm vào
        balancedBinaryTree.insertNode(root, 15); // Node 15 sẽ được thêm vào
        balancedBinaryTree.insertNode(root, 20); // Node 20 sẽ không được thêm vì không cân bằng

        // Thử tìm kiếm một node
        Node searchResult = balancedBinaryTree.search(15);
        if (searchResult != null) {
            System.out.println("Node found with value: " + searchResult.getValue());
        } else {
            System.out.println("Node not found");
        }

        // Duyệt cây theo chiều sâu (DFS)
        System.out.println("DFS Traversal:");
        List<Node> dfsResult = balancedBinaryTree.traverse("DFS");
        for (Node node : dfsResult) {
            System.out.print(node.getValue() + " ");
        }
        System.out.println();

        // Duyệt cây theo chiều rộng (BFS)
        System.out.println("BFS Traversal:");
        List<Node> bfsResult = balancedBinaryTree.traverse("BFS");
        for (Node node : bfsResult) {
            System.out.print(node.getValue() + " ");
        }
        System.out.println();

        // Thử cập nhật một node
        balancedBinaryTree.updateNode(15, 30);  // Cập nhật node 15 thành 30
        System.out.println("After updating node 15 to 30:");
        bfsResult = balancedBinaryTree.traverse("BFS");
        for (Node node : bfsResult) {
            System.out.print(node.getValue() + " ");
        }
        System.out.println();

        // Thử xóa một node
        balancedBinaryTree.deleteNode(5);
        System.out.println("After deleting node 5:");
        bfsResult = balancedBinaryTree.traverse("BFS");
        for (Node node : bfsResult) {
            System.out.print(node.getValue() + " ");
        }
    }
}
