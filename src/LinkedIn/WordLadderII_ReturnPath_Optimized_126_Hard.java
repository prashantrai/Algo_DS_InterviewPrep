package LinkedIn;
import java.util.*;

public class WordLadderII_ReturnPath_Optimized_126_Hard {

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return result;

        // Graph of word -> next level words
        Map<String, List<String>> graph = new HashMap<>();

        // BFS phase: build graph with only shortest paths
        bfsBuildGraph(beginWord, endWord, dict, graph);

        // DFS phase: collect all paths
        List<String> path = new ArrayList<>();
        path.add(beginWord);
        dfsEnumerate(beginWord, endWord, graph, path, result);

        return result;
    }

    private void bfsBuildGraph(String beginWord, String endWord, Set<String> dict,
                               Map<String, List<String>> graph) {
        Queue<String> q = new ArrayDeque<>();
        q.offer(beginWord);

        Set<String> visited = new HashSet<>();
        visited.add(beginWord);

        boolean foundEnd = false;
        while (!q.isEmpty() && !foundEnd) {
            int size = q.size();
            Set<String> levelVisited = new HashSet<>();

            for (int i = 0; i < size; i++) {
                String word = q.poll();
                char[] arr = word.toCharArray();

                for (int j = 0; j < arr.length; j++) {
                    char orig = arr[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == orig) continue;
                        arr[j] = c;
                        String next = new String(arr);

                        if (dict.contains(next)) {
                            if (!visited.contains(next)) {
                                if (!levelVisited.contains(next)) {
                                    q.offer(next);
                                    levelVisited.add(next);
                                }
                                // build edge word -> next
                                graph.computeIfAbsent(word, k -> new ArrayList<>()).add(next);
                            }
                            if (next.equals(endWord)) foundEnd = true;
                        }
                    }
                    arr[j] = orig;
                }
            }
            visited.addAll(levelVisited);
        }
    }

    private void dfsEnumerate(String curr, String end, Map<String, List<String>> graph,
                              List<String> path, List<List<String>> result) {
        if (curr.equals(end)) {
            result.add(new ArrayList<>(path));
            return;
        }

        if (!graph.containsKey(curr)) return;

        for (String next : graph.get(curr)) {
            path.add(next);
            dfsEnumerate(next, end, graph, path, result);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        WordLadderII_ReturnPath_Optimized_126_Hard solver = new WordLadderII_ReturnPath_Optimized_126_Hard();

        System.out.println(solver.findLadders("hit", "cog",
                Arrays.asList("hot","dot","dog","lot","log","cog")));

        System.out.println(solver.findLadders("hit", "cog",
                Arrays.asList("hot","dot","dog","lot","log")));
    }
}
