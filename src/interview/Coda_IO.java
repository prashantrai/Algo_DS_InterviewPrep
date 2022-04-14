package interview;



import java.io.*;
import java.util.*;


//https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

// Given a node definition 
// interface TreeNode {
//   left?: TreeNode;
//   right?: TreeNode;
//   value: string;
// }

// Write a function that takes a root node as input, and returns a string with the values of the tree 
// in depth first pre-order (root, then left subtree, then right subtree), separated by spaces


// Input 1
//         1
//         /\
//        2  5
//       /\  /\
//      3 4  6 7
     
// Input 2
//         1
//        / \
//       2   5
//      /   /
//     3   6
//    /   /
//   4   7

// Input 3
//       1
//      / \
//    401 88
//    /
//  349
//  /
// 90

// Output 1 -> "1 2 3 4 5 6 7"
// Output 2 -> "1 2 3 4 5 6 7"
// Output 3 -> "1 401 349 90 88"

// Part 2
// Now that we have the node traversal in place, let's tweak the return value. 
// When visiting a node in the tree, the string should receive a number of dashes equal to the depth, 
// followed by the value of that node.

// Output 1 -> "1 -2 --3 --4 -5 --6 --7"
// Output 2 -> "1 -2 --3 ---4 -5 --6 ---7"
// Output 3 -> "1 -401 --349 ---90 -88"

// Part 3
// Now let's reverse the inputs. Make a function that takes as input a string,
// in the format given by your previous method, and returns the nodes that make up that tree. 
// (i.e., the input format for part 2)

// Note: To remove ambiguity in the string format, when constructing a node at a particular depth, 
// if the parent does not have a child yet, make it the left child, otherwise make it the right child.

// You can assume a valid tree is represented by the input, and you can use space as the delimiter


// https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/


class Coda_IO {
  
  static StringBuilder sb = new StringBuilder();
  
  public static class TreeNode {
    String value;
    TreeNode left;
    TreeNode right;
    

    public TreeNode(String value) {
        this.value = value;
    }

    public TreeNode(String value, TreeNode left) {
        this.value = value;
        this.left = left;
    }

    public TreeNode(String value, TreeNode left, TreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
    
    public void preOrder(TreeNode node, int depth) {
      
      if(node != null) {
        
        visit(node, depth);
        preOrder(node.left , depth+1);
        preOrder(node.right, depth+1);

      }
    }

    private void visit(TreeNode node, int depth) {
      for(int i=0; i<depth; i++) {    
      
        sb.append("-");
      }
      sb.append(node.value);
      sb.append(" ");
    }
  }

  public static void main(String[] args) {
    TreeNode tree1 = new TreeNode("1", new TreeNode("2", new TreeNode("3"), new TreeNode("4")), new TreeNode("5", new TreeNode("6"), new TreeNode("7")));

    TreeNode tree2 = new TreeNode("1", new TreeNode("2", new TreeNode("3", new TreeNode("4"))), new TreeNode("5", new TreeNode("6", new TreeNode("7"))));

    TreeNode tree3 = new TreeNode("1", new TreeNode("401", new TreeNode("349", new TreeNode("90"))), new TreeNode("88"));

    // For part 3
    // String input1 = "1 -2 --3 --4 -5 --6 --7";
    // String input2 = "1 -2 --3 ---4 -5 --6 ---7";
    // String input3 = "1 -401 --349 ---90 -88";
    
    tree1.preOrder(tree1, 0);
    System.out.println("1: "+sb);
    
    //sb.
    tree2.preOrder(tree2, 0);
    System.out.println("2: "+sb);
    
    tree3.preOrder(tree3 , 0);
    System.out.println("3: "+sb);
    
    
  }
  
  
  public static TreeNode buildTree (String s) {
  
    if(s == null || s.length() == 0) return null;
    
    String[] arr = s.split(" ");
    
    Queue<TreeNode> q = new LinkedList<>();
    TreeNode root = new TreeNode(arr[0]);
    
    q.offer(root);
    
    int idx = 1;
    
    // "1 -2 --3 ---4 -5 --6 ---7"
    
    while(!q.isEmpty() && idx < arr.length) {
      
      TreeNode node = q.poll();
      String str = removeDash(arr[idx]);
      
      // Left Node
      int countDashLeft = countDash(str);
      TreeNode leftNode = new TreeNode(str);
      node.left(leftNode);
      q.offer(leftNode);
      
      idx++;
      
      if(idx > arr.length) break;
      
      str = arr[idx]; //next val
      
      // Right Ndoe
      int countDashRight = countDash(str);
      if(countDashRight == countDashLeft) { // only if saame level node
        TreeNode rightNode = new TreeNode(str);
        node.right(rightNode);
        q.offer(rightNode);
      }
      
      idx++;      
    
    }
    
    return root;
  }
  
  
  public static int countDash(String s) {
    
    int count = 0;
    
    for (int i=0; i<s.length(); i++) {
      if(s.charAt(i) == '-') 
        count++;
    }
    
    return count;
  }
   
  private removeDash(String s) {
    
    if(s.charAt(0) != '-') return;
    
    int i=0;
    while(s.charAt(i) == '-') {
      i++;
    }
    
    return s.substring(i-1, s.length());
  }
  
  
}
