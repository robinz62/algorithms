public class Misc {
    
    /**
     * Sorts the input array, shuffling it first to counteract adversarial
     * inputs.
     */
    void sort(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
        Arrays.sort(A);
    }

    /**
     * Performs binary search for k in sorted array A in the closed range
     * [l, r]. If k is found, then the index of the leftmost occurrence is
     * returned. Otherwise, returns (-ins - 1), where ins is where k would be
     * inserted into the array. O(log n).
     */
    public static int binarySearchLeft(int[] A, int l, int r, int k) {
        int lower = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (A[m] == k && (m-1 < 0 || A[m-1] < k)) {
                lower = m;
                break;
            }
            if (A[m] < k) {
                l = m + 1;
            } else if (A[m] >= k) {
                r = m - 1;
            }
        }
        return lower >= 0 ? lower : -l - 1;
    }

    /**
     * Performs binary search for k in sorted array A in the closed range
     * [l, r]. If k is found, then the index of the rightmost occurrence is
     * returned. Otherwise, returns (-ins - 1), where ins is where k would be
     * inserted into the array. O(log n).
     */
    public static int binarySearchRight(int[] A, int l, int r, int k) {
        int upper = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (A[m] == k && (m+1==A.length || A[m+1] > k)) {
                upper = m;
                break;
            }
            if (A[m] <= k) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return upper >= 0 ? upper : -l - 1;
    }

    /**
     * Code snippet for looping through submasks in descending order. Total of
     * 2^n submasks for a mask with cardinality n. Note 0 is not processed.
     * 
     * Tip: iterating through all submasks of all subsets is O(3^n).
     */
    public static void enumerateSubmasks(int mask) {
        int s = mask;
        while (s > 0) {
            // use s here
            s = (s-1) & mask;
        }
    }

    /**
     * Code snippet for looping over all submasks with k set bits. Subsets are
     * generated in increasing order. Note that if longs are used, be sure to
     * replace 1 with 1l in shift operations.
     */
    public static void enumerateSubsetsSizeK(int n, int k) {
        int mask = (1 << k) - 1, r, c;
        while (mask <= (1 << n) - (1 << (n-k))) {
            // use mask here
            c = mask & -mask, r = mask+c, mask = r | (((r ^ mask) >> 2) / c);
        }
    }

    /**
     * Code snippet to coordinate-compress the input array. Remove the last line
     * if you don't want to modify the input array in place.
     */
    public static void coordinateCompress(int[] a) {
        int[] b = new int[a.length];
        System.arraycopy(a, 0, b, 0, a.length);
        sort(b);
        Map<Integer, Integer> map = new HashMap<>();
        int idx = 0;
        map.put(b[0], idx++);
        for (int i = 1; i < b.length; i++) if (b[i] != b[i-1]) map.put(b[i], idx++);
        for (int i = 0; i < n; i++) a[i] = map.get(a[i]);
    }

    /**
     * Manacher's algorithm for finding all palindromic substrings of a string.
     * Returns the length of the longest palindromic substring.
     * 
     * d1[i] stores odd-length palindromes. d2[i] stores even-length
     * palindromes. For d1, i is the center of the palindrome, and the length of
     * the palindrome is d1[i]*2 - 1. For d2, i is the right-center of the
     * palindrome, and the length of the palindrome is d2[i]*2.
     */
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
}
