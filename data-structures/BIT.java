class BIT {
    int[] bit;
    BIT(int n) {
        bit = new int[n+1];
    }
    BIT(int[] arr) {
        bit = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            add(i, arr[i]);
        }
    }

    // Queries the closed range [0, i]
    int query(int i) {
        i++;
        int sum = 0;
        while (i >= 1) {
            sum += bit[i];
            i -= i & -i;
        }
        return sum;
    }

    void add(int i, int value) {
        i++;
        while (i < bit.length) {
            bit[i] += value;
            i += i & -i;
        }
    }
}

// 2D BIT
class BIT2D {
    int[][] bit;
    BIT2D(int m, int n) {
        bit = new int[m+1][n+1];
    }
    BIT2D(int[][] arr) {
        bit = new int[arr.length + 1][arr[0].length + 1];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                add(i, j, arr[i][j]);
            }
        }
    }

    // Queries the closed range (0, 0) through (r, c)
    int query(int r, int c) {
        r++;
        c++;
        int sum = 0;
        for (int i = r; i >= 1; i -= i & -i) {
            for (int j = c; j >= 1; j -= j & -j) {
                sum += bit[i][j];
            }
        }
        return sum;
    }

    void add(int r, int c, int value) {
        r++;
        c++;
        for (int i = r; i <= bit.length; i += i & -i) {
            for (int j = c; j <= bit[0].length; j += j & -j) {
                bit[i][j] += value;
            }
        }
    }
}