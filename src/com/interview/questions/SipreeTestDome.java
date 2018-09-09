package com.interview.questions;

import java.util.ArrayList;

public class SipreeTestDome {

	public static void main(String[] args) {
		System.out.println("Sipree Online test 'TestDome'...");
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
	
	private ArrayList<Integer> data;

    public Snapshot(ArrayList<Integer> data) {
        this.data = data;
    }

    public ArrayList<Integer> restore() {
        return this.data;
    }

	
}


