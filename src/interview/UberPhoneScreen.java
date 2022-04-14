package interview;

import java.util.ArrayList;
import java.util.HashMap;

public class UberPhoneScreen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

// obj1 = {'id': 1, 'name': 'Joe', 'refs': [20, 21]}
// obj2 = {'id': 20, 'name': 'Harvard', 'refs': [80]}
// obj3 = {'id': 21, 'name': 'MIT', 'refs': [80]}
// obj4 = {'id': 80, 'name': 'Boston', 'refs': []}
// obj = obj1
// objs = [obj1, obj2, obj3, obj4]
// obj1 == {'id': 1, 'name': 'Joe', 'refs': [20, 21], 'refs2': [obj2, obj3]}
//
/*
 * expected = { 
 * 				'id': 1, 
 * 				'name': 'Joe', 
 *  			'refs2': 
 *  				[ 
 *  					{ 	'id': 20, 
 *  						'name': 'Harvard', 
 *  						'refs2': 
 *  								[ 
 *  									{ 
 *  										'id': 80, 
 *  										'name': 'Boston', 
 *  										'refs2': [] 
 *  									} 
 *  								] 
 *  					}, 
 *  					{
 * 							'id': 21, 
 * 							'name': 'MIT', 
 * 							'refs2': 
 * 									[ 
 * 										{ 
 * 											'id': 80, 
 * 											'name': 'Boston', 
 * 											'refs2': []
 * 										} 
 * 									] 
 * 						} 
 * 					] 
 * 				}
 */

class Question1 {

	class Node {
	     int id;
	     String name;
	     ArrayList<Integer> refs;
	     ArrayList<Node> refs2;
	     
	     public Node(int id, String name, ArrayList<Integer> refs) {
	         this.id = id;
	         this.name = name;
	         this.refs = refs;
	     }
	}
 

	 public Node dereference(Node obj, ArrayList<Node> objs) {
	     
	     if(obj == null || objs == null) return null;
	     
	     ArrayList<Integer> refs_1 = obj.refs;
	     ArrayList<Node> refs_2 = obj.refs2;
	     
	     HashMap<Integer, Node> refsMap = new HashMap<Integer, Node>();
	     
	     for(Node node : objs) {
	         refsMap.put(node.id, node);        
	     }
	     
	     Node node = getMappedObj (obj, refsMap);
	
	     return node;
	 }
	 
	 
	 public Node getMappedObj(Node obj, HashMap<Integer, Node> map) {
	     
	     if(obj == null) return null;
	     
	     Node node = null;
	     
	     for(Integer id : obj.refs) {
	         node = getMappedObj(map.get(id), map);
	     }
	     
	     if(node != null) { 
	         for(Integer id : obj.refs)   {
	             obj.refs2.add(refsMap.get(n));        
	         }
	     }
	     node.refs_2 = refs_2;
	     return node;        
	 }
}