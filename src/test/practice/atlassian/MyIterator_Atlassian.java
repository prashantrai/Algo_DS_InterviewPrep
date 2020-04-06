package test.practice.atlassian;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class MyIterator_Atlassian {
    public static void main(String args[] ) throws Exception {
        
        List<Integer> list = Arrays.asList(1,2,3,4);
        Iterator itr = list.iterator();
        
        MyIterator myItr = new MyIterator(itr);
        
        for(int i : list) {
            System.out.println("hasNext: "+ myItr.hasNext());
            System.out.println("Peek: "+ myItr.peek());
            System.out.println("Next: "+ myItr.next());
        }
        
        System.out.println(">>hasNext: "+ myItr.hasNext());
        System.out.println(">>Peek: "+ myItr.peek());
        System.out.println(">>Next: "+ myItr.next());
        
        System.out.println(">>hasNext: "+ myItr.hasNext());
    }
}


class MyIterator implements Iterator {
    
    private Iterator itr;
    private Integer data = null;
    boolean noSuchElement = false;
    
    public MyIterator(Iterator itr) {
        this.itr = itr;
        moveItrator();
    }
    
    @Override
    public boolean hasNext() {
        //return !noSuchElement;
        return data != null;
    }
    
    @Override
    public Integer next() {        
       Integer res = data;
       moveItrator();
       return res;
    } 

    public Integer peek() {
        return data;
    }
    
    private void moveItrator() {
        if (itr.hasNext()) {
            data = (Integer) itr.next();
        } else {
            noSuchElement = true;
            data = null;        
        }
    }
    
    
 }