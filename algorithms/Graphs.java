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

    /**
     * Finds bridges of a graph.
     */
    class Bridges {
        int time;
        int n;
        List<List<Integer>> adj;
        boolean[] visited;
        int[] s;
        int[] low;

        void dfs(int u, int p) {
            visited[u] = true;
            s[u] = low[u] = time++;
            for (int v : adj.get(u)) {
                if (v == p) continue;
                if (visited[v]) {
                    low[u] = Math.min(low[u], s[v]);
                } else {
                    dfs(v, u);
                    low[u] = Math.min(low[u], low[v]);
                    if (low[v] > s[u]) {
                        // Process bridge (u, v) here.
                    }
                }
            }
        }

        void bridges() {
            time = 0;
            visited = new boolean[n];
            s = new int[n];
            low = new int[n];
            Arrays.fill(s, -1);
            Arrays.fill(low, -1);
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    dfs(i, -1);
                }
            }
        }
    }
}