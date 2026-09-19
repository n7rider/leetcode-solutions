/*
4. Median of Two Sorted Arrays
HARD

Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.

The overall run time complexity should be O(log (m+n)).



Example 1:

Input: nums1 = [1,3], nums2 = [2]
Output: 2.00000
Explanation: merged array = [1,2,3] and median is 2.

Example 2:

Input: nums1 = [1,2], nums2 = [3,4]
Output: 2.50000
Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.



Constraints:

    nums1.length == m
    nums2.length == n
    0 <= m <= 1000
    0 <= n <= 1000
    1 <= m + n <= 2000
    -10^6 <= nums1[i], nums2[i] <= 10^6


 */

public class Problem_0004_2 {
    public static void main(String[] args) {
        int[] a = {1, 10, 12, 14, 16,  18, 20, 22, 24, 100};
        int[] b = {5, 11, 13, 15, 17, 200};
        // Expected - 6
        System.out.println(median(a, b));

        int[] c = {1, 2, 3, 4, 5,  6,  7, 8, 9, 10, 11, 12, 13, 14};
        int[] d = {1, 2, 4, 18, 19, 20, 21};
        // Expected
        System.out.println(median(c, d));

        int[] e = {1, 3};
        int[] f = {2, 4, 5};
        System.out.println(median(e, f));

        int[] g = {1, 2};
        int[] h = {3, 4, 5};
        System.out.println(median(g, h));
    }

    static double median(int[] nums1, int[] nums2) {
        // Always binary search the smaller array
        if (nums1.length > nums2.length) {
            return median(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;

        int left1 = 0;
        int right1 = m;

        while (left1 <= right1) {
            int mid1 = (left1 + right1) / 2;

            int midOverall = (m + n) / 2;
            // This is to make sure we are still at the median. If mid1 moves in one direction, mid2 moves in the other
            // e.g., When mid1 is exactly mid, we do (m + n)/2 - m/2 = n/2
            // If mid1 is too much to its left (m + n)/2 - mid1/2 = Almost at (m + n)/2. Since mid1 moves to left too much, we rarely move mid2
            // which still stays close to its rightmost
            int mid2 = (midOverall - mid1);

            // If n is odd e.g., 5, n/2 = 2, we can split 0-1, 2-4 (OR) 0-2, 3-4
            // If n is even e.g., 6, n/2 = 3, we need to split 0-2, 3-5
            // So left part can always end at n/2 - 1, odd will get the first type, and even will get what we want
            int leftPart1 = mid1 <= 0 ? Integer.MIN_VALUE : nums1[mid1 - 1];
            int rightPart1 = mid1 >= nums1.length ? Integer.MAX_VALUE : nums1[mid1];

            int leftPart2 = mid2 <= 0 ? Integer.MIN_VALUE : nums2[mid2 - 1];
            int rightPart2 = mid2 >= nums2.length ? Integer.MAX_VALUE : nums2[mid2];

            // Odd no. of entries
            // e.g., 1 [2] [5] 6 | 3 [4] [7] 8 9    Median = 5 (More ele on right, but mid on left). More ele on left will never. num1 is always smaller
            // Looks like the min of the right ones, so the third ele.
            // We need the third largest. THe reason is - num1.len < num2.len (Always happen for odd no. of entries), and so we need to let 2 ele pass and
            // pick the 3rd one. There is a 4th one left (max of right1 & right2), and then num2 will have more numbers. So 3 is the median.
            // We know min (left1, left2) & max(right1, right2) are rejected. How do we know max(left1, left2) is always smaller than min(right1, right2)?
            // Let's say left2 > left1. We know it's smaller than right1 (That's why we entered here), and it's smaller than right2 too (the array is sorted),
            // so the min (right1, right2) is always the third.

            // Even no. of entries
            // e.g., 1 [2] [5] 6 | 3 [4] [7] 8    Median = mid(4, 5) i.e., bigger of the left, smaller of the right and then median
            if (leftPart1 <= rightPart2 && leftPart2 <= rightPart1) {
                if((m + n) % 2 == 1) {
                    // We know the
                    return Math.min(rightPart1, rightPart2);
                } else {
                    return (Math.max(leftPart1, leftPart2) + Math.min(rightPart1, rightPart2)) / 2.0;
                }
            }

            // Move leftPart backwards
            if (leftPart1 > rightPart2) {
                right1 = mid1 - 1;
            } else {
                left1 = mid1 + 1;
            }
        }

        return 0;

    }
}

/*
The simplest way is to use merge sort.
Steps:
- Compare elements (with null checks) across arrays
- copy smaller one to a third array,
- Increment idx of array with smaller one
- Keep going until both arrays are done

Now, find ele with idx = (m + n) / 2
Runtime: O(m + n)

Req runtime is O(log (m+n)). It has loggers, so we need some sort of binary search

When merged, one can fit into any position with respect to the other
e.g., a can be entirely before b, or entirely after, or mixed up
So total count can't be used. What is the common logic?

It's simple if a[0] > b[len - 1] || b[0] > a[len - 1]
We sum and take the median.

If they're mixed with each other, then we need to find the one and see how far the other takes
it towards the next side

1, 2, 3, 4, 5, 6, 7, 8, 9, 10
8, 10, 12, 14, 16
Answer: 8


Mid (a, b) = 5.5 | 12
Since both are sorted, obviously the median is between 5.5, 12
go towards that side in both
Mid = 7.5 | 10
Mid now = 8.5 | 9
Mid now = 8 | 8
So median = 8?

Let's see if it works on disproportionate sets
A : 1, 2, 3, ....., 100
B: 1, 2, 3
Answer: 49

Mid = 50.5 | 2
Mid = 25 | 1.5 (The median is not between these two anymore)
Is it true say we need to find median of the following at this point?
A 1, 2, 3, ....50
B 1, 2
No. Because we are doing half of half and skewing there. We should stop at mid of larger and find
mid of smaller to see where it in respect to that.

e.g., 50 | 2 (with 1 ele left)
Do merge sort with until nothing is left to return 49

What about a bigger B

1, 2, 3, ....., 100
1, 2, 3, ...10

Mid = 50 | 5
We can go back 5 steps but do we want to.

Another ex:
1, 2, 3, ....... 1000
200, 201, .... 300

500 | 250
We don't want to walk back 50 steps here

What if we do binary search in A to find B inner end?
eg. in 500 | 250
find the pos where A's 300 lies using binary search (idx=300)

We know the merge looks like this now
(1... 300 ) ... 1000
 mix of a & b... a only in this zone

 it's straightforward here, but if B is close to the center it's tricky


A 1, 2, 3, ...., 1000
B 471... 490, 511... 550
Answer: 510

Mid 500 | 520
Let's set boundaries here

- We know median is between these
- We need to find how many ele A has between 500 and 520 (A has 20)
- We need to find how many ele B has between 500 and 520 (B has 10)

What is A's median ele in (500, 520)? 510. Similarly, B's is 515
This doesn't help.

Coming back to median count, A has 20, B has 10/
What if we do the same binary folding between these?
Mid for a,b = 510 | 515

How many A has between these - 5
B has 5

Mid for a, b = 512 | 512
So median = 512?
Surely, it's 510. let's try another ex - the one that didn't work

A : 1, 2, 3, ....., 100
B: 1, 2, 3

Mid = 50 | 2
Between 2 and 50, A has 48, B has 1

So, what if we move based on the smaller's count?

We know median will get swayed by that many only.
So B has 1, A can just do merge sort (walk back) for 1 no,


Try with bigger ex

Mid of A, B = 500 | 520. Between these A has 20, B has 10
We know the answer is A will be swayed towards A's no, but B will push it by 10/2 = 5 num

Moved by 5 will be 515 which is not correct
Reason is, the counter for 60 ele of B started when its first e le started

Let's do it based on first ele of B then then?

HINT: Checked with ChatGPT. All you need is the centre element in the concatenated list.

A 1, 2, 3, ...., 1000
B 471... 490, 571... 590
Answer: 520

So, in this example, total ele = 1000+20+20=1040, we need the 520, 521st items

Mid for a, b = Idx=500, 20 | Val=500, 490
Total ele before = 500+20 = 520. We are 10 items early. teh question is should we do binary search for both or one
We need to do both because arrays are sorted

Mid for a, b = Idx=750, 30 | Val=750,580
Total ele before = 780,  go backwards

Idx=625, 25 | Val=625,575
Total ele before = 650, go backwards

Idx=562, 22 | Ele before =584
Idx=531, 21 | Ele before = 552
Idx=515, 20 | Ele before = 535
Idx=507, 20 | Ele before= 527
Idx=507

This is not correct. We are not even looking at the values, and are merely trying to arrive at a position

Check and decide which one to move

Circling back to what is correct:
Mid for a, b = Idx=500, 20 | Val=500, 490
Total ele before higher no i.e., 500 = 500+20 = 520. We are 20 items early.
Let's go 5 up each and fix?

At mid = 10  ==> 510th, 30th | Val=510, 580
20 for left  ==> 520th, 20th | Val=520, 490
20 for right ==> 500th, 30th | Val=500, 580

20 for left < mid, so mid has lot of smaller ele
take al from mid

let's say B is 471...485, 571...595 | and answer: 515

At mid=10

Mid for a, b = Idx=500, 20 | Val=500, 566
Total ele before lower no. 500 = 500+ binarySearch(B for 500) = 15. This is 515. We need 5 ele more.

We know within 500, we have 515 ele.

Add 5 to both sides
add 5 to A | idx=505 | Val=505
add 5 to B | idx=20 | Val=576

Total ele before lower no. 505 = 505 + binarySrach(B for 500) = 15. This is  520. Done!!

Let's say we have we more 505+15+2=522.

We can subtract 2 from each and try again.


 */