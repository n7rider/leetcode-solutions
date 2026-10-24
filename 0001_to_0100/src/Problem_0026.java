import java.util.Arrays;

/*
26. Remove Duplicates from Sorted Array
Easy
Topics
premium lock iconCompanies
Hint

Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place such that each unique element appears only once. The relative order of the elements should be kept the same.

Consider the number of unique elements in nums to be k​​​​​​​​​​​​​​. After removing duplicates, return the number of unique elements k.

The first k elements of nums should contain the unique numbers in sorted order. The remaining elements beyond index k - 1 can be ignored.

Custom Judge:

The judge will test your solution with the following code:

int[] nums = [...]; // Input array
int[] expectedNums = [...]; // The expected answer with correct length

int k = removeDuplicates(nums); // Calls your implementation

assert k == expectedNums.length;
for (int i = 0; i < k; i++) {
    assert nums[i] == expectedNums[i];
}

If all assertions pass, then your solution will be accepted.



Example 1:

Input: nums = [1,1,2]
Output: 2, nums = [1,2,_]
Explanation: Your function should return k = 2, with the first two elements of nums being 1 and 2 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).

Example 2:

Input: nums = [0,0,1,1,1,2,2,3,3,4]
Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
Explanation: Your function should return k = 5, with the first five elements of nums being 0, 1, 2, 3, and 4 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).



Constraints:

    1 <= nums.length <= 3 * 104
    -100 <= nums[i] <= 100
    nums is sorted in non-decreasing order.


 */
public class Problem_0026 {

    public static void main() {
        Problem_0026 obj = new Problem_0026();
        int[] arr1 = new int[] {1,1,2};
        System.out.println(obj.removeDuplicates(arr1));
        System.out.println(Arrays.toString(arr1) + "\n");

        int[] arr2 = new int[] {0,0,1,1,1,2,2,3,3,4};
        System.out.println(obj.removeDuplicates(arr2));
        System.out.println(Arrays.toString(arr2) + "\n");
    }

    public int removeDuplicates(int[] nums) {
        // Skip null check
        if(nums.length <= 1) {
            return nums.length;
        }
        int len = 1;
        int prev = nums[0];
        for(int i = 1; i < nums.length; i++) {
            if(nums[i] != prev) {
                len++;
                prev = nums[i];

                if(nums[len - 1] != nums[i]) {
                    nums[len - 1] = nums[i];
                }
            }
        }
        return len;
    }
}

/*
Simplest solution - Use a set and get its count.
However, this takes O(n) additional size.

Do it at O(1)?
Array is sorted in ascending order, so if there are dupes, it has to come before the next number

So, check with prev number and inc only when the num changes?

Alg:
if(nums.length <=1)
    return nums.length

len = 1
prev = nums[0]
for(int i = 1; i < nums.length - 1; i++)
    if nums[i] != prev
        // new num
        len++
        prev = nums[i]
    // if is false if it's a dupe, ignore and keep going

return len


The AI has a few suggestions
we don't need a separate prev, since we're modifying the arr's index with len of unique items, the last modifying pos of the array
can give both len and prev's index. Advantage of thinking the problem as a whole, and not in parts. Maybe do both?


 */