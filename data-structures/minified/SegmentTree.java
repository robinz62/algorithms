// For range sum query.
class SegmentTree {
    int n;
    int[] arr;

    SegmentTree(int[] arr) {
        n = arr.length;
        this.arr = new int[n*2];
        for (int i = 0; i < n; i++) {
            this.arr[n + i] = arr[i];
        }
        build();
    }

    void build() {
        for (int i = n - 1; i > 0; i--) {
            arr[i] = arr[i*2] + arr[i*2+1];
        }
    }

    void modify(int i, int value) {
        arr[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            arr[i] = arr[i*2] + arr[i*2+1];
        }
    }

    int query(int l, int r) {
        l += n;
        r += n;
        int sum = 0;
        while (l < r) {
            if ((l & 1) > 0) {
                sum += arr[l++];
            }
            if ((r & 1) > 0) {
                sum += arr[--r];
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
    int[] arr;

    SegmentTree(int[] arr) {
        n = arr.length;
        this.arr = new int[n*2];
        for (int i = 0; i < n; i++) {
            this.arr[n + i] = arr[i];
        }
        build();
    }

    void build() {
        for (int i = n - 1; i > 0; i--) {
            arr[i] = Math.min(arr[i*2], arr[i*2+1]);
        }
    }

    void modify(int i, int value) {
        arr[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            arr[i] = Math.min(arr[i*2], arr[i*2+1]);
        }
    }

    int query(int l, int r) {
        l += n;
        r += n;
        int min = arr[l];
        while (l < r) {
            if ((l & 1) > 0) {
                min = Math.min(min, arr[l++]);
            }
            if ((r & 1) > 0) {
                min = Math.min(min, arr[--r]);
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
    int[] arr;

    SegmentTree(int[] arr) {
        n = arr.length;
        this.arr = new int[n*2];
        for (int i = 0; i < n; i++) {
            this.arr[n + i] = arr[i];
        }
        build();
    }

    void build() {
        for (int i = n - 1; i > 0; i--) {
            arr[i] = Math.max(arr[i*2], arr[i*2+1]);
        }
    }

    void modify(int i, int value) {
        arr[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            arr[i] = Math.max(arr[i*2], arr[i*2+1]);
        }
    }

    int query(int l, int r) {
        l += n;
        r += n;
        int max = arr[l];
        while (l < r) {
            if ((l & 1) > 0) {
                max = Math.max(max, arr[l++]);
            }
            if ((r & 1) > 0) {
                max = Math.max(max, arr[--r]);
            }
            l /= 2;
            r /= 2;
        }
        return sum;
    }
}