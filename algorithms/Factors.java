import java.util.*;

/**
 * Contains various basic algorithms related to number theory.
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
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                factors.add(i);
                factors.add(n / i);
            }
        }
        return factors;
    }

    /**
     * Returns the prime factorization of the specified number.
     * 
     * @param n the number to prime-factorize.
     * @return a map containing prime factors and their counts.
     */
    public Map<Integer, Integer> primeFactorization(int n) {
        Map<Integer, Integer> pFactors = new HashMap<>();
        while (n % 2 == 0) {
            pFactors.put(2, pFactors.getOrDefault(2, 0) + 1);
        }
        for (int i = 3; i * i < n; i += 2) {
            while (n % i == 0) {
                pFactors.put(i, pFactors.getOrDefault(i, 0) + 1);
            }
        }
        if (n > 2) {
            pFactors.put(n, pFactors.getOrDefault(n, 0) + 1);
        }
        return pFactors;
    }

    // TODO: log n factorization query (using sieve)

    /**
     * Returns the greatest common denominator of the specified numbers.
     * 
     * @param a the first number.
     * @param b the second number.
     * @return the greatest common denominator.
     */
    public int gcd(int a, int b) {
        if (a == 0) {
            return b;
        }
        return gcd(b % a, a);
    }
}