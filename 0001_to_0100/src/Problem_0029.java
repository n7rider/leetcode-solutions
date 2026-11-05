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
public class Problem_0029 {
    public static void main() {

        System.out.println(-2147483648/-1);
        System.out.println(-2147483647/-1);
        System.out.println(2147483647+1);

        Problem_0029 obj = new Problem_0029();
        System.out.println(obj.divide(10, 3));
        System.out.println(obj.divide(7, -3));

        System.out.println(obj.divide(-1, 1));
        System.out.println(obj.divide(-7, -2));
        System.out.println(obj.divide(-2147483648, -1));

    }

    public int divide(int dividend, int divisor) {

        int pileup = divisor;
        int quot = 0;
        int div1 = dividend;
        int div2 = divisor;
        boolean oneNeg = false;
        boolean bothNeg = false;

        // - 2147483648 & -1 , just need to switch the <= operator with >= operator
        if(dividend < 0 && divisor < 0) {
            bothNeg = true;
        } else if(dividend < 0 ^ divisor < 0) {
            // -342, 17 Or 342, -17.
            // Just need neg in the quotient. So can make div1 -ve, div +ve, use gtcheck
            div1 = dividend >= 0 ? -dividend : dividend;
            div2 = Math.abs(divisor);
            oneNeg = true;
            pileup = -div2;
        }

        while(hasReached(pileup, div1, bothNeg, oneNeg)) {
            if(oneNeg) {
                pileup -= div2;
            } else {
                pileup += div2;
            }
            quot++;
        }

        if(dividend < 0 ^ divisor < 0) {
            return -quot;
        }

        return quot;
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
 */
