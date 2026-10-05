import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
17. Letter Combinations of a Phone Number
Medium
Topics

Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.

A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.


Example 1:

Input: digits = "23"
Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]

Example 2:

Input: digits = "2"
Output: ["a","b","c"]

Constraints:

    1 <= digits.length <= 4
    digits[i] is a digit in the range ['2', '9'].

Str[] output = [ {""} ];
for i = 0 to strCollected.len - 1 // iterate through each button
   // do cartesian product of output * inp[i]
   tempOut = []
   for outputIter = 0 to outputIter.len - 1  // iterate through output
     for j = 0 to btn[i].len - 1            // iterate through curr btn
            tempOut.add output[i] + btn[j]

   for outEntry: outSet  // iterate through output
     for j = 0 to btn[i].len - 1            // iterate through curr btn
            outSet.add outEntry + btn[j]
     outSet.remove(outEntry)

 */
public class Problem_0017 {
    public static void main() {
        Problem_0017 obj = new Problem_0017();
        var out1 = obj.letterCombinations("23");
        System.out.println(out1.size() + " " + out1);

        var out2 = obj.letterCombinations("645");
        System.out.println(out2.size() + " " + out2);

        var out3 = obj.letterCombinations("7433");
        System.out.println(out3.size() + " " + out3);

        var out4 = obj.letterCombinations("74723");
        System.out.println(out4.size() + " " + out4);
    }

    public List<String> letterCombinations(String digits) {
        // Skip parse validation, len validation, num range validation [2-9]
        // Num-range validation happens within the loop though

        String[] numPad = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
        Integer parsedDig = Integer.parseInt(digits);
        List<String> out = new ArrayList<>();
        out.add("");
        for(int i = 0; i < digits.length(); i++) {
            int currNum = Integer.parseInt(String.valueOf(digits.charAt(i)));
            List<String> tempOut = new ArrayList<>();
            // Skip num range validation [2-9]
            for(int j = 0; j < out.size(); j++) {
                for(int k = 0; k < numPad[currNum].length(); k++) {
                    String currStr = out.get(j);
                    char currNewChar = numPad[currNum].charAt(k);
                    tempOut.add(currStr + currNewChar);
                }
            }
            out = tempOut;
        }

        return out;
    }
}

/*
This is to find all possible combinations

Have hardcoded char[] and fetch by idx
e.g., char[][] btnLetters = [{}, {}, {'a', 'b', 'c'} ....]

Consider n = input length, m = avg. letters in a btn, the runtime is O(n) with a simple for loop

any order is fine, so we can go by % 10

output = String[]
while i > 0
    int btn = i % 10
    for int j = 0 to char[btn].length - 1
        StringBuilder temp = new <>
        temp.concat char[btn][j]
    output.add temp
    i = i / 10

Concerns:
Runtime is O(n * m) for two letters
Order is reversed
Alg. is simple
Works only for two letters
Actually builds a word for chars inside a word

Rebuild:
2 - a, b, c | 3 - d, e, f

with two nums, you can do a Cartesian product

for i = 0 to num1.length - 1
    for j = 0 to num1.length - 1
        add i+j to output

We don't know the len of input, so we can create an array outside or recursion

array outside:
for inpIter = 0 to inp.length - 1 // If inp is 7827, it runs 4 times
    // you'll need configurable loops, so recursion is easier

let's write the idea first, that's simple


Vague idea??
for inpIter = 0 to inp.length - 1
    call recHelper(inp, inpIter, valSoFar, inpIdx - 1, output)


Consider 2, 3 = 2 - a, b, c | 3 - d, e, f
Output = a* (d, e, f), b * (d, e, f), c * (d, e, f)
So, write upto n-1, and then finally loop at each val, and product each ele of yourself?
Something like this:
for i = 0 to strCollected.len - 1
    // do this only if this is determined as the final step

    for j = 0 to btn[curr].len - 1
        output.add (i+j)

What if inp is 3 letters long, what do we do at the first step
e.g., inp = 6 8 2 - mno | tuv | abc
First step collects m*(t,u,v) | n*(t,u,v,) | ...
So the same process, but we don't consider it as the output until we reach the final step
So the final process

Str[] output = [ {""} ];
for i = 0 to strCollected.len - 1 // iterate through each button
   // do cartesian product of output * inp[i]
   tempOut = []
   for outputIter = 0 to outputIter.len - 1  // iterate through output
     for j = 0 to btn[i].len - 1            // iterate through curr btn
            tempOut.add output[i] + btn[j]

 e.g., Consider 6 8 2 from above, well too many 3s
 Let's consider 7 2 3 4 - pqrs, abc, def, ghi
7, 2 = 4*3=12 | add 3 = 12*3 | add 4 | 36 *4
So, runtime is (avg len) is multipled by itself for btn press times i.e., it's O(n^m) runtime, and the len is the same

*** The reason why this is O(n^m) and not O(n^ loop-count) i.e., O(n^3) is because the middle loop runs up to the len of the output,
and not just n times.
// Assuming all btn have same seq len
First, middle + last loop = O(1 * n) = O(n)
Second, O(n * n) = O(n^2)
Third, O(n^2 * n) = O(n^3)
Fourth, = O(n^4)

so, the inner 2 loops themselves exceed O(n^3) which is the runtime for 3 for loops
And at each len, it's at O(n^m) where m is the len of button presses
So the runtime actually is O(n^m + n^(m-1) + .... n)


Can we improve upon this?
Can reduce space usage by using a Hashset for output, and then removing each entry after the internal 2 loops finish -- something like this

   for outEntry: outSet  // iterate through output
     for j = 0 to btn[i].len - 1            // iterate through curr btn
            outSet.add outEntry + btn[j]
     outSet.remove(outEntry)

// Concurrent modification is not allowed, so an iterator should be used then.



1 loop - O(n)
// Realized n*n can be expressed as n happens n times
2 loops - n     happens n times - O(n^2)
3 loops - n * n happens n times - O(n^2 ) * n = O(n^3)
4 loops - O(n^3) happens n times = O(n ^ 4)

 */