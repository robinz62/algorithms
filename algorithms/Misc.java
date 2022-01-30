public class Misc {
    
    // Sorts the input array, shuffling first to preclude adversarial
    // quickselect pivots.
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

    // Returns the leftmost occurrence of k in the closed range A[l..r]. If k
    // is not present, returns (-ins-1), where ins is where k would be
    // inserted.
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

    // Returns the rightmost occurrence of k in the closed range A[l..r]. If k
    // is not present, returns (-ins-1), where ins is where k would be
    // inserted.
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

    // Code snippet for iterating through submasks in descending order. Total
    // of 2^n submasks for a mask with cardinalty n. Note: 0 is NOT processed.
    //
    // Tip: iterating through all submasks of all submasks is O(3^n)
    public static void enumerateSubmasks(int mask) {
        int s = mask;
        while (s > 0) {
            // use s here
            s = (s-1) & mask;
        }
    }

    // Code snippet for iterating over all submasks with k set bits. Subsets
    // are generated in increasing order.
    //
    // Tip: if using longs, be sure to replace 1 with 1l.
    public static void enumerateSubsetsSizeK(int n, int k) {
        int mask = (1 << k) - 1, r, c;
        while (mask <= (1 << n) - (1 << (n-k))) {
            // use mask here
            c = mask & -mask, r = mask+c, mask = r | (((r ^ mask) >> 2) / c);
        }
    }

    // Input: an array A of size 2^N.
    // Output: An array [count], where count[mask] is the sum over A[sub],
    // for all submasks of mask.
    // Source: https://codeforces.com/blog/entry/45223
    //
    // To obtain counts over supermasks of a mask, change the if-conditional to
    // be (... == 0) rather than (... > 0).
    //
    // Runtime: O(N * 2^N)
    public static int[] sumOverSubsets(int[] A, int N) {
        int[] F = new int[1 << N];
        for (int i = 0; i < (1 << N); i++) F[i] = A[i];
        for (int i = 0; i < N; i++)
            for (int mask = 0; mask < (1 << N); mask++)
                if ((mask & (1 << i)) > 0) F[mask] += F[mask ^ (1 << i)];
        return F;
    }

    // Code snippet to coordinate-compress the input array. Remove the last
    // line if you don't want to modify the input array in place.
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
}
