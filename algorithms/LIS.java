import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Implementations of the solution to the Longest Increasing Subsequence problem
 * for various input types where the subsequences are strictly increasing. Runs
 * in O(n log n).
 */
public class LIS {
    
    /**
     * Returns an array of the indices of the elements comprising a longest
     * increasing subsequence.
     * 
     * dp[i] is the index of the smallest element that terminates an increasing
     * subsequence of length i + 1.
     * 
     * to[i] contains the index of the previous element for the increasing
     * subsequence ending at index i. used for reconstructing the solution.
     * 
     * len stores the length of the longest increasing subsequence found.
     */
    public static <T> int[] lis(T[] nums, Comparator<? super T> c) {
        Integer[] dp = new Integer[nums.length];
        int[] to = new int[nums.length];
        int len = 0;

        for (int i = 0; i < nums.length; ++i) {
            int idx = Arrays.binarySearch(dp, 0, len, i,
                new Comparator<Integer>() {
                    public int compare(Integer a, Integer b) {
                        return c.compare(nums[a], nums[b]);
                    }
                });
            if (idx < 0) {
                idx = -idx - 1;
                dp[idx] = i;
                to[i] = idx > 0 ? dp[idx - 1] : -1;
                if (idx == len) {
                    ++len;
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
     * Returns the length of a longest increasing subsequence for when
     * reconstructing the solution is not necessary. It has a slightly simpler
     * dynamic programming definition and is much more concise.
     * 
     * dp[i] is the smallest element that terminates an increasing subsequence
     * of length i + 1.
     * 
     * len stores the length of the longest increasing subsequence found.
     */
    public static int lengthOfLis(int[] arr) {
        int[] dp = new int[arr.length];
        int len = 0;

        for (int i : arr) {
            int idx = Arrays.binarySearch(dp, 0, len, i);
            if (idx < 0) {
                idx = -idx - 1;
                dp[idx] = i;
                if (idx == len) {
                    ++len;
                }
            }
        }
        return len;
    }

    /*
     * Overloaded variants of lis(...) are available below for convenience. I
     * won't bother overloading the lengthOfLis(...) method. The length of
     * course can be found by taking the length of the result array.
     */

    public static <T extends Comparable<? super T>> int[] lis(T[] nums) {
        Integer[] dp = new Integer[nums.length];
        int[] to = new int[nums.length];
        int len = 0;

        for (int i = 0; i < nums.length; ++i) {
            int idx = Arrays.binarySearch(dp, 0, len, i,
                new Comparator<Integer>() {
                    public int compare(Integer a, Integer b) {
                        return nums[a].compareTo(nums[b]);
                    }
                });
            if (idx < 0) {
                idx = -idx - 1;
                dp[idx] = i;
                to[i] = idx > 0 ? dp[idx - 1] : -1;
                if (idx == len) {
                    ++len;
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

    public static int[] lis(int[] nums) {
        Integer[] dp = new Integer[nums.length];
        int[] to = new int[nums.length];
        int len = 0;

        for (int i = 0; i < nums.length; ++i) {
            int idx = Arrays.binarySearch(dp, 0, len, i,
                new Comparator<Integer>() {
                    public int compare(Integer a, Integer b) {
                        return Integer.compare(nums[a], nums[b]);
                    }
                });
            if (idx < 0) {
                idx = -idx - 1;
                dp[idx] = i;
                to[i] = idx > 0 ? dp[idx - 1] : -1;
                if (idx == len) {
                    ++len;
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

    public static <T extends Comparable<? super T>> int[] lis(List<T> nums) {
        Integer[] dp = new Integer[nums.size()];
        int[] to = new int[nums.size()];
        int len = 0;

        for (int i = 0; i < nums.size(); ++i) {
            int idx = Arrays.binarySearch(dp, 0, len, i,
                new Comparator<Integer>() {
                    public int compare(Integer a, Integer b) {
                        return nums.get(a).compareTo(nums.get(b));
                    }
                });
            if (idx < 0) {
                idx = -idx - 1;
                dp[idx] = i;
                to[i] = idx > 0 ? dp[idx - 1] : -1;
                if (idx == len) {
                    ++len;
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

    public static <T> int[] lis(List<T> nums, Comparable<? super T> c) {
        Integer[] dp = new Integer[nums.length];
        int[] to = new int[nums.length];
        int len = 0;

        for (int i = 0; i < nums.length; ++i) {
            int idx = Arrays.binarySearch(dp, 0, len, i,
                new Comparator<Integer>() {
                    public int compare(Integer a, Integer b) {
                        return c.compare(nums.get(a), nums.get(b));
                    }
                });
            if (idx < 0) {
                idx = -idx - 1;
                dp[idx] = i;
                to[i] = idx > 0 ? dp[idx - 1] : -1;
                if (idx == len) {
                    ++len;
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