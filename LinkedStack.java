import nodes.Node;
import java.util.EmptyStackException;

public class LinkedStack<E> implements Cloneable {
   private Node<E> top;

	public LinkedStack() {
		top = null;
	}
	
	public Object clone() {
		LinkedStack answer;
		
		try {
			answer = (LinkedStack) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("This class does not implement cloneable");
		}
	
	answer.top = Node.listCopy(top);
	return answer;
	}
	
	public boolean isEmpty() {
		return (top == null);
	}
	
	public E peek() {
		if (top == null) {
			throw new EmptyStackException();
		}
		
		return top.getData();
	}
	
//	public E itemAt(int n) {
//		// pre condition: 0 <= n and n < size()
//		// post-condition: the return value is the item that is
//		// n from the top (with the top at n = 0
//		// the next at n = 1, and so on). The
//		// stack is not changed
//		
//		E answer;
//		
//		if (0 <= n && n < size()) {
////			answer = Node.listPosition(n);
//		} else {
//			throw new EmptyStackException();
//		}
//	}
	
	public E pop() {
		E answer;
		
		if (top == null) {
			throw new EmptyStackException();
		} else {
			answer = top.getData();
			top = top.getLink();
		}
		
		return answer;
	}
	
	public void push(E item) {
		top = new Node(item, top);
	}
	
	public int size() {
		return Node.listLength(top);
	}
}