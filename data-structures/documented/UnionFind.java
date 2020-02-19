import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A disjoint-set data structure with n elements labeled [0, n - 1]. This
 * implementation utilizes union-by-rank and path compression.
 */
public class UnionFind {
    private int n;
    private int numComponents;
    private int[] parent;
    private int[] rank;

    /**
     * Initialize a disjoint-set data structure with the specified number of
     * elements.
     * 
     * @param n the initial number of elements.
     */
    public UnionFind(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        this.n = n;
        numComponents = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    /**
     * Unions the components containing the specified elements.
     * 
     * @param u the element representing the first component.
     * @param v the element representing the second component.
     * @throws NoSuchElementException if <code>u</code> or <code>v</code> are
     *                                not valid elements.
     */
    public void union(int u, int v) {
        if (u < 0 || u >= n || v < 0 || v >= n) {
            throw new NoSuchElementException();
        }
        int x = find(u);
        int y = find(v);
        if (x == y) {
            return;
        }
        if (rank[x] < rank[y]) {
            parent[x] = y;
        } else if (rank[x] > rank[y]) {
            parent[y] = x;
        } else {
            parent[x] = y;
            rank[y]++;
        }
        numComponents--;
    }

    /**
     * Finds and returns the representative element of the component containing
     * the specified element.
     * 
     * @param u the element whose representative is to be retrieved.
     * @return the representative element.
     * @throws NoSuchElementException if <code>u</code> is not a valid element.
     */
    public int find(int u) {
        if (u < 0 || u >= n) {
            throw new NoSuchElementException();
        }
        int current = u;
        List<Integer> toUpdate = new ArrayList<>();
        while (parent[current] != current) {
            toUpdate.add(current);
            current = parent[current];
        }
        for (Integer node : toUpdate) {
            parent[node] = current;
        }
        return current;
    }

    /**
     * Returns the number of disjoint components (sets).
     * 
     * @return the number of components.
     */
    public int getNumComponents() {
        return numComponents;
    }
}