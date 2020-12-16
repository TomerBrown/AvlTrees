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

	//Fields
	private IAVLNode root;
	private int size;
	private IAVLNode min;
	private IAVLNode max;

	//Constructor for new empty AVLTree
	public AVLTree(){
		this.root = new AVLNode();
		this.size = 0;
		this.min = null;
		this.max = null;
	}
	// Sets the root of a tree to a given node
	private void setRoot(IAVLNode node){
		this.root = node;
		IAVLNode parent = node.getParent();
		if (parent != null) {
			if(parent.getLeft() == node) {
				parent.setLeft(new AVLNode());
			}
			else {
				parent.setRight(new AVLNode());
			}
		}

		node.setParent(null);
	}

  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty() {
    return !this.getRoot().isRealNode();
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */

 // given key k and node recrusivly searches the node with key equals to k.
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
	//given a node, find the minimum value of the subtree that node is the root of.
 	private IAVLNode minNode(IAVLNode node){
 	while (node.isRealNode()){
 		node = node.getLeft();
	}
 	return node.getParent();
	}
	public IAVLNode maxNode(IAVLNode node){
		while (node.isRealNode()){
			node = node.getRight();
		}
		return node.getParent();
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
  private void rightRotation (IAVLNode y){
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
	//Performs left Rotation
  private void leftRotation(IAVLNode y){
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
  // given a node y calculate his height base on his 2 children as in formula.
  private static int calcHeight (IAVLNode y){
  	return Math.max(y.getLeft().getHeight(),y.getRight().getHeight())+1;
  }
  //Performs right then left Rotation
  private void rightThenLeftRotation (IAVLNode z){
  	rightRotation(z.getRight());
  	leftRotation(z);
  }
	//Performs right then left Rotation
	private void leftThenRightRotation (IAVLNode z){
  		leftRotation(z.getLeft());
		rightRotation(z);
	}
	//calculate balance factor based on the two children of a given node.
  private static int getBalanceFactor (IAVLNode node){
  	return node.getLeft().getHeight()- node.getRight().getHeight();
  }

	//perform insert of a node given its key and info
	// an envelope function of insert_rec that performs the actual insertion.
   public int insert(int k, String i) {
   	IAVLNode node = new AVLNode(k,i);
   	if (this.empty()){
		updateMinMaxInsert(node);
   		this.root = node;
		this.size++;
   	}
   	else {
		if (insert_rec(this.root, node)){
			this.size++;
			updateMinMaxInsert(node);
		}
		else {
			return -1;}

   	}

   	return keepBalanced(node);
	}

	// insert_rec is recursive function that makes insert. return true if inserted new node and false if node already exsits and nothing has been done
	public boolean insert_rec (IAVLNode node , IAVLNode to_insert){
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
   			return insert_rec(node.getRight() , to_insert);
		}
   		else if (node.getKey()==to_insert.getKey()){
   			return false;
		}
   		else{
   			return insert_rec(node.getLeft(),to_insert);
		}
	}
	return true;
	}

	//updates the fields min and max for insertion of new node
	private void updateMinMaxInsert(IAVLNode node){
  		// if the first element should be the min and the max
  		if (this.empty()){
  			this.min = node;
  			this.max = node;
		}
  		int key = node.getKey();
  		if (key<this.min.getKey()){ //if key smaller than minimum -> update minimum
  			min = node;
		}
  		if (key> this.max.getKey()){ //if key larger than minimum -> update maximum
  			max = node;
		}

	}

	// determines thae which operation should be used on a specific node.
	//returns:
		// 1 - if node is a leaf
		// 2 - if node has one child
		// 3 -  if node has 2 children
	private int nodeKind(IAVLNode node) {
		if (node.getHeight() == 0) { //node is a leaf
			return 1;
		} else if (!node.getLeft().isRealNode() || !node.getRight().isRealNode()) {// node has one child
			return 2;
		} else { //node has 2 children
			return 3;
		}
	}

	private void calcHeightLeaf(IAVLNode node){
		IAVLNode temp = node;
		while(temp.getKey() != root.getKey()) {
			temp.setHeight(calcHeight(temp));
			temp = temp.getParent();
		}
		temp.setHeight(calcHeight(temp));
	}
	private static boolean isLeaf (IAVLNode node){
		return (!(node.getRight().isRealNode() || node .getLeft().isRealNode()));
	}

	//return the predeseccor of a node.
	// pre: node is in tree
	// if node == min -> returns null
	private IAVLNode predecessor (IAVLNode node){
		if (node == this.min) return null;
		IAVLNode temp = node .getLeft();
		if (temp.isRealNode()) {
			while (temp.getRight().isRealNode()) {
				temp = temp.getRight();
			}
		}
		else{
			temp = node.getParent();
			while (temp.getLeft() == node){
				node = temp;
				temp = temp.getParent();
			}

		}
		return temp;

	}
	//return the Successor of a node.
	// pre: node is in tree
	// if node == max -> returns null
	private IAVLNode successor(IAVLNode node){
		if (node == this.max) return null;
   		IAVLNode temp = node.getRight();
   		if(temp.isRealNode()){
   			while (temp.getLeft().isRealNode()){
   				temp = temp.getLeft();
			}
		}
   		else{
   			temp = node.getParent();
   			if (temp!= null){
				while(temp.getRight()== node){
					node = temp;
					temp = temp.getParent();
				}
			}
		}
   		return temp;
	}
	// updates min and max of tree before any deletion occoured!
	// pre: node is in tree
	private void updateMinMaxDelete(IAVLNode node){
		if (node == max) { //if no predecessor to max => only one node in tree => the tree becomes empty after delete => should set min and max to null
			max = predecessor(max);
		}
		if (node == min){ //if no successor to min => only one node in tree => the tree becomes empty after delete => should set min and max to null
			min = successor(min);
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
	   public int delete(int k) {
	   		IAVLNode node = search_node(k, this.root);
		   	if(node == null) {
			   return -1;
		   	}
		   	updateMinMaxDelete(node);
		   	this.size--;
		   	if(node.getKey() == this.getRoot().getKey()) {
		   		return deleteRoot();
		   	}
		   	int kind = nodeKind(node);
		   	if (kind == 1) {//node is a leaf
				IAVLNode parent = node.getParent();
				if (parent.getLeft().getKey() == node.getKey()) {
					parent.setLeft(new AVLNode());
				} else {
					parent.setRight(new AVLNode());
				}
				calcHeightLeaf(parent);
				return keepBalanced(parent);
			}
		   	if(kind == 2) { //node has 1 child
				IAVLNode parent = node.getParent();
				if(parent.getRight().getKey() == node.getKey()) {
					if(node.getLeft().isRealNode()) {
						IAVLNode temp = node.getLeft();
						parent.setRight(temp);
						temp.setParent(parent);
					}
					else {
						IAVLNode temp = node.getRight();
						parent.setRight(temp);
						temp.setParent(parent);
					}
				}
				else {
					if(node.getLeft().isRealNode()) {
						IAVLNode temp = node.getLeft();
						parent.setLeft(temp);
						temp.setParent(parent);
					}
					else {
						IAVLNode temp = node.getRight();
						parent.setLeft(temp);
						temp.setParent(parent);
					}
				}
				calcHeightLeaf(parent);		
				return keepBalanced(parent);
		   	}
		   	if (kind == 3){// node has 2 children
		   		IAVLNode successor = successor(node);
		   		IAVLNode par = node.getParent();
		   		IAVLNode temp = successor;
		   		if(successor.getKey() == node.getRight().getKey()) {
		   			if(par.getRight().getKey() == node.getKey()) {
		   				par.setRight(successor);
		   				successor.setParent(par);
		   				successor.setLeft(node.getLeft());
		   				node.getLeft().setParent(successor);
		   			}
		   			else {
		   				par.setLeft(successor);
		   				successor.setParent(par);
		   				successor.setLeft(node.getLeft());
		   				node.getLeft().setParent(successor);
		   			}
		   			calcHeightLeaf(successor);
		   			return keepBalanced(successor);
		   		}
		   		else {
		   			if(par.getRight().getKey() == node.getKey()) {
		   				   
						   successor.getParent().setLeft(successor.getRight());
						   successor.getRight().setParent(successor.getParent());
						   successor.setParent(par);
						   par.setRight(successor);
						   successor.setLeft(node.getLeft());
						   node.getLeft().setParent(successor);
						   successor.setRight(node.getRight());
						   node.getRight().setParent(successor);
		   			}
		   			else {
						   successor.getParent().setLeft(successor.getRight());
						   successor.getRight().setParent(successor.getParent());
						   successor.setParent(par);
						   par.setLeft(successor);
						   successor.setLeft(node.getLeft());
						   node.getLeft().setParent(successor);
						   successor.setRight(node.getRight());
						   node.getRight().setParent(successor);
		   			}
		   			if(temp.getRight().isRealNode()) {
		   				IAVLNode leaf = temp.getRight();
		   				calcHeightLeaf(leaf);
			   			return keepBalanced(leaf);
		   			}
		   			else {
		   				IAVLNode leaf = temp.getParent();
		   				calcHeightLeaf(leaf);
		   				return keepBalanced(leaf);
		   				
		   			}
		   			
		   		}
		   	}
		   	return 0;
	   }
	   
	   public int deleteRoot() {
	   		IAVLNode node = this.root;
		   	int kind = nodeKind(node);
		   	if (kind == 1) {//node is a leaf
				setRoot(new AVLNode());
			}
		   	if(kind == 2) { //node has 1 child
				if(node.getRight().isRealNode()) {
					setRoot(node.getRight());
					node.getRight().setParent(null);
				}
				else {
					setRoot(node.getLeft());
					node.getLeft().setParent(null);
				}
		   	}
		   	if (kind == 3){// node has 2 children
		   		IAVLNode successor = successor(node);
		   		if(successor.getKey() == node.getRight().getKey()) {
		   			successor.setLeft(node.getLeft());
		   			successor.setParent(null);
		   			setRoot(successor);
		   			calcHeightLeaf(successor);
		   			return keepBalanced(successor);
		   		}
		   		else {
		   			IAVLNode parent = successor.getParent();
		   			parent.setLeft(successor.getRight());
		   			node.getRight().setParent(successor);
		   			successor.setRight(node.getRight());
		   			successor.setLeft(node.getLeft());
		   			successor.setParent(null);
		   			setRoot(successor);
		   			calcHeightLeaf(parent);
		   			return keepBalanced(parent);
		   		}
		   	}
		   	return 0;
	   }
	
		// goes up tree and fixes problems and performs rotation if necessary.
	   private int keepBalanced(IAVLNode node) {
		   int cnt = 0;
		   while(node!=null) {
		   	
	   
			   int bf = getBalanceFactor(node);
			   node.setHeight(calcHeight(node));
			   if(bf == -2) {
				   int rightBF = getBalanceFactor(node.getRight());
				   if (rightBF < 1) {
					   leftRotation(node);
					   cnt++;
				   }
				   else {
					   rightThenLeftRotation(node);
					   cnt = cnt +2;
				   }
			   }
			   if (bf == 2) {
				   int leftBF = getBalanceFactor(node.getLeft());
				   if(leftBF > -1) {
					   rightRotation(node);
					   cnt++;
				   }
				   else {
					   leftThenRightRotation(node);
					   cnt = cnt +2;
				   }
			   }
			   node = node.getParent();
		   }
		   return cnt;
	   }
   
   private void setHeightAfterDeleteLeaf(IAVLNode node) {
	   IAVLNode temp = node;
	   IAVLNode parent = node.getParent();
	   if(node.getKey()>parent.getKey()) {
		   temp = parent.getRight();
	   }
	   else {
		   temp = parent.getLeft();
	   }
	   while(temp.getKey() != root.getKey()) {
		   temp.setHeight(calcHeight(node));
		   temp = temp.getParent();
	   }
	   temp.setHeight(calcHeight(temp));
	   
   }
   

   private IAVLNode nextNode(IAVLNode node) {
	   if(node.getRight().isRealNode()) {
		   node = node.getRight();
		   while(node.getLeft().isRealNode()) {
			   node = node.getLeft();
		   }
		   return node;
	   }
	   else {
		   IAVLNode temp = node.getParent();
		   while(temp.getKey() < node.getKey()) {
			   temp = temp.getParent(); 
			   }
		   return temp;
		   }
	   }
	   

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
	   if(this.min == null) return null;
	   return this.min.getValue(); // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
	   if(this.max== null) return null;
	   return this.max.getValue(); // to be replaced by student code
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
	// return an ordered array of all keys in tree.
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
	   IAVLNode node = search_node(x, this.root);
	   IAVLNode pred = this.predecessor(node);
	   IAVLNode suc = this.successor(node);
	   AVLTree smaller =createSubtree(node.getLeft());
	   AVLTree bigger = createSubtree(node.getRight());
	   updateMinMaxSplit(smaller,bigger ,node,pred,suc);


	   while(node != this.getRoot()) {
		   IAVLNode parent = node.getParent();
		   IAVLNode temp = new AVLNode(parent.getKey(), parent.getValue());	
		   if(parent.getRight().getKey()  == node.getKey()) {
			   AVLTree r = createSubtree(parent.getLeft());		   
			   smaller.join(temp, r);
		   }
		   else {
			   AVLTree r = createSubtree(parent.getRight());
			   bigger.join(temp, r);
		   }
		   node = parent;
	   }
	   smaller.delete(x);
	   bigger.delete(x);
	   AVLTree[] array = new AVLTree[2];
	   array[0] = smaller;
	   array[1] = bigger;
	   return array;
   }
   // given a node creates a subtree with node as it's root.
	// after the action this tree isn't AVL tree!
  private AVLTree createSubtree(IAVLNode node) {
	  AVLTree tree = new AVLTree();
	  if (!node.isRealNode()) return tree;
	  tree.setRoot(node);
	  tree.min = tree.minNode(node);
	  tree.max = tree.maxNode(node);
	  return tree;
  }

  // @pre: keys(smaller)< x < key(larger)
  private  void updateMinMaxSplit(AVLTree smaller, AVLTree larger , IAVLNode x,IAVLNode pred,IAVLNode suc){
   	smaller.min = this.min;
   	smaller.max = pred;
   	larger.min = suc;
   	larger.max = this.max;
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
	   this.size += t.size()+1;
	   int complex = this.calcComplexity(t);
   		//Initial check if one or both is empty
	   if (this.empty()&&t.empty()){
	   	this.insert(x.getKey(),x.getValue());
	   	return complex;
	   }
	   if  (this.empty()){ //only this is empty
	   	t.insert(x.getKey(),x.getValue());
	   	this.root = t.getRoot();
	   	this.min = t.min;
	   	this.max=t.max;
	   	return complex;
	   }
	   if (t.empty()){ //only t is empty
		   this.insert(x.getKey(),x.getValue());
		   return complex;
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
	   	updateMinMaxJoin(higherTree,shorterTree,higher_is_larger); // set tree min and max accordingly
   		int largerTreeHeight = higherTree.getRoot().getHeight();
	   	int smallerTreeHeight = shorterTree.getRoot().getHeight();
	   	if (largerTreeHeight-smallerTreeHeight<=1) {//that means we can simply merge
			simpleMerge(t, x);
			return complex;
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
	   keepBalanced(x);

	   return complex;

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

	//sets the pointer to the min and max new of the tree after join.
	private void updateMinMaxJoin (AVLTree higher , AVLTree smaller , boolean higherIsLarger) {
		if (higherIsLarger) {
			this.min = smaller.min;
			this.max = higher.max;
		} else {
			this.min = higher.min;
			this.max = smaller.max;
		}
	}




	/**
	   * public interface IAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IAVLNode {
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
		
		public String getInfo() {
			return this.info;
		}
		
		public void setInfo(String info) {
			this.info = info;
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
		public int getBalanceFactor(){
	   	if (!isRealNode()){
	   		return 0;
		}
	   	return this.left.getHeight() - this.right.getHeight();
		}
  }
}

  

