import java.util.*;

class LCA {
    int n;
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
        l += n;
        r += n;
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
        first[u] = euler.size() - 1;
        euler.add(u);
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                dfs(adj, v, visited, euler, currDepth + 1);
            }
            euler.add(u);
        }
    }

    private void build(List<Integer> euler) {
        st = new int[euler.size() * 2];
        for (int i = 0; i < n; i++) {
            st[n + i] = euler.get(i);
        }
        for (int i = n - 1; i > 0; i--) {
            st[i] = depth[st[i*2]] < depth[st[i*2 + 1]] ? st[i*2] : st[i*2 + 1];
        }
    }
}