import java.util.*;

/**
 * Various graph-related algorithms.
 */
public class Graphs {

    /**
     * Used for graph traversals in matrix graphs.
     */
    int[][] dirs = new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}};

    /**
     * DFS skeleton for matrix graphs where edges join adjacent cells. This
     * function assumes the given entry has already been marked visited.
     */
    void dfs(int[][] A, boolean[][] visited, int i, int j) {
        for (int[] dir : dirs) {
            int r = i+dir[0];
            int c = j+dir[1];
            if (r >= 0 && c >= 0 && r < A.length && c < A[0].length && !visited[r][c]) {
                visited[r][c] = true;
                dfs(A, visited, r, c);
            }
        }
    }

    /**
     * BFS skeleton for matrix graphs where edges join adjacent cells.
     */
    void bfs(int[][] A, boolean[][] visited, int i, int j) {
        Deque<int[]> q = new ArrayDeque<>();
        q.addLast(new int[]{i, j});
        while (!q.isEmpty()) {
            int[] u = q.removeFirst();
            for (int[] dir : dirs) {
                int r = u[0]+dir[0];
                int c = u[1]+dir[1];
                if (r >= 0 && c >= 0 && r < A.length && c < A[0].length && !visited[r][c]) {
                    visited[r][c] = true;
                    q.addLast(new int[]{r, c});
                }
            }
        }
    }
}