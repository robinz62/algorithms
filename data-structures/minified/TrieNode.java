class TrieNode {
    TrieNode[] children;
    boolean isWord;

    static void add(TrieNode root, String word) {
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if (root.children == null) root.children = new TrieNode[26];
            if (root.children[c] == null) root.children[c] = new Node();
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