/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */

public class AVLTree {
	private IAVLNode root;
	private int size;
	public AVLTree(){
		this.root = new AVLNode();
		this.size = 0;
	}

  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty() {
    return this.size==0;
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k)
  {
	IAVLNode root = this.root;
	return search_rec(k,root);
  }
  private String search_rec (int k, IAVLNode node){
  	if (!node.isRealNode()){
  		return null;
	}
  	if (node.getKey() == k){
  		return node.getValue();
	}
  	if (node.getKey()<k){
  		return search_rec(k,node.getRight());
	}
  	else{
  		return search_rec(k,node.getLeft());
	}
  }

  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the AVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * promotion/rotation - counted as one rebalnce operation, double-rotation is counted as 2.
   * returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
   	IAVLNode node = new AVLNode(k,i);
   	insert_rec(this.root,node);
   	return 0;

   }
	public void insert_rec (IAVLNode node , IAVLNode to_insert){
   	if (!node.isRealNode()){
   		node = to_insert;
	}
   	else{
   		if (node.getKey()<to_insert.getKey()){
   			insert_rec(node.getLeft() , to_insert);
		}
   		else{
   			insert_rec(node.getRight(),to_insert);
		}
	}

	}
  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * demotion/rotation - counted as one rebalnce operation, double-rotation is counted as 2.
   * returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
	   return 42;	// to be replaced by student code
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
	   return "42"; // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
	   return "42"; // to be replaced by student code
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
        int[] arr = new int[42]; // to be replaced by student code
        return arr;              // to be replaced by student code
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
        String[] arr = new String[42]; // to be replaced by student code
        return arr;                    // to be replaced by student code
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    */
   public int size()
   {
	   return 42; // to be replaced by student code
   }
   
     /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    *
    * precondition: none
    * postcondition: none
    */
   public IAVLNode getRoot()
   {
	   return null;
   }
     /**
    * public string split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	  * precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
    * postcondition: none
    */   
   public AVLTree[] split(int x)
   {
	   return null; 
   }
   /**
    * public join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	  * precondition: keys(x,t) < keys() or keys(x,t) > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   return 0; 
   }

	/**
	   * public interface IAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IAVLNode{	
		public int getKey(); //returns node's key (for virtuval node return -1)
		public String getValue(); //returns node's value [info] (for virtuval node return null)
		public void setLeft(IAVLNode node); //sets left child
		public IAVLNode getLeft(); //returns left child (if there is no left child return null)
		public void setRight(IAVLNode node); //sets right child
		public IAVLNode getRight(); //returns right child (if there is no right child return null)
		public void setParent(IAVLNode node); //sets parent
		public IAVLNode getParent(); //returns the parent (if there is no parent return null)
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node
    	public void setHeight(int height); // sets the height of the node
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes)
	}

   /**
   * public class AVLNode
   *
   * If you wish to implement classes other than AVLTree
   * (for example AVLNode), do it in this file, not in 
   * another file.
   * This class can and must be modified.
   * (It must implement IAVLNode)
   */
  public class AVLNode implements IAVLNode {
	   private int key;
	   private String info;
	   private IAVLNode left;
	   private IAVLNode right;
	   private IAVLNode parent;
	   private int height;

	   public AVLNode () {
	   	this.key =-1;
	   	this.height =-1;
	   }
		public AVLNode (int key, String value){
			this.key = key;
			this.info = value;
			this.left = new AVLNode();
			this.right = new AVLNode();
			this.height =0;
		}


	   public int getKey() {
		   return this.key; // to be replaced by student code
	   }

	   public String getValue() {

		   return this.info; // to be replaced by student code
	   }

	   public void setLeft(IAVLNode node) {
		   this.left = node;

	   }

	   public IAVLNode getLeft() {

		   return this.left;
	   }

	   public void setRight(IAVLNode node) {
		   this.right = node;
	   }

	   public IAVLNode getRight() {

		   return this.right;
	   }

	   public void setParent(IAVLNode node) {
		   this.parent = node;
	   }

	   public IAVLNode getParent() {
		   return this.parent;
	   }

	   // Returns True if this is a non-virtual AVL node
	   public boolean isRealNode() {
		   return this.key!=-1; // to be replaced by student code
	   }

		public void setHeight(int height)
		{
		  this.height= height; // to be replaced by student code
		}
		public int getHeight()
		{
		  return this.height; // to be replaced by student code
		}
		// return the balance factor of a node
		public int get_balance_factor(){
	   	if (!isRealNode()){
	   		return 0;
		}
	   	return this.left.getHeight() - this.right.getHeight();
		}
  }

}
  
