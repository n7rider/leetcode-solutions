/*
29. Divide Two Integers
Medium
Topics
premium lock iconCompanies

Given two integers dividend and divisor, divide two integers without using multiplication, division, and mod operator.

The integer division should truncate toward zero, which means losing its fractional part. For example, 8.345 would be truncated to 8, and -2.7335 would be truncated to -2.

Return the quotient after dividing dividend by divisor.

Note: Assume we are dealing with an environment that could only store integers within the 32-bit signed integer range: [−2^31, 2^31 − 1]. For this problem, if the quotient is strictly greater than 2^31 - 1, then return 2^31 - 1, and if the quotient is strictly less than -2^31, then return -2^31.



Example 1:

Input: dividend = 10, divisor = 3
Output: 3
Explanation: 10/3 = 3.33333.. which is truncated to 3.

Example 2:

Input: dividend = 7, divisor = -3
Output: -2
Explanation: 7/-3 = -2.33333.. which is truncated to -2.



Constraints:

    -231 <= dividend, divisor <= 231 - 1
    divisor != 0


 */
public class Problem_0029_Illustrate {
    public static void main() {

        System.out.println(-2147483648/-1);
        System.out.println(-2147483647/-1);
        System.out.println(2147483647+1);

        Problem_0029_Illustrate obj = new Problem_0029_Illustrate();

        System.out.println(obj.divide(134, 3));

        System.out.println(obj.divide(10, 3));
        System.out.println(obj.divide(7, -3));

        System.out.println(obj.divide(-1, 1));
        System.out.println(obj.divide(-7, -2));
        System.out.println(obj.divide(-2147483648, -1));
        System.out.println(obj.divide(-2147483648, 1));
        System.out.println(obj.divide(-2147483648, -2));
        System.out.println(obj.divide(-2147483648, 2));

    }

    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // Convert to long before taking absolute values.
        long a = Math.abs((long) dividend);
        long b = Math.abs((long) divisor);

        long quotient = 0;

        while (a >= b) {
            long chunk = b;
            long multiple = 1;

            // Find the largest doubled divisor that fits.
            while (a >= (chunk << 1)) {
                chunk <<= 1;
                multiple <<= 1;
            }

            // Subtract that chunk and accumulate its quotient.
            a -= chunk;
            quotient += multiple;
        }

        // Quotient is negative iff signs differ.
        if ((dividend < 0) ^ (divisor < 0)) {
            quotient = -quotient;
        }

        return (int) quotient;
    }

    private boolean hasReached(int pileup, int div1, boolean bothNeg, boolean oneNeg) {
        if(oneNeg) {
            return pileup >= div1;
        } else if(bothNeg) {
            return pileup >= div1;
        } else {
            return pileup <= div1;
        }

    }
}

/*
No div, mul, mod

Mul- piling up until dividend is reached. Can't use
Mod - Could have used for ??

Can we use add, sub?
With add & sub, we can pile up divisor until dividend is reached
Bitwise operators - Can do but gets complex

Simplest way:
add & sub:

int quot = 0, pileup = 0
while (pileup <= dividend)
    pileup += divisor

return quotient

Note: couple of nuances:
if dividend < 0 ^ divisor ^ 0, return -quotient
Do abs before starting

---

Bitwise operator eg., 11011 / 11 i.e., 27 / 3 = 9

With div, we can't do anything piecemeal, so the 11 needs to be processed across all digs in 11011

How about we binary search it then?
But we don't know which way the answer lies.

So, going with the +/- way

After looking at -2147483648, it is obvious that doing abs will fail
Need to decide the direction based on the operands

Simplest way:
-1, -2147483648
keep adding -1 until it reaches there

==== Checking with ChatGPT

Apparently -2147483648 / -1 will never work. Because we keep accumulating in while loop until pileup >= dividend
And when we reach -2147483648, the value overflows to -2147483648+1 = 2147483647. This is still true. And adding -1 to this
number also keeps the while loop true forever.

The AI solution too handles this in a special case.

Other than that, my initial math case works. Make everything positive, just add - to quotient at the end.

The difference is with the pileup. I considered my bitwise but ignored it for not able to do anything except multiply by 2.
But apparently there's a way. Since we can't multiply directly, we maintain two values. divisor * 2, quotient = 1 * 2
until the former reaches the dividend. Take the quotient away.
Reduce the dividend - divisor and do the same thing again (e.g., with 33/3, divisor stops at 3,6,12,24. add quot i.e., 8 to final quotient
For the remaining, 33-24=9, do the same thing. It stops 3,6 and so on.

Consider it this way. Bitwise operator can multiply only by 2, but that's all we have. And it's still better than a while loop running all the way.
So keep multiplying the divisor with it until we reach dividend, and repeat.
Also, consider the strategy here. If you can't do something like a/b=c, you can process multiple vars until you get what is needed.
 */
