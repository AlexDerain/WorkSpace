
public class VectorStack {
	
	private Vector data;
	
	public VectorStack(){
		data = new Vector(10000);
	}
	
	public void push(Object o){
		data.addLast(o);
	}
	
	public Object pop(){
		Object next =  data.getLast();
		data.removeLast();
		return next;
	}
	
	public Object top(){
		return data.getLast();
	}
	
	public int size(){
		return data.size();
	}
	
	public boolean empty(){
		return data.isEmpty();
	}
}
