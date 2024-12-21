package tree;

import java.util.List;

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
	public Node search(int value){
		return searchDFS(root, value);
    }

    private Node searchDFS(Node node, int value) {
        if (node == null) return null;
        if (node.getValue() == value) return node;

        for (Node child : node.getChildren()) {
            Node result = searchDFS(child, value);
            if (result != null) return result;
        }
        return null;
	}; 
	public abstract List<Node> traverse(String algorithm){
		List<Node> result = new ArrayList<>();
			if ("DFS".equalsIgnoreCase(algorithm)) {
				dfsTraversal(root, result);
			} else if ("BFS".equalsIgnoreCase(algorithm)) {
				bfsTraversal(root, result);
			}
			return result;
    }; 	// duyet cay theo dfs hoac bfs
	private void dfsTraversal(Node node, List<Node> result) {
        if (node == null) return;
        result.add(node); // Thêm nút vào danh sách
        for (Node child : node.getChildren()) {
            dfsTraversal(child, result);
        }
    }

    private void bfsTraversal(Node node, List<Node> result) {
        if (node == null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            result.add(current); // Thêm nút vào danh sách
            queue.addAll(current.getChildren());
        }
    }
}
