import java.util.*;

// TODO: mobius function, totient function

/**
 * Contains various basic algorithms related to integer factors.
 */
public class Factors {

    /**
     * Returns a list containing the factors of the specified number (n) in
     * arbitrary order. O(sqrt(n))
     */
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

    /**
     * Returns the prime factorization of the specified number (n) as a map from
     * prime factor to count. O(sqrt(n))
     */
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

    /**
     * Returns the prime factorization of the specified number (n) using a
     * precomputed smallest-prime array (sp), where sp[i] is the smallest prime
     * factor of i. sp must have size at least n + 1. O(log n)
     */
    public static Map<Integer, Integer> primeFactorize(int n, int[] sp) {
        Map<Integer, Integer> factors = new HashMap<>();
        while (n != 1) {
            factors.put(sp[n], factors.getOrDefault(sp[n], 0) + 1);
            n /= sp[n];
        }
        return factors;
    }

    /**
     * Computes and returns an array prime, where prime[i] is true if i is prime and
     * false otherwise (for i <= n). Implemented using linear sieve. O(n)
     */
    public static boolean[] sieve(int n) {
        List<Integer> primes = new ArrayList<>();
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                primes.add(i);
                for (int j = 0; j < primes.size() && i * primes.get(j) <= n; j++) {
                    prime[i * primes.get(j)] = false;
                    if (i % primes.get(j) == 0) break;
                }
            }
        }
        return prime;
    }

    /**
     * Computes and returns an array sp, where sp[i] is the smallest prime factor of
     * i (for 2 <= i <= n). Implemented using sieve of eratosthenes. O(n loglog(n))
     */
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

    /**
     * Returns the greatest common denominator of the specified numbers (a) and (b). O(log n).
     */
    public static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
    }

    /**
     * Returns the least common multiple of the specified numbers (a) and (b). O(log n).
     */
    public static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    /**
     * Returns Euler's totient function of the input number. O(sqrt(n)).
     */
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

    /**
     * Make sure everything works.
     */
    public static void main(String[] args) {
        FactorsTest.run();
        System.out.println("DONE");
    }
}

/**
 * Tests
 */
class FactorsTest {
    static int[] sp;

    public static void run() {
        sp = Factors.smallestPrime(200000);

        factorsTest(1, Arrays.asList(1));
        factorsTest(2, Arrays.asList(1, 2));
        factorsTest(4, Arrays.asList(1, 2, 4));
        factorsTest(7, Arrays.asList(1, 7));
        factorsTest(12, Arrays.asList(1, 2, 3, 4, 6, 12));

        Map<Integer, Integer> map = new HashMap<>();
        primeFactorizeTest(1, map);
        map.put(2, 1);
        primeFactorizeTest(2, map);
        map.clear();
        map.put(3, 1);
        primeFactorizeTest(3, map);
        map.put(2, 1);
        primeFactorizeTest(6, map);
        map.put(2, 2);
        primeFactorizeTest(12, map);
        map.put(5, 2);
        primeFactorizeTest(300, map);

        gcdTest(1, 1, 1);
        gcdTest(1, 2, 1);
        gcdTest(2, 8, 2);
        gcdTest(12, 18, 6);
        gcdTest(5, 5, 5);
    }

    static void factorsTest(int n, List<Integer> expected) {
        List<Integer> actual = Factors.factors(n);
        Collections.sort(actual);
        Collections.sort(expected);
        if (!actual.equals(expected)) {
            System.out.println("FAIL: factors(" + n + ")");
            System.out.println("  expected: " + expected);
            System.out.println("  actual:   " + actual);
        }
    }

    static void primeFactorizeTest(int n, Map<Integer, Integer> expected) {
        Map<Integer, Integer> actual = Factors.primeFactorize(n);
        if (!actual.equals(expected)) {
            System.out.println("FAIL: primeFactorize(" + n + ")");
            System.out.println("  expected: " + expected);
            System.out.println("  actual:   " + actual);
        }
        actual = Factors.primeFactorize(n, sp);
        if (!actual.equals(expected)) {
            System.out.println("FAIL: primeFactorize(" + n + ", sp)");
            System.out.println("  expected: " + expected);
            System.out.println("  actual:   " + actual);
        }
    }

    static void gcdTest(int n, int m, int expected) {
        int actual = Factors.gcd(n, m);
        if (actual != expected) {
            System.out.println("FAIL: gcd(" + n + ", " + m + ")");
            System.out.println("  expected: " + expected);
            System.out.println("  actual:   " + actual);
        }
    }
}