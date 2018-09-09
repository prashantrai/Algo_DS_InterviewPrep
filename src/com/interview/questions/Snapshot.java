package com.interview.questions;

import java.util.ArrayList;

public class Snapshot {
	private ArrayList<Integer> data;
	private ArrayList<Integer> dataClone;

	public Snapshot(ArrayList<Integer> data) {
		this.data = data;
		dataClone = (ArrayList<Integer>) data.clone();
	}

	public ArrayList<Integer> restore() {
		return (ArrayList<Integer>) this.dataClone.clone();
	}

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		
		Snapshot snap = new Snapshot(list);
		list.set(0, 3);
		list = snap.restore();
		System.out.println(list); // Should output [1, 2]
		
		list.add(4);
		list = snap.restore();
		System.out.println(list); // Should output [1, 2]
	}
}
