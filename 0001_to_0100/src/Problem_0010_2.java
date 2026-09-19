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
public class Problem_0010_2 {
    static void main() {
        Problem_0010_2 obj = new Problem_0010_2();
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
        System.out.println(obj.isMatch("abcd", ".*cd")); // Needs Optimized stop at the char after next char. Doesn't work, but should it?

    }

    public boolean isMatch(String s, String p) {
        int pPos = 0;
        int sPos = 0;

        while(pPos < p.length()) {
            char pChar = p.charAt(pPos);
            char sChar = sPos < s.length() ? s.charAt(sPos) : Character.MIN_VALUE;
            CharType charType = findCharType(pChar);
            char nextPChar = pPos < p.length() - 2 ? p.charAt(pPos + 1) : Character.MIN_VALUE;
            switch (charType) {
                case ALPHABET -> {
                    if(pChar != sChar) {
                        // If chars are not the same, allow if * is next e.g., c => d*c
                        if(findCharType(nextPChar) == CharType.ASTERISK) {
                            pPos++;
                        } else {
                            return false;
                        }
                    } else {
                        pPos++;
                        sPos++;
                    }
                }
                case PERIOD -> {
                    if(findCharType(nextPChar) == CharType.ASTERISK) {
                        // Find all text in pattern until the next symbol. We're going to jump to this
                        // because .* needs to skip everything other than what is left to be matched
                        // e.g., ababababababb => .*abab | abab => .*
                        // Next is *, so go next + 1 i.e., 2
                        int[] nextWordBounds = findNextWordBounds(p, pPos + 2);
                        // Pattern has no more words left. Symbols can't follow .*. It's the end, so return true
                        if(nextWordBounds[1] == 0) {
                            return true;
                        }
                        int endOfLastOccInS = findEndOfLastOcc(s, sPos, p, nextWordBounds[0], nextWordBounds[1]);
                        // Things have moved, move pattern to end too
                        if(endOfLastOccInS != (sPos + 2)) {
                            sPos = endOfLastOccInS;
                            pPos = nextWordBounds[1] + 1;
                        }
                    } else {
                        pPos++;
                        sPos++;
                    }
                }
                case ASTERISK -> {
                    char prevPChar = s.charAt(pPos - 1); // Given constraint. * is always preceded by a char
                    pPos++;
                    // Skip past the char before * in both pattern and char e.g., aa => a*
                    while(pPos < p.length() && p.charAt(pPos) == prevPChar) {
                        pPos++;
                    }
                    while(sPos < s.length() && s.charAt(sPos) == prevPChar) {
                        sPos++;
                    }
                }
            }
        }

        System.out.printf("Finished comparing %s and %s. sPos = %d, pPos = %d\n", s, p, sPos, pPos);
        return sPos >= s.length();
    }

    private int findEndOfLastOcc(String s, int sPos, String p, int pPos, int count) {
        int charsFound = 0;
        int out = s.length();
        int currCount = count;
        for(int i = s.length() - 1; i >= sPos - count && i >= 0; i--) {
            if(s.charAt(i) == p.charAt(pPos + currCount - 1)) {
                currCount--;
            } else {
                currCount = count;
            }
            if(currCount == 0) {
                return i + count;
            }
        }
        return sPos + 1;
    }

    private int[] findNextWordBounds(String string, int currPos) {
        if(currPos >= string.length()) {
            return new int[] { currPos, 0 };
        }
        int ele1 = currPos;
        while(currPos < string.length() && findCharType(string.charAt(currPos)) == CharType.ALPHABET) {
            currPos++;
        }
        System.out.printf("Word bound found for .*. %d, %d i.e., %s %s \n", ele1, currPos - 1, string, string.substring(ele1, currPos));
        return new int[] { ele1, currPos - ele1}; // { startIdx, count}
    }

    enum CharType {
        ALPHABET, PERIOD, ASTERISK
    }

    private CharType findCharType(char c) {
        if(c >= 'a' && c <= 'z') {
            return CharType.ALPHABET;
        }
        if(c == '.') {
            return CharType.PERIOD;
        }
        // Skipping validation because of the given constraint
        return CharType.ASTERISK;
    }

}

/*
a* => aa
.* => ab

Let's design the first case

if pChar is alphabetic
    do equals check

if pChar is *
    increment currSChar until prevPchar == currSChar // This is greedy. It will break when you compare aaaa => a*a
    // More of these non-greedy cases aaaa => a*a | aaaabb => a*aabb
    increment pChar until pChar = prevPChar (Essentially, go on until we see the next char in pattern)

if pChar is .
    skip one char

// What is the case of .* ? Include in *'s case or .'s case?
In * block => Increment until you see next pattern // Won't work for abab => .*ab because you need to be greedy enough
If you're very greedy, it'll break for abab => .*abab because you are not greedy enough
Going from the back seems the right way but will it get complex. What if we find the last occurence of the first letter?
e.g., In abab => .*abab | take the entire remaining pattern and find it in string from the last
What about abababababab => .*abab, it works.
What about abababababab => .*abab.*, just search for contiguous string until it hits a .*. But what about a . or a *? Do we need the same precaution

ababababababb => .*abab* It works if we just jump to the end of the pattern. We already have logic in * to backfill
ababababababb => .*abab. This works too.

Additional corner cases:
abc -> d*abc | If next char is '*' no need to fail with equality check. What about abc -> d*.*abc? Still fine
What about abcbcbc -> .*bc.* Works again
 */