package Apple;
import java.util.ArrayList;
import java.util.List;

public class CrosswordClues {
    public static void main(String[] args) {
        int width = 3;
        String puzzle = "#.......#";
        List<List<Integer>> result = findClueIndices(width, puzzle);
        System.out.println("Across clues: " + result.get(0));
        System.out.println("Down clues: " + result.get(1));
        
        int width2 = 8;
        String puzzle2 = "...##......#............##...######...##............#......##...";
        List<List<Integer>> result2 = findClueIndices(width2, puzzle2);
        System.out.println("Across clues: " + result2.get(0));
        System.out.println("Down clues: " + result2.get(1));
    }

    public static List<List<Integer>> findClueIndices(int width, String puzzle) {
        int height = puzzle.length() / width;
        int clueNumber = 1;
        List<Integer> across = new ArrayList<>();
        List<Integer> down = new ArrayList<>();
        
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int index = row * width + col;
                if (puzzle.charAt(index) != '#') {
                    // Check for "across" clue start
                    boolean startAcross = (col == 0 || puzzle.charAt(index - 1) == '#');
                    if (startAcross && (col + 1 < width && puzzle.charAt(index + 1) != '#')) {
                        across.add(clueNumber);
                    }
                    // Check for "down" clue start
                    boolean startDown = (row == 0 || puzzle.charAt(index - width) == '#');
                    if (startDown && (row + 1 < height && puzzle.charAt(index + width) != '#')) {
                        down.add(clueNumber);
                    }
                    // Increment clue number if it's a start of any clue
                    if (startAcross || startDown) {
                        clueNumber++;
                    }
                }
            }
        }
        
        List<List<Integer>> result = new ArrayList<>();
        result.add(across);
        result.add(down);
        return result;
    }
}
