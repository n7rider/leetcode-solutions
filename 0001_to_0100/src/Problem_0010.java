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
public class Problem_0010 {
    static void main() {
        Problem_0010 obj = new Problem_0010();
//        System.out.println(obj.isMatch("aa", "a")); // F
//        System.out.println(obj.isMatch("ab", "ab")); // T
//        System.out.println(obj.isMatch("aaaa", "ab")); // F
//
//        System.out.println();
//        System.out.println(obj.isMatch("aa", "a*")); // T
//        System.out.println(obj.isMatch("abbbbb", "ab*")); // T
//        System.out.println(obj.isMatch("abbbbbaaa", "ab*a")); // Needs greedy check and optimized stop. Doesn't work, but should it?
//
//        System.out.println();
//        System.out.println(obj.isMatch("ab", ".*")); // T
//        System.out.println(obj.isMatch("abcdefe", ".*")); // T
//        System.out.println(obj.isMatch("abab", ".*")); // T
//        System.out.println(obj.isMatch("mississippi", "mis*is*p*.")); // F. Don't move stringPos if nextChar is *
//        System.out.println(obj.isMatch("aab", "c*a*b")); // T. Involves greedy check, ignore if next ele is a *
//
//        System.out.println();
//        System.out.println(obj.isMatch("ab", ".*c")); // F
        System.out.println(obj.isMatch("aaa", "a*a")); // T
//        System.out.println(obj.isMatch("abcd", ".*cd")); // Needs Optimized stop at the char after next char. Doesn't work, but should it?

    }

    public boolean isMatch(String s, String p) {
        int stringPos = 0;
        int patternPos = 0;
        while (patternPos < p.length()) {
            char currentSChar = stringPos < s.length() ? s.charAt(stringPos) : Character.MIN_VALUE;
            char c = p.charAt(patternPos);
            if (c >= 'a' && c <= 'z') {
                char nextPChar = patternPos < p.length() - 1 ? p.charAt(patternPos + 1) : Character.MIN_VALUE;
                // If '*' is the next char, let it go because if so, the current char can even occur 0 times
                if (c != currentSChar && nextPChar != '*') {
                    // Final chance. See if we can backfill entries into a prev *
                    char prevPChar = patternPos > 1 ? p.charAt(patternPos - 1) : Character.MIN_VALUE;
                    if(prevPChar == '*') {
                        // Constraint: '*' is always preceded by a valid character
                        char prevPCharAgain = p.charAt(patternPos - 2);
                        if(prevPCharAgain == c) {
                            System.out.printf("Saved by backfill %d<-->%d\n", stringPos, patternPos);
                            stringPos++;
                        } else {
                            System.out.printf("No backfill. Mismatch at %d<-->%d\n", stringPos, patternPos);
                            return false;
                        }
                    } else {
                        System.out.printf("Mismatch at %d<-->%d\n", stringPos, patternPos);
                        return false;
                    }
                } else {
                // If '*' is the next char, stay here, so the prev pattern char can be matched from this pos e.g., s= cy, p=d*y,
                if (nextPChar != '*') {
                    stringPos++;
                }
                }
            }

            if (c == '*') {
                stringPos = findIdxOfPatternEnd(p, patternPos, s, stringPos); // returns where the next char begins
            }

            if (c == '.') {
                stringPos++;
            }

            patternPos++;
        }

        return stringPos >= s.length(); // True only if the end of the string is reached
    }

    int findIdxOfPatternEnd(String p, int patternPos, String s, int stringPos) {
        char prevPChar = p.charAt(patternPos - 1); // Constraint: There is always a char before *
        char nextPChar = patternPos < (p.length() - 1) ? p.charAt(patternPos + 1) : Character.MIN_VALUE;
        if (prevPChar == '.') {
            return s.length();
        }
        // We stop for 3 possibilities
        // Run to the end if there is no more chars to be pattern-matched
        // If there is a next char, keep going until the current char stops
        // optimized stop -- later
        while (stringPos < s.length() && s.charAt(stringPos) == prevPChar) {
            // If there's a next char in the pattern, we need to stop when the present char meets its send. This makes optimized stop fail, but we'll fix it later.
//            if(nextPChar != Character.MIN_VALUE && s.charAt(stringPos) != prevPChar) {
//                System.out.printf("The * loop breaks at %d <--> %d\n", stringPos, patternPos);
//                break;
//            }
            System.out.printf("The * loop goes on. stringPos=%d | s.char(stringPos) = %c\n", stringPos, s.charAt(stringPos));
            stringPos++;
        }
        System.out.printf("The * loop ends at %d <--> %d\n", stringPos, patternPos);
        return stringPos;
    }
}

/*
a* => aa
.* => ab

Let's design the first case

int stringPos = 0
int patternPos = 0
while(patternPos < pattern.length)
    char c = pattern[patternPos]
    if c >= 'a' and c <= 'z'
        if(c != s[stringPos])
            return false
        stringPos++
    if( c == '*')
        char prev = pattern[patternPos - 1] // constraint: there is always a char before *
        stringPos = findIdxOfPatternEnd(prev, s, stringPos) // returns where the pattern stops, and the next char begins
    if(c == '.')
        stringPos++ // a free pass
    patternPos++
return true



findIdxOfPatternEnd(prev, s, stringPos)
    if(prev == '.')
        return s.length - 1
    while(stringPos < s.length && s[stringPos] == prev)
        stringPos++
    return stringPos


As suspected, we can't go greedy and consume everything after seeing a *
e.g., "abbbbbaaa", "ab*a" => We should look for an 'a' at the end
// This has two special cases - don't go greedy after the *, optimize that the 'a' at the end of the pattern
is for the last 'a' in the string, and not anything else


Leetcode tries to run s= "aab", p="c*a*b", and expects a true
// This has just one special case - don't go greedy after the * i.e., stop when you see the first char that's next in the pattern


================
Lessons learnt:
- Don't go for == in a while condition when >= is the safer choice. You don't know where you'll add twice and make this break.
- In Leetcode, you can usually expect tests to cover all corner cases. So simple solutions that work for straightforward applications won't be enough.
Prepare for this accordingly (In this example, I thought they won't go for all corner cases and went with simple ifs and elses which became too
unmanageable. I'll try with a new attempt again.
 */