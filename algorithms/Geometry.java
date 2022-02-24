import java.util.*;

public class Geometry {

    // Rotates a matrix 45 degrees clockwise. Note the matrix locations do NOT
    // correspond to cartesian coordinates.
    //
    // Input
    // 01 02 03 04 
    // 05 06 07 08
    // 09 10 11 12
    //
    // Output
    // .. .. 01 .. .. ..
    // .. 05 .. 02 .. ..
    // 09 .. 06 .. 03 ..
    // .. 10 .. 07 .. 04
    // .. .. 11 .. 08 ..
    // .. .. .. 12 .. ..
    int[][] rotateMatrix45Clockwise(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] rotated = new int[m + n - 1][m + n - 1];
        int offset = -m + 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rotated[i + j][-i + j - offset] = mat[i][j];
            }
        }
        return rotated;
    }

    // A companion to the above [rotateMatrix45Clockwise], this function
    // returns where the original location (i, j) maps to.
    int[] rotatePoint45Clockwise(int[] point, int m, int n) {
        return new int[]{point[0] + point[1], -point[0] + point[1] + m - 1};
    }
}