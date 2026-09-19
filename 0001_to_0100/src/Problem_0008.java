/*
8. String to Integer (atoi)
Medium
Companies

Implement the myAtoi(string s) function, which converts a string to a 32-bit signed integer.

The algorithm for myAtoi(string s) is as follows:

    Whitespace: Ignore any leading whitespace (" ").
    Signedness: Determine the sign by checking if the next character is '-' or '+', assuming positivity if neither present.
    Conversion: Read the integer by skipping leading zeros until a non-digit character is encountered or the end of the string
    is reached. If no digits were read, then the result is 0.
    Rounding: If the integer is out of the 32-bit signed integer range [-2^31, 2^31 - 1], then round the integer to remain
    in the range. Specifically, integers less than -2^31 should be rounded to -2^31, and integers greater than 2^31 - 1 should be rounded to 2^31 - 1.

Return the integer as the final result.

Example 1:

Input: s = "42"

Output: 42

Explanation:

The underlined characters are what is read in and the caret is the current reader position.
Step 1: "42" (no characters read because there is no leading whitespace)
         ^
Step 2: "42" (no characters read because there is neither a '-' nor '+')
         ^
Step 3: "42" ("42" is read in)
           ^

Example 2:

Input: s = " -042"

Output: -42

Explanation:

Step 1: "   -042" (leading whitespace is read and ignored)
            ^
Step 2: "   -042" ('-' is read, so the result should be negative)
             ^
Step 3: "   -042" ("042" is read in, leading zeros ignored in the result)
               ^

Example 3:

Input: s = "1337c0d3"

Output: 1337

Explanation:

Step 1: "1337c0d3" (no characters read because there is no leading whitespace)
         ^
Step 2: "1337c0d3" (no characters read because there is neither a '-' nor '+')
         ^
Step 3: "1337c0d3" ("1337" is read in; reading stops because the next character is a non-digit)
             ^

Example 4:

Input: s = "0-1"

Output: 0

Explanation:

Step 1: "0-1" (no characters read because there is no leading whitespace)
         ^
Step 2: "0-1" (no characters read because there is neither a '-' nor '+')
         ^
Step 3: "0-1" ("0" is read in; reading stops because the next character is a non-digit)
          ^

Example 5:

Input: s = "words and 987"

Output: 0

Explanation:

Reading stops at the first non-digit character 'w'.



Constraints:

    0 <= s.length <= 200
    s consists of English letters (lower-case and upper-case), digits (0-9), ' ', '+', '-', and '.'.


 */
public class Problem_0008 {
    public static void main(String[] args) {
        Problem_0008 obj = new Problem_0008();
        System.out.println(obj.myAtoi("42"));
        System.out.println(obj.myAtoi("-042"));
        System.out.println(obj.myAtoi("1337c0d3"));
        System.out.println(obj.myAtoi("0-1"));
        System.out.println(obj.myAtoi("words and 987"));
        System.out.println(obj.myAtoi(" 2147483648"));
        System.out.println(obj.myAtoi("-2147483649"));
        System.out.println(obj.myAtoi("2147483646"));

    }

    public int myAtoi(String s) {
        int c = 0;

        while (c < s.length() && s.charAt(c) == ' ') {
            c++;
        }

        int sign = 1;
        boolean signFound = false;
        int out = 0;
        while(c < s.length()) {
            if(!signFound && s.charAt(c) == '-') {
                sign = -1;
                signFound = true;
                c++;
                continue;
            }
            if(!signFound && s.charAt(c) == '+') {
                signFound = true;
                c++;
                continue;
            }
            if(isNum(s.charAt(c))) {
                if(withinRange((s.charAt(c) - '0'), out, sign)) {
                    signFound = true; // nums have started, no more signs allowed
                    out = out * 10 + (s.charAt(c) - '0');
                    c++;
                    continue;
                } else {
                    return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                }
            }
            return sign * out;
        }
        return sign * out;
    }

    private boolean isNum(char x) {
        return x >= '0' && x <= '9';
    }

    private boolean withinRange(int currDig, int numSoFar, int sign) {
        if(sign == 1) {
            if(numSoFar > Integer.MAX_VALUE / 10 || (numSoFar == Integer.MAX_VALUE / 10 && currDig > Integer.MAX_VALUE % 10)) {
                System.out.printf("+ve range exceeded: %d %d %d\n", currDig, numSoFar, Integer.MAX_VALUE);
                return false;
            }
        } else {
            numSoFar *= -1;
            currDig *= -1;
            if(numSoFar < Integer.MIN_VALUE / 10 || (numSoFar == Integer.MIN_VALUE / 10 && currDig < Integer.MIN_VALUE % 10)) {
                return false;
            }
        }
        return true;
    }
}

/*
atoi:
    c = 0
    int sign = 1
    // spaceChk
    while(c < s.length && s.charAt(c) == ' ')
        c++

    bool signFound = false
    while(c < s.length)
        if(s.charAt(c) == '-' && !signFound)
            sign = -1;
            signFound = true
            c++
            continue;
        if(s.charAt(c) == '+'  && !signFound)
            signFound = true
            c++
            continue
        if(isNum(s.charAt(c))
            if(withinRange(s.charAt(c), out, sign)
                signFound = true // nums have started, no more signs
                out = out * 10 + s.charAt(c)
                c++
                continue
            else
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE
        else
            return sign * out;

withInRange(currDig, int numSoFar, sign) // int (and not Integer) for param 1 & 2 because we mutate
    if(sign == 1)
        if(numSoFar > MAX_VALUE / 10 || (numSoFar == MAX_VALUE / 10 && currDig > MAX_VALUE % 10)
            return false
    else
        numSoFar *= -1
        currDig *= -1
        if(numSoFar < MIN_VALUE / 10 || (numSoFar == MIN_VALUE / 10 && (currDig < MIN_VALUE % 10)
            return false
    return true

isNum(char x)
    return x >= '0' and x <= '9'


-======================

Runtime
1ms
Beats 100.00%
Memory
43.89MB
Beats 41.38%

Notes:
We can take the sign checker outside the second loop if I do c++ only when a sign is encountered.

 */
