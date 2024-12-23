package tree;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Collections;


public abstract class Tree {
	protected Node root;
	
	public Tree() {
		this.root = null;
	}
    public Node getRoot() {
        return root;
    }
	
    public void createTree(int value) {
    	this.root = new Node(value);
    };
	public abstract boolean insertNode(Node parent, int value);
	public abstract void deleteNode(int value);
	public abstract boolean updateNode(int oldValue, int newValue);
    public Node search(int value) {
        return searchDFS(root, value);
    }
    // Trả về đường đi từ gốc đến giá trị tìm được
    public List<Node> findPath(int value) {
        Node node = search(value);
        if (node != null) {
            // Nếu tìm thấy node, trả về đường đi từ node đến gốc
            return getPathToRoot(node);
        }
        return null; // Nếu không tìm thấy node, trả về null
    }
    
    public List<Node> traverse(String algorithm) {
        List<Node> result = new ArrayList<>();
        if ("DFS".equalsIgnoreCase(algorithm)) {
            dfsTraversal(root, result);
        } else if ("BFS".equalsIgnoreCase(algorithm)) {
            bfsTraversal(root, result);
        } else if ("InOrder".equalsIgnoreCase(algorithm)) {
            inOrderTraversal(root, result);
        } else if ("PreOrder".equalsIgnoreCase(algorithm)) {
            preOrderTraversal(root, result);
        } else if ("PostOrder".equalsIgnoreCase(algorithm)) {
            postOrderTraversal(root, result);
        }
        return result;
    }
    //DFS(Pre-order)
    private void dfsTraversal(Node node, List<Node> result) {
        if (node == null) return;
        result.add(node); 
        for (Node child : node.getChildren()) {
            dfsTraversal(child, result);
        }
    }

    //BFS
    private void bfsTraversal(Node node, List<Node> result) {
        if (node == null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            result.add(current);
            queue.addAll(current.getChildren());
        }
    }
    
    //In-order
    private void inOrderTraversal(Node node, List<Node> result) {
        if (node == null) return;
        if (!node.getChildren().isEmpty()) {
            inOrderTraversal(node.getChildren().get(0), result); // Duyệt trái
        }
        result.add(node); // Thêm nút hiện tại
        for (int i = 1; i < node.getChildren().size(); i++) { // Duyệt các nút còn lại
            inOrderTraversal(node.getChildren().get(i), result);
        }
    }

    // Pre-order
    private void preOrderTraversal(Node node, List<Node> result) {
        if (node == null) return;
        result.add(node); // Thêm nút hiện tại
        for (Node child : node.getChildren()) {
            preOrderTraversal(child, result);
        }
    }

    // Post-order 
    private void postOrderTraversal(Node node, List<Node> result) {
        if (node == null) return;
        for (Node child : node.getChildren()) {
            postOrderTraversal(child, result);
        }
        result.add(node); // Thêm nút hiện tại sau khi duyệt hết các con
    }
    

    //Tìm kiếm DFS
    private Node searchDFS(Node node, int value) {
        if (node == null) return null;
        if (node.getValue() == value) return node;

        for (Node child : node.getChildren()) {
            Node result = searchDFS(child, value);
            if (result != null) return result;
        }
        return null;
    }
    //Tìm kiếm BFS
    /*private Node searchBFS(Node node, int value) {
        if (node == null) return null;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
          
        while (!queue.isEmpty()) {
            Node current = queue.poll();  
            if (current.getValue() == value) {
                return current;  
            }
            queue.addAll(current.getChildren());
        }
        return null;  // Nếu không tìm thấy node, trả về null
    }*/
    // Trả về đường đi từ gốc đến giá trị tìm được
    public void printPath(Node node) {
        List<Node> path = getPathToRoot(node);
        if (path != null) {
            for (Node n : path) {
                System.out.print(n.getValue() + " ");
            }
            System.out.println(); 
        } else {
            System.out.println("Node không tồn tại trong cây.");
        }
    }

    // Phương thức lấy đường đi từ node đến gốc cây
    public List<Node> getPathToRoot(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.getParent();  // Đi lên node cha
        }
        Collections.reverse(path);  // Đảo ngược để có đường đi từ gốc đến node
        return path;
    }
    
    public Node findNodeByValue(Node root, int value) {
        if (root == null) return null;
        
        if (root.getValue() == value) {
            return root;
        }

        // Tìm kiếm trong các node con
        for (Node child : root.getChildren()) {
            Node result = findNodeByValue(child, value);
            if (result != null) return result;
        }

        return null;  
    }
}