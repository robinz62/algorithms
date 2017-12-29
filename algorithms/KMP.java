/**
 * Implementation of the Knuth-Morris-Pratt string searching algorithm.
 */
public class KMP {

    /**
     * Returns the first occurence of word in text.
     */
    public static int search(String word, String text) {
        int[] kmp = getKmpArray(word);
        int i = 0;
        int j = 0;
        while (i < text.length()) {
            if (text.charAt(i) == word.charAt(j)) {
                ++i;
                ++j;
                if (j == word.length()) {
                    return i - word.length();
                }
            } else if (j != 0) {
                j = kmp[j - 1];
            } else {
                ++i;
            }
        }
        return -1;
    }

    /**
     * Returns the kmp array where kmp[i] is the length of the longest prefix
     * which is also a suffix for the string ending at index i.
     */
    public static int[] getKmpArray(String word) {
        int[] kmp = new int[word.length()];
        int i = 1;
        int j = 0;
        while (i < kmp.length) {
            if (word.charAt(i) == word.charAt(j)) {
                kmp[i] = j + 1;
                ++i;
                ++j;
            } else if (j != 0) {
                j = kmp[j - 1];
            } else {
                ++i;
            }
        }
        return kmp;
    }

    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("enter word: ");
        String word = sc.nextLine();
        System.out.print("enter text: ");
        String text = sc.nextLine();

        System.out.print("kmp array : ");
        System.out.println(java.util.Arrays.toString(getKmpArray(word)));
        System.out.print("word index: ");
        System.out.println(search(word, text));
    }
}