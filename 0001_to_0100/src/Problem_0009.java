/*
Given an integer x, return true if x is a palindrome, and false otherwise.

Example 1:
Input: x = 121
Output: true
Explanation: 121 reads as 121 from left to right and from right to left

Example 2:
Input: x = -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it comes 121-. Therefore it is not a palindrome.

Example 3:
Input: x = 10
Output: false
Explanation: Reads 01 from right to left.
Therefore it is not a palindrome

Constraints:
-2^31 <= x <= 2^31 - 1

 */
public class Problem_0009 {

    public static void main() {
        Problem_0009 obj = new Problem_0009();
        System.out.println(obj.isPalindrome(121));
        System.out.println(obj.isPalindrome(-121));
        System.out.println(obj.isPalindrome(10));
    }

    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }

        int y = 0;
        int xCopy = x;
        while(xCopy > 0) {
            y = y * 10 + (xCopy % 10);
            xCopy = xCopy / 10;
        }

        return (x == y);
    }
}


/*
-121 is not allowed.
The - is not read as minus last in int, unless we are talking about bit-level palindrome.
However, we are talking about integer palindrome, not a palindrome in terms of binary (The example given is 121, so
we are not using binary for sure)

core logic for palindrome:
int y = 0;
while (x > 0)
    y = y * 10 + x % 10
    x = x / 10

return x == y

reject negatives before hand

======================
Things to watch out for:
The logic that I use for reversing mutates the source. If you are mutating x, remember to take a copy and use it for the equality comparison at the end

ChatGPT does these additional validations:
- Numbers ending in 0 other than 0 can never be palindromes (x % 10 == 0 && x != 0)
- Compares only upto the first half digits (i.e., while (x > reversedHalf)), and then x == reversedHalf || x == reversedHalf / 10
    The first condition works for even number of digits, the second condition works for odd number of digits e.g., 121
        where reversedHalf = 12, x = 1


=======================
Runtime
5ms
Beats78.90%
Memory
45.65MB
Beats93.45%

 */

