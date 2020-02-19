/**
 * A segment tree that supports range sum queries. Adapted from
 * http://codeforces.com/blog/entry/18051
 *
 */
public class SegmentTree {
    private int n;
    private int[] arr;

    public SegmentTree(int[] arr) {
        n = arr.length;
        this.arr = new int[n * 2];
        for (int i = 0; i < n; i++) {
            this.arr[n + i] = arr[i];
        }
        build();
    }

    private void build() {
        for (int i = n - 1; i > 0; i--) {
            arr[i] = arr[i * 2] + arr[i * 2 + 1];
        }
    }

    /**
     * Modifies the value of the array at the specified index.
     * 
     * @param i     the index to modify.
     * @param value the new value to set.
     */
    public void modify(int i, int value) {
        arr[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            arr[i] = arr[i * 2] + arr[i * 2 + 1];
        }
    }

    /**
     * Returns the sum of elements in the specified half-open range [l, r).
     * 
     * @param l the range's left bound (inclusive).
     * @param r the range's right bound (exclusive).
     * @return the sum of the elements in the range [l, r).
     */
    public int query(int l, int r) {
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