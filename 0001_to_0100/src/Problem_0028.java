/*
28. Find the Index of the First Occurrence in a String
Easy
Topics
premium lock iconCompanies

Given two strings needle and haystack, return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.



Example 1:

Input: haystack = "sadbutsad", needle = "sad"
Output: 0
Explanation: "sad" occurs at index 0 and 6.
The first occurrence is at index 0, so we return 0.

Example 2:

Input: haystack = "leetcode", needle = "leeto"
Output: -1
Explanation: "leeto" did not occur in "leetcode", so we return -1.



Constraints:

    1 <= haystack.length, needle.length <= 104
    haystack and needle consist of only lowercase English characters.


 */
public class Problem_0028 {
    public static void main(String[] args) {
        Problem_0028 obj = new Problem_0028();
        System.out.println(obj.strStr("sadbutsad", "sad"));
        System.out.println(obj.strStr("leetcode", "leeto"));
        System.out.println(obj.strStr("jjojjojjojjen", "jjojjen"));
    }

    public int strStr(String haystack, String needle) {
        for(int i = 0; i <= haystack.length() - needle.length(); i++) {
            boolean isMatch = true;
            for(int j = 0; j < needle.length(); j++) {
                if(needle.charAt(j) != haystack.charAt(i + j)) {
                    isMatch = false;
                    break;
                }
            }

            if(isMatch) {
                return i;
            }
        }

        return -1;
    }
}

/*
Simplest solution:
check if first char matches
    if yes check if other chars matches in a loop

// eg haystack.len = 10, needle.len = 3, we need to stop after checking 7(0-idx) because 8, 9 can't make anything
// so, i <= 10-3
for(int i = 0; i <= haystack.len - needle.len; i++)
    if(haystack.charAt(i) == needle.charAt(i)
        doesMatch = seekRemChar(needle, haystack, i)
        if(doesMatch)
            return i
return -1

seekRemChar(needle, haystack, hayStckCurr)
    for(int j = 1; j < needle.len; j++)
        if needle[j] != hayStackCurr[i+j]
            return false
return true


Merging both conditions now
for(int i = 0; i <= haystack.len - needle.len; i++)
    bool match = true
    for(int j = 0; j < needle.len; j++)
        if needle[j] != hayStackCurr[i+j]
            match = false
            break
    if match
        return i

return -1

seekRemChar(needle, haystack, hayStckCurr)
    for(int j = 1; j < needle.len; j++)
        if needle[j] != hayStackCurr[i+j]
            return false
return true

My code is simple but it go up to O(nm) if many substrings of haystack look similar to haystack e.g., panpandpapanda when searching
for panda
The KMP algorithm assures it's always O(n) by finding the number of salvagable digits
e.g., assume you're finding in "jjoojjoojjoojjen" -> a needle named "oojjoojjen", the LPS (longest prefix suffix) is found by
finding if a letter matches the first one, and if yes, you set one in the int[] lps for this letter's index
If more letters continue to match, we keep increasing the value as we go. So for above example, we get [0, 1, 0, 0, 1, 2, 3, 4, 0, 0]
the 1234 is because of the oojj'oojj'en part.

When we lookup the haystack, we fail at this point after almost matching everything jj'oojjoojj'oojjen
Rather than comparing again from every o in the middle, we lookup the LPS for this index of j (8). LPS[8] returns 4, so this basically
telling you can reuse the last 4, i.e., the jjoojj'oojj'oojjen part, and resume with j=5. WE end up using this and finding a match.
Thus this remains strictly O(n).

 */