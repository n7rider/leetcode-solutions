/*
1. Two Sum
Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

You can return the answer in any order.

Example 1:

Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
Example 2:

Input: nums = [3,2,4], target = 6
Output: [1,2]
Example 3:

Input: nums = [3,3], target = 6
Output: [0,1]

Constraints:

2 <= nums.length <= 104
-109 <= nums[i] <= 109
-109 <= target <= 109
Only one valid answer exists.


Follow-up: Can you come up with an algorithm that is less than O(n2) time complexity?

Notes:
The solution says we can finish in one-pass and still keep the HashMap as Integer, Integer
if we look for target-current while we are storing to the hashmap.
 */

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Problem_0001 {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, List<Integer>> idxByValMap = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            if(idxByValMap.get(nums[i]) == null) {
                List<Integer> newList = new ArrayList<>();
                newList.add(i);
                idxByValMap.put(nums[i], newList);
            } else {
                var val = idxByValMap.get(nums[i]);
                val.add(i);
            }
        }

        for(Integer key: idxByValMap.keySet()) {
            int otherNum = target - key;
            if(idxByValMap.get(otherNum) != null) {
                if(key == otherNum) {
                    if(idxByValMap.get(key).size() > 1) {
                        return new int[] { idxByValMap.get(key).get(0), idxByValMap.get(key).get(1) };
                    }
                } else {
                    return new int[]{idxByValMap.get(key).getFirst(), idxByValMap.get(otherNum).getFirst()};
                }
            }
        }
        return null;
    }

    static class Value {
        List<Integer> indexes;
    }

    public int[] twoSum_v2(int[] nums, int target) {
        Map<Integer, Integer> idxByValMap = IntStream.range(0, nums.length)
                .boxed()
                .collect(Collectors.toMap(i -> nums[i], i -> i));
        for (Integer currVal : idxByValMap.keySet()) {
            if (idxByValMap.containsKey(target - currVal)) {
                return new int[]{idxByValMap.get(currVal), idxByValMap.get(target - currVal)};
            }
        }
        return null;
    }

    public int[] twoSum_v1(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}

// How do we do it
// Create a unique object for dupe (OR) store count
// Latter is easy, just change Hashmap to<Integer, List<Integer>>
// Former might needs its own class - with hash and equals implementation
// hashcode resolves to value, but equals resolves to value + index
// So we always check hashcode for contains, but equals is different