package ctci.ch7.ood;

import java.util.Arrays;
import java.util.Iterator;

public class CircularArrayDemo {

	public static void main(String[] args) {
		
		CircularArray<String> arr = new CircularArray<String>(5);
		arr.set("A", 0);
		arr.set("B", 1);
		arr.set("C", 2);
		arr.set("D", 3);
		arr.set("E", 4);
		System.out.println(arr);
		System.out.println(arr.get(0));
		
		arr.rotate(-2);
		
		System.out.println(arr);
		System.out.println(arr.get(0));
		
		arr.rotate(-10);
		
		System.out.println(arr);
		System.out.println(arr.get(0));
		
		
		/*
		for (String s : arr) {
			System.out.println(s);
		}
		System.out.println("---");
		Iterator iter = arr.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}*/
		
	}

}


class CircularArray<T> implements Iterable<T> {
	
	private T items[];
	int head = 0;
	
	public CircularArray(int size) {
		items = (T[]) new Object[size];
	}
	
	public int convert(int index) {
		if(index < 0) {
			index += items.length;
		}
		
		return (head + index) % items.length;
	}
	
	public void rotate(int shiftRight) {
		head = convert(shiftRight);
	}
	
	public T get(int index) {
		if(index < 0 || index > items.length) {
			throw new IndexOutOfBoundsException("Incorrect index " + index + " for array of size " + items.length);
		}
		return items[convert(index)];
	}
	
	public void set(T item, int index) {
		items[convert(index)] = item;
	} 
	
	public String toString() {
		System.out.println("Current head point at: "+head);
		return Arrays.toString(items);
	}
	
	
	@Override
	public Iterator<T> iterator() {
		return new CircularArrayIterator<T>(this);
	}
	
	private class CircularArrayIterator<T> implements Iterator<T> {

		private int current = -1;
		private T[] _items;
		
		public CircularArrayIterator(CircularArray<T> arr) {
			_items = arr.items;
		}
		
		@Override
		public boolean hasNext() {

			return  current < items.length;
		}

		@Override
		public T next() {
			current++;
			return (T) _items[convert(current)];
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
}