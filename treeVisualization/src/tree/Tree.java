package tree;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Tree {
	protected Node root;
	
	public Tree() {
		this.root = null;
	}
    public Node getRoot() {
        return root;
    }
	
	public abstract void createTree(int value);
	public abstract void insertNode(Node parent, int value);
	public abstract void deleteNode(int value);
	public abstract void updateNode(int oldValue, int newValue);
    public Node search(int value) {
        return searchDFS(root, value);
    }

    public List<Node> traverse(String algorithm) {
        List<Node> result = new ArrayList<>();
        if ("DFS".equalsIgnoreCase(algorithm)) {
            dfsTraversal(root, result);
        } else if ("BFS".equalsIgnoreCase(algorithm)) {
            bfsTraversal(root, result);
        }
        return result;
    }
    //DFS
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
}