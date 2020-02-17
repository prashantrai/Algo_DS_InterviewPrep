package test.practice.misc;

import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

/**
 * LRU 1.
 * 
 * 
 * get put capacity
 * 
 * 
 * 
 * c <-> a -> b -> d Map<data, data>
 */

class LRUCache {
	public static void main(String[] args) {

		LRU<Integer, Integer> lru = new LRU<>(5);
		lru.add(1, 11);
		lru.add(2, 22);
		lru.add(3, 33);
		lru.add(4, 44);
		lru.add(5, 55);
		lru.add(6, 66); // [100, 66, 44, 33, 22]

		lru.add(5, 100);

		System.out.println(lru.size());

		System.out.println(lru.list);

		System.out.println(lru.get(3));
		// System.out.println(lru.map);
		System.out.println(lru.list);

	}

	private static class LRU<K, V> {
		int capacity;
		LinkedList<V> list;
		Map<K, V> map; /// -replce this by set
		int size;

		public LRU(int size) {
			this.capacity = size;
			list = new LinkedList<>();
			map = new HashMap<>();
		}

		public int size() {
			return size;
		}

		public void add(K k, V v) {
			if (list.size() < capacity) {
				list.addFirst(v);
				map.put(k, v); /// --replce this with set
				size++;

			} else if (list.size() == capacity) {

				if (!map.containsKey(v) && list.size() == capacity) {
					V val = list.get(list.size() - 1);
					map.remove(val);
					list.removeLast();

					list.addFirst(v);
					map.put(k, v);

				} else if (map.containsKey(k)) {

					V val = map.get(k);
					list.remove(val);
					list.addFirst(v);
					map.put(k, v);
				} else {
					map.remove(list.get(capacity - 1));
					list.removeLast();

					list.add(v);
					map.put(k, v);
				}
			}

		}

		public V get(K k) {

			if (!map.containsKey(k)) {
				return null;
			}

			V val = map.get(k);
			map.remove(val);
			list.remove(val);

			list.addFirst(val);

			return map.get(k);
		}

	}

}