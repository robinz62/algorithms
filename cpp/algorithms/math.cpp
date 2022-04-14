#include <bits/stdc++.h>

using namespace std;

// Computes (base)^(exponent) modulo (m).
int64_t mod_pow(int64_t base, int64_t exponent, int64_t m) {
    int64_t ans = 1;
    base = base % m;
    while (exponent > 0) {
        if ((exponent & 1) == 1) ans = ans * base % m;
        exponent >>= 1;
        base = base * base % m;
    } 
    return ans;
}

// Computes (a)^-1 modulo (m) using Fermat's little theorem.
int64_t mod_inverse(int64_t a, int64_t m) {
    return mod_pow(a, m - 2, m);
}

// Computes (a)^-1 modulo (m) using the gcd algorithm.
int64_t mod_inverse_gcd(int64_t a, int64_t m) {
    if (m == 1) return 0;

    int64_t m0 = m;
    int64_t y = 0;
    int64_t x = 1;
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

// Given (a) and (b), the solution to x*a + y*b = gcd(a, b) is stored in (x)
// and (y). The explicit return value is gcd(a, b).
int64_t extended_gcd(int64_t a, int64_t b, int64_t& x, int64_t& y) {
    x = 1, y = 0;
    int64_t x1 = 0, y1 = 1, a1 = a, b1 = b;
    while (b1 != 0) {
        int64_t q = a1 / b1;

        tie(x, x1) = make_tuple(x1, x - q * x1);
        tie(y, y1) = make_tuple(y1, y - q * y1);
        tie(a1, b1) = make_tuple(b1, a1 - q * b1);
    }
    return a1;
}

// Solves the system of equations x = a[i] (mod n[i]), returning the answer
// modulo lcm(n[i]).
int64_t crt(vector<int64_t> a, vector<int64_t> n) {
    auto normalize = [](int64_t& x, int64_t mod) {
        x %= mod;
        if (x < 0) x += mod;
    };

    int t = (int) a.size();
    for (int i = 0; i < t; i++) normalize(a[i], n[i]);

    int64_t ans = a[0];
    int64_t lcm = n[0];
    for (int i = 1; i < t; i++) {
        int64_t x, y;
        int64_t g = extended_gcd(lcm, n[i], x, y);
        if ((a[i] - ans) % g != 0) return -1;
        ans += x * (a[i] - ans) / g % (n[i] / g) * lcm;
        lcm = lcm / g * n[i];
        normalize(ans, lcm);
    }

    return ans;
}
