import java.util.Comparator;

public class PriorityQueue {

	private class PriorityPair implements Comparable{
		public Object element;
		public Object priority;
		
		public PriorityPair(Object element, Object priority){
			this.element = element;
			this.priority = priority;
		}
		
		public int compareTo(Object o){
			PriorityPair p2 = (PriorityPair)o;
			return ((Comparable)priority).compareTo(p2.priority);
		}
	}
	
	private LinkedList data;
	
	public PriorityQueue(){
		data = new LinkedList();
	}
	
	public void push(Object o, int priority){
		PriorityPair d = new PriorityPair(o, priority);
		data.addSorted(d);
	}
	
	public Object pop(){
		Comparable d = data.getFirst();
		data.removeFirst();
		return d;
	}
	
	public Object top(){
		return data.getFirst();
	}
}
