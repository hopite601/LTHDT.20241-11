package tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GenericTree extends Tree{
	
	public GenericTree() {
		super();
	}
	
	@Override
	public void createTree(int value) {
		this.root = new Node(value);
	}
	
	@Override
	public void insertNode(Node parent, int value) {
		if(parent != null) {
			Node newNode = new Node(value);
			parent.addChild(newNode);
		} else {
			System.out.println("parent node is null, cannot insert");
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
}
