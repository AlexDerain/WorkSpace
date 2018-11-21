
public class Vector {
	protected Object data[];
	protected int count;
	
	public Vector(int capacity){
		data = new Object[capacity];
		count = 0;
	}
	
	public Object getFirst(){
		return data[0];
	}
	
	public Object getLast(){
		return data[count - 1];
	}
	
	public Object get(int index){
		return data[index];
	}
	
	public void addFirst(Object o){
		count++;
		for(int i = count - 2; i >= 0; i--){
			data[i + 1] = data[i];
		}
		data[0] = o;
	}
	
	public void addLast(Object o){
		data[count] = o;
		count++;
	}
	
	public void set(int index, Object o){
		data[index] = o;
	}
	
	public void removeLast(){
		count--;
	}
	
	public void removeFirst(){
		for(int i = 0; i < count - 1; i++){
			data[i] = data[i + 1];
		}
		count--;
	}
	
	public void reverse(){
		for (int i = 0; i < count / 2; i++){
			Object t = data[i];
			data[i] = data[count - i - 1];
			data[count - 1 - i] = t;
		}
	}
	
	public int size(){
		return count;
	}
	
	public boolean isEmpty(){
		return size() == 0;
	}
	
	public boolean contain(Object o){
		for (int i = 0; i < count; i++){
			if (data[i].equals(o))
				return true;
		}
		return false;
	}
	
	public void print(){
		System.out.print("( ");
		for (int i = 0; i < count; i++){
			System.out.print(data[i] + " ");
		}
		System.out.println(")");
	}
}
