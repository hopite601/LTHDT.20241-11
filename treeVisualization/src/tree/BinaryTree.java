package tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTree extends Tree {

	public BinaryTree() {
		super();
	}
	
	@Override
	public void createTree(int value) {
		this.root = new Node(value);
	}
	
	@Override
    public void insertNode(Node parent, int value) {
        if (parent != null) {
            if (parent.getChildren().size() < 2) {
                Node newNode = new Node(value);
                parent.addChild(newNode);
            } else {
                System.out.println("A binary node can have only two children.");
            }
        }
    }
	
	@Override
	public void deleteNode(int value) {
		Node deleteNode = search(value);
		if(deleteNode != null) {
			deleteNode.removeSelf();
		}
	}
	
	 @Override
    public void updateNode(int oldValue, int newValue) {
        Node nodeToUpdate = search(oldValue);
        if (nodeToUpdate != null) {
            nodeToUpdate.setValue(newValue);
        }
    }
	
	@Override
	public Node search(int value) {
		if(root == null) {
			return null;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.add(root);
		while(!queue.isEmpty()) {
			Node current  = queue.poll(); // phan tu dau tien trong queue
			
			if(current.getValue() == value) {
				return current;
			}
			
			for(Node child : current.getChildren()) {
				queue.add(child);
			}
		}
		return null; 
	}
	
	@Override
    public List<Node> traverse(String algorithm) {
        if (algorithm.equalsIgnoreCase("DFS")) {
            return traverseDFS();
        } else if (algorithm.equalsIgnoreCase("BFS")) {
            return traverseBFS();
        }
        return new LinkedList<>(); 
    }

    // duyet DFS
    public List<Node> traverseDFS() {
        List<Node> result = new LinkedList<>();
        if (root != null) {
            traverseDFSRecursive(root, result);
        }
        return result;
    }
    private void traverseDFSRecursive(Node current, List<Node> result) {
        result.add(current); // them node hien tai vao ket qua

        for (Node child : current.getChildren()) {
            traverseDFSRecursive(child, result); // duyet tiep cac node con
        }
    }

    // duyet BFS
    public List<Node> traverseBFS() {
        List<Node> result = new LinkedList<>();
        if (root != null) {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                Node current = queue.poll();
                result.add(current); // Thêm giá trị của node vào kết quả

                // Thêm tất cả các con vào queue
                for (Node child : current.getChildren()) {
                    queue.add(child);
                }
            }
        }
        return result;
    }
	
}
