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
public class Problem_0029_2 {
    public static void main() {

        System.out.println(-2147483648/-1);
        System.out.println(-2147483647/-1);
        System.out.println(2147483647+1);

        Problem_0029_2 obj = new Problem_0029_2();
        System.out.println(obj.divide(10, 3));
        System.out.println(obj.divide(7, -3));

        System.out.println(obj.divide(-1, 1));
        System.out.println(obj.divide(-7, -2));
        System.out.println(obj.divide(-2147483648, -1));
        System.out.println(obj.divide(-2147483648, 1));
        System.out.println(obj.divide(-2147483648, -2));
        System.out.println(obj.divide(-2147483648, 2));

        System.out.println(obj.divide(-1010369383, -2147483648));
        System.out.println(obj.divide(-2147483648, -2147483648));
    }
    /*
    2147483647
-2147483648
1073741824
-1073741824
     */

    public int divide(int dividend, int divisor) {

        boolean isMinValueHandlingNeeded = false;
        if(dividend == Integer.MIN_VALUE || divisor == Integer.MIN_VALUE) {
            if(dividend == divisor) {
                return 1;
            }
            if(dividend == Integer.MIN_VALUE && divisor == -1) {
                return Integer.MAX_VALUE;
            }
            if(dividend == Integer.MIN_VALUE && divisor == 1) {
                return Integer.MIN_VALUE;
            }
            if(divisor == Integer.MIN_VALUE) {
                // We know dividend is not as this number, so quot will be 0 anyway
                return 0;
            }
            if (dividend == Integer.MIN_VALUE) {
                dividend = dividend + Math.abs(divisor);
                isMinValueHandlingNeeded = true;
            }
        }

        boolean isOneNeg = dividend < 0 ^ divisor < 0;
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);

        int currDiv = dividend;
        int finalQuot = 0;

        while(currDiv >= divisor) {
            int quot = 1;
            // accumulatedDiv starts at divisor, and gets multiplied by some nums until it reached the actual dividend
            // If z = x/y, z * y = x. In z*y, consider accDiv starts with z, and gets multiplied by y to reach x.
            int accDiv = divisor;

            while(currDiv >= (accDiv << 1) && accDiv << 1 > 0) {
                accDiv = accDiv << 1;
                quot = quot << 1;
            }

            finalQuot = finalQuot + quot;
            currDiv = currDiv - accDiv;
        }


        if(isMinValueHandlingNeeded) {
            finalQuot = finalQuot + 1;
        }
        if(isOneNeg) {
            finalQuot = -finalQuot;
        }
        return finalQuot;
    }

}

/*

int accDiv = divisor

while currDiv >= divisor
    quot = 1
    accDiv = divisor
    while (currDiv >= accDiv << 1)
        accDiv = accDiv << 1
        quot = quot << 1

    quot = quot + finalQuot

    diff = currDiv - accDiv

    currDiv = diff


return quot

===
This works fine, but takes a long time due to int being int.
The AI solution converts to long, but this is not allowed, this is the way. Need to handle all weird ways in which
Integer.MIN_VALUE works.

 */
