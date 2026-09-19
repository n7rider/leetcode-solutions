import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Given a string s, return the longest in s.



Example 1:

Input: s = "babad"
Output: "bab"
Explanation: "aba" is also a valid answer.

Example 2:

Input: s = "cbbd"
Output: "bb"



Constraints:

    1 <= s.length <= 1000
    s consist of only digits and English letters.

 */
public class Problem_0005 {
    public static void main(String[] args) {
        Problem_0005 obj = new Problem_0005();
        // System.out.println(obj.longestPalindrome("babad"));
//        System.out.println(obj.longestPalindrome("cbbd"));
//        System.out.println(obj.longestPalindrome("c"));
//        System.out.println(obj.longestPalindrome("padap"));
        System.out.println(obj.longestPalindrome("aacabdkacaa")); // Expected: aaca
    }

    public String longestPalindrome(String s) {
        // Validation skipped due to constraints
        int longestLen = 0;
        int longestLenStart = 0;
        int longestLenEnd = 0;

        // Add pos to hash map
        Map<Character, List<Integer>> map = createMap(s);

        // Iterate through string
        for(int i = 0; i < s.length(); i++) {
            List<Integer> numList = map.get(s.charAt(i));
            // Skip if this char doesn't have any recurring entry
            if(numList == null || numList.size() < 2) {
                continue;
            }

            int startIdx = getStartIdxInArray(numList, i);
            // Skip is this is the last item in the arraylist, or if rem. len will not be bigger than longestLen
            if((startIdx >= numList.size() - 1) || (s.length() - numList.get(startIdx) <= longestLen)) {
                continue;
            }

            for(int k = startIdx + 1; k < numList.size(); k++) {
                // Won't be bigger than longestLen
                if(numList.get(k) - numList.get(startIdx) <= longestLen) {
                    continue;
                }
                boolean palFlag = isPalindrome(s, numList.get(startIdx), numList.get(k));
                if(palFlag && numList.get(k) - numList.get(startIdx) > longestLen) {
                    longestLen = numList.get(k) - numList.get(startIdx);
                    longestLenStart = numList.get(startIdx);
                    longestLenEnd = numList.get(k);
                }
            }
        }

        return s.substring(longestLenStart, longestLenEnd + 1);

    }

    private Map<Character, List<Integer>> createMap(String s) {
        Map<Character, List<Integer>> map = new HashMap<>();
        for(int i = 0; i < s.length(); i++) {
            if(map.get(s.charAt(i)) != null) {
                map.get(s.charAt(i)).add(i);
            } else {
                List<Integer> numList = new ArrayList<>();
                numList.add(i);
                map.put(s.charAt(i), numList);
            }
        }
        return map;
    }

    private Integer getStartIdxInArray(List<Integer> numList, int i) {
        // Find start point in map's value i.e., the array
        // TODO Modifying the array is not recommended even if concurrent modification is allowed. Is there a better way to iterate everytime? Add additional array? Additonal field within the for loop?
        for(int j = 0; j < numList.size(); j++) {
            if(i == numList.get(j)) {
                return j;
            }
        }

        return null;
    }

    private boolean isPalindrome(String s, int start, int end) {
        for(int l = 0; l <= (end - start) /2; l++) {
            if(s.charAt(start + l) != s.charAt(end - l)) {
                return false;
            }
        }
        return true;
    }
}

/*
Simplest way to find if a string is palindrome or not
Compare 0<->n-1, 1<->n-2, and so on.

To find a pal. substring in a string, how do we know n-1 position?
Maybe start only if you find the same char as the current one and work backwards?

In babad, compare only when we reach b at index 2?

Algorithm:
Start at idx 0
out = 0
start_idx = 0
for i = 0; i < s.len -1 ; i++
  for j = i + 1; j < s.len; j++
    if s[start_idx] == s[i]
        out = pal_check(out, s, start_idx, i)

pal_check (int out, String s, int start, int curr)
  if(curr - start <= out)
    return out

  boolean isPal = pal_check_helper(s, start, curr)
  if (isPal)
    return curr - start;
  else
    return out;

pal_check_helper(String s, int start, int curr)
  int x = 0
  for x = 0; x < (curr-start)/2 ; x++
    if s[start+x] != s[curr-x]
      return false
  return true

Algo exp:
Look for some char's occurrence to start computing palindrome
Runtime is O(n^2) for the 2 loops
    Assuming char repeats O(n) times, the other for loop runs O(n), so worst time is O(n^3).
    It's closer to O(n^2) than O(n^3) though (because char can repeat n/2 times avg, palindrome len < curr. max is n/2, pal. check goes full len n/2 times avg, so the third n by average is ~O(n/8)).

How to reduce runtime?
    Use hashmap to find next char (skips iteration in the first for loop)
        This adds an initial O(n), but you need one loop instead of two loops, so O(n^2) is the worst case
        It's closer to O(n) than O(n^2) though

Can we go O(n)?
    We can go if we store hashmaps of all lens forward and backward, but that's overkill
        A pal_check say from O(2) to (8) needs a substring of s[2 to 4] to be compared with s[8 to 6]
            Even with a trie, it's another O(n).


Notes:
=============
Wasted time because I wrote for i = 1 t0 10, instead of the whole thing.
 */

/*
Simplest way to find if a string is palindrome or not
Compare 0<->n-1, 1<->n-2, and so on.

To find a pal. substring in a string, how do we know n-1 position?
Maybe start only if you find the same char as the current one and work backwards?

In babad, compare only when we reach b at index 2?

Algorithm:
Start at idx 0
out = 0
start_idx = 0
for i = start_idx + 1 to s.len - 1
  if s[start_idx] == s[i]
    out = pal_check(out, s, start_idx, i)

pal_check (int out, String s, int start, int curr)
  if(curr - start <= out)
    return out

  boolean isPal = pal_check_helper(s, start, curr)
  if (isPal)
    return curr - start;
  else
    return out;

pal_check_helper(String s, int start, int curr)
  int x = 0

Results:
=================
Runtime
170ms
Beats19.94%
Memory
46.07MB
Beats43.25%

Comparison:
===============
ChatGPT uses a for loop where i is assumed as the center of the word, and it compares both sides of the text.

The for loop takes O(n). Assuming, half clear the first letter check, half of that are palindromes, we are again looking at O(n^2).

This looks simpler than mine (Though the Hashmap tries to just jump to the next repeating char and saves some compute), and treating current as center is quite elegant.
 */