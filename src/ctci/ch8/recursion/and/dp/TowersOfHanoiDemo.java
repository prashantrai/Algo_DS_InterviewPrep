package ctci.ch8.recursion.and.dp;

import java.util.Stack;

public class TowersOfHanoiDemo {

	public static void main(String[] args) {
		
		int n = 3;
		Tower[] towers = new Tower[n];
		
		for(int i=0; i<n; i++) {
			towers[i] = new Tower(i);
		}
		
		Tower t = towers[0];
		for(int i=n-1; i>=0; i--) {
			t.add(i);
		}
		
		//System.out.println("towers[0]:: "+t.disks.peek());
		
		t.moveDisk(n, towers[2], towers[1]);
		
		System.out.println("towers[2]:: "+towers[2]);
	}

}

class Tower {
	
	Stack<Integer> disks;
	private int index;
	
	public Tower(int index) {
		disks = new Stack<Integer>();
		this.index = index;
	}
	
	public int index() { return index; }
	
	public void add(int d) {
		if(!disks.isEmpty() && disks.peek() <= d) {
			System.err.println("Can't push " + d);
		} else {
			disks.push(d);
		}
	}
	
	public void moveTopTo(Tower t) {
		//if(disks.isEmpty()) 
			//return;
		
		int top = disks.pop();
		t.add(top);
	}

	public void moveDisk(int n, Tower destination, Tower buffer) {
		
		if(n > 0) {
			System.out.println("a [n="+n+"]. src: "+this +",  buff: "+buffer + ", dest: "+destination+"\n");
			moveDisk(n-1, buffer, destination);
			
			System.out.println("b [n="+n+"]. src: "+this +",  buff: "+buffer + ", dest: "+destination+"\n");
			moveTopTo(destination);
			
			System.out.println("c [n="+n+"]. src: "+this +",  buff: "+buffer + ", dest: "+destination+"\n");
			
			buffer.moveDisk(n-1, destination, this);
			System.out.println("d [n="+n+"]. src: "+this +",  buff: "+buffer + ", dest: "+destination+"\n");
			

			
		}
	}
	
	public String toString() {
		return disks.toString();
	}
	
	
	
}
