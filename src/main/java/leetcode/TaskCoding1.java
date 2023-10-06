package leetcode;

public class TaskCoding1 {
// find on board given word ( horiz, vertic, diagonal - from left top to right down corner only)
    public static void main(String[] args) {
        TaskCoding1 solution = new TaskCoding1();
        char[][] board = {
                {'s', 'o', 's', 'o'},
                {'s', 'o', 'o', 's'},
                {'s', 's', 's', 's'}};
        System.out.println(solution.solution(board, "sos")); // Expected Output: 3
    }

    int solution(char[][] board, String word) {
        int match = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                match = match + getMatch(board, word, i, j);
            }
        }

        return match;
    }

    private int getMatch(char[][] board, String word, int boardIndex, int arrayIndex) {
        int wordLength = word.length();
        int boardLength = board.length;
        int arrayLength = board[0].length;
        int match = 0;

        // horizontal
        if (arrayIndex + wordLength <= arrayLength) {
            boolean isMatch = true;
            int wordIndex = 0;
            for (int k = arrayIndex; wordIndex < wordLength; k++) {
                if (board[boardIndex][k] != word.charAt(wordIndex++)) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                match++;
            }
        }

        // vertical
        if (boardIndex + wordLength <= boardLength) {
            boolean isMatch = true;
            int wordIndex = 0;
            for (int k = boardIndex; wordIndex < wordLength; k++) {
                if (board[k][arrayIndex] != word.charAt(wordIndex++)) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                match++;
            }
        }

        // diagonal
        if ((arrayIndex + wordLength <= arrayLength) && (boardIndex + wordLength <= boardLength)) {
            boolean isMatch = true;
            int wordIndex = 0;
            for (int k = boardIndex; wordIndex < wordLength; k++) {
                if (board[k][arrayIndex + wordIndex] != word.charAt(wordIndex++)) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                match++;
            }
        }

        return match;
    }


}
