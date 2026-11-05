import java.util.Arrays;

/*
27. Remove Element
Easy
Topics
premium lock iconCompanies
Hint

Given an integer array nums and an integer val, remove all occurrences of val in nums in-place. The order of the elements may be changed. Then return the number of elements in nums which are not equal to val.

Consider the number of elements in nums which are not equal to val be k, to get accepted, you need to do the following things:

    Change the array nums such that the first k elements of nums contain the elements which are not equal to val. The remaining elements of nums are not important as well as the size of nums.
    Return k.

Custom Judge:

The judge will test your solution with the following code:

int[] nums = [...]; // Input array
int val = ...; // Value to remove
int[] expectedNums = [...]; // The expected answer with correct length.
                            // It is sorted with no values equaling val.

int k = removeElement(nums, val); // Calls your implementation

assert k == expectedNums.length;
sort(nums, 0, k); // Sort the first k elements of nums
for (int i = 0; i < actualLength; i++) {
    assert nums[i] == expectedNums[i];
}

If all assertions pass, then your solution will be accepted.



Example 1:

Input: nums = [3,2,2,3], val = 3
Output: 2, nums = [2,2,_,_]
Explanation: Your function should return k = 2, with the first two elements of nums being 2.
It does not matter what you leave beyond the returned k (hence they are underscores).

Example 2:

Input: nums = [0,1,2,2,3,0,4,2], val = 2
Output: 5, nums = [0,1,4,0,3,_,_,_]
Explanation: Your function should return k = 5, with the first five elements of nums containing 0, 0, 1, 3, and 4.
Note that the five elements can be returned in any order.
It does not matter what you leave beyond the returned k (hence they are underscores).



Constraints:

    0 <= nums.length <= 100
    0 <= nums[i] <= 50
    0 <= val <= 100


 */
public class Problem_0027 {
    public static void main() {
        Problem_0027 obj = new Problem_0027();
        int[] arr1 = new int[] {3,2,2,3};
        System.out.println(obj.removeElement(arr1, 3));
        System.out.println(Arrays.toString(arr1));

        int[] arr2 = new int[] {0,1,2,2,3,0,4,2};
        System.out.println(obj.removeElement(arr2, 2));
        System.out.println(Arrays.toString(arr2));

        int[] arr3 = new int[] {1};
        System.out.println(obj.removeElement(arr3, 1));
        System.out.println(Arrays.toString(arr3));

        int[] arr4 = new int[] {2};
        System.out.println(obj.removeElement(arr4, 3));
        System.out.println(Arrays.toString(arr4));
    }

    public int removeElement(int[] nums, int val) {
        if(nums == null || nums.length == 0) {
            return 0;
        }

        int left = 0;
        int right = nums.length - 1;
        // right by default takes up a idx. left should check it too, can't ignore it just because right's on it.
        while(left <= right && left < nums.length) {
            // swap needed if left == val
            if(nums[left] == val) {
                // loop runs until we find a substitute
                while (left <= right && nums[right] == val)
                    right--;
                // left == val, and we reached its position, no more items to swap
                if (left >= right)
                    break;
                else {
                    int temp = nums[right];
                    nums[right] = nums[left];
                    nums[left] = temp;
                    // This pos can't be used, so do right-- keeping it ready for the next item
                    right--;
                }
            }
            left++;
        }
        // Three scenarios - we stop at nums.length, or when left == right with outer loop, or when the if loop stops because right reaches left.
        // In first, left has gone beyond arry and ends at +1 of array length (so left works for 1-based idx).
        // In the other two, left has gone beyond right and it stops. Anything above right is not needed, so right is the 0-based answer. Left is the 1-index based answer
        return left;
    }
}

/*
Simplest soln:
1. When val appears, find a slot after val after its pos
    Iterate until we find something != val, then swap
2. Continue until we reach the end

Downside: We'll keep moving val, again and again until it reaches the right end

How about we put them to the right end in the first place? Have a counter from right end to find a slot for val?

Alg:
left = 0
right = nums.len - 1
while(left < right)
    // swap needed if left == val
    if nums[left] == val
        // loop runs until we find a substitute
        while(left < right && nums[right] == val)
            right--;
        // left == val, and we reached its position, no more items to swap
        if (left >= right)
            return left - 1
        else
            int temp = nums[right]
            nums[right] = nums[left]
            nums[left] = temp
    left++
    right--

ChatGPT suggestions:
The second condition in the outer loop is not needed.

 */