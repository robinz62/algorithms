import java.util.*;

/**
 * Segment tree-based LCA. O(n) preprocess, O(log n) query.
 */
class LCA {
    int n;
    int m;
    int[] first;
    int[] depth;
    int[] st;

    LCA(List<List<Integer>> adj, int root) {
        n = adj.size();
        first = new int[n];
        depth = new int[n];
        boolean[] visited = new boolean[n];
        List<Integer> euler = new ArrayList<>(2 * n);
        dfs(adj, root, visited, euler, 0);
        build(euler);
    }

    int lca(int u, int v) {
        int l = first[u];
        int r = first[v];
        if (l > r) {
            l = first[v];
            r = first[u];
        }
        r++;
        l += m;
        r += m;
        int minNode = st[l];
        while (l < r) {
            if ((l & 1) > 0) {
                minNode = depth[st[l]] < depth[minNode] ? st[l] : minNode;
                l++;
            }
            if ((r & 1) > 0) {
                r--;
                minNode = depth[st[r]] < depth[minNode] ? st[r] : minNode;
            }
            l /= 2;
            r /= 2;
        }
        return minNode;
    }

    private void dfs(List<List<Integer>> adj, int u, boolean[] visited,
            List<Integer> euler, int currDepth) {
        visited[u] = true;
        depth[u] = currDepth;
        first[u] = euler.size();
        euler.add(u);
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                dfs(adj, v, visited, euler, currDepth + 1);
                euler.add(u);
            }
        }
    }

    private void build(List<Integer> euler) {
        m = euler.size();
        st = new int[m * 2];
        for (int i = 0; i < m; i++) {
            st[m+i] = euler.get(i);
        }
        for (int i = m - 1; i > 0; i--) {
            st[i] = depth[st[i*2]] < depth[st[i*2+1]] ? st[i*2] : st[i*2+1];
        }
    }
}

/**
 * Binary lifting-based LCA. O(n log n) preprocess, O(log n) query.
 */
class LCA {
    int time = 0;
    int[] s;
    int[] f;
    int[][] up;

    LCA(List<List<Integer>> adj, int root) {
        s = new int[adj.size()];
        f = new int[adj.size()];
        int log2ceil = 31 - Integer.numberOfLeadingZeros(adj.size());
        up = new int[adj.size()][log2ceil + 1];
        boolean[] visited = new boolean[adj.size()];
        visited[root] = true;
        up[root][0] = root;
        dfs(adj, visited, root);

        for (int j = 1; j <= log2ceil; j++) {
            for (int i = 0; i < adj.size(); i++) {
                int x = up[i][j-1];
                up[i][j] = x == root ? root : up[x][j-1];
            }
        }
    }

    int lca(int u, int v) {
        if (u == v) return u;
        if (isAncestor(u, v)) return u;
        if (isAncestor(v, u)) return v;
        int j = up[0].length-1;
        int curr = u;
        while (j >= 0) {
            if (isAncestor(up[curr][j], v)) j--;
            else curr = up[curr][j];
        }
        return up[curr][0];
    }

    boolean isAncestor(int u, int v) {
        return s[u] < s[v] && f[u] > f[v];
    }

    private void dfs(List<List<Integer>> adj, boolean[] visited, int u) {
        s[u] = time++;
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                visited[v] = true;
                up[v][0] = u;
                dfs(adj, visited, v);
            }
        }
        f[u] = time++;
    }
}