// For range sum query.
class SegmentTree {
    int n;
    int[] st;

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
            st[i] = st[i*2] + st[i*2+1];
        }
    }

    void modify(int i, int value) {
        st[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            st[i] = st[i*2] + st[i*2+1];
        }
    }

    // Note: input range is half-open [l, r)
    int query(int l, int r) {
        l += n;
        r += n;
        int sum = 0;
        while (l < r) {
            if ((l & 1) > 0) {
                sum += st[l++];
            }
            if ((r & 1) > 0) {
                sum += st[--r];
            }
            l /= 2;
            r /= 2;
        }
        return sum;
    }
}

// For range min query.
class SegmentTree {
    int n;
    int[] st;

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
            st[i] = Math.min(st[i*2], st[i*2+1]);
        }
    }

    void modify(int i, int value) {
        st[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            asti] = Math.min(st[i*2], st[i*2+1]);
        }
    }

    int query(int l, int r) {
        l += n;
        r += n;
        int min = st[l];
        while (l < r) {
            if ((l & 1) > 0) {
                min = Math.min(min, st[l++]);
            }
            if ((r & 1) > 0) {
                min = Math.min(min, st[--r]);
            }
            l /= 2;
            r /= 2;
        }
        return sum;
    }
}

// For range max query.
class SegmentTree {
    int n;
    int[] st;

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
            st[i] = Math.max(st[i*2], st[i*2+1]);
        }
    }

    void modify(int i, int value) {
        st[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            st[i] = Math.max(st[i*2], st[i*2+1]);
        }
    }

    int query(int l, int r) {
        l += n;
        r += n;
        int max = st[l];
        while (l < r) {
            if ((l & 1) > 0) {
                max = Math.max(max, st[l++]);
            }
            if ((r & 1) > 0) {
                max = Math.max(max, st[--r]);
            }
            l /= 2;
            r /= 2;
        }
        return sum;
    }
}