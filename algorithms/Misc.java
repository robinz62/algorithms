public class Misc {

    /**
     * Performs binary search for k in sorted array A in the half-open range
     * [l, r). If k is found, then the index of the leftmost occurrence is
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
     * Performs binary search for k in sorted array A in the half-open range
     * [l, r). If k is found, then the index of the rightmost occurrence is
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

    /*
     * Computes (base^exponent) mod m. O(log n) where n is the exponent.
     */
    public static long modPow(long base, long exponent, long m) {
        long ans = 1;
        base = base % m;
        while (exponent > 0) {
            if ((exponent & 1) == 1) ans = (ans * base) % m;
            exponent >>= 1;
            base = (base * base) % m;
        } 
        return ans;
    }

    /**
     * Computes the a^(-1) mod m, the modular inverse of a (modulo m). This
     * algorithm uses the Euclidean gcd method. a and m must be relatively
     * prime. O(log m).
     */
    public static long modInverseGcd(long a, long m) {
        if (m == 1) return 0;

        long m0 = m;
        long y = 0;
        long x = 1;
        while (a > 1) {
            long q = a / m;
            long t = m;
            m = a % m;
            a = t;
            t = y;
            y = x - q * y;
            x = t;
        }
        if (x < 0) x += m0;
        return x; 
    }

    /**
     * Computes a^(-1) mod m, the modular inverse of a (modulo m). This
     * algorithm is based on Fermat's little theorem. m must be prime.
     * O(log m).
     */
    public static long modInverseFermat(long a, long m) {
        return modPow(a, m - 2, m);
    }
}