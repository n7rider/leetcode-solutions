import java.util.*;

/*
15. 3Sum
Medium
Topics
premium lock iconCompanies
Hint

Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.

Notice that the solution set must not contain duplicate triplets.



Example 1:

Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
Explanation:
nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
The distinct triplets are [-1,0,1] and [-1,-1,2].
Notice that the order of the output and the order of the triplets does not matter.

Example 2:

Input: nums = [0,1,1]
Output: []
Explanation: The only possible triplet does not sum up to 0.

Example 3:

Input: nums = [0,0,0]
Output: [[0,0,0]]
Explanation: The only possible triplet sums up to 0.



Constraints:

    3 <= nums.length <= 3000
    -105 <= nums[i] <= 105

After comparing:

ChatGPT does sum of all 3. It has a for loop that counts i, but for j and k, there are two values left and right running from
either ends of the array, skipping duplicates.
Evaluation of my approach: as expected, checkAndAdd() is considered excessive and is hard to explain (IMPORTANT), when though it works. If using Set, ChatGPT
recommends creating a new Set in the inner loop, and add seen values. So we keep add values between i and j during every iteration. This means all the checkAndadd()
logic is not needed

 */
public class Problem_0015 {

    public static void main(String[] args) {
        Problem_0015 problem0015 = new Problem_0015();
        System.out.println(problem0015.threeSum(new int[] {-1, 0, 1, 2, -1, -4}));
        System.out.println(problem0015.threeSum(new int[] {0, 1, 1}));
        System.out.println(problem0015.threeSum(new int[] {0, 0, 0}));
        System.out.println(problem0015.threeSum(new int[] {1, 1, 1}));
    }

    public List<List<Integer>> threeSum(int[] nums) {
        // Skip null, empty check

        System.out.println("\nProcessing " + Arrays.toString(nums));
        Arrays.sort(nums);
        List<List<Integer>> output = new ArrayList<>();
        if(nums[0] > 0 ||nums[nums.length - 1] < 0) {
            System.out.println("   No possibility of triplets. Returning");
            return output;
        }

        Set<Integer> thirdNumSet = new HashSet<>();
        for(int num: nums) {
            thirdNumSet.add(num);
        }

        for(int i = 0; i < nums.length - 1; i++) {
            for(int j = i + 1; j < nums.length; j++) {
                int firstNum = nums[i];
                int secondNum = nums[j];
                int tempSum = firstNum + secondNum;
                if(thirdNumSet.contains(-tempSum)) {
                    checkAndAdd(nums, i, j, output);
                }

                while((j + 1) < nums.length && nums[j + 1] == secondNum) {
                    j++;
                }
                System.out.println("   j moved to index: " + j);

                // Move firstNum (i) only if the second num had reached the end without finding something else
                if((j + 1) == nums.length) {
                    System.out.println("   No more possible values for secondNum. Adjusting firstNum instead");
                    while((i + 1) < nums.length && nums[i + 1] == firstNum) {
                        i++;
                    }
                    System.out.println("   i moved to index: " + i);
                }
            }
        }

        return output;
    }

    private void checkAndAdd(int[] nums, int i, int j, List<List<Integer>> output) {
        int firstNum = nums[i];
        int secondNum = nums[j];
        int tempSum = firstNum + secondNum;
        // Edge case: If -tempSum == secondNum e.g., -4, 2, 2, we need to check if we have two 2's.
        // We have skip logic, so we'll never process the second occurrence of an item with 'j', so use that
        boolean flag = true;
        if(firstNum == -tempSum) {
            if ((i + 1) >= nums.length || nums[i + 1] != firstNum) {
                flag = false;
            }
        }
        if(secondNum == -tempSum) {
            if((j + 1) >= nums.length || nums[j + 1] != secondNum) {
                flag = false;
            }
        }
        // This is to prevent -1, 0, 1 from presenting itself as duplicate with -1, 1, 0
        // This prevents the thirdNum from getting reused. The array is sorted, so we never need to go back for the third num
        // It would have been found already
        if(-tempSum < firstNum || -tempSum < secondNum) {
            flag = false;
        }
        if(flag) {
            output.add(List.of(nums[i], nums[j], -tempSum));
        }
    }
}

/*
The easiest solution is O(n^3) where we have i, j, k loops running to find sum.

We can cut it to O(n^2) by adding just 2, and checking a hashmap or a set to see if the other exists

A hashmap - Can remember position, so we can prevent dupes. But wait, the order of triplets doesn't matter, order of output doesn't matter too.
A set - Can tell if an item exists or not, much simpler.

Let's try with a set first

for nums = 0 to len - 1
    set.add nums[i]

for i = 0 to l - 2
    for j = i+1 to l-1
        temp_sum = num[i] + num[j]
        if (set.contains(-temp_sum))
            output.add (new int[] { i, j, -temp_sum }

Two problems here:
- Checking dupes is unnecessarily complex. e.g., if we see -3, -1 first, and see -1, -3 again later, we need to go through all outputs in all order to eliminate dupes.
- The whole O(n^2) loop is not needed if we don't have a mix of -ve and +ve nums

The order of output doesn't matter too. So the easier way is to sort the input first.

But, what if we have -1, -3 and 4, but then we have -1, -5 and 6. So after sorting, we just skip dupes only as long as both i and j have the same values as before

Going to the code then:
 */