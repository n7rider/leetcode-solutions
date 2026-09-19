/*
7. Reverse Integer
Medium
Topics
premium lock iconCompanies

Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside the signed 32-bit integer range [-231, 231 - 1], then return 0.

Assume the environment does not allow you to store 64-bit integers (signed or unsigned).



Example 1:

Input: x = 123
Output: 321

Example 2:

Input: x = -123
Output: -321

Example 3:

Input: x = 120
Output: 21



Constraints:

    -231 <= x <= 231 - 1

 */
public class Problem_0007 {
    public static void main(String[] args) {
//        System.out.println(reverse(123));
//        System.out.println(reverse(-123));
//        System.out.println(reverse(120));
//        System.out.println(reverse(2147483647));
//        System.out.println(reverse(-2147483412));
        System.out.println(reverse(-1463847412));
    }

    public static int reverse(int x) {
        int MAX = Integer.MAX_VALUE; // 2147483647
        int MIN = Integer.MIN_VALUE; // -2147483648
        int out = 0;
        while (x != 0) {
            int currDig = x % 10;
            if (out > MAX / 10 || (out == MAX / 10 && currDig > MAX % 10))
                return 0;
            if (out < MIN / 10 || (out == MIN / 10 && currDig < MIN % 10))
                return 0;

            out = out * 10 + currDig;
            x = x / 10;
        }
        return out;
    }
}

/*
Reversing is simple with a while loop

How to find if we exceed Limits

ex: 2147483647. Reversing returns 7463847412
so, we can check if reversed no. so far > 214748364 || ( 214748364 && next-dig > 6
for -neg check if reversed no. so far  < -214748364 1 (= 214748364 && next-dig > 7)

reverse (x)
    int MAX = Integer.MAX;
    int MIN = Integer.MIN;
    while (x> 0)
        out = out * 10 + ( x % 10)
        x = x / 10
        if (out > MAX / 10 || (out == MAX / 10 && (x % 10) > MAX % 10)
            return 0
        if (out < MIN / 10 || (out == MUN / 10 && (x % 10) > MIN % 10)
            return 0
    return out

Lessons:
-----
Remember x % 10 will return negative if x < 0, so use < > operators accordingly.
Runtime
1ms
Beats99.96%
Memory
42.37MB
Beats90.22%

*/