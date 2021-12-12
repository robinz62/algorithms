import java.util.*;

// TODO: mobius function, totient function seive
// Other sieve resources:
// - https://codeforces.com/blog/entry/22229
// - https://codeforces.com/blog/entry/8989

// Contains assorted math-related algorithms.
public class Math {

    // Returns a list containing the factors of the specified number (n) in
    // arbitrary order. O(sqrt(n))
    public static List<Integer> factors(int n) {
        List<Integer> factors = new ArrayList<>();
        int i = 1;
        for (; i * i < n; i++) {
            if (n % i == 0) {
                factors.add(i);
                factors.add(n / i);
            }
        }
        if (i * i == n) factors.add(i);
        return factors;
    }


    // Returns the prime factorization of the specified number (n) as a map from
    // prime factor to count. O(sqrt(n))
    public static Map<Integer, Integer> primeFactorize(int n) {
        Map<Integer, Integer> factors = new HashMap<>();
        while (n % 2 == 0) {
            factors.put(2, factors.getOrDefault(2, 0) + 1);
            n /= 2;
        }
        for (int i = 3; i * i <= n; i += 2) {
            while (n % i == 0) {
                factors.put(i, factors.getOrDefault(i, 0) + 1);
                n /= i;
            }
        }
        if (n > 2) {
            factors.put(n, factors.getOrDefault(n, 0) + 1);
        }
        return factors;
    }

    // Returns the prime factorization of the specified number (n) using a
    // precomputed smallest-prime array (sp), where sp[i] is the smallest prime
    // factor of i. sp must have size at least n + 1. O(log n)
    public static Map<Integer, Integer> primeFactorize(int n, int[] sp) {
        Map<Integer, Integer> factors = new HashMap<>();
        while (n != 1) {
            factors.put(sp[n], factors.getOrDefault(sp[n], 0) + 1);
            n /= sp[n];
        }
        return factors;
    }

    // Computes and returns an array sp, where sp[i] is the smallest prime
    // factor of i (for 2 <= i <= n). Implemented using sieve of eratosthenes.
    // O(n loglog(n))
    public static int[] smallestPrime(int n) {
        int[] sp = new int[n + 1];
        for (int i = 0; i <= n; i++)
            sp[i] = i;
        for (int i = 4; i <= n; i += 2)
            sp[i] = 2;
        for (int i = 3; i * i <= n; i += 2) {
            if (sp[i] == i) {
                for (int j = i * i; j <= n; j += i) {
                    if (sp[j] == j)
                        sp[j] = i;
                }
            }
        }
        return sp;
    }

    // Computes and returns an array prime, where prime[i] is true if i is prime
    // and false otherwise (for i <= n). Implemented using linear sieve. O(n).
    public static boolean[] sieve(int n) {
        List<Integer> primes = new ArrayList<>();
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);
        prime[0] = prime[1] = false;
        for (int i = 2; i <= n; i++) {
            if (prime[i]) primes.add(i);
            for (int j = 0; j < primes.size() && i * primes.get(j) <= n; j++) {
                prime[i * primes.get(j)] = false;
                if (i % primes.get(j) == 0) break;
            }
        }
        return prime;
    }

    // Returns the greatest common denominator of the specified numbers (a) and
    // (b). O(log n).
    public static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
    }

    // Returns gcd(a, b) and computes [x, y] such that ax + by = gcd(a, b).
    // Works for negative numbers.
    public static int gcd(int a, int b, int[] xy) {
        if (b == 0) {
            xy[0] = 1;
            xy[1] = 0;
            return a;
        }
        int[] xy1 = new int[2];
        int d = gcd(b, a % b, xy1);
        xy[0] = xy1[1];
        xy[1] = xy1[0] - xy1[1] * (a / b);
        return d;
    }

    // Returns the least common multiple of the specified numbers (a) and (b).
    // O(log n).
    public static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    // Computes (base^exponent) mod m. O(log n) where n is the exponent.
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

    // Computes a^(-1) mod m, the modular inverse of a (modulo m). This
    // algorithm is based on Fermat's little theorem. m must be prime. O(log m).
    public static long modInverseFermat(long a, long m) {
        return modPow(a, m - 2, m);
    }

    // Computes the a^(-1) mod m, the modular inverse of a (modulo m). This
    // algorithm uses the Euclidean gcd method. a and m must be relatively
    // prime. O(log m).
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

    // Returns Euler's totient function of the input number. O(sqrt(n)).
    public static int phi(int n) {
        int result = n;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                while (n % i == 0) n /= i;
                result -= result / i;
            }
        }
        if (n > 1) result -= result / n;
        return result;
    }

    // Returns the matrix product A * B. Always returns a new object. Naive
    // cubic algorithm O(mnp).
    double[][] matrixMult(double[][] A, double[][] B) {
        int m = A.length;
        int n = A[0].length;
        int p = B[0].length;
        double[][] C = new double[m][p];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < p; j++)
                for (int k = 0; k < n; k++)
                    C[i][j] += A[i][k] * B[k][j];
        return C;
    }

    // Returns the matrix exponentiation A^k. Requires k > 0. Always returns a
    // new object. Runs in O(f log(k)), where f is the running time of matrix
    // multiplication.
    double[][] matrixExp(double[][] A, int k) {
        int m = A.length;
        int n = A[0].length;
        if (k == 1) {
            double[][] res = new double[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    res[i][j] = A[i][j];
            return res;
        }
        double[][] half = matrixExp(A, k / 2);
        double[][] res = matrixMult(half, half);
        if (k % 2 == 0) return res;
        res = matrixMult(res, A);
        return res;
    }
}