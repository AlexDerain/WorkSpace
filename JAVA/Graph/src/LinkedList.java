import java.util.Comparator;

public class LinkedList {
	private class ListElement{
		private Comparable el1;
		private ListElement el2;
		
		public ListElement(Comparable o, ListElement el){
			el1 = o;
			el2 = el;
		}
		
		public ListElement (Comparable o){
			el1 = o;
			el2 = null;
		}
		
		public Comparable first(){
			return el1;
		}
		
		public ListElement rest(){
			return el2;
		}
		
		public void setFirst(Comparable value){
			el1 = value;
		}
		
		public void setRest(ListElement el){
			el2 = el;
		}
	}
	
	private ListElement head;
	private int count = 0;
	
	public LinkedList(){
		head = null;
	}
	
	public void addFirst(Comparable o){
		head = new ListElement(o, head);
		count++;
	}
	
	public void addLast(Comparable o){
		ListElement d = head;
		if(d == null){
			addFirst(o);
		}
		else{
			while(d.rest() != null){
				d = d.rest();
			}
			d.setRest(new ListElement(o));
		}
		count++;
	}
	
	public void add(int n, Comparable o){
		ListElement d = head;
		if (n == 0){
			head = new ListElement(o, head);
		}
		else{
				while(n > 1){
					d = d.rest();
					n--;
			}
			d.el2 = new ListElement(o, d.el2);
		}
		count++;
	}
	
	public void addSorted(Comparable o){
		if(head == null){
			head = new ListElement(o, null);
		}
		else if(head.first().compareTo(o) > 0){
			head = new ListElement(o, head);
		}
		else{
			ListElement d = head;
			while((d.rest() != null) && (d.rest().first().compareTo(o) < 0)){
				d = d.rest();
			}
			ListElement next = d.rest();
			d.setRest(new ListElement(o, next));
		}
		count++;
	}

	public void removeFirst(){
		head = head.rest();
		count--;
	}
	
	public void removeLast(){
		ListElement d = head;
		while(d.rest().rest() != null){
			d = d.rest();
		}
		d.setRest(null);
	}
	
	public void remove(int n){
		ListElement d = head;
		if(n == 0){
			removeFirst();
		}
		else if(n == count - 1){
			removeLast();
		}
		else{
			while(n > 1){
				d = d.rest();
				n--;
			}
			d.setRest(d.rest().rest());
		}
	}
	
	public Comparable getFirst(){
		return head.first();
	}
	
	public Comparable getLast(){
		ListElement d = head;
		while(d.rest() != null){
			d = d.rest();
		}
		return d.first();
	}
	
	public Comparable get(int n){
		ListElement d = head;
		while(n > 0){
			d = d.rest();
			n--;
		}
		return d.first();
	}
	
	public boolean search(Comparable o){
		ListElement d = head;
		while(d.first() != o){
			d = d.rest();
			if(d.rest() == null){
				return false;
			}
		}
		return true;
	}
	
	public void append(LinkedList list2){
		ListElement d = head;
		while(d.rest() != null){
			d = d.rest();
		}
		d.setRest(list2.head);
	}
	
	public int size(){
		return count;
	}
	
	public void print(){
		System.out.print("( ");
		ListElement d = head;
		while(d != null){
			System.out.print(d.first().toString() + " ");
			d = d.rest();
		}
		System.out.println(")");
	}
}
