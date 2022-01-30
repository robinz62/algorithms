// This file contains various segment trees.
// 1) compact segment tree (from codeforces)
// 2) standard segment tree with friendly indexing
// 3) segment tree with range updates

// TODO: object-based segment tree, to support sparse

// Compact segment tree (2n space) based on
// http://codeforces.com/blog/entry/18051. Doesn't allow more complicated
// behavior like traversing from root.
class SegmentTree {
    int n;
    int[] st;

    // Modify identity and combine together.
    int identity = 0;
    int combine(int a, int b) {
        return a + b;
    }

    SegmentTree(int[] arr) {
        n = arr.length;
        st = new int[n*2];
        for (int i = 0; i < n; i++) {
            st[n + i] = arr[i];
        }
        build();
    }

    void build() {
        for (int i = n - 1; i > 0; i--) {
            st[i] = combine(st[i*2], st[i*2+1]);
        }
    }

    void modify(int i, int value) {
        st[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            st[i] = combine(st[i*2], st[i*2+1]);
        }
    }

    // Note: input range is half-open [l, r)
    int query(int l, int r) {
        l += n;
        r += n;
        int resl = identity;
        int resr = identity;
        while (l < r) {
            if ((l & 1) > 0) resl = combine(resl, st[l++]);
            if ((r & 1) > 0) resr = combine(st[--r], resr);
            l /= 2;
            r /= 2;
        }
        return combine(resl, resr);
    }
}

// A standard segment tree, where node i has children i*2 and i*2+1. This
// allows traversing up/down the segment tree as needed for more involved
// types of queries.
class SegmentTree {
    int n;
    int[] st;

    // Modify identity and combine together.
    int identity = 0;
    int combine(int a, int b) {
        return a + b;
    }

    SegmentTree(int[] arr) {
        n = arr.length;
        st = new int[4*n];
        build(arr, 1, 0, n-1);
    }

    void build(int[] arr, int i, int l, int r) {
        if (l == r) st[i] = arr[l];
        else {
            int m = l + (r - l) / 2;
            build(arr, 2*i, l, m);
            build(arr, 2*i+1, m+1, r);
            st[i] = combine(st[2*i], st[2*i+1]);
        }
    }

    void modify(int i, int value) {
        modify(1, 0, n-1, i, value);
    }

    private void modify(int v, int l, int r, int i, int value) {
        if (l == r) t[v] = value;
        else {
            int m = l + (r - l) / 2;
            if (i <= m) modify(2*v, l, m, i, value);
            else modify(2*v+1, m+1, r, i, value);
            t[v] = combine(t[2*v], t[2*v+1]);
        }
    }

    // Note: input range is closed [l, r]
    int query(int l, int r) {
        return query(1, 0, n-1, l, r);
    }

    private int query(int v, int l, int r, int ql, int qr) {
        if (ql > qr) return identity;
        if (l == ql && r == qr) return st[v];
        int m = l + (r - l) / 2;
        int resl = query(2*v, l, m, ql, Math.min(qr, m));
        int resr = query(2*v+1, m+1, r, Math.max(ql, m+1), qr);
        return combine(resl, resr);
    }
}

// Segment tree which supports range updates. Note lazyFunction, which governs
// how updates are applied to node values as well as other lazy values.
// 
// Examples:
//   for range add, return currVal + lazyVal
//   for range assign, return lazyVal
class SegmentTree {
    int n;
    int[] st;
    boolean[] hasLazy;
    int[] lazy;

    // Adjust identity, combine, and lazyFunction together to suit needs.
    // Default code below is range minimum query with range addition.
    int identity = 0;
    int combine(int a, int b) {
        return Math.min(a, b);
    }
    int lazyFunction(int currVal, int lazyVal) {
        return currVal + lazyVal;
    }

    SegmentTree(int[] arr) {
        n = arr.length;
        st = new int[4*n];
        hasLazy = new boolean[4*n];
        lazy = new int[4*n];
        build(arr, 1, 0, n-1);
    }

    private void build(int[] arr, int i, int l, int r) {
        if (l == r) st[i] = arr[l];
        else {
            int m = l + (r - l) / 2;
            build(arr, 2*i, l, m);
            build(arr, 2*i+1, m+1, r);
            st[i] = combine(st[2*i], st[2*i+1]);
        }
    }

    private void push(int v) {
        if (!hasLazy[v]) return;
        st[2*v] = lazyFunction(st[2*v], lazy[v]);
        lazy[2*v] = lazyFunction(lazy[2*v], lazy[v]);
        st[2*v+1] = lazyFunction(st[2*v+1], lazy[v]);
        lazy[2*v+1] = lazyFunction(lazy[2*v+1], lazy[v]);
        hasLazy[2*v] = hasLazy[2*v+1] = true;
        hasLazy[v] = false;
        lazy[v] = 0;
    }

    void modify(int l, int r, int value) {
        modify(1, 0, n-1, l, r, value);
    }

    private void modify(int v, int l, int r, int ql, int qr, int value) {
        if (ql > qr) return;
        if (l == ql && r == qr) {
            st[v] = lazyFunction(st[v], value);
            lazy[v] = lazyFunction(lazy[v], value);
            hasLazy[v] = true;
        } else {
            push(v);
            int m = l + (r - l) / 2;
            modify(2*v, l, m, ql, Math.min(qr, m), value);
            modify(2*v+1, m+1, r, Math.max(ql, m+1), qr, value);
            st[v] = combine(st[2*v], st[2*v+1]);
        }
    }

    // Note: input range is closed [l, r]
    int query(int l, int r) {
        return query(1, 0, n-1, l, r);
    }

    private int query(int v, int l, int r, int ql, int qr) {
        if (ql > qr) return identity;
        if (l == ql && r == qr) return st[v];
        push(v);
        int m = l + (r - l) / 2;
        int resl = query(2*v, l, m, ql, Math.min(qr, m));
        int resr = query(2*v+1, m+1, r, Math.max(ql, m+1), qr);
        return combine(resl, resr);
    }
}