import java.util.*;

// Data structure for quickly (O(log n)) querying the kth ancestor of a vertex
// in a tree (or forest).
//
// NOTE: untested
class KthAncestor {
    int[][] up;

    KthAncestor(List<List<Integer>> adj, List<Integer> roots) {
        int n = adj.size();
        int log2ceil = 31 - Integer.numberOfLeadingZeros(n);
        up = new int[n][log2ceil + 1];
        for (int root : roots) {
            up[root][0] = -1;
            dfs(adj, root, -1);
        }

        for (int j = 1; j <= log2ceil; j++) {
            for (int i = 0; i < adj.size(); i++) {
                int x = up[i][j-1];
                up[i][j] = x == -1 ? -1 : up[x][j-1];
            }
        }
    }

    KthAncestor(int[] p) {
        int n = p.length;
        int log2ceil = 31 - Integer.numberOfLeadingZeros(n);
        up = new int[n][log2ceil + 1];
        for (int i = 0; i < n; i++) up[i][0] = p[i];
        for (int j = 1; j <= log2ceil; j++) {
            for (int i = 0; i < n; i++) {
                int x = up[i][j-1];
                up[i][j] = x == -1 ? -1 : up[x][j-1];
            }
        }
    }

    int ancestor(int u, int k) {
        int j = up[u].length - 1;
        while (k != 0 && u != -1) {
            if ((1 << j) > k) i--;
            else {
                u = up[u][i];
                k -= (1 << j);
            }
        }
        return u;
    }

    private void dfs(List<List<Integer>> adj, int u, int p) {
        for (int v : adj.get(u)) {
            if (v == p) continue;
            visited[v] = true;
            up[v][0] = u;
            dfs(adj, v, u);
        }
    }
}