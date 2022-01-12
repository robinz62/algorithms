class TrieNode {
    TrieNode[] children;
    boolean isWord;

    static void add(TrieNode root, String word) {
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if (root.children == null) root.children = new TrieNode[26];
            if (root.children[c] == null) root.children[c] = new TrieNode();
            root = root.children[c];
        }
        root.isWord = true;
    }

    static TrieNode contains(TrieNode root, String prefix) {
        for (int i = 0; i < prefix.length(); i++) {
            int c = prefix.charAt(i) - 'a';
            if (root.children == null || root.children[c] == null) return null;
            root = root.children[c];
        }
        return root;
    }
}

// Code snippets for fast binary tries.
class BinaryTrie {
    int MAX_BIT = 29;
    int MAX_ALLOC = 10_000_000;  // Ensure this is > (MAX_BIT+1) * N

    int[] left = new int[MAX_ALLOC];
    int[] right = new int[MAX_ALLOC];
    // Other data as needed e.g.
    // int[] here = new int[MAX_ALLOC];  // The value at this node, only applies to leaves
    int id = 0;

    int create() {
        left[id] = right[id] = -1;
        return id++;
    }

    // Example usage: add all elements of an array to a trie and return the root
    int convertToTrie(int[] a) {
        int root = create();
        for (int ai : a) {
            int curr = root;
            for (int b = MAX_BIT; b >= 0; b--) {
                int bit = ((1 << b) & ai) > 0 ? 1 : 0;
                if (bit == 0) {
                    if (left[curr] == -1) left[curr] = create();
                    curr = left[curr];
                } else {
                    if (right[curr] == -1) right[curr] = create();
                    curr = right[curr];
                }
            }
        }
        return root;
    }

    // Example usage: process all values under a node
    void process(int node) {
        if (left[node] == -1 && right[node] == -1) {
            // Process leaf here
        }
        if (left[node] != -1) process(left[node]);
        if (right[node] != -1) process(right[node]);
    }
}