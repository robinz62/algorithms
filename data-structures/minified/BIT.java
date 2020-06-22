// For range sum query.
class BIT {
    int[] arr;
    BIT(int[] arr) {
        this.arr = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            add(i, arr[i]);
        }
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