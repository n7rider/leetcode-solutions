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
public class    Problem_0010_4 {
    public static void main(String[] args) {
        Problem_0010_4 obj = new Problem_0010_4();
//        System.out.println(obj.isMatch("aa", "a")); // F
//        System.out.println(obj.isMatch("ab", "ab")); // T
//        System.out.println(obj.isMatch("aaaa", "ab")); // F
//
//        System.out.println();
//        System.out.println(obj.isMatch("aa", "a*")); // T
//        System.out.println(obj.isMatch("abbbbb", "ab*")); // T
//        System.out.println(obj.isMatch("abbbbbaaa", "ab*a")); // F. Needs greedy check and optimized stop.
//
//        System.out.println();
//        System.out.println(obj.isMatch("ab", ".*")); // T
//        System.out.println(obj.isMatch("abcdefe", ".*")); // T
//        System.out.println(obj.isMatch("abab", ".*")); // T
//        // DOESN'T WORK
//        System.out.println(obj.isMatch("mississippi", "mis*is*p*.")); // F. Don't move stringPos if nextChar is *
//        System.out.println(obj.isMatch("aab", "c*a*b")); // T. Involves greedy check, ignore if next ele is a *
////
//        System.out.println();
//        System.out.println(obj.isMatch("ab", ".*c")); // F
//        System.out.println(obj.isMatch("aaa", "a*a")); // T
//        System.out.println(obj.isMatch("abcd", ".*cd")); // T. Needs Optimized stop at the char after next char. Doesn't work, but should it?
//
//        System.out.println(obj.isMatch("abcdefg", "a.*g")); // T. Lengths are disproportionate, but is still true
//
//        System.out.println();
//        System.out.println(obj.isMatch("abb", "a.*b"));


        System.out.println(obj.isMatch("aab", "c*a*b"));

    }

    public boolean isMatch(String s, String p) {
        int flagsRowL = p.length() + 1;
        int flagsColL = s.length() + 1;
       boolean[][] flags = new boolean[flagsRowL][flagsColL];

       flags[0][0] = true;

       // j, 0 = j-2,0
       // c*a*b | aab breaks because it's false until c(*) | (a), but suddenly succeeds when facing c*(a), (a)
        // So on facing (*) set, flags[i][j-2]=0 e.g., for c(*)a*b | aab, set flags[i][0] = flags[i-2][0]=true, so
        // basically copying over 0, 0.
        // If we're halfway through e.g., c*a(*)b | aab, we'll copy over previous such manipulation
        // For another variant e.g., c*aa(*)b | aab, we'll copy over the value of c*(a)a*b | aab, which would have inherited the basic
        // version above.
        for(int i = 1; i <= p.length(); i++) {
            char pC = p.charAt(i-1);
            if(pC == '*') {
                flags[i][0] = flags[i-2][0];
            }
        }

       for(int i = 1; i <= p.length(); i++) {
           for(int j = 1; j <= s.length(); j++) {
               char pC = p.charAt(i - 1);
               char sC = s.charAt(j - 1);
               if(pC == '.' || pC == sC) {
                   flags[i][j] = flags[i -1][j-1];
               }

               if(pC == '*') {
                   // Case 1: a* has 0 chars in string => If pc(i-3)==sC(j-1) then copy over flags[i-2][j]. No need the if cond
                   //         actually. If za(*)cd | (z)d are considered, you just copy over flags[i-2][j]
                   // Case 2a: a* has chars in string and we're halfway through  => If pc(i-2)==sc(j-1) then copy over
                   //           flags[i-1][j-1]. e.g., ab(*)c | ab(b)bbbc OR ab(*)c | abbb(b)bc Once again, no need to compare
                   // Case 2b: a* has chars in string and this is the first occ in string e.g., ab(*)c | a(b)bbbbc.
                   //           Copy over flags[i-1][j]
                   // Case 3a: pattern has .* and we're halfway through e.g., .(*) | a(b) - Comparing . and a works. Copy over
                   //           flags[i-1][j-1]
                   // Case 3b: pattern has .* and this is the first occ => copy over flags[i][j-1] e.g., .(*) | (a) ||| .(*) | a(b)

                   // a.*b | abb

                   // Case 1: Zero usage of x*
                   flags[i][j] = flags[i-2][j];
                   char pcPrev = p.charAt(i - 2);

                   // Case 2: Add case 1's output too. We don't want to remove it if that's what actually happened
                   if(pcPrev == sC) {
                       flags[i][j] = flags[i-2][j] | flags[i][j-1] | flags[i-1][j];
                   }

                   // Case 3: Add case 1's output too.
                   if(pcPrev == '.') {
                       flags[i][j] = flags[i-2][j] | flags[i][j-1] | flags[i-1][j-1];
                   }
               }
           }
       }

       return flags[flagsRowL - 1][flagsColL - 1];
       }
}

/*
We're creating an boolean 2D array of sLen+1, pLen+1, and we'll mark off elements as we go.
This prevents going back and further -- this just makes it complex

flag[][] = boolean[sLen+1][pLen+1]

// Pattern matches when no chars are considered from either
// Also serves as a seed to continue polling true if prev conditions are true so far
flag[0][0] = true


for i = 0; i < sLen; i++
    for j = 0; j < pLen; j++
        // ab & ab => Just copy prev
        // ab & .b =>
        if(s.charAt(i) == p.charAt(j) || p.charAt(j) == '.')
            flag[i+1][j+1] = flag[i][j]

        // aa & a* => Copy the value of a==a
        // aaaa & a* => At i=2, copy check from '2nd a & *' to '3rd a & *'
        // abcd & .* => At
        if(p.charAt(j) == '*')
            // flag[i][j] just updates diagonals. i,j+1 copies flag for increasing s length
            // If we have abcdefg & a.*g, i, j check may not work but the i,j+1 will
            flag[i+1][j+1] = flag[i][j] | flag[i][j + 1]

return flag[sLen][pLen]


Notes:
After comparing with Leetcode, we can merge case 2 and 3 into the following
```
    if (prev == '.' || prev == sC) {
        flags[i][j] |= flags[i][j-1];
    }
```

This is because [i-1][j] (i.e., if it worked for pattern until before the *, it works fine. Just add possibility of zero usage of *)

 */