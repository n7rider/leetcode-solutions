import java.util.Arrays;
import java.util.List;

/*
16. 3Sum Closest
Medium
Topics
premium lock iconCompanies

You are given an integer array nums of length n and an integer target.

Find three integers at distinct indices in nums such that the sum is closest to target.

Return the sum of the three integers.

You may assume that each input would have exactly one solution.



Example 1:

Input: nums = [-1,2,1,-4], target = 1
Output: 2
Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).

Example 2:

Input: nums = [0,0,0], target = 1
Output: 0
Explanation: The sum that is closest to the target is 0. (0 + 0 + 0 = 0).



Constraints:

    3 <= nums.length <= 500
    -1000 <= nums[i] <= 1000
    -104 <= target <= 104


 */
public class Problem_0016 {
    public static void main(String[] args) {
        Problem_0016 obj = new Problem_0016();
        // Becomes -4, -1, 1, 2 on sorted.
//        System.out.println(obj.threeSumClosest(new int[] {-1, 2, 1, -4}, 1));
//        System.out.println(obj.threeSumClosest(new int[] {0, 0, 0}, 1));
//        System.out.println(obj.threeSumClosest(new int[] {10,20,30,40,50,60,70,80,90}, 1));
//        System.out.println(obj.threeSumClosest(new int[] {0, 1, 2}, 0));
//        System.out.println(obj.threeSumClosest(new int[] {2,3,8,9,10}, 16));
        System.out.println(obj.threeSumClosest(new int[] {2,5,6,7}, 16));
    }

    public int threeSumClosest(int[] nums, int target) {
        // Validate nums is not null and len is at least 3

        Arrays.sort(nums);
        int closestSum = nums[0] + nums[1] + nums[2];
        for(int i = 0; i < nums.length - 2; i++) {
            // Start from i + 2, we need a triplet, so the other num has to come from the middle
            for(int j = i + 2; j < nums.length; j++) {
                int currClosestThirdNum = findClosestThirdNum(nums, i, j, target);
                int currSum = nums[i] + nums[j] + currClosestThirdNum;
                if(Math.abs(target - (nums[i] + nums[j] + currClosestThirdNum)) < Math.abs(target - closestSum)) {
                    System.out.printf("Closest is set. %d + %d + %d = %d\n", nums[i], nums[j], currClosestThirdNum, currSum);
                    closestSum = currSum;
                }
            }
        }
        return closestSum;
    }

    int findClosestThirdNum(int[] nums, int num1Idx, int num2Idx, int target) {
        int tempSum = nums[num1Idx] + nums[num2Idx];
        int numReq = target - tempSum;

        int currClosestThirdNum = nums[num1Idx + 1];
        return binarySearch(nums, numReq, num1Idx + 1, num2Idx - 1, currClosestThirdNum);
    }

    int binarySearch(int[] nums, int numReq, int num1Idx, int num2Idx, int currClosestThirdNum) {
        if(num1Idx > num2Idx) {
            return currClosestThirdNum;
        }

        int mid = (num1Idx + num2Idx) / 2;
        if(nums[mid] == numReq) {
            return numReq;
        }
        // Set mid as the closest so far if it's closer to numsReq than the current
        if(Math.abs(numReq - nums[mid]) < Math.abs(numReq - currClosestThirdNum)) {
            currClosestThirdNum = nums[mid];
        }

        // Imagine a series ..... -1, 0, [1], 2, 3, 50......
        // and it will take several times to reach 3 which is the closestNum
        // However, since the array is sorted, we know which way to go
        // If the order is 0........ arr[mid] .... numReq... end, we go right
        if(nums[mid] < numReq) {
            return binarySearch(nums, numReq, mid + 1, num2Idx, currClosestThirdNum);
        } else {
            return binarySearch(nums, numReq, num1Idx, mid - 1, currClosestThirdNum);
        }
    }
}

/*
similar to prev but keep displacing out with closer.

sort is needed to stop early.

same i, hashset, j sol can work in O(n^2) time.

binary search is needed to find the closest, so set may not needed. But the idea applies. Binary search is to be done between i and j only.

Algo:

// Skip val that len <=3

outputVal
for int i = 0; i < nums.len - 2; i++
    Set set

    for j = i + 1; j < nums.len; j++
        sum = i + j
        binarySearch(sum, target, outputVal)

return outputVal

binarySearch(sum, target, outputVal+List, arr, a, b)
    int idealNeededHere = target - sum // e.g., with nums = [-1,2,1,-4], target = 2 | a=-1, b= 2, idealNeededHere = 2-1 = 1
    // say outputVal is currently at -7  assuming
    int currCloseness = abs(1- -7) = 8

    int left = a + 1
    int right = b-1
    if(left < right)
        mid = (left + right) / 2
        if(abs( idealNeededHere - arr[mid] ) < currCloseness
            replace outputVal+List, currCloseness // outputVal+List as an object would be easier to carry arond an dupdate


        // if ideal < 0 but is > arr[mid], they lie in x-axis like this: arr[mid] .... ideal... 0
        // going to the left of arr[mid] is not going to help in a sorted array
         if(ideal < 0 && arr[mid] < ideal == false)
            binarySearch(..., left, mid - 1)
        // if ideal > 0 but is < arr[mid], they lie in x-axis like this: 0... ideal .... arr[mid]
        // going to the right of arr[mid] is not going to help in a sorted array
        if(idealNeededHere > 0 && idealNeededHere <= arr[mid] == false)
            binarySearcH(..., mid+1, right)

Inputs on the code:
Watch out for boundary even before starting binary search too.

After comparing with ChatGPT:
Adding binary search in addition to the sort and for-loops adds an additional (* O log n) to the output.

My idea was to use the binary search to find the num closest to (target - sum of 1 + 2). However, the ChatGPT solution
is much simpler, it goes from left for num2, right for num3. And it does left++ or right-- based on how close the number
is to the target. I probably should have thought of the solution with emphasis that the array is sorted. I wasn't a big fan
of the boundaries-based approach in the previous example, but it's ideal for this solution

 */