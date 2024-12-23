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
	public boolean insertNode(Node parent, int value) {
		if (parent == null) {
	        System.out.println("Parent node is null, cannot insert");
	        return false;
	    }

	    if (search(value) != null) {
	        System.out.println("Node already exists with value: " + value);
	        return false;
	    }

	    Node newNode = new Node(value);
	    parent.addChild(newNode);
	    return true;
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
	     // Kiểm tra nếu giá trị mới đã tồn tại trong cây
	     if (search(newValue) != null) {
	         System.out.println("Cannot update. Node with value " + newValue + " already exists.");
	         return false;
	     }
	     // Tìm node cần cập nhật
	     Node nodeToUpdate = search(oldValue);
	     if (nodeToUpdate != null) {
	         nodeToUpdate.setValue(newValue); // Cập nhật giá trị
	         System.out.println("Updated node value from " + oldValue + " to " + newValue);
	         return true;
	     } else {
	         System.out.println("Node with value " + oldValue + " not found.");
	         return false;
	     }
	 }
}
