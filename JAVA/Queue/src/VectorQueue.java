
public class VectorQueue {

	private Vector data;
	
	public VectorQueue(){
		data = new Vector(10000);
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
	
	public boolean empty(){
		return data.isEmpty();
	}
}
