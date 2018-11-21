package Vector;

/**
 *
 * @author DING Rui
 *
 * This class is copied and modified from the exercise last semester, where unnecessary methods are deleted.
 */
public class Vector {

    private int pointer;
    private Object[] data;

    public Vector(){
        pointer = 0;
        data = new Object[100];
    }

    public void addLast(Object v){
        data[pointer] = v;
        ++pointer;
    }

    public Object get(int i){
        return data[i];
    }

    public int size(){
        return pointer;
    }
}
