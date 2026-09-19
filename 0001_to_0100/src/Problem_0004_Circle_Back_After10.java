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

public class Problem_0004_Circle_Back_After10 {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] b = {4, 7, 9};
        // Expected - 6
        System.out.println(median(a, b));

        int[] c = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        int[] d = {1, 2, 4, 7, 9, 20};
        // Expected
        System.out.println(median(c, d));

        int[] e = {1, 3};
        int[] f = {2, 4, 5};
        System.out.println(median(e, f));
    }

    static double median(int[] nums1, int[] nums2) {
        // Validate lengths of nums1 and nums2
        int total = nums1.length + nums2.length;
        return medianHelper(nums1, nums2);
    }

    private static int medianHelper(int[] a, int[] b) {
        // Validations: If total len 1, return 1
        int totalLen = a.length + b.length;
        int medianPos = (totalLen + 1) / 2; //1-based, so add 1
        int eleBeforeCurrIdx = 0;

        int[] arrWSmallerEle = a[(a.length) /2] < b[(b.length) / 2] ? a : b;
        int[] arrWLargerEle = arrWSmallerEle == a ? b : a;

        int newIdxSmallerArray = (arrWSmallerEle.length) / 2;
        int newIdxLargerArray = (arrWLargerEle.length) / 2;
        int diff = 0;

        while(true) {
            if (eleBeforeCurrIdx == medianPos) {
                return bigger(arrWSmallerEle[newIdxSmallerArray], arrWLargerEle[newIdxLargerArray]);
            } else {
                // No change needed if smaller is smaller
                if(arrWSmallerEle[newIdxSmallerArray] > arrWLargerEle[newIdxLargerArray]) {
                    int[] tempArr = arrWSmallerEle;
                    arrWSmallerEle = arrWLargerEle;
                    arrWLargerEle = tempArr;

                    int tempVal = newIdxSmallerArray;
                    newIdxSmallerArray = newIdxLargerArray;
                    newIdxLargerArray = tempVal;
                }

                newIdxSmallerArray = newIdxSmallerArray + diff;
                newIdxLargerArray = newIdxLargerArray + diff;

                int idxInLargerArray = findIdxLeVal(arrWLargerEle, arrWSmallerEle[newIdxSmallerArray]);
                eleBeforeCurrIdx = newIdxSmallerArray + idxInLargerArray + 2; // Each array is 0-based, so add 2
                newIdxLargerArray = idxInLargerArray;
                // Need to add/sub elements
                diff = medianPos - eleBeforeCurrIdx;
            }
        }
    }

    // Find highest idx less than or equal to value -- use binary search
    private static int findIdxLeVal(int[] z, int val) {
        int start = 0;
        int end = z.length - 1;
        int closestIdx = -1, diff = Integer.MAX_VALUE;
        while(start <= end) {
            int mid = (start + end) / 2;
            if(val == z[mid]) {
                return mid;
            }

            if (val > z[mid]) {
                if((val - z[mid]) < diff) {
                    diff = val - z[mid];
                    closestIdx = mid;
                }
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        // Didn't find an exact value, use the closest
        return closestIdx;
    }

    private static int bigger(int a, int b) {
        if(a > b) {
            return a;
        }
        return b;
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