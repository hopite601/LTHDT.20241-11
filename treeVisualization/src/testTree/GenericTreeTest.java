package testTree;

import java.util.List;

import tree.GenericTree;
import tree.Node;

public class GenericTreeTest {
    public static void main(String[] args) {
        GenericTree tree = new GenericTree();
        
        tree.createTree(1); // tao cay co root = 1

        // Thêm các node vào cây
        Node rootNode = tree.getRoot();
        tree.insertNode(rootNode, 2);  // them node 2 lam con node root
        tree.insertNode(rootNode, 3);  // them node 3 lam con node root

        // Thêm các node con cho node 2
        Node node2 = tree.search(2); // Tìm node có giá trị 2
        tree.insertNode(node2, 4);  // Thêm node có giá trị 4 làm con của node 2
        tree.insertNode(node2, 5);  // Thêm node có giá trị 5 làm con của node 2
	
        // Thêm các node con cho node 3
        Node node3 = tree.search(3); // Tìm node có giá trị 3
        tree.insertNode(node3, 6);  // Thêm node có giá trị 6 làm con của node 3
        tree.insertNode(node3, 7);  // Thêm node có giá trị 7 làm con của node 3

        // Duyệt cây theo DFS
        System.out.println("DFS Traversal:");
        List<Node> dfsResult = tree.traverse("DFS");
        for (Node node : dfsResult) {
            System.out.print(node.getValue() + " ");
        }
        System.out.println();

        // Duyệt cây theo BFS
        System.out.println("BFS Traversal:");
        List<Node> bfsResult = tree.traverse("BFS");
        for (Node node : bfsResult) {
            System.out.print(node.getValue() + " ");
        }
        System.out.println();

        // Tìm kiếm node trong cây
        int searchValue = 5;
        Node foundNode = tree.search(searchValue);
        if (foundNode != null) {
            System.out.println("Node with value " + searchValue + " found.");
        } else {
            System.out.println("Node with value " + searchValue + " not found.");
        }

        // Cập nhật giá trị node
        tree.updateNode(5, 10); // Cập nhật giá trị node có giá trị 5 thành 10
        System.out.println("After update:");
        List<Node> updatedResult = tree.traverse("DFS");
        for (Node node : updatedResult) {
            System.out.print(node.getValue() + " ");
        }
        System.out.println();

        // Xóa node
        tree.deleteNode(3); // Xóa node có giá trị 3
        System.out.println("After deletion of node 3:");
        List<Node> afterDeletionResult = tree.traverse("DFS");
        for (Node node : afterDeletionResult) {
            System.out.print(node.getValue() + " ");
        }
    }
}
