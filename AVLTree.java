import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */


public class AVLTree {
	public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		AVLTree tree2 = new AVLTree();
		AVLTree tree3 = new AVLTree();
		AVLTree tree4 = new AVLTree();

		tree.insert(1,"Asdas");
		tree.insert(2,"tomer");;
		tree.insert(3,"tomer");





		tree2.insert(5,"tomer");
		tree2.insert(6,"tomer");
		tree2.insert(7,"tomer");
		tree2.insert(8,"tomer");
		tree2.insert(9,"tomer");
		tree2.insert(10,"tomer");
		tree2.insert(11,"tomer");
		tree2.insert(12,"tomer");
		tree2.insert(13,"tomer");
		int [] keys = tree.keysToArray();

		tree4.insert(18,"asdsa");
		tree4.insert(17,"asdsa");
		tree4.insert(19,"asdsa");
		tree4.insert(21,"asdsa");
		tree4.insert(23,"asdsa");

		AVLTree tree5 = new AVLTree();
		//tree5.insert(5,"asd");

		tree3.insert(14,"asdsa");
		IAVLNode node = tree3.getRoot();

		//System.out.println("AVL: "+tree.isAVL());
		//System.out.println("BST: "+tree.isBST());
		//tree.print();

		//System.out.println("AVL: "+tree.isAVL());
		//System.out.println("BST: "+tree.isBST());
		//tree2.print();

		printJoined(tree2,tree4,node);




	}
	private static void printJoined(AVLTree tree1 , AVLTree tree2,IAVLNode node){
		//System.out.println("AVL: "+tree2.isAVL());
		//System.out.println("BST: "+tree2.isBST());
		System.out.println(tree1.join(node,tree2));
		tree1.print();
	}

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
 private void print (){
	 TreePrinter.print(this.getRoot());
 }
  public String search(int k)
  {
	IAVLNode root = this.root;
	if (search_node(k,root)==null){
		return null;
	}
	else {
	return search_node(k, root).getValue();
	}
  }
  private IAVLNode search_node (int k, IAVLNode node){
  	if (!node.isRealNode()){
  		return null;
	}
  	if (node.getKey() == k){
  		return node;
	}
  	if (node.getKey()<k){
  		return search_node(k,node.getRight());
	}
  	else{
  		return search_node(k,node.getLeft());
	}
  }

  //Checks AVL Property
  public boolean isAVL (){
 	return isAVl_rec((AVLNode) this.getRoot());
  }
  public boolean isAVl_rec(AVLNode node){
	  if (!node.isRealNode()) {
		  return true;
	  }
 	else if (Math.abs(node.getBalanceFactor())>1) {
		return false;
	}
 	else{
 		return (isAVl_rec((AVLNode) node.getLeft())&& isAVl_rec((AVLNode) node.getRight()));
	}
  }

  //Checks Binary Tree Property
	public boolean isBST(){
 		return isBST_rec(this.getRoot(),this.minNode().getKey(),this.maxNode().getKey());
	}
	public boolean isBST_rec(IAVLNode node,int min,int max){
 		if (!node.isRealNode()) return true;
 		if (node.getKey()<min || node.getKey()>max){
 			return false;
		}
 		else{
 			return isBST_rec(node.getLeft(),min,node.getKey()) && isBST_rec(node.getRight(),node.getKey(),max);
		}
	}
	public IAVLNode minNode(){
 	IAVLNode node = this.getRoot();
 	while (node.getLeft().isRealNode()){
 		node = node.getLeft();
	}
 	return node;
	}
	public IAVLNode maxNode(){
		IAVLNode node = this.getRoot();
		while (node.getRight().isRealNode()){
			node = node.getRight();
		}
		return node;
	}
	//count length of route from the node to the root
	// @pre: node is inside tree
	public int getHeightByNode (IAVLNode node){
 		if (!node.isRealNode()){
 			return -1;
		}
 		int heightLeft = getHeightByNode(node.getLeft());
 		int heightRight = getHeightByNode(node.getRight());
 		return Math.max(heightLeft,heightRight)+1;
	}
	public int getHeightByKey(int key){
 		return getHeightByNode(search_node(key,this.getRoot()));
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
  // node balance factor is -2
  public void rightRotation (IAVLNode y){
		IAVLNode x = y.getLeft();
	  	if (y==this.getRoot()){
		this.root = x;
	  	}
	  	else {
			if (y.getParent().getLeft() == y) {
				y.getParent().setLeft(x);
			} else {
				y.getParent().setRight(x);
			}
		}
		IAVLNode b  = x.getRight();
		x.setRight(y);
		x.setParent(y.getParent());
		y.setParent(x);
		y.setLeft(b);
	  	y.setHeight(calcHeight(y));
	  	x.setHeight(calcHeight(x));
		b.setParent(y);
  }

  public void leftRotation(IAVLNode y){
  	IAVLNode x = y.getRight();
  	if(y==this.getRoot()){
  		this.root = x;
	}
  	else {
  		if (y.getParent().getRight()==y){
			y.getParent().setRight(x);
		}
		else{
			y.getParent().setLeft(x);
		}
	}
  	IAVLNode b = x.getLeft();
  	x.setLeft(y);
  	x.setParent(y.getParent());
  	y.setParent(x);
  	y.setRight(b);
  	y.setHeight(calcHeight(y));
  	x.setHeight(calcHeight(x));
  	b.setParent(y);
  }

  public static int calcHeight (IAVLNode y){
  	return Math.max(y.getLeft().getHeight(),y.getRight().getHeight())+1;
  }

  public void rightThenLeftRotation (IAVLNode z){
  	rightRotation(z.getRight());
  	leftRotation(z);
  }
	public void leftThenRightRotation (IAVLNode z){
  		leftRotation(z.getLeft());
		rightRotation(z);
	}
  private static int getBalanceFactor (IAVLNode node){
  	return node.getLeft().getHeight()- node.getRight().getHeight();
  }
   public int insert(int k, String i) {
  	int cnt = 0;
   	IAVLNode node = new AVLNode(k,i);
   	if (this.empty()){
   		this.root = node;
		this.size++;
   	}
   	else {
		insert_rec(this.root, node);
		this.size++;
   	}

   	int bf;
   	while(node!=null){
   		bf = getBalanceFactor(node);
   		if (bf<-1) {
			if (getBalanceFactor(node.getRight()) > 0) {
				rightThenLeftRotation(node);
			} else {
				leftRotation(node);
			}
		}
		else if (bf>1){
			if (getBalanceFactor(node.getRight()) > 0){
				rightRotation(node);
			}
			else{
				leftThenRightRotation(node);
			}
		}
		else{
			node.setHeight(Math.max(node.getLeft().getHeight(),node.getRight().getHeight())+1);
		}
		node = node.getParent();

		}
	   return 0;
	}


	public void insert_rec (IAVLNode node , IAVLNode to_insert){
   	if (!node.isRealNode()){
   		if (node.getParent().getLeft()==node) node.getParent().setLeft(to_insert);
   		else node.getParent().setRight(to_insert);
   		to_insert.setParent(node.getParent());
   		while (node.getParent()!= null){
   			node = node.getParent();
   			node.setHeight(Math.max(node.getLeft().getHeight(),node.getRight().getHeight())+1);
		}
	}
   	else{
   		if (node.getKey()<to_insert.getKey()){
   			insert_rec(node.getRight() , to_insert);
		}
   		else{
   			insert_rec(node.getLeft(),to_insert);
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
	   return minNode().getValue() ; // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
	   return this.maxNode().getValue(); // to be replaced by student code
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public void treeToNodeArray (IAVLNode node , IAVLNode [] arr, int [] c){
  	if (!node.isRealNode()) return;
  	treeToNodeArray(node.getLeft(),arr,c);
  	arr [c[0]] = node;
  	c[0]++;
  	treeToNodeArray(node.getRight(),arr,c);

  }

  public int[] keysToArray()
  {
	  IAVLNode [] arr = new IAVLNode [this.size()];
	  this.treeToNodeArray(this.getRoot(),arr,new int [] {0});
	  int [] ret = new int [arr.length];
	  for (int i=0; i< arr.length;i++){
	  	ret[i] = arr[i].getKey();
	  }
	  return ret;
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
	  IAVLNode [] arr = new IAVLNode [this.size()];
	  this.treeToNodeArray(this.getRoot(),arr,new int [] {0});
	  String [] ret = new String [arr.length];
	  for (int i=0; i< arr.length;i++){
		  ret[i] = arr[i].getValue();
	  }
	  return ret;
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
	   return this.size; // to be replaced by student code
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
	   return this.root;
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
   		//Initial check if one or both is empty
	   if (!this.getRoot().isRealNode()){
	   	this.root = t.getRoot();
	   	t.insert(x.getKey(),x.getValue());
	   	return this.calcComplexity(t);
	   }
	   if (!t.getRoot().isRealNode()){
		   this.insert(x.getKey(),x.getValue());
		   return this.calcComplexity(t);
	   }
   		//Set pointers for larger and smaller trees according to theirs height (Complexity of stage: O(1))
   		AVLTree higherTree, shorterTree;
   		if (this.getRoot().getHeight()>t.getRoot().getHeight()){
   			higherTree = this;
   			shorterTree = t;
		}
   		else{
   			shorterTree = this;
   			higherTree = t;
		}
   		boolean higher_is_larger = higherTree.getRoot().getKey()>x.getKey();
   		int largerTreeHeight = higherTree.getRoot().getHeight();
	   	int smallerTreeHeight = shorterTree.getRoot().getHeight();
	   	if (largerTreeHeight-smallerTreeHeight<=1) {//that means we can simply merge
			simpleMerge(t, x);
			return this.calcComplexity(t);
		}
	   		if (higher_is_larger){
	   			x.setRight(higherTree.getRoot());
				x.setLeft(higherTree.getRoot());

			}
	   		else{
				x.getRight().setParent(x);
				x.getLeft().setParent(x);
	   		}

   		IAVLNode node = getNodeWithHeight(higherTree,smallerTreeHeight,higher_is_larger);
   		if (higher_is_larger) {
			setPointersForJoinLeft(shorterTree.getRoot(), node, x);
		}
   		else{
   			setPointersForJoinRight(shorterTree.getRoot(),node,x);
		}
	   this.root = higherTree.root;
	   setHeightUpTree (x);

	   return this.calcComplexity(t);

   }
	//Calculate the complexity as requested in conditions
	private int calcComplexity (AVLTree t){
   	return Math.abs(this.getRoot().getHeight()-t.getRoot().getHeight()) +1;
	}
   //Simple merge = check who is bigger and merge. needs to set the root to x
	private void simpleMerge (AVLTree other, IAVLNode x){
   	if (this.getRoot().getKey()> other.getRoot().getKey()){
   		x.setRight(this.getRoot());
		x.setLeft(other.getRoot());
	}
   	else{
		x.setRight(other.getRoot());
		x.setLeft(this.getRoot());
	}
   	x.getLeft().setParent(x);
   	x.getRight().setParent(x);
   	x.setHeight(calcHeight(x));
   	this.root = x;
	}
   // gets pointers to the root of the smaller tree, the node with appropriate height on the left branch of higherTree and x
	// sets the appropriate pointers that maintains tree relations
   private static void setPointersForJoinLeft (IAVLNode smallerRoot, IAVLNode foundNode, IAVLNode x){
	   x.setLeft(smallerRoot);
	   x.setRight(foundNode);
	   foundNode.getParent().setLeft(x);
	   x.setParent(foundNode.getParent());
	   foundNode.setParent(x);
	   smallerRoot.setParent(x);
   }
	// gets pointers to the root of the smaller tree, the node with appropriate height on the right branch of higherTree and x
	// sets the appropriate pointers that maintains tree relations
	private static void setPointersForJoinRight (IAVLNode smallerRoot, IAVLNode foundNode, IAVLNode x){
		x.setRight(smallerRoot);
		x.setLeft(foundNode);
		foundNode.getParent().setRight(x);
		x.setParent(foundNode.getParent());
		foundNode.setParent(x);
		smallerRoot.setParent(x);
	}
   //Gets a node and goes upstream in the tree and updates heights with property: node.height = max(node.left.key, node.right.key)+1;
   private static void setHeightUpTree (IAVLNode node){
	   while (node!=null){
		   node.setHeight(calcHeight(node));
		   node=node.getParent();
	   }
   }

   //Gets the node from larger tree that satisfies the propery ($ret.getHeight()-shorter.getHeight()== 0 or -1)
	// node will be on the left branch if longer >x>shorter and on the right branch otherwise (if shorter>x>longer)
	private static IAVLNode getNodeWithHeight(AVLTree higherTree, int smallerTreeHeight, boolean largerIsBigger){
		IAVLNode node = higherTree.getRoot();
		if  (largerIsBigger){
			while (node.getHeight()-smallerTreeHeight>1){
				node = node.getLeft();
			}
		}
		else {
			while (node.getHeight() - smallerTreeHeight > 1) {
				node = node.getRight();
			}
		}
   		return node;
	}

	/**
	   * public interface IAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IAVLNode extends TreePrinter.PrintableNode {
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
			this.left.setParent(this);
			this.right = new AVLNode();
			this.right.setParent(this);
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

	   @Override
	   public String getText() {
		   String str;
		   if (this.isRealNode()) {
			   if (this.getParent() == null) {
				   str = "[" + String.valueOf(this.getKey()) + " ,h-" + String.valueOf(this.getHeight()) +"]";
			   } else {
				   str = "[" + String.valueOf(this.getKey()) + " ,h-" + String.valueOf(this.getHeight()) + ",p-" + String.valueOf(this.getParent().getKey()) + "]";
			   }
			   return str;
		   }
		   else{
				   return "X";
			   }
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
		public int getBalanceFactor(){
	   	if (!isRealNode()){
	   		return 0;
		}
	   	return this.left.getHeight() - this.right.getHeight();
		}
  }
}

  

