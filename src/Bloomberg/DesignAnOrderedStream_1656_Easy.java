package Bloomberg;

import java.util.ArrayList;
import java.util.List;

public class DesignAnOrderedStream_1656_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	class OrderedStream {
	    int ptr;
	    String[] res;
	    
	    public OrderedStream(int n) {
	        res = new String[n];
	    }
	    
	    public List<String> insert(int idKey, String value) {
	        
	        List<String> list = new ArrayList<>();
	        res[idKey-1] = value;
	        
	        while(ptr<res.length && res[ptr] != null) {
	            list.add(res[ptr]);
	            ptr++;
	        }
	        
	        return list;
	    }
	}

	/**
	 * Your OrderedStream object will be instantiated and called as such:
	 * OrderedStream obj = new OrderedStream(n);
	 * List<String> param_1 = obj.insert(idKey,value);
	 */

}
