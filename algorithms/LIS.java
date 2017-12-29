/**
 * Implementations of the solution to the Longest Increasing Subsequence problem
 * for various input types where the subsequences are strictly increasing. Runs
 * in O(n log n).
 */
public class LIS {
    
    /**
     * Returns a list of the indices of the elements comprising a longest
     * increasing subsequence.
     */
    public static int[] lis(int[] arr) {
        // dp[i] is the index of the smallest element that terminates an
        // increasing subsequence of length i + 1.
        Integer[] dp = new Integer[arr.length];
        dp[0] = 0;

        // to[i] contains the index of the previous element for the increasing
        // subsequence ending at index i. used for reconstructing the solution.
        int[] to = new int[arr.length];
        to[0] = -1;

        // stores the length of the longest increasing subsequence found.
        int len = 1;

        for (int i = 1; i < arr.length; ++i) {
            if (arr[i] > arr[dp[len - 1]]) {
                dp[len] = i;
                to[i] = dp[len - 1];
                ++len;
            } else {
                int idx = java.util.Arrays.<Integer>binarySearch(dp, 0, len, i,
                    new java.util.Comparator<Integer>() {
                        public int compare(Integer a, Integer b) {
                            return Integer.compare(arr[a], arr[b]);
                        }
                    });
                if (idx < 0) {
                    idx = -idx - 1;
                    dp[idx] = i;
                    to[i] = idx > 0 ? dp[idx - 1] : -1;
                }
            }
        }

        int[] indices = new int[len];
        int i = indices.length - 1;
        int curr = dp[len - 1];
        while (curr != -1) {
            indices[i--] = curr;
            curr = to[curr];
        }

        return indices;
    }

    /**
     * Returns the length of a longest increasing subsequence. Somewhat more
     * concise due to not needing to reconstruct the solution.
     */
    public static int lengthOfLis(int[] arr) {
        // dp[i] is the smallest element that terminates an increasing
        // subsequence of length i + 1.
        int[] dp = new int[arr.length];
        dp[0] = arr[0];

        // stores the length of the longest increasing subsequence found.
        int len = 1;

        for (int i = 1; i < arr.length; ++i) {
            if (arr[i] > dp[len - 1]) {
                dp[len] = arr[i];
                ++len;
            } else {
                int idx = java.util.Arrays.binarySearch(dp, 0, len, arr[i]);
                if (idx < 0) {
                    idx = -idx - 1;
                    dp[idx] = arr[i];
                }
            }
        }
        return len;
    }

    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("enter space-separated numbers: ");
        String line = sc.nextLine();
        sc.close();
        String[] numStr = line.split(" ");
        int[] nums = new int[numStr.length];
        for (int i = 0; i < numStr.length; ++i) {
            nums[i] = Integer.parseInt(numStr[i]);
        }
        
        int[] indices = lis(nums);
        int[] numbers = new int[indices.length];
        for (int i = 0; i < indices.length; ++i) {
            numbers[i] = nums[indices[i]];
        }
        System.out.print("indices: ");
        System.out.println(java.util.Arrays.toString(indices));
        System.out.print("numbers: ");
        System.out.println(java.util.Arrays.toString(numbers));
    }
}