package oracle;

import java.util.ArrayDeque;
import java.util.Deque;

public class BalancingParenthesis {
    public static void main(String[] args) {
        System.out.println(getMin(")))()()()())())())))))())))))))())()))()()()))))))((("));
    }

    public static int getMin(String s) {
        Deque<Character> dq = new ArrayDeque<>();
        char[] ch = s.toCharArray();
        int counter = 0;
        for (int i = 0; i < ch.length; i++) {
            char item = ch[i];
            if (dq.size() == 0 && item == ')') {
                counter++;
            } else if (item == '(') {
                dq.push(item);
            } else if (item == ')' && dq.peek() == '(') {
                dq.poll();
            }
        }
        return counter+dq.size();
    }
}
