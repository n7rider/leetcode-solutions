/*
Given a string s, find the length of the longest without duplicate characters.

Example 1:

Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3. Note that "bca" and "cab" are also correct answers.

Example 2:

Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.

Example 3:

Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.

Constraints:

    0 <= s.length <= 5 * 10^4
    s consists of English letters, digits, symbols and spaces.

 */

import java.util.HashMap;
import java.util.Map;

public class Problem_0003 {
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> charMap = new HashMap<>();
        int maxLen = 0, currSubStrStart = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean flag = charMap.containsKey(c);
            if(flag) { // exists already
                int existingIdx = charMap.get(c);
                if((i - currSubStrStart) > maxLen) {
                    maxLen = i - currSubStrStart;
                }
                // Remove chars before dupe's first occurrence. We'll consider only the index from dupe's first occurrence + 1 hereafter.
                for(int j = currSubStrStart; j < existingIdx; j++) {
                    charMap.remove(s.charAt(j));
                }
                currSubStrStart = existingIdx + 1;
            }
            charMap.put(c, i);
        }

        return Math.max(s.length() - currSubStrStart, maxLen);
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("pwwkew"));
    }
}