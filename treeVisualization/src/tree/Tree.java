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
	public abstract Node search(int value);
	public abstract List<Node> traverse(String algorithm); 	// duyet cay theo dfs hoac bfs
}
