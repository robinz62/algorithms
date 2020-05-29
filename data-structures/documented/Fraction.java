/**
 * A class representing an exact fraction. This class is immutable.For purposes of equality testing,
 * all fractions constructed with a denominator of 0 are considered equal.
 * 
 * This implementation uses 64 bit integers internally for data and operations.
 * Hence, the product of any numerator/denominator should be < 2^63-1.
 */
public class Fraction implements Comparable<Fraction> {
    private final long n;
    private final long d;

    public Fraction(long n, long d) {
        if (n == 0) {
            this.n = 0;
            this.d = 1;
            return;
        }
        if (d == 0) {
            this.n = 1;
            this.d = 0;
            return;
        }
        int sign = n * d < 0 ? -1 : 1;
        n = Math.abs(n);
        d = Math.abs(d);
        long g = gcd(n, d);
        this.n = sign * n / g;
        this.d = d / g;
    }

    /**
     * Returns a fraction with the value of f1 + f2.
     */
    static Fraction add(Fraction f1, Fraction f2) {
        return new Fraction(f1.n*f2.d+f1.d*f2.n, f1.d*f2.d);
    }

    /**
     * Returns a fraction with the value of f1 - f2.
     */
    static Fraction subtract(Fraction f1, Fraction f2) {
        return new Fraction(f1.n*f2.d-f1.d*f2.n, f1.d*f2.d);
    }

    /**
     * Returns a fraction with the value of f1 * f2.
     */
    static Fraction multiply(Fraction f1, Fraction f2) {
        return new Fraction(f1.n*f2.n, f1.d*f2.d);
    }

    /**
     * Returns a fraction with the value of f1 / f2.
     */
    static Fraction divide(Fraction f1, Fraction f2) {
        return new Fraction(f1.n*f2.d, f1.d*f2.n);
    }

    public boolean equals(Object o) {
        if (!(o instanceof Fraction)) return false;
        Fraction f = (Fraction) o;
        return n == f.n && d == f.d;
    }

    public int compareTo(Fraction f) {
        return Long.compare(n*f.d, f.n*d);
    }

    public String toString() {
        return n + "/" + d;
    }

    private static long gcd(long a, long b) {
        return a == 0 ? b : gcd(b % a, a);
    }
}