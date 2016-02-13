package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
		this.size = 0;
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		if(word.length() > 0){
			word = word.toLowerCase();
			TrieNode n = root;
			for(int i = 0; i < word.length(); i++){
				Character c = word.charAt(i);
				if(n.getValidNextCharacters().contains(c)){
					n = n.getChild(c);
				}
				else{
					n = n.insert(c);		
				}
			}
			if(!n.endsWord()){
				n.setEndsWord(true);
				this.size++;
				//this.printTree();
				return true;
			}
		}
	    return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return this.size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		s = s.toLowerCase();
		if(s.length()>0){
			TrieNode n = root;
			for(int i = 0; i < s.length(); i++){
				char c = s.charAt(i);
				if(!n.getValidNextCharacters().contains(c))
					return false;
				n = n.getChild(c);
			}
			return true;
		}
		return false;
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 Queue<TrieNode> q = new LinkedList<TrieNode>();
    	 List<String> lst = new ArrayList<String>();
    	 TrieNode n = this.root;
    	 prefix = prefix.toLowerCase();
    	 //System.out.println("Prfex: " + prefix);
    	 if(this.size()<= 0 || numCompletions <= 0 || (!this.isWord(prefix) && prefix.length() > 0))
    		 return lst;
    	 for(int i = 0; i < prefix.length(); i++){
    		 char c = prefix.charAt(i);
    		 n = n.getChild(c);
    	 }
    	 q.add(n);
    	 //System.out.println("term: " + n.getText() + " Pre: " + prefix);
    	 int count = 0;
    	 while(!q.isEmpty() && count < numCompletions){
    		 n = q.remove();
    		 if(n.endsWord()){
    			 lst.add(n.getText());
    			 count++;
    			 System.out.println("word: " + n.getText());
    		 }
			 for(Character k : n.getValidNextCharacters()){
				 q.add(n.getChild(k));
				 System.out.println("add: "+ n.getChild(k).getText());
				 
			 }
    		 
    	 }
         return lst;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}