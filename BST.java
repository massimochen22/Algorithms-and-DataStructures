import java.util.ArrayDeque;
import java.util.ArrayList;

/** BST implementation for Dictionary ADT */
class BST<K extends Comparable<K>, E> implements Dictionary<K, E> {
  private BSTNode<K, E> root; // Root of BST
  private int nodecount; // Size of BST

  /** Constructor */
  BST() {
    root = null;
    nodecount = 0;
  }

  /** Reinitialize tree */
  public void clear() {
    root = null;
    nodecount = 0;
  }

  /**
   * Insert a record into the tree.
   * 
   * @param k
   *          Key value of the record.
   * @param e
   *          The record to insert.
   */
  public void insert(K k, E e) {
    root = inserthelp(root, k, e);
    nodecount++;
  }

  /**
   * Remove a record from the tree.
   * 
   * @param k
   *          Key value of record to remove.
   * @return Record removed, or null if there is none.
   */
  public E remove(K k) {
    E temp = findhelp(root, k); // find it
    if (temp != null) {
      root = removehelp(root, k); // remove it
      // System.out.println("called removehelp");
      nodecount--;
    }
    return temp;
  }

  /**
   * Remove/return root node from dictionary.
   * 
   * @return The record removed, null if empty.
   */
  public E removeAny() {
    if (root == null)
      return null;
    E temp = root.element();
    root = removehelp(root, root.key());
    --nodecount;
    return temp;
  }

  /**
   * @return Record with key k, null if none.
   * @param k
   *          The key value to find.
   */
  public E find(K k) {
    return findhelp(root, k);
  }

  /** @return Number of records in dictionary. */
  public int size() {
    return nodecount;
  }

  private E findhelp(BSTNode<K, E> rt, K k) {
    if (rt == null)
      return null;
    if (rt.key().compareTo(k) > 0)
      return findhelp(rt.left(), k);
    else if (rt.key().compareTo(k) == 0)
      return rt.element();
    else
      return findhelp(rt.right(), k);
  }

  private BSTNode<K, E> inserthelp(BSTNode<K, E> rt, K k, E e) {
    if (rt == null)
      return new BSTNode<K, E>(k, e);
    if (rt.key().compareTo(k) > 0)
      rt.setLeft(inserthelp(rt.left(), k, e));
    else
      rt.setRight(inserthelp(rt.right(), k, e));
    return rt;
  }

  private BSTNode<K, E> getmin(BSTNode<K, E> rt) {
    if (rt.left() == null)
      return rt;
    else
      return getmin(rt.left());
  }

  private BSTNode<K, E> deletemin(BSTNode<K, E> rt) {
    if (rt.left() == null)
      return rt.right();

    else {
      rt.setLeft(deletemin(rt.left()));
      return rt;
    }
  }

  /**
   * Remove a node with key value k
   * 
   * @return The tree with the node removed
   */
  private BSTNode<K, E> removehelp(BSTNode<K, E> rt, K k) {
    if (rt == null)
      return null;
    if (rt.key().compareTo(k) > 0)
      rt.setLeft(removehelp(rt.left(), k));
    else if (rt.key().compareTo(k) < 0)
      rt.setRight(removehelp(rt.right(), k));
    else { // Found it, remove it
      if (rt.left() == null)
        return rt.right();
      else if (rt.right() == null)
        return rt.left();
      else { // Two children
        BSTNode<K, E> temp = getmin(rt.right());
        rt.setElement(temp.element());
        rt.setKey(temp.key());
        rt.setRight(deletemin(rt.right()));
      }
    }
    return rt;
  }

  /**
   * Creates a list storing the the nodes in the subtree of a node, ordered
   * according to the inorder traversal of the subtree.
   */

  protected void inorderElements(BSTNode<K, E> v, ArrayList<E> elts) {
    // elts.add(v.element());
    if (v.left() != null)
      inorderElements(v.left(), elts); // recurse on left child
    elts.add(v.element());
    if (v.right() != null)
      inorderElements(v.right(), elts); // recurse on right child
  }
  
  protected void levelorderElements(BSTNode<K, E> v, ArrayDeque<BSTNode<K, E>> dq,ArrayList<E> elts) {
	  
	  dq.addLast(v);
	  while(! dq.isEmpty()) {
		  BSTNode<K, E> next = dq.removeFirst();
		  elts.add(next.element());
		  if (next.left() != null)
			  dq.addLast(next.left());
		  if (next.right() != null)
			  dq.addLast(next.right());
		  
	  }
  }
  protected void inorderKeys(BSTNode<K, E> v, ArrayList<K> elts) {
	    // elts.add(v.element());
	    if (v.left() != null)
	    	inorderKeys(v.left(), elts); // recurse on left child
	    elts.add(v.key());
	    if (v.right() != null)
	      inorderKeys(v.right(), elts); // recurse on right child
	  }

  /** Returns an iterable collection of the tree nodes. */
  public ArrayList<E> values() {
    ArrayList<E> elements = new ArrayList<E>();
    ArrayDeque<BSTNode<K, E>> dq = new ArrayDeque<BSTNode<K, E>>();
    if (size() != 0)
   levelorderElements(root, dq, elements); //assign positions in level order
      //inorderElements(root, elements); // assign positions in order
    	
    return elements;
  }
  
  public ArrayList<K> keyValues() {
	    ArrayList<K> keys = new ArrayList<K>();
	    if (size() != 0)
	   //levelorderElements(root, dq, elements); //assign positions in level order
	    	inorderKeys(root, keys); // assign positions in order
	    	
	    return keys;
	  }
  

  public ArrayList<E> findAll(K k) {
    ArrayList<E> al = new ArrayList<E>();
    findAllhelp(root, k, al);
    return al;
  }

  protected void findAllhelp(BSTNode<K, E> rt, K k, ArrayList<E> a) {
    if (rt == null)
      return;
    if (rt.key().compareTo(k) > 0)
      findAllhelp(rt.left(), k, a);
    else if (rt.key().compareTo(k) == 0) {
      a.add(rt.element());
      findAllhelp(rt.right(), k, a);
    } else
      findAllhelp(rt.right(), k, a);
  }

  /* the following are for solving the exercises in Shaffer, ch. 5 */

  public Iterable<E> range(K a, K b) {
    ArrayList<E> elements = new ArrayList<E>();
    rangehelp(root, elements, a, b);
    return elements;
  }

  protected void rangehelp(BSTNode<K, E> v, ArrayList<E> elts, K a, K b)  {
	    // elts.add(v.element());
	    if (v.left() != null)
	    	rangehelp(v.left(), elts, a, b); // recurse on left child
	    
	    if(!(v.key().compareTo(a) < 0) && !(v.key().compareTo(b) > 0)) {
	    	elts.add(v.element());
	    }
	    if (v.right() != null)
	    	rangehelp(v.right(), elts, a,  b); // recurse on right child
	  }
	  
}