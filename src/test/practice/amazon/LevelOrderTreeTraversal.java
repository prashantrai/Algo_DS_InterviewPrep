package test.practice.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelOrderTreeTraversal {

	public static void main(String[] args) {
		TreeNode tree =  new TreeNode(12);
		tree.insertInOrder(7);
		tree.insertInOrder(8);
		tree.insertInOrder(6);
		tree.insertInOrder(4);
		tree.insertInOrder(16);
		tree.insertInOrder(13);
		tree.insertInOrder(15);
		tree.insertInOrder(17);
		tree.insertInOrder(18);
		
		printLeverOrder(tree);
		
		TreeNode tree2 =  new TreeNode(1);
		tree2.insertInOrder(2);
		List<List<Integer>> result  = levelOrder(tree);
		System.out.println("\nResult:: "+result);
		
		result  = levelOrder2(tree);
		System.out.println("Result:: "+result);

	}
	
	//--BFS approach to traverse level by level
	public static void printLeverOrder (TreeNode root) {
		
		if(root == null) return;
		
		LinkedList<TreeNode> q = new LinkedList<TreeNode>();
		q.add(root);
		
		while (!q.isEmpty()) {
			
			TreeNode node = q.removeFirst();
			
			if(node != null) {
				//--print current node
				System.out.print(node.data + " ");
				
				//--add left and right children to queue
				q.addLast(node.left);
				q.addLast(node.right);
			}
			
		}
	}
	
	
	//--Return list for each level - bfs solution 
	public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) 
        	return res;
        
        
        LinkedList<TreeNode> q = new LinkedList<>();
        q.addLast(root);
        
        while (!q.isEmpty()) {
            int size = q.size(); //--size has to be calulated before as we need the currnet size to iterate through the for loop. If we calculate in for loop then it will always have an updated queue size and  loop will not exit wen needed
            List<Integer> list = new ArrayList<>();
            for(int i=0; i<size; i++) {
            	TreeNode curr = q.removeFirst();
            	if(curr != null) {
            		
            		list.add(curr.data);
            		
            		if(curr.left != null) 
            			q.addLast(curr.left);
            		if(curr.right != null) 
            			q.addLast(curr.right);
            		
            	}
            	
            }
            res.add(list);

        }
        return res;
    }
	
	public static List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        bfs(root,res);
        return res;
    }

    private static void bfs(TreeNode node, List<List<Integer>> res){
        if(null == node){
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()){
            int size = queue.size();
            List<Integer> list = new ArrayList<>(size);
            for(int i = 0; i < size; i++){
                TreeNode tmp = queue.poll();
                list.add(tmp.data);
                if(null != tmp.left){
                    queue.offer(tmp.left);
                }
                if(null != tmp.right){
                    queue.offer(tmp.right);
                }
            }
            res.add(list);
        }
    }

}
