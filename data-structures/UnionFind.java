import java.util.*;

/**
 * DSU featuring union-by-rank and path compression.
 * Nodes are labeled [0, n-1].
 */
class UnionFind {
    int n;
    int numComponents;
    int[] parent;
    int[] rank;

    UnionFind(int n) {
        this.n = numComponents = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    void union(int u, int v) {
        int x = find(u);
        int y = find(v);
        if (x == y) return;
        if (rank[x] < rank[y]) parent[x] = y;
        else if (rank[x] > rank[y]) parent[y] = x;
        else {
            parent[x] = y;
            rank[y]++;
        }
        numComponents--;
    }

    int find(int u) {
        int current = u;
        List<Integer> toUpdate = new ArrayList<>();
        while (parent[current] != current) {
            toUpdate.add(current);
            current = parent[current];
        }
        for (Integer node : toUpdate) parent[node] = current;
        return current;
    }

    int getNumComponents() {
        return numComponents;
    }
}