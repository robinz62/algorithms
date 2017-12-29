public class KMP {
    
    public int[] getKmpArray(String tgt) {
        int[] dp = new int[tgt.length()];
        int i = 1;
        int j = 0;
        while (i < dp.length) {
            if (tgt.charAt(i) == tgt.charAt(j)) {
                dp[i] = j + 1;
                ++i;
                ++j;
            } else if (j == 0) {
                ++i;
            } else {
                j = dp[j];
            }
        }
    }

    public static void main(String[] args) {
        
    }
}