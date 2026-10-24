import java.util.ArrayList;
import java.util.List;

/*
22. Generate Parentheses
Medium
Topics
premium lock iconCompanies

Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.



Example 1:

Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]

Example 2:

Input: n = 1
Output: ["()"]



Constraints:

    1 <= n <= 8


 */
public class Problem_0022 {
    public static void main(String[] args) {
        Problem_0022 obj = new Problem_0022();
        System.out.println(obj.generateParenthesis(3));
    }

    public List<String> generateParenthesis(int n) {
        // Skip validations for n
        List<String> outputList = new ArrayList<>();
        recHelper(0, 0, n, new StringBuilder(), outputList);
        return outputList;
    }

    private void recHelper(int openCount, int closeCount, int n, StringBuilder curr, List<String> outputList) {
        if(curr.length() == 2 * n) {
            outputList.add(curr.toString());
            System.out.println("added to output list: " + curr);
            return;
        }

        if(openCount < n) {
            curr.append("(");
            recHelper(openCount + 1, closeCount, n, curr, outputList);
            curr.deleteCharAt(curr.length() - 1);
        }

        if(closeCount < openCount) {
            curr.append(")");
            recHelper(openCount, closeCount + 1, n, curr, outputList);
            curr.deleteCharAt(curr.length() - 1);
        }
    }
}

/*
1 () - 1        | Rejected - )(
2 ()() (())
We're looking at permutation (arrangement) so, 4P4 = n! / (n-r)! = 4! / 0! = 24
However, valid patterns are few. There are dupes too. Let's look at it some other way
3 - ()()()
nPr = 6P4 = 6! / 2! = 360
However, valid patterns = 5

Let's see if these can be constructed from 1

One - ()
Two - How about we insert before and after each pos
    () * () = ()() (()) ()() // First and last are same because one () going before or after another () doesn't make much difference
Three
    ()() (()) * () = ()()() (())() ()()() ()(()) ()()()  ||   ()(()) (()()) ((())) (()()) (())()
        Accepted - 1, 2, 4, 7, 8            || Rejected  - 3 (same as 1), 5 (same as 1), 6 (same as 4), 9 (same as 7), 10 (same as 2)
        So we can also say we choose 1, 2, 6, 7, 8 || 3, 4, 5, 9, 10
        Output - ()()() (())() ()(()) (()()) ((()))
        Pattern - 101010 110010 101100 110100 111000
        Ascending order - 101010 101100 110010 110100 111000

Ascending order - 101010 101100 110010 110100 111000
Is there a progression?
1010 => 1100 so 1 shifts left, then 101100 becomes 110 ... wait let's try descending

111000 => push a 1 down 110100, push more 110010, can't end with 1
next time push next one
    110100 => 101100 just one time, but what logic?
    101010 => can always add at last, but what logic?

Removing duplicates by sets is cumbersome. Is there a better way?

We always multiply by (), so is there a simpler way?
How about changing number of (, ) before and after ()?

For Two
    opens = 0 to 1
    throw in ()
    fill in the rest

so, we get ()(), (())
so, 1010, 1100

For Three
    opens = 0 to 2
    throw in ()
    fill in the rest

What if we can push by count logic?
11110000
    11101000 11100100 11100010
      x
               11010100 11001100
                 1101


111000 ..(1)
    Start with (3rd 1, so can let 2 zeros in front)
    Letting 1 zero up
    110100 ..(2)
    Letting 2 zero up
    110010 ..(3)

    Start with (2nd 1, so can let 1 zero in front)
    101100 .. (4)
        Has one 1 under, so can let 1 zero in front
            101010 ... (5)

    Start with (3rd 1, so can have 0 zero in front)
    Finish

That worked. So the same for 4 ()
11110000
    Start with 4th 1, so can let 4-1 = 3 zeros in front). Always 1 zero starting the pack
    Letting 1 one zero up
    11101000 ... (1)
    2 zero up
    11100100
    3 zero up
    11100010

    start with 3rd, so can let 3-1=2 zeros in front. (Also, it has one zero underneath, so a recursion there)
    // 1 zero up
    11011000
        1 zero under, so can let in front of it
            11010100
    // 2 zero up
    11001100
            11001010
and so on

Algorithm:

for int i = 1 ... n
    int num = prep init num // we have 111000. Pass this from elsewhere to allow recursion
    addToOut(num)
    for(int i = 0; i < n - 1; i++)  // one zero need to start, so we finish at < n-1
        int nthOne = n-i; // so start with 3rd n
        int numValAtThisOne = findNumValAtThisOne(num, 3)
        int zerosThatCanBeLetUp =




prep init num(n)
    // 10 aka 2 for 1, 1100 aka 8 + 2 for 2
    for 1, create 1 and LS 1 time
    for 2, create 2^0 + 2^1, and LS 2 times
    for 3, create 2^0 + 2^1 + ^2, and LS 3 times

    Wait, how about using -1

    so for 1, 2^2 - 1, and LS 1 time
    for 2, 2^3 - 1, and LS 2 times, so 100-1 = 11 and
    int ls = Math.pow(2, n + 1)
    ls--
    num = ls << n

findNumValAtThisOne(num, 3) // example we have 111000, give me val of 1000
    int x = 1
    x = x << 3
    return x

============================

Looked up ChatGPT solution, and it uses a recursion that builds it one by one.
I was looking at a way to jump from one finished solution to another.
I also missed building a basic case

algorithm:
recursive method (int open, int close, int currLen, int n, StringBuilder currOutput, List outputList)

    if output == n*2
        add to outputList

    // add one by one, doing open < n goes for ((())) first.
    // doing open <= close will go for ()()() first. Doesn't matter
    if open < n
        add ( to currOutput
        rec (open + 1, close, currLen + 1, n, currOutput, outputList)

    if close < open
        add ) to currOutput
        rec (open, close +1, currLen + 1, n, currOutput, outputList)





 */
