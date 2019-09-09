import java.util.*;

// TODO: these functions should be tested

/**
 * Contains various basic algorithms related to integer factors.
 */
public class Factors {

    /**
     * Returns a list containing the factors of the specified number (not in
     * any particular order).
     * 
     * @param n the number to find factors of.
     * @return the list of factors.
     */
    public List<Integer> factors(int n) {
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
     * Returns the prime factorization of the specified number O(sqrt n)
     * 
     * @param n the number to prime-factorize.
     * @return a map containing the prime factors and their counts.
     */
    public Map<Integer, Integer> primeFactorize(int n) {
        Map<Integer, Integer> pFactors = new HashMap<>();
        while (n % 2 == 0) {
            pFactors.put(2, pFactors.getOrDefault(2, 0) + 1);
            n /= 2;
        }
        for (int i = 3; i * i <= n; i += 2) {
            while (n % i == 0) {
                pFactors.put(i, pFactors.getOrDefault(i, 0) + 1);
                n /= i;
            }
        }
        if (n > 2) {
            pFactors.put(n, pFactors.getOrDefault(n, 0) + 1);
        }
        return pFactors;
    }

    /**
     * Returns the prime factorization of the specified number, using the
     * precomputed smallest-prime array (sp, where sp[i] is the smallest prime
     * factor of i) for O(log n) time.
     * 
     * @param n the number to prime-factorize.
     * @param sp the smallest-prime array
     * @return a map containing the prime factors and their counts.
     */
    public Map<Integer, Integer> primeFactorize(int n, int[] sp) {
        Map<Integer, Integer> pFactors = new HashMap<>();
        while (n != 1) {
            pFactors.put(sp[n], pFactors.getOrDefault(sp[n], 0) + 1);
            n /= sp[n];
        }
        return pFactors;
    }

    /**
     * Computes and returns an array prime, where prime[i] is true if i is
     * prime and false otherwise (for i <= n). Implemented using linear sieve.
     * 
     * @param n the upper limit of the sieve.
     * @return the prime array.
     */
    public boolean[] sieve(int n) {
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
     * Computes and returns an array sp, where sp[i] is the smallest prime
     * factor of i (for 2 <= i <= n). Implemented using sieve of eratosthenes.
     * 
     * @param n the upper limit of the sieve.
     * @return the smallest-prime array.
     */
    public void smallestPrime(int n) {
        sp = new int[n + 1];
        for (int i = 2; i <= n; i += 2) sp[i] = 2;
        for (int i = 3; i <= n; i += 2) sp[i] = i;
        for (int i = 3; i * i <= n; i += 2)
            if (sp[i] == i)
                for (int j = i * i; j <= n; j += i)
                    if (sp[j] == j)
                        sp[j] = i;
    }

    /**
     * Returns the greatest common denominator of the specified numbers.
     * 
     * @param a the first number.
     * @param b the second number.
     * @return the greatest common denominator.
     */
    public int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
    }

    /**
     * Returns the least common multiple of the specified numbers.
     * 
     * @param a the first number.
     * @param b the second number.
     * @return the least common multiple.
     */
    public int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }
}