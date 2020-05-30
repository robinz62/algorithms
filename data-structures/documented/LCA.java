import java.util.ArrayList;
import java.util.List;

/**
 * A data-structure for making Least Common Ancestor queries on a rooted tree.
 * This implementation requires O(n) preprocessing time and provides O(log n)
 * query time. Internally, it uses a segment tree to provide range minimum
 * queries.
 */
public class LCA {

    private int n;
    private int[] first;
    private int[] depth;
    private int[] st;

    /**
     * Constructs the LCA datastructure from the given (undirected) tree. The
     * vertices are labeled [0, n).
     *
     * @param adj  the adjacency list of the tree.
     * @param root the root of the tree.
     */
    public LCA(List<List<Integer>> adj, int root) {
        n = adj.size();
        first = new int[n];
        depth = new int[n];
        boolean[] visited = new boolean[n];
        List<Integer> euler = new ArrayList<>(2 * n);
        dfs(adj, root, visited, euler, 0);
        build(euler);
    }

    /**
     * Returns the least common ancestor of nodes u and v.
     */
    public int lca(int u, int v) {
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

    /**
     * Performs dfs, computing the euler tour, the depth of each node, and the
     * first occurrence of each node.
     */
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

    /**
     * Construts the segment tree of nodes. Each segment contains the node
     * with minimum depth.
     */
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