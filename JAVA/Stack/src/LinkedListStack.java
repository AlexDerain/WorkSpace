
public class LinkedListStack {
	
	private LinkedList data;
	
	public LinkedListStack(){
		data = new LinkedList();
	}
	
	public void push(Comparable o){
		data.addLast(o);
	}
	
	public Comparable pop(){
		Comparable next = data.getLast();
		data.removeLast();
		return next;
	}
	
	public Comparable top(){
		return data.getLast();
	}
	
	public int size(){
		return data.size();
	}
	
	public boolean empty(){
		if(data.size() == 0)
			return true;
		else
			return false;
	}
}
