
public class DictionaryPair {

	private Object key;
	private Object value;
	
	public DictionaryPair(Object someKey, Object someValue){
		key = someKey;
		value = someValue;
	}
	
	public Object getKey(){
		return key;
	}
	
	public Object getValue(){
		return value;
	}
	
	public void setKey(Object newKey){
		key = newKey;
	}
	
	public void setValue(Object newValue){
		value = newValue;
	}	
}
