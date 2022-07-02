package my.tasks.triesstructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Runner {

    public static void main(String[] args) {
        TriesStructure wordsMap = new TriesStructure();
        wordsMap.addWord("mother");
        wordsMap.addWord("father");
        wordsMap.addWord("moon");
        String lookUpWord1 = "mother";
        String lookUpWord3 = "moon";
        String lookUpWord2 = "man";
        System.out.println("word " + lookUpWord1 + " is present: " + wordsMap.isWordPresent(lookUpWord1));
        System.out.println("word " + lookUpWord2 + " is present: " + wordsMap.isWordPresent(lookUpWord2));
        System.out.println("word " + lookUpWord3 + " is present: " + wordsMap.isWordPresent(lookUpWord3));
    }
}


class TriesStructure {

    private Map<Character, Node> rootMap = new HashMap<>();

    public void addWord(String word) {
        char[] arr = word.toCharArray();
        Queue<Character> queue = getQueue(arr);
        addSymbol(queue, rootMap);
    }

    public boolean isWordPresent(String word) {
        char[] arr = word.toCharArray();
        Queue<Character> queue = getQueue(arr);
        return cascadeFindWord(queue, rootMap);
    }

    private boolean cascadeFindWord(Queue<Character> queue, Map<Character, Node> rootMap) {
        for (Map.Entry<Character, Node> entry : rootMap.entrySet()) {
            Queue<Character> qCopy = new LinkedList<>(queue);
            if (entry.getKey().equals(qCopy.peek())) {
                return findWord(qCopy, rootMap);
            }
        }
        return false;
    }


    private boolean findWord(Queue<Character> queue, Map<Character, Node> rootMap) {
        if (queue.isEmpty()) {
            return true;
        }
        Character character = queue.poll();
        if (rootMap.containsKey(character)) {
            return findWord(queue, rootMap.get(character).children);
        } else {
            return false;
        }
    }

    private void addSymbol(Queue<Character> queue, Map<Character, Node> map) {
        if (queue.isEmpty()) {
            return;
        }
        Character character = queue.poll();
        map.computeIfPresent(character, (k, v) -> {
            addSymbol(queue, v.children);
            return v;
        });
        map.computeIfAbsent(character, k -> {
            Node node = new Node();
            addSymbol(queue, node.children);
            return node;
        });
    }

    private Queue<Character> getQueue(char[] arr) {
        Queue<Character> queue = new LinkedList<>();
        for (char symbol : arr) {
            queue.add(symbol);
        }
        return queue;
    }

    private static class Node {

        Map<Character, Node> children = new HashMap<>();
    }
}