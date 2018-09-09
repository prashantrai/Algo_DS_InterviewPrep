package ctci.ch3.stack.and.queues;

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
	
	private LinkedList<Dog> queueDog;
	private LinkedList<Cat> queueCat;
	private int order = 0;
	
	
	public AnimalQueue() {
		queueCat = new LinkedList<Cat>(); 
		queueDog = new LinkedList<Dog>(); 
	}
	
	//enqueue
	
	public boolean enqueue(Animal animal) {
		
		if(animal == null) {
			return false;
		}
		
		animal.setOrder(order);
		order++;
		
		if(animal instanceof Dog) {
			queueDog.addLast((Dog)animal);
		} else {
			queueCat.addLast((Cat)animal);
		}
		
		return true;
	}
	
	//dequeueAny
	public Animal dequeueAny() {
		
		if(queueCat.size() == 0) {
			return dequeueDog();
		}
		
		else if(queueDog.size() == 0) {
			return dequeueCat();
		}
		
		Cat cat = queueCat.peek();
		Dog dog = queueDog.peek();
		
		if(cat.getOrder() > dog.getOrder()) {
			return dequeueDog();
		} else {
			return dequeueCat();
		} 
		
	}
	
	//dequeueDog
	public Dog dequeueDog() {
		return queueDog.poll();
	}
	
	//dequeueCat
	public Cat dequeueCat() {
		return queueCat.poll();
	}
	
}



class Dog extends Animal {

	public Dog(String name) {
		super(name);
	}
	public String toString() {
		String s ="";
		
		s += "[name:"+ name + ", order="+ order +"]";
		
		return s;
	}
}
class Cat extends Animal {
	
	public Cat(String name) {
		super(name);
	}
	public String toString() {
		String s ="";
		
		s += "[name:"+ name + ", order="+ order +"]";
		
		return s;
	}
}

abstract class Animal {
	
	protected String name;
	protected int order;
	
	public Animal(String name) {
		this.name = name;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public int getOrder() {
		return order;
	}
	
	public String toSrting() {
		String s ="";
		
		s += "[name:"+ this.name + ", order="+ this.order +"]";
		
		return s;
	}
}
