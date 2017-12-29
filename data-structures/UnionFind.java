/**
 * A disjoint-set data structure with n elements labeled [0, n - 1]
 */
public class UnionFind {
	private int n;
	private int numComponents;
	private int[] parent;
	private int[] rank;

	public UnionFind(int n) {
		if (n < 0) throw new IllegalArgumentException("n must be >= 0");
		this.n = numComponents = n;
		parent = new int[n];
		rank = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
		}
	}

	public void union(int u, int v) {
		if (u < 0 || u >= n || v < 0 || v >= n) {
			throw new NoSuchElementException();
		}
		int x = find(u);
		int y = find(v);
		if (x == y) { return; }
		if (rank[x] < rank[y]) {
			parent[x] = y;
		} else if (rank[x] > rank[y]) {
			parent[y] = x;
		} else {
			parent[x] = y;
			++rank[y];
		}
		++numComponents;
	}

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

	public int getNumComponents() {
		return numComponents;
	}

}