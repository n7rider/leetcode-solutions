/*
You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).

Find two lines that together with the x-axis form a container, such that the container contains the most water.

Return the maximum amount of water a container can store.

Notice that you may not slant the container.



Example 1:

Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.

Example 2:

Input: height = [1,1]
Output: 1



Constraints:

    n == height.length
    2 <= n <= 105
    0 <= height[i] <= 104

 */

public class Problem_0011 {
}

/*
Simplest solution:
Find vol. at all combinations and find the biggest vol.
    The vol. held between two pillars is the smallest no. * distance between them
    You can memoize inputs
    We just need the max, not where the max happened.
    Larger horizontal distances doesn't mean a winner e.g., [9,8,1,1,1,1] - the first two win over considering hte whole length

Simplest solution

int max = 0
for int i = 0; i < arr.len - 1; i++
    for j = i + 1; j < arr.len; j++
        int sm = Math.min(arr[i], arr[j]
        int vol = sm * (j-i)
        if (vol > max)
            max = vol

Will max at both sides always win? No, ex. shows that's not possible.
Will max at one side always wins?
What about [1, 8, 5, 9, 5, 5, 5, 5, 5] => Obviously 8, 9 don't win. 8, final 5 win

So, how about find max from left side, and go step by  step for the right pos?
Consider [1, 10, 2, 6, 6, 6, 6, 6] - It works even if there is  a min in the middle
This should always work -- because even if there is a min the middle, elongating to the max works

What if the high is in the middle
Consider [1, 5, 5, 5, 5, 10, 5, 5, 5, 5] - In this case, considering all 5s is the better choice

Simplest needs O(n^2) time.
How about we assume the longest is the best and slowly decrement from both sides?
Consider [1 1 2 4 5 6 19 19] - won't work if we uniformly iterate from both sides

What if we run through once to find the max 2, then go through the rest?
Won't work for [1 18 18 18 18 18 18 19 19]

Let's find cons difference in the array [1, 7, -2, -4, 3, -1, 4, -5, 4]
Is there anything that makes you choose 7 & 4, 7 & other 4

The biggest adv here is it helps you skip over things that are smaller and won't be a factor at all
Even if we consider the 18, 19 one above, we'll end up with 1, 17, 0, 0, 0, 0, 0, 1, 0

Whoa this gives me an idea, find the max sum and it gives the answer?

But that would take O(n^2) as well? No, just check only at +ve ones, because -ves can't give you a max.
However, from the above diff arr [1, 17, 0, 0, 0, 0, 0, 1, 0], I can see that adding 0 is required to the max. Otherwise, we'll miss the ideal max.

So we shouldn't skip 0

find localMax:
int sum = maxSum = Int.MIN
int maxL = maxR = 0
for int i = 0; i < arr.len; i++
    if arr[i] < 0
        maxR=Max(i - 1, 0)
    else
        sum =

Hmm, this gets odd. Let's try diff arr again with n1-n2 this time

[1 18 18 18 18 18 18 19 19] = [1 -17 0 0 0 0  0 -1 0]




 */