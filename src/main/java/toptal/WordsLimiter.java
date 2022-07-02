package toptal;

public class WordsLimiter {

    public static final int SPACE_LENGTH = 1;
    public static final String SPACE = " ";

    public static void main(String[] args) {
        System.out.println(new WordsLimiter().solution("sdfg dfg fdg d", 10));
    }

    public String solution(String message, int K) {
        if (message.length() <= K) {
            return message;
        }
        int counter = 0;
        StringBuilder result = new StringBuilder(500);
        String[] words = message.split(" ");
        counter = words[0].length();
        if (counter > K) {
            return words[0];
        }
        result.append(words[0]);
        for (int i = 1; i < words.length; i++) {
            counter += words[i].length() + SPACE_LENGTH;
            if (counter > K) {
                return result.toString();
            }
            result.append(SPACE).append(words[i]);
        }
        return result.toString();
    }
}
