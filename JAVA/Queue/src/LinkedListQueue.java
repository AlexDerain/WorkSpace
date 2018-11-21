
public class LinkedListQueue {

	private LinkedList data;
	
	public LinkedListQueue(){
		data = new LinkedList();
	}
	
	public void push(Comparable o){
		data.addLast(o);
	}
	
	public Comparable pop(){
		Comparable next = data.getFirst();
		data.removeFirst();
		return next;
	}
	
	public Comparable top(){
		return data.getFirst();
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
