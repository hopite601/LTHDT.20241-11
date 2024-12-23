package tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTree extends Tree {

	public BinaryTree() {
		super();
	}
	
	
	@Override
    public boolean insertNode(Node parent, int value) {
        if (parent != null) {
            if (parent.getChildren().size() < 2) {
                Node newNode = new Node(value);
                parent.addChild(newNode);
                return true;
            } else {
                System.out.println("A binary node can have only two children.");
                return false;
            }
        }
        return false;
    }
	
	@Override
	public void deleteNode(int value) {
		Node deleteNode = search(value);
		if(deleteNode != null) {
			deleteNode.removeSelf();
		}
	}
	
	 @Override
	 public boolean updateNode(int oldValue, int newValue) {
	        Node nodeToUpdate = search(oldValue);
	        if (nodeToUpdate != null) {
	            nodeToUpdate.setValue(newValue);
	        }
	        return true;
	  }
	
	
	
}
