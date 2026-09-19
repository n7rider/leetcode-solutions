/*
6. Zigzag Conversion
Medium
Topics
premium lock iconCompanies

The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)

P   A   H   N
A P L S I I G
Y   I   R

And then read line by line: "PAHNAPLSIIGYIR"

Write the code that will take a string and make this conversion given a number of rows:

string convert(string s, int numRows);



Example 1:

Input: s = "PAYPALISHIRING", numRows = 3
Output: "PAHNAPLSIIGYIR"

Example 2:

Input: s = "PAYPALISHIRING", numRows = 4
Output: "PINALSIGYAHRPI"
Explanation:
P     I    N
A   L S  I G
Y A   H R
P     I

Example 3:

Input: s = "A", numRows = 1
Output: "A"



Constraints:

    1 <= s.length <= 1000
    s consists of English letters (lower-case and upper-case), ',' and '.'.
    1 <= numRows <= 1000


 */
public class Problem_0006 {
    public static void main(String[] args) {
        System.out.println(convert("A", 1));
        System.out.println(convert("PAYPALISHIRING", 3));
        System.out.println(convert("PAYPALISHIRING", 4));

    }

    private static String convert(String s, int numRows) {
        int bottomSkip = (numRows - 1) + (numRows - 2) + 1; // i.e., 0, 6, 12 for n = 4

        if(bottomSkip <= 0) {
            return s;
        }

        int topSkip = 0; // no upwards triangle when we begin
        StringBuilder outSb = new StringBuilder();

        for (int i = 0; i < numRows; i++) {
            int currChar = i;
            boolean downwardsTraversal = true; // In downwardsTraversal,
            while (currChar < s.length()) {
                if (downwardsTraversal && bottomSkip != 0) {
                    outSb.append(s.charAt(currChar));
                    currChar += bottomSkip;
                }
                if (!downwardsTraversal && topSkip != 0) { // If oddCount
                    outSb.append(s.charAt(currChar));
                    currChar += topSkip;
                }
                downwardsTraversal = !downwardsTraversal;
            }
            bottomSkip -= 2;
            topSkip += 2;
        }
        return outSb.toString();
    }
}

/*
How does this work?
num rows = n
go 0 to (n-1) in [row i, col 0]
go (n-1) to 1 in [row (n-1 to 1), col (i)]
repeat

how do we convert back?
num rows = n
use the same logic as above, but rather than insert, do a read


convertToScrambled(String s, int numRows)
    var out = createMatrix(s, numRows)
    getScrambledFromMatrix(out)

convertFromScrambled(String s, int numRows)


createMatrix(s, numRows)
    // 4 + (4-1-1) is needed for (4-1) rows (OR) (4+4-2) chars. So we need s / (4+4-2) chars * (4 + (4-1-1)) cols (+1 if there is remainder
    int cols req = s % (2 * numRows - 2) == 0
        ? s / (   2 * numRows - 2  ) * (numRows + numRows - 1 - 1)
        : s / (   2 * numRows - 2  ) * (numRows + numRows - 1 - 1) +  (numRows + numRows - 1 - 1)
    int charsPerSet = numRows + numRows - 1 - 1;
    int colsPerSet = numRows - 1;
    int numSets = s.length / charsPerSet;
    int colsRequired = s.length % charsPerSet == 0
        ? numSets * charsPerSet
        : (numSets + 1) * charsPerSet // Adding 1 for simplicity, can do a while loop for the exact count
    char[][ out = new char[numRows][colsRequired]
    int count = 0
    int rowPos = 0
    int colPos = 0
    while(true)
        if(count % (numRows - 1) == 0) // each set has numRows-1 cols each
            for(i = 0; i < numRows; i++)
                if(count == s.length)
                    return out
                out[rowPos++][colPos] = s.charAt(count++)
            rowPos = 0;
            colPos++;
        else
            for(i = numRows - 1 - 1; i > 0; i--) // penultimate row
                if(count == s.length)
                    return out
                out[i][colPos++] = s.charAt(count++)
    return out



add(char[][] out, int currIdx, numChars, count)
    for(i = 0; i < count; i++)
        out

getScrambledFromMatrix(int[][] out)
    StringBuffer outSb
    for(int i = 0; i < out.length; i++)
        for(int j = 0; j < out[0].length; j++)
            if(out[i][j] == '' || out[i][j] == null)
                continue
            outSb.append(out[i][j]
    return outSb.toString()

Is there a simpler way?

"PAYPALISHIRING"

P   A   H   N
A P L S I I G
Y   I   R

And then read line by line: "PAHNAPLSIIGYIR"

Input: s = "PAYPALISHIRING", numRows = 3
Output: "PAHNAPLSIIGYIR"

Input: s = "PAYPALISHIRING", numRows = 4
Output: "PINALSIGYAHRPI"
Explanation:

P     I    N
A   L S  I G
Y A   H R
P     I

Test the last example:

P A Y P A L I S H I  R  I  N  G
0 1 2 3 4 5 6 7 8 9 10 11 12 13

PIN follows regular spacing of numRows + (numRows -2) i.e., 0, 6, 12
ALSIG follows regular spacing of
    (6 - 2) for the bottom skips (so, A, L = 1, 5)
    (6 - (6-2) for the top skips (L, S = 5, 7)
    Alternate these (1, 5, 7, 11, 13)
YAHR follows regular spacing of
    (6-2)-2 for the bottom skips (so, YA = 2, 4
    (6 - ((6-2)-2) for the top skips (so, AH = 4, 8)
    Alternate these (2, 4, 8, 10)
Stop when ColsPerSet is reached

convertToScrambled(String s, int numRows)
    int bottomSkip = (numRows -1) + (numRows - 2) + 1 // i.e., 0, 6, 12
    int topSkip = 0 // no upwards triangle when we begin
    Stringbuffer outSb
    for(int i = 0; i < numRows; i++)
        int currChar = i;
        boolean evenCount = true;
        while(count < s.length)
            s = s.append(currChar)
            if evenCount
                currChar += bottomSkip
            else
                currChar += topSkip
            evenCount = !evenCount
        bottomSkip-=2
        topSkip+=2
    return outSb.toString()



 */

/*
Runtime
==========
3ms
Beats93.61%
Memory
46.32MB
Beats87.79%

ChatGPT creates a StringBuilder for each row and merges them.
To jump between each SB, it changes traversal direction to i++ to i--. Quite readable, and intuitive
*/