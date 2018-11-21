package Vector;

/**
 *
 * @author DING Rui
 *
 * This class is copied and modified from the exercise last semester, where unnecessary methods are deleted.
 *
 * PS: Because this class is created quite long time ago, some of the names of the parameters and variables are not so accurate
 * The String "value" actually means the name of current property
 * The Vector "property" actually means the names of the values under current property
 * Just in case of confusion
 */
public class Tree {

    public class TreeNode{
        protected String value; //the name of current feature
        protected Vector next;  //next feature that needs to be considered under each property
        protected Vector property;  //properties of current feature, normally 2 or 3
        //each next tree is under a specific property
        public TreeNode(){
            value = null;
            next = new Vector();
            property = new Vector();
        }

        public TreeNode(String v){
            value = v;
            next = new Vector();
            property = new Vector();
        }

        public void setValue(String v){
            value = v;
        }

        public String getValue() {
            return value;
        }

        public void pushNext(Tree node){
            next.addLast(node);
        }

        public void pushProperty(String v){
            property.addLast(v);
        }
    }

    private TreeNode root;

    public Tree(){
        root = new TreeNode();
    }

    public Tree(String v){
        root = new TreeNode(v);
    }

    public String getValue(){
        return root.getValue();
    }

    public void setValue(String v){
        root.setValue(v);
    }

    public Vector getNext(){
        return root.next;
    }

    public Vector getProperty(){
        return root.property;
    }

    public void pushNext(Tree node){
        root.pushNext(node);
    }

    public void pushProperty(String v){
        root.pushProperty(v);
    }
}
