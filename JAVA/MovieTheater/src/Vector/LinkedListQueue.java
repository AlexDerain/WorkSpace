package Vector;


public class LinkedListQueue {

	private LinkedList data;
	
	public LinkedListQueue(){
		data = new LinkedList();
	}
	
	public void push(Object o){
		data.addLast(o);
	}
	
	public Object pop(){
		Object next = data.getFirst();
		data.removeFirst();
		return next;
	}
	
	public Object top(){
		return data.getFirst();
	}
	
	public int size(){
		return data.size();
	}
	
	public void print(){
		data.print();
	}
	
	public boolean isEmpty(){
		return data.size() == 0;
	}
}
