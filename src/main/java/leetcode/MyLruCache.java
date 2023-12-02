package leetcode;

/*
1. LRU cache
2. Size limit = set when create cache
3. implement:
- void put(int key, int value) (O(1))
- get(key) should return value if exists , otherwise return -1 (O(1))
- update value if exists, otherwise - add key value pare
- if capacity exceeds - remove LRU
 */

import java.util.HashMap;
import java.util.Map;

class Runner{
    public static void main(String[] args) {
        MyLruCache lru = new MyLruCache(3);
        lru.put(1, new MyLruCache.Node(1));
        lru.put(2, new MyLruCache.Node(2));
        lru.put(3, new MyLruCache.Node(3));
        lru.put(5, new MyLruCache.Node(5));
        System.out.println(lru.get(4));

    }
}
public class MyLruCache {
    private Map<Integer, Node> cache;
    private Integer capacity;
    private Integer size = 0;

    private Node head;
    private Node tail;

    public MyLruCache(Integer capacity) {
        this.cache = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(Integer key){
        if(cache.containsKey(key)){
            Node value = cache.get(key);

            // for head prev = null
            if(value.prev != null) {
                Node prev = value.prev;
                Node next = value.next;
                prev.next = next;
                next.prev = prev;
            }
            Node temp = head;
            head=value;
            head.next=temp;
            temp.prev = head;

            return value.value;
        }
        return -1;
    }

    public void put(Integer key, Node value) {
        if (size < capacity) { // size < capacity
            if (!cache.containsKey(key)) {
                size++;
            }
            cache.put(key, value);
            if (head == null) {
                head = value;
                tail = value;
            } else {
                Node temp = head;
                head=value;
                head.next=temp;
                temp.prev = head;
            }
        } else {  // size >= capacity
            // remove LRU
            Node prev = tail.prev;
            tail = prev;
            tail.next = null;

            Node temp = head;
            head=value;
            head.next=temp;
            temp.prev = head;
        }
    }


    static class Node {
        int value;
        Node next;
        Node prev;

        public Node(int value) {
            this.value = value;
        }
    }

}


