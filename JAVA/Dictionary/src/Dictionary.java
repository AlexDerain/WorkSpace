
public class Dictionary {

	private Vector data;
	
	public Dictionary(){
		data = new Vector(10000);
	}
	
	public void add(Object key, Object value){
		DictionaryPair d = new DictionaryPair(key, value);
		data.addLast(d);
	}
	
	public int findPosition(Object key){
		for(int i = 0; i < data.size(); i++){
			if(((DictionaryPair)data.get(i)).getKey().equals(key)){
				return i;
			}
		}
		return -1;
	}
	
	public Object find(Object key){
		for(int i = 0; i < data.size(); i++){
			if(((DictionaryPair)data.get(i)).getKey().equals(key)){
				return data.get(i);
			}
		}
		return null;
	}
}
