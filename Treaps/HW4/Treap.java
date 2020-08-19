package HW4;

import java.util.Random;
import java.util.Stack;

public class Treap<E extends Comparable<E>>{
	public static class Node<E> {
		
		// data fields
		public E data;
		public int priority;
		public Node<E> left;
		public Node<E> right;
		
		// constructors
		public Node(E data, int priority) {
			if (data == null) {
				throw new IllegalArgumentException("Node constructor: data is null");
			}
			this.data = data;
			this.priority = priority;
			this.left = null;
			this.right = null;
		}
		
		// methods
		
		/**
		 * rotates to the right
		 * @return reference to the root of the result
		 */
		Node<E> rotateRight() {
			Node<E> temp1 = new Node<E>(this.data, this.priority);
			temp1.left = this.left;
			temp1.right = this.right;
			
			Node<E> x = this.left;
			Node<E> y = x.right;
			temp1.left = y;
			x.right = temp1;
			
			this.data = x.data;
			this.priority = x.priority;
			this.left = x.left;
			this.right = x.right;
			
			return x;
		}
		
		/**
		 * rotates to the left
		 * @return reference to the root of the result
		 */
		Node <E> rotateLeft() {
			Node<E> temp1 = new Node<E>(this.data, this.priority);
			temp1.left = this.left;
			temp1.right = this.right;
			
			Node<E> x = this.right;
			Node<E> y = x.left;
			temp1.right = y;
			x.left = temp1;
			
			this.data = x.data;
			this.priority = x.priority;
			this.left = x.left;
			this.right = x.right;
			
			return x;
		}
		
		/**
		 * toString method for Node class
		 * @return String representation of the node(key, priority)
		 */
		public String toString() {
			String keyString = String.valueOf(data);
			String priorityString = String.valueOf(priority);
			return "(" + "key = " + keyString + ", " + "priority = " + priorityString + ")";	
			
		}
	}
	
	// data fields
	private Random priorityGenerator;
	private Node<E> root;
	
	// constructors
	public Treap() {
		this.priorityGenerator = new Random();
		this.root = null;
	}
	
	public Treap(long seed) {
		this.priorityGenerator = new Random(seed);
		this.root = null;
	}
	
	// methods
	
	/**
	 * insert given element into the BST
	 * @param key, data to be contained in new node
	 * @return boolean, true if successfully added, false if tree was not modified
	 */
	boolean add(E key) {
		return add(key, this.priorityGenerator.nextInt());
	}
	
	/**
	 * insert given element into the BST
	 * @param key, data to be contained in new node
	 * @param priority, for ordering tree in heap structure
	 * @return boolean, true if successfully added, false if tree was not modified
	 */
	boolean add(E key, int priority) {
		Node<E> n1 = new Node<E>(key, priority);
		Node<E> current = this.root;
		Node<E> parent = null;
		Stack<Node<E>> s1 = new Stack<Node<E>>();
		
		while(current != null) {
			parent = current;
			if (key.compareTo(current.data) == 0) {
				return false;
			}
			else if (key.compareTo(current.data) < 0) {
				s1.push(current);
				current = current.left;
			}
			else {
				s1.push(current);
				current = current.right;
			}
		}
		if (parent == null) {
			parent = n1;
			root = n1;
		}
		else if (key.compareTo(parent.data) < 0) {
			parent.left = n1;
			reheap(s1);
		}
		else {
			parent.right = n1;
			reheap(s1);
		}
		return true;
	}
	/**
	 * Used to reheap for the add function
	 * @param nStack, Stack of nodes from add
	 */
	private void reheap(Stack<Node<E>> nStack) {
		while(nStack.size() > 1) {
			Node<E> n = nStack.pop();
			
			if (n.left != null && n.left.priority > n.priority) {
				n.rotateRight();
			}
			if (n.right != null && n.right.priority > n.priority) {
				n.rotateLeft();
			}
		}
		Node<E> r = nStack.pop();
		if(r.left != null && r.left.priority > r.priority) {
			root = r.left;
			r.rotateRight();
		}
		if (r.right != null && r.right.priority > r.priority) {
			root = r.right;
			r.rotateLeft();
		}
	}
	/**
	 * 
	 * @param r, root given in delete function
	 * @param key, key given in delete function
	 * @return node we wish to remove
	 */
	private Node<E> deleteHelper(Node<E> r, E key) {
		if (r == null) {
			return null;
		}
		if (key.compareTo(r.data) < 0) {
			r.left = deleteHelper(r.left, key);
		}
		else if (key.compareTo(r.data) > 0) {
			r.right = deleteHelper(r.right, key);
		}
		else {
			//node has no children
			if (r.left == null && r.right == null) {
				root = null;
			}
			//node has 2 children
			else if (r.left != null && r.right != null) {
				if (r.left.priority < r.right.priority) {
					r = r.rotateLeft();
					r.left = deleteHelper(r.left, key);
				}
				else {
					r = r.rotateRight();
					r.right = deleteHelper(r.right, key);
				}
				
			}
			//node has one child
			else {
				if (r.left != null) {
					r = r.left;
				}
				else {
					r = r.right;
				}
			}
		}
		return r;
	}
	
	/**
	 * deletes element(node) with given key
	 * @param key, data contained in node you are deleting
	 * @return boolean, true if successfully deleted, false if tree was not modified
	 */
	boolean delete(E key) {
		if (deleteHelper(this.root, key) != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * finds element(node) with given key
	 * @param Node<E> root, root of node you are trying to find
	 * @param key, data contained in node you are trying to find
	 * @return boolean, true if successfully found, false if not found
	 */ 
	private boolean find(Node<E> root, E key) {
		if (root == null) {
			return false;
		}
		if (root.data == key) {
			return true;
		}
		if (key.compareTo(root.data) < 0) {
			return find(root.left, key);
		}
		else {
			return find(root.right, key);
		}
	}
	
	/**
	 * finds element(node) with given key
	 * @param key, data contained in node you are trying to find
	 * @return boolean, true if successfully found, false if not found
	 */
	public boolean find(E key) {
		return find(root, key);
	}
	
	private StringBuilder toString(Node<E> current, int i) {
		StringBuilder r = new StringBuilder() ;
		for (int j=0; j<i; j++) {
			r.append("  ");
		}
		
		if (current==null) {
			r.append("null\n");
		} else {
			r.append(current.toString()+"\n");
			r.append(toString(current.left,i+1));
			r.append(toString(current.right,i+1));
			
		}
		return r;
		
	}
	/**
	 * toString method
	 * @return String representation of the tree
	 */
	public String toString() {
		return toString(root,0).toString();
	}
	
	//testing
	public static void main(String[] args) {
		Treap<Integer> testTree = new Treap <Integer>();
		testTree.add(4 ,19);
		testTree.add(2 ,31);
		testTree.add(6 ,70);
		testTree.add(1 ,84);
		testTree.add(3 ,12);
		testTree.add(5 ,83);
		testTree.add(7 ,26);
		
		//System.out.println(testTree);
		testTree.delete(5);
		System.out.println(testTree);
		System.out.println(testTree.find(6));
	}
}
