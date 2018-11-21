import java.util.Comparator;

public class Tree {

    public class TreeNode implements Comparable{
        private Comparable value;
        private TreeNode leftNode;
        private TreeNode rightNode;
        public TreeNode(Comparable v){
            value = v;
            leftNode = null;
            rightNode = null;
        }

        public TreeNode(Comparable v, TreeNode left, TreeNode right){
            value = v;
            leftNode = left;
            rightNode = right;
        }

        public TreeNode getLeftNode() {
            return leftNode;
        }

        public TreeNode getRightNode() {
            return rightNode;
        }

        public Comparable getValue() {
            return value;
        }

        public int compareTo(Object o){
            return value.compareTo(((TreeNode)o).value);
        }
    }

    private TreeNode root;

    public Tree(){
        root = null;
    }

    /*
    We traverse the tree.
    Current holds the pointer to the TreeNode we are currently checking
    Parent holds the pointer to the parent of the current TreeNode
     */
    private void insertAtNode(Comparable element, TreeNode current, TreeNode parent){
        //if the node we check is empty
        if(current == null){
            TreeNode newNode = new TreeNode(element);
            //the current node is empty, but we have a parent
            if(parent != null){
                //do we add it to the left?
                if(element.compareTo(parent.value) < 0){
                    parent.leftNode = newNode;
                }
                //or do we add it to the right?
                else {
                    parent.rightNode = newNode;
                }
            }
            /*
            the current node is empty and it has no parent,
            we actually have an empty tree
             */
            else
                root = newNode;
        }
        else if(element.compareTo(current.value) == 0){
            /*
            if the element is already in the tree,
            what should we do?
            at this point, wo do not care
             */
        }
        //if the element is smaller than current, go left
        else if(element.compareTo(current.value) < 0){
            insertAtNode(element, current.getLeftNode(), current);
        }
        //if the element is bigger than current, go right
        else
            insertAtNode(element, current.getRightNode(), current);
    }

    public void insert(Comparable element){

    }

    private boolean findNode(Comparable element, TreeNode current){
        if(current == null)
            return false;
        else if(element.compareTo(current.value) == 0)
            return true;
        else if(element.compareTo(current.value) < 0){
            return findNode(element, current.getLeftNode());
        }
        else
            return findNode(element, current.getRightNode());
    }

    public boolean find(Comparable element){
        return findNode(element, root);
    }
}
