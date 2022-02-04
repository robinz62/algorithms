import java.util.*;

// Various graph algorithms
public class Graphs {

    // directions for matrix graphs
    int[][] dirs = new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}};

    // DFS skeleton for matrix graphs where edges join adjacent cells. This
    // function assumes the given entry has already been marked visited.
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

    // BFS skeleton for matrix graphs where edges join adjacent cells.
    void bfs(int[][] A, boolean[][] visited, int i, int j) {
        Deque<int[]> q = new ArrayDeque<>();
        visited[i][j] = true;
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

    // Heap must support long keys.
    void dijkstra(int n, int s, List<List<int[]>> adj) {
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[s] = 0;
        boolean[] done = new boolean[n];
        int[] parent = new int[n];  // optional
        Arrays.fill(parent, -1);

        Heap heap = new Heap(n);
        for (int i = 0; i < n; i++) heap.add(i, dist[i]);

        while (!heap.isEmpty()) {
            int u = heap.extractMin();
            done[u] = true;
            if (dist[u] == Long.MAX_VALUE) continue;
            for (int[] vw : adj.get(u)) {
                int v = vw[0];
                int w = vw[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                    heap.decreaseKey(v, dist[v]);
                }
            }
        }
    }

    void bellmanFord(int n, int s, List<int[]> edges) {
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[s] = 0;
        int[] parent = new int[n];  // optional
        Arrays.fill(parent, -1);

        for (int i = 0; i < n-1; i++) {
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int w = edge[2];
                if (dist[u] != Long.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                }

                // Uncomment if graph is undirected
                // if (dist[v] != Long.MAX_VALUE && dist[v] + w < dist[u]) {
                //     dist[u] = dist[v] + w;
                //     parent[u] = v;
                // }
            }
        }

        // Detect negative cycles if needed
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            if (dist[u] != Long.MAX_VALUE && dist[u] + w < dist[v]) {
                // Negative cycle exists
            }
            // Uncomment if graph is undirected
            // if (dist[v] != Long.MAX_VALUE && dist[v] + w < dist[u]) {
            //     // Negative cycle exists
            // }
        }
    }

    void floydWarshall(int n, List<int[]> edges) {
        long[][] dist = new long[n][n];
        for (long[] row : dist) Arrays.fill(row, Long.MAX_VALUE);
        for (int i = 0; i < n; i++) dist[i][i] = 0;
        for (int[] edge : edges) dist[edge[0]][edge[1]] = edge[2];
        // Swap line above with line below if undirected
        // for (int[] edge : edges) dist[edge[0]][edge[1]] = dist[edge[1]][edge[0]] = edge[2];

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != Long.MAX_VALUE && dist[k][j] != Long.MAX_VALUE && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
    }

    // Code snippet for iterative dfs. Probably needed if n ~ 10^6 or larger.
    // We keep track of which phase of the dfs each node is on using color.
    // color[u] = -1 means not started
    // color[i] means we need to process the ith node in adjacency list
    void iterativeDfs(int n, List<List<Integer>> adj) {
        List<Integer> stack = new ArrayList<>();
        boolean[] visited = new boolean[n];
        int[] color = new int[n];
        Arrays.fill(color, -1);

        // any data you want to keep track of that a stack frame would normally
        // keep track of
        int[] stack_data = new int[n];

        // This outermost loop is not needed if you know there will only be
        // one search
        for (int s = 0; s < n; s++) {
            if (visited[s]) continue;

            visited[s] = true;
            stack.add(s);
            while (!stack.isEmpty()) {
                int u = stack.get(stack.size() - 1);
                if (color[u] == 0) {
                    // do work prior to visiting neighbors
                } else if (color[u] < adj.get(u).size()) {
                    int v = adj.get(u).get(color[u]);
                    if (!visited[v]) {
                        visited[v] = true;
                        stack.add(v);
                    }
                    color[u]++;
                } else {
                    // do work after visiting all neighbors
                    
                    stack.remove(stack.size() - 1);
                }
            }
        }
    }

    // Finds bridges of a graph.
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