/*
Given an input string s and a pattern p, implement regular expression matching with support for '.' and '*' where:

    '.' Matches any single character.
    '*' Matches zero or more of the preceding element.

Return a boolean indicating whether the matching covers the entire input string (not partial).

Example 1:

Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".

Example 2:

Input: s = "aa", p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".

Example 3:

Input: s = "ab", p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".

Constraints:

    1 <= s.length <= 20
    1 <= p.length <= 20
    s contains only lowercase English letters.
    p contains only lowercase English letters, '.', and '*'.
    It is guaranteed for each appearance of the character '*', there will be a previous valid character to match.

 */
public class Problem_0010_3 {
    public static void main(String[] args) {
        Problem_0010_3 obj = new Problem_0010_3();
        System.out.println(obj.isMatch("aa", "a")); // F
        System.out.println(obj.isMatch("ab", "ab")); // T
        System.out.println(obj.isMatch("aaaa", "ab")); // F

        System.out.println();
        System.out.println(obj.isMatch("aa", "a*")); // T
        System.out.println(obj.isMatch("abbbbb", "ab*")); // T
        System.out.println(obj.isMatch("abbbbbaaa", "ab*a")); // F. Needs greedy check and optimized stop.

        System.out.println();
        System.out.println(obj.isMatch("ab", ".*")); // T
        System.out.println(obj.isMatch("abcdefe", ".*")); // T
        System.out.println(obj.isMatch("abab", ".*")); // T
        // DOESN'T WORK
        System.out.println(obj.isMatch("mississippi", "mis*is*p*.")); // F. Don't move stringPos if nextChar is *
        System.out.println(obj.isMatch("aab", "c*a*b")); // T. Involves greedy check, ignore if next ele is a *
//
        System.out.println();
        System.out.println(obj.isMatch("ab", ".*c")); // F
        System.out.println(obj.isMatch("aaa", "a*a")); // T
        System.out.println(obj.isMatch("abcd", ".*cd")); // T. Needs Optimized stop at the char after next char. Doesn't work, but should it?

    }

    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        // dp[i][j] = whether s[0..i-1] matches p[0..j-1]
        boolean[][] dp = new boolean[m + 1][n + 1];

        // Empty string matches empty pattern
        dp[0][0] = true;

        // Handle patterns like a*, a*b*, a*b*c*
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                char sc = s.charAt(i - 1);
                char pc = p.charAt(j - 1);

                // Case 1: direct match or '.'
                if (pc == '.' || pc == sc) {
                    dp[i][j] = dp[i - 1][j - 1];
                }

                // Case 2: '*'
                else if (pc == '*') {

                    // Zero occurrences of previous character
                    dp[i][j] = dp[i][j - 2];

                    // One or more occurrences
                    char prev = p.charAt(j - 2);

                    if (prev == '.' || prev == sc) {
                        dp[i][j] |= dp[i - 1][j];
                    }
                }
            }
        }

        return dp[m][n];
    }
}

/*
Why does this work simpler?
It tries all combinations of patterns
It tries to reuse previously found results

Where does it work when mine didn't?
The | gate to merge previous result with new discovery in case 2.  This is an OR of (result before we saw the a* in the pattern |
    result until the previous letter i.e., before we saw the * in the pattern)
This applies true to all combinations of the word that go up to the '*' in the pattern.
Handling the . is made very simple. Just mark the current char as true and move on.
Greedy check is complex with my approach. We don't know the right mix for * we need to use. This is where the OR gate comes in handy.

 */