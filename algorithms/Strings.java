public class Strings {
    // Returns the index of the first occurrence of word in text.
    public static int search(String word, String text) {
        int[] kmp = getKmpArray(word);
        int i = 0;
        int j = 0;
        while (i < text.length()) {
            if (text.charAt(i) == word.charAt(j)) {
                ++i;
                ++j;
                if (j == word.length()) {
                    return i - word.length();
                }
            } else if (j != 0) {
                j = kmp[j - 1];
            } else {
                ++i;
            }
        }
        return -1;
    }

    // Returns an array kmp, where kmp[i] is the length of the longest proper
    // prefix which is also a suffix for the string ending at index i.
    public static int[] getKmpArray(String word) {
        int[] kmp = new int[word.length()];
        int i = 1;
        int j = 0;
        while (i < kmp.length) {
            if (word.charAt(i) == word.charAt(j)) {
                kmp[i] = j + 1;
                ++i;
                ++j;
            } else if (j != 0) {
                j = kmp[j - 1];
            } else {
                ++i;
            }
        }
        return kmp;
    }

    // Manacher's algorithm for finding all palindromic substrings of a string.
    // Returns the length of the longest palindromic substring.
    // 
    // d1[i] stores odd-length palindromes. d2[i] stores even-length
    // palindromes. For d1, i is the center of the palindrome, and the length of
    // the palindrome is d1[i]*2 - 1. For d2, i is the right-center of the
    // palindrome, and the length of the palindrome is d2[i]*2.
    //
    public static int manacher(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[] d1 = new int[n];
        for (int i = 0, l = 0, r = -1; i < n; i++) {
            int k = (i > r) ? 1 : Math.min(d1[l + r - i], r - i + 1);
            while (0 <= i - k && i + k < n && s[i - k] == s[i + k]) {
                k++;
            }
            d1[i] = k--;
            if (i + k > r) {
                l = i - k;
                r = i + k;
            }
        }
        int[] d2 = new int[n];
        for (int i = 0, l = 0, r = -1; i < n; i++) {
            int k = (i > r) ? 0 : Math.min(d2[l + r - i + 1], r - i + 1);
            while (0 <= i - k - 1 && i + k < n && s[i - k - 1] == s[i + k]) {
                k++;
            }
            d2[i] = k--;
            if (i + k > r) {
                l = i - k - 1;
                r = i + k ;
            }
        }

        int ans = 0;
        for (int di : d1) ans = Math.max(ans, di * 2 - 1);
        for (int di : d2) ans = Math.max(ans, di * 2);
        return ans;
    }

    // Computes a string hash of the input string. The prime p should be a
    // prime just larger than the alphabet size. m should be a large prime,
    // such as 10e9 + 7 or 10e9 + 9. Consider using multiple m to decrease
    // collision probability.
    //
    // The input string should not contain the value 0. Consider pre-mapping
    // inputs using a->1, b->2, etc. or 0->1, 1->2, 2->3, etc.
    //
    // This code snippet can also be easily adapted for rolling hashes. Update
    // windows of size k by val = val - s[i-k] + p^k-1 * s[i].
    void stringHash(int[] s, int p, int m) {
        long val = 0;
        long ppow = 1;
        for (int i = 0; i < s.length; i++) {
            val = (val + ppow * s[i]) % m;
        }
        return (int) val;
    }
}
