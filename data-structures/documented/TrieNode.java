/**
 * A base Trie that is easy to extend with extra functionality.
 */
public class TrieNode {
    TrieNode[] children;
    boolean isWord;

    /**
     * Adds the given word to the given trie.
     */
    static void add(TrieNode root, String word) {
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if (root.children == null) root.children = new TrieNode[26];
            if (root.children[c] == null) root.children[c] = new TrieNode();
            root = root.children[c];
        }
        root.isWord = true;
    }
    
    /**
     * Traverses the given trie and returns the node corresponding to prefix,
     * or null if the trie does not contain prefix.
     */
    static TrieNode contains(TrieNode root, String prefix) {
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if (root.children == null || root.children[c] == null) return null;
            root = root.children[c];
        }
        return root;
    }
}