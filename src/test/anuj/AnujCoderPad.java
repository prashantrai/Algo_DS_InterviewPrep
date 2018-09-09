package test.anuj;

/**
Find shortest unique prefix to represent each word in the list.

Example:

Input: [zebra, dog, duck, dove]
Output: {z, dog, du, dov}
where we can see that
zebra = z
dog = dog
duck = du
dove = dov
NOTE : Assume that no word is prefix of another. In other words, the representation is always possible.

thought of sharing my solution with you..


package com.ge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Prefix {
  public static void main(String[] args) {
    ArrayList<String> a = new ArrayList<>(Arrays.asList("zebra", "dog", "duck", "dot"));
    ArrayList<String> prefixValues = prefix(a);
    System.out.println(prefixValues);
  }

  public static ArrayList<String> prefix(ArrayList<String> a) {
    Trie trie = new Trie(a);
    StringBuffer buffer = new StringBuffer("");
    StringBuffer bufferForLast = new StringBuffer("");
    LinkedHashMap<String, String> listOfPrefixes = new LinkedHashMap<>();
    for (int i = 0; i < a.size(); i++) {
      listOfPrefixes.put(a.get(i), null);
    }
    getAllStringPrefixes(trie.root, buffer, bufferForLast, listOfPrefixes);
    return new ArrayList(listOfPrefixes.values());
  }

  private static void getAllStringPrefixes(Node node, StringBuffer buffer, StringBuffer bufferForLast,
      LinkedHashMap<String, String> listOfPrefixes) {
    HashMap<Character, Node> children = node.children;
    if (children.size() > 1) {
      //this is the point where you grow the buffer.
      for (Character charValues : children.keySet()) {
        StringBuffer oldBufferValue = new StringBuffer(buffer);
        buffer.append(charValues);
        bufferForLast = new StringBuffer(buffer);
        getAllStringPrefixes(children.get(charValues), buffer, bufferForLast, listOfPrefixes);
        buffer = oldBufferValue;
      }
    } else if (children.size() == 1) {
      Node currentNode = children.values().iterator().next();
      buffer.append(currentNode.value);
      getAllStringPrefixes(currentNode, buffer, bufferForLast, listOfPrefixes);
    } else if (children.size() == 0) {
      listOfPrefixes.put(buffer.toString(), bufferForLast.toString());
    }
  }

  private static class Trie {
    Node root;

    public Trie(ArrayList<String> a) {
      root = new Node();
      for (int i = 0; i < a.size(); i++) {
        buildTrie(root, a.get(i));
      }
    }

    private static void buildTrie(Node root, String string) {
      Node currentNode = root;
      for (int i = 0; i < string.length(); i++) {
        if (currentNode.children.containsKey(string.charAt(i))) {
          currentNode = currentNode.children.get(string.charAt(i));
        } else {
          Node node = new Node(string.charAt(i));
          currentNode.children.put(string.charAt(i), node);
          currentNode = node;
        }
      }
    }

    @Override
    public String toString() {
      return "Trie{" + "root=" + root + '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Trie trie = (Trie)o;
      return Objects.equals(root, trie.root);
    }

    @Override
    public int hashCode() {
      return Objects.hash(root);
    }
  }

  private static class Node {
    char value;
    HashMap<Character, Node> children;

    public Node() {
      children = new HashMap<>();
    }

    public Node(char value) {
      this.value = value;
      children = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Node node = (Node)o;
      return value == node.value && Objects.equals(children, node.children);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value, children);
    }

    @Override
    public String toString() {
      return "Node{" + "value=" + value + ", children=" + children + '}';
    }
  }
}

 * */



/**
 ARRANGE2 :: 
	
	You are given a sequence of black and white horses, and a set of K stables numbered 1 to K. 
	You have to accommodate the horses into the stables in such a way that the following conditions are satisfied:
	
	You fill the horses into the stables preserving the relative order of horses. For instance, 
	you cannot put horse 1 into stable 2 and horse 2 into stable 1. 
	You have to preserve the ordering of the horses.
	
	No stable should be empty and no horse should be left unaccommodated.
	
	Take the product (number of white horses * number of black horses) for each stable and take the sum of all these products. 
	This value should be the minimum among all possible accommodation arrangements
	
	
	Example:
	
	Input: {WWWB} , K = 2
	Output: 0
	
	Explanation:
		We have 3 choices {W, WWB}, {WW, WB}, {WWW, B}
		for first choice we will get 1*0 + 2*1 = 2.
		for second choice we will get 2*0 + 1*1 = 1.
		for third choice we will get 3*0 + 0*1 = 0.
		
		Of the 3 choices, the third choice is the best option. 
		
		If a solution is not possible, then return -1 
	
 * */




public class AnujCoderPad {

	//List<Stable> stables; //--could be an array 2 of size K
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int K = 2;
		Stable[] stables = new Stable[K];
		

	}

}

class Stable {
	
	//List<String> white;
	//List<String> black;
	
}
