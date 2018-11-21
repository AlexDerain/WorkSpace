
public class CircularVector {

	private Object data[];
	private int first;
	private int count;
	private int capacity;
	private int last;
	
	public CircularVector(int capacity){
		count = 0;
		first = 0;
		last = 0;
		data = new Object[capacity];
		this.capacity = capacity;
	}
	
	public int size(){
		return count;
	}
	
	public void addFirst(Object element){
		first = (first + capacity - 1) % capacity;
		data[first] = element;
		count++;
	}
	
	public void addLast(Object element){
		last = (first + count) % capacity;
		data[last] = element;
		count++;
	}
	
	public Object getFirst(){
		return data[first];
	}
	
	public Object getLast(){
		return data[last];
	}
	
	public void removeFirst(){
		if(count > 0){
			first = (first + 1) % capacity;
			count--;
		}
	}
	
	public void removeLast(){
		if(count > 0){
			last = (last - 1) % capacity;
			count--;
		}
	}
	
	public void print(){
		System.out.print("[ ");
		for(int i = 0; i < count; i++){
			int index = (first + i) % capacity;
			System.out.print(data[index] + " ");
		}
		System.out.println("]");
	}
}
