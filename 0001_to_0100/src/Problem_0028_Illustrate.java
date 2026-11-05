public class Problem_0028_Illustrate {
    public static void main(String[] args) {
        Problem_0028_Illustrate obj = new Problem_0028_Illustrate();
//        System.out.println(obj.strStr("sadbutsad", "sad"));
//        System.out.println(obj.strStr("leetcode", "leeto"));
        System.out.println(obj.strStr("jjoojjoojjoojjen", "oojjoojjen"));
    }

    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();

        int[] lps = buildLPS(needle);
        int i = 0; // haystack pointer
        int j = 0; // needle pointer

        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;

                if (j == m) {
                    return i - m;
                }
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }

        return -1;
    }

    private int[] buildLPS(String needle) {
        int[] lps = new int[needle.length()];
        int len = 0;
        int i = 1;

        while (i < needle.length()) {
            if (needle.charAt(i) == needle.charAt(len)) {
                lps[i] = ++len;
                i++;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }

        return lps;
    }
}