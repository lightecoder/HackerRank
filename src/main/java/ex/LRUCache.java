package ex;
// Design a data structure that follows the constraints of a Least Recently Used(LRU) cache.

// Implement the LRUCache class:

// LRUCache(int capacity) Initialize the LRU cache with positive size capacity.

// int get(int key) Return the value of the key if the key exists, otherwise return -1.

// void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.

// The functions get and put must each run in O(1) average time complexity.

// Example:


//  lRUCache = new LRUCache(2);
// lRUCaLRUCacheche.put(1, 1); // cache is {1=1}
// lRUCache.put(2, 2); // cache is {1=1, 2=2}
// lRUCache.get(1);    // return 1
// lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
// lRUCache.get(2);    // returns -1 (not found)
// lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
// lRUCache.get(1);    // return -1 (not found)
// lRUCache.get(3);    // return 3
// lRUCache.get(4);    // return 4

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class LRUCache {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(5);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);
        cache.printCache();
        System.out.println(cache.get(6));
        System.out.println(cache.get(1));
        cache.printCache();
        System.out.println("#########");
        System.out.println(cache.get(3));
        cache.printCache();
        cache.put(6, 6);
        System.out.println("#########");
        cache.printCache();
    }

    public Map<Integer, Node> cache;
    int capacity;
    Node head;
    Node tail;

    public LRUCache(int capacity) {
        cache = new LinkedHashMap<>(capacity);
        this.capacity = capacity;
    }

    // insert in tail. Head will be LRU element
    void put(int key, int value) {
        Node node = new Node(value);
        if (cache.size() == capacity && capacity != 0) {
            if (cache.size() == 1) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.prev = null;
            }
        }
        if (cache.size() == 0) {
            tail = node;
            head = node;
        } else {
            Node temp = tail;
            temp.next = node;
            tail = node;
            tail.prev = temp;
        }
        cache.put(key, node);
    }

    int get(int key) {
        boolean present = cache.containsKey(key);
        if (present) {
            Node current = cache.get(key);
            if (cache.get(key).next != null && cache.get(key).prev != null) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
            } else if (cache.get(key).prev == null) {
                current.next.prev = null;
                head = current.next;
            }
            tail.next = current;
            current.prev = tail;
            current.next = null;
            tail = current;
        }
        return present ? cache.get(key).value : -1;
    }

    void printCache() {
        Node node = head;
        while (node != null) {
            System.out.println(node);
            node = node.next;
        }
    }

}

class Node {
    int value;
    Node next;
    Node prev;

    Node(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return value == node.value && Objects.equals(next, node.next) && Objects.equals(prev, node.prev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Node{" + "value=" + value + '}';
    }
}

