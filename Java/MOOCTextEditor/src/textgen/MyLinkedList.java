package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		this.size = 0;
		head = new LLNode<E>();
		tail = new LLNode<E>();
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		LLNode<E> node = new LLNode<E>(element);
		node.prev = tail.prev;
		node.next = node.prev.next;
		node.prev.next = node;
		node.next.prev = node;
		this.size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if(index > this.size -1 || index < 0)
			throw new IndexOutOfBoundsException("The requested index is out of bound.");
		LLNode<E> p = head.next;
		int inter = 0;
		while(inter < index){
			p = p.next;
			inter++;
		}
		return p.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if(index < 0 || index > this.size)
			throw new IndexOutOfBoundsException("The insert index is out of bounds.");
		LLNode<E> pFront = head;
		LLNode<E> node = new LLNode<E>(element);
		
		for(int i = 0; i < index; i++){
			pFront = pFront.next;
		}		
		node.next = pFront.next;
		node.prev = node.next.prev;
		pFront.next = node;
		pFront.next.prev = node;
		this.size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if(index < 0 || index > this.size)
			throw new IndexOutOfBoundsException("The insert index is out of bounds.");
		LLNode<E> p = head.next;
		for(int i =0; i < index; i++)
			p = p.next;
		p.prev.next = p.next;
		p.next.prev = p.prev;
		this.size--;
		return p.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if(index < 0 || index > this.size-1)
			throw new IndexOutOfBoundsException("The insert index is out of bounds.");
		LLNode<E> p = head.next;
		for(int i = 0; i < index; i++){
			p = p.next;
		}
		E old = p.data;
		p.data = element;
		return old;
	} 
	
	public String toString(){
		String list = "";
		if(this.size == 0)
			list += "Empty Linked List.";
		else{
			LLNode<E> p = head.next;
			for(int i = 0; i < this.size; i++){
				list += p.data;
				if(i < this.size -1)
					list += ", ";
				p = p.next;
			}
		}
		return list;
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor
	
	// Add another constructor
	public LLNode(){
		this.prev = null;
		this.next = null;
		this.data = null;
	}
	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
