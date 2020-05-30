public class Misc {

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
}