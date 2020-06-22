// For range sum query.
class BIT {
    int[] arr;
    BIT(int[] arr) {
        this.arr = new int[arr.length + 1];
        System.arraycopy(arr, 0, this.arr, 1, arr.length);
    }

    // Queries the closed range [0, i]
    int query(int i) {
        i++;
        int sum = 0;
        while (i >= 1) {
            sum += arr[i];
            i -= i & -i;
        }
        return sum;
    }

    void add(int i, int value) {
        i++;
        while (i < arr.length) {
            arr[i] += value;
            i += i & -i;
        }
    }
}