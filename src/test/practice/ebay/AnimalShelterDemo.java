package test.practice.ebay;

import java.util.LinkedList;

public class AnimalShelterDemo {

	public static void main(String[] args) {

		AnimalQueue queue = new AnimalQueue();
		
		Dog d1 = new Dog("DA");
		Dog d2 = new Dog("DB");
		Dog d3 = new Dog("DC");
		Dog d4 = new Dog("DD");
		
		Cat c1 = new Cat("CA");
		Cat c2 = new Cat("CB");
		Cat c3 = new Cat("CC");
		Cat c4 = new Cat("CD");
		
		queue.enqueue(d1); 
		queue.enqueue(c1); 
		queue.enqueue(c2); 
		queue.enqueue(d2); 
		queue.enqueue(c3); 
		queue.enqueue(d3); 
		queue.enqueue(d4); 
		queue.enqueue(c4);
		
		System.out.println(queue);
		
		System.out.println("dequeueAny: "+queue.dequeueAny());
		System.out.println("dequeueDog: "+ queue.dequeueDog());
		System.out.println("dequeueCat:" + queue.dequeueCat());
		
		
	}

}

class AnimalQueue {
	private LinkedList<Dog> dogQ;
	private LinkedList<Cat> catQ;
	private int order;
	
	public AnimalQueue() {
		dogQ = new LinkedList<Dog>();
		catQ = new LinkedList<Cat>();
	}
	
	public boolean enqueue(Animal a) {
		
		if(a == null) return false;
		
		a.setOrder(order);
		order++;
		
		if(a instanceof Dog) {
			dogQ.addLast((Dog) a);
		}
		else if(a instanceof Cat) {
			catQ.addLast((Cat) a);
		}
		
		return true;
	}
	
	public Animal dequeueAny() {
		if(dogQ.size() == 0) {
			return dequeueCat();
			
		} else if(catQ.size() == 0) {
			return dequeueDog();
		}
		
		Dog dog = dogQ.peek();
		Cat cat = catQ.peek();
		
		if(dog.isOlderThan(cat)) {
			return dequeueDog();
		} else 
			return dequeueCat();
	}
	
	public Dog dequeueDog() {
		return dogQ.poll();
	}
	
	public Cat dequeueCat() {
		return catQ.poll();
	}
	
}



class Dog extends Animal {
	String name;
	public Dog(String name) {
		this.name = name;
	}
	public String toString() {
		String s ="";
		
		s += "[name:"+ name + ", order="+ order +"]";
		
		return s;
	}
}

class Cat extends Animal {
	String name;
	public Cat(String name) {
		this.name = name;
	}
	public String toString() {
		String s ="";
		
		s += "[name:"+ name + ", order="+ order +"]";
		
		return s;
	}
}



abstract class Animal {
	String name;
	int order;
	
	public void setOrder(int order) { 
		this.order = order;
	}
	public int getOrder() {
		return order;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public boolean isOlderThan(Animal a) {
		return this.getOrder() < a.getOrder();
	}
	
	public String toSrting() {
		String s ="";
		
		s += "[name:"+ this.name + ", order="+ this.order +"]";
		
		return s;
	}
	
}
