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


 */