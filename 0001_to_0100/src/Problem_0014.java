/*

14. Longest Common Prefix
Solved
Easy
Topics
premium lock iconCompanies

Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string "".



Example 1:

Input: strs = ["flower","flow","flight"]
Output: "fl"

Example 2:

Input: strs = ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.



Constraints:

    1 <= strs.length <= 200
    0 <= strs[i].length <= 200
    strs[i] consists of only lowercase English letters if it is non-empty.


 */
public class Problem_0014 {

    public static void main(String[] args) {
        Problem_0014 obj = new Problem_0014();
        System.out.println(obj.longestCommonPrefix(new String[] {"flower", "flow", "flight"}));
        System.out.println(obj.longestCommonPrefix(new String[] {"dog", "racecar", "car"}));
        System.out.println(obj.longestCommonPrefix(new String[] {"flower", "flower", "flight"}));
        System.out.println(obj.longestCommonPrefix(new String[] {"flower", "flower", "flow"}));
        System.out.println(obj.longestCommonPrefix(new String[] {"flower", "flower", "flower"}));
    }

    public String longestCommonPrefix(String[] strs) {
        int highestSearchable = findLenOfSmallestStr(strs);
        // This is the point at which the value flips from 'common so far' to not
        int inflectionPt = findLcpInflectionPoint(strs, highestSearchable);
        System.out.println("\n" + inflectionPt);
        // Returns 0-based value, so add + 1
        if(inflectionPt < 0) {
            return "";
        } else {
            return strs[0].substring(0, inflectionPt + 1);
        }
    }

    private int findLenOfSmallestStr(String[] strs) {
        int smallest = Integer.MAX_VALUE;
        for(int i = 0; i < strs.length; i++) {
            if(strs[i].length() < smallest) {
                smallest = strs[i].length();
            }
        }
        // Return 0-based
        return smallest - 1;
    }

    private int findLcpInflectionPoint(String[] strs, int highestSearchable) {
        int start = 0;
        int end = highestSearchable;
        int mid;
        int highestSoFar = -1;
        while(start <= end) {
            mid = (start + end) / 2;
            boolean isCommonAtPos = isCommonAtPos(strs, mid, start);
            if(isCommonAtPos) {
                // still common at this pos, search higher
                start = mid + 1;
                highestSoFar = mid;
            } else {
                // not common at this pos, search lower
                end = mid - 1;
            }
        }
        return highestSoFar;
    }

    private boolean isCommonAtPos(String[] strs, int currPos, int foundSoFar) {
        for(int i = foundSoFar; i <= currPos; i++) {
            char ch = strs[0].charAt(i);
            for(int j = 1; j < strs.length; j++) {
                if(ch != strs[j].charAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }

}


/*
Common prefix (and not a common seq) seems simple

find smallest string & go up to its len looking for prefix - Simple but needs one additional O(n) run at the start
    Runtime = O(n) + l * O(n)
Do bounds-safe check but keep checking them all?
    Runtime - O(n) but add a null check, also needs jumping around to find a bigger string, so this is bad

In the first approach, shall we go from o to l-1 or l-1 to 0
    Both have their own use case but the average is same
    However, a binary search can reduce the runtime O(log n)

Alg:
int smallest = Integer.MAX_VALUE;
for(int i = 0; i < strs.length; i++)
    if(strs[i].length() < smallest)
        smallest = strs[i].length()


int start = 0
int end = highestSearchable
int searchPos = highestSearchable
int commonLenSoFar = -1
while(start < end)
    isCommonAtCurrLen(strs, highestSearchable, start) // we know it matches until start, so the method is optimized
    if(isCommonAtCurrLen)
        // still common at this pos, search higher
        start = (start + end) / 2
    else
        // not common at this pos, search lower
        end = (start + end) / 2


 */