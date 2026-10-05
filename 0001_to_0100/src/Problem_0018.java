import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
18. 4Sum
Medium
Topics
premium lock iconCompanies

Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:

    0 <= a, b, c, d < n
    a, b, c, and d are distinct.
    nums[a] + nums[b] + nums[c] + nums[d] == target

You may return the answer in any order.



Example 1:

Input: nums = [1,0,-1,0,-2,2], target = 0
Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]

Example 2:

Input: nums = [2,2,2,2,2], target = 8
Output: [[2,2,2,2]]



Constraints:

    1 <= nums.length <= 200
    -109 <= nums[i] <= 109
    -109 <= target <= 109


for int i = 0; i < num.len - 3; i++
    for int j = i + 1; j < num.len - 2; j++
        for int k = j + 1; k < num.len - 1; k++
            for int l = num.len - 1; l > 0; l--
                sum = num[i] + num[j] + num[k] + num[l]
                if sum == target
                    add ijkl to output
                    move k to the next num // can be l too
                if sum > target
                    l-- // Can also check sum [ijk] > target, then directly increment k. A bigger l can't help there.
                else
                    k++

After checking with AI:
    - Needed to change sum to long since it can go out of range
Worked other than a mistake with the missing continue

 */
public class Problem_0018 {
    public static void main(String[] args) {
        Problem_0018 obj = new Problem_0018();

        var out1 = obj.fourSum(new int[] {1,0,-1,0,-2,2}, 0);
        System.out.println(out1);
        System.out.println();

        var out2 = obj.fourSum(new int[] {2, 2, 2, 2, 2}, 8);
        System.out.println(out2);
        System.out.println();

        var out3 = obj.fourSum(new int[] {2, 1, 0, 2, 2, 2}, 7);
        System.out.println(out3);
        System.out.println();

        var out4 = obj.fourSum(new int[] {-3,-2,-1,0,0,1,2,3}, 0);
        System.out.println(out4);
        System.out.println();

        var out5 = obj.fourSum(new int[] {1000000000,1000000000,1000000000,1000000000}, -294967296);
        System.out.println(out5);
        System.out.println();

    }

//    var out4 = obj.fourSum(new int[] {-3,-2,-1,0,0,1,2,3}, 0);
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        List<List<Integer>> output = new ArrayList<>();
        int i = 0;
        int j;
        int k;
        int l;
        while(i < nums.length - 3) {
            j = i + 1;
            while(j < nums.length - 2) {
                k = j + 1;
                l = nums.length - 1;
                while(k < nums.length - 1 && l > 2 && l > k) {
                        long sum = (long) nums[i] + nums[j] + nums[k] + nums[l];
                        if (sum == target) {
                            output.add(List.of(nums[i], nums[j], nums[k], nums[l]));
                            k = nextUniqueNum(k, nums, 1); // Can move either k or l, moving k
                            continue;
                        }

                        if (sum > target) {
                            l = nextUniqueNumRev(l, nums, k);
                        } else {
                            k = nextUniqueNum(k, nums, 1);
                        }
                }
                j = nextUniqueNum(j, nums, 2);
            }
            i = nextUniqueNum(i, nums, 3);
        }
        return output;
    }

    private int nextUniqueNum(int x, int[] nums, int stopBuffer) {
        int nextX = x;
        while(nextX < nums.length - stopBuffer && nums[nextX] == nums[x]) {
            nextX++;
        }
        return nextX;
    }

    private int nextUniqueNum_Old(int x, int[] nums, int stopBuffer) {
        int nextX = x;
        do {
            nextX++;
        }
        while(nextX < nums.length - stopBuffer && nums[nextX] == nums[x]);
        return nextX;
    }

    private int nextUniqueNumRev(int x, int[] nums, int stopBuffer) {
        int nextX = x;
        while(nextX > stopBuffer && nums[nextX] == nums[x]) {
            nextX--;
        }
        return nextX;
    }

    private int nextUniqueNumRev_Old(int x, int[] nums, int stopBuffer) {
        int nextX = x;
        do {
            nextX--;
        }
        while(nextX > stopBuffer && nums[nextX] == nums[x]);
        return nextX;
    }
}

/*
Simplest answer: Recursive answer - Do a DFS until we run through all of n - 4 times, so O(n^4)

We can create a set with O(n) to use 3 loops + O(n) lookup to bring it to O(n^3)

Can we do a O(n^2) look and a O(n^2) set to bring to 2 * O(n^2)?

Do initial sort, because we want only unique items. This is simpler with sorting
findTarget(nums, idx = 0, countSoFar = 0, target, outputList = {}, currIdxSet = {}, currIdxSum = 0)
    if idx >= nums.len
        return

    if countSoFar > 3
        return

    if currIdxSum + num[idx] == target
        Add to outputList

    while i < nums.len
        findTarget(nums, idx + 1, countSoFar + 1, target, outputList, currIdxSet.add(i), currIdxSum + nums[i]
        currIdxSet.remove(i)

        int i = idx + 1;
        while(i < nums.len && num[idx] == num[i])
            i++

This is the basic recursion.

Here is the algo for O(n^2) attempt:

Create a O(n^2) lookup -- e.g., 7 - 4,3 (or idx of 4,3)

Sort first.
Hashmap won't work because it's harder to find uniqueness check directly. We'll need a hashmap of num, count, then need to check for every quadruplet
for i = 0 to num.len - 2
    for j = i + 1 to num.len - 1
        Hashmap.put (num[i] + num[j], int[] { i, j } )

Let's try the left & right bound-based approach

for int i = 0; i < num.len - 3; i++
    for int j = i + 1; j < num.len - 2; j++
        for int k = j + 1; k < num.len - 1; k++
            for int l = num.len - 1; l > 0; l--
                sum = num[i] + num[j] + num[k] + num[l]
                if sum == target
                    add ijkl to output
                    move k to the next num // can be l too
                if sum > target
                    l-- // Can also check sum [ijk] > target, then directly increment k. A bigger l can't help there.
                else
                    k++

This reduces runtime to O(n^3) and is simpler than a hashmap


 */
