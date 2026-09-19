/*
12. Integer to Roman
Medium
Topics
premium lock iconCompanies

Seven different symbols represent Roman numerals with the following values:
Symbol	Value
I	1
V	5
X	10
L	50
C	100
D	500
M	1000

Roman numerals are formed by appending the conversions of decimal place values from highest to lowest. Converting a decimal place value into a Roman numeral has the following rules:

    If the value does not start with 4 or 9, select the symbol of the maximal value that can be subtracted from the input, append that symbol to the result, subtract its value, and convert the remainder to a Roman numeral.
    If the value starts with 4 or 9 use the subtractive form representing one symbol subtracted from the following symbol, for example, 4 is 1 (I) less than 5 (V): IV and 9 is 1 (I) less than 10 (X): IX. Only the following subtractive forms are used: 4 (IV), 9 (IX), 40 (XL), 90 (XC), 400 (CD) and 900 (CM).
    Only powers of 10 (I, X, C, M) can be appended consecutively at most 3 times to represent multiples of 10. You cannot append 5 (V), 50 (L), or 500 (D) multiple times. If you need to append a symbol 4 times use the subtractive form.

Given an integer, convert it to a Roman numeral.



Example 1:

Input: num = 3749

Output: "MMMDCCXLIX"

Explanation:

3000 = MMM as 1000 (M) + 1000 (M) + 1000 (M)
 700 = DCC as 500 (D) + 100 (C) + 100 (C)
  40 = XL as 10 (X) less of 50 (L)
   9 = IX as 1 (I) less of 10 (X)
Note: 49 is not 1 (I) less of 50 (L) because the conversion is based on decimal places

Example 2:

Input: num = 58

Output: "LVIII"

Explanation:

50 = L
 8 = VIII

Example 3:

Input: num = 1994

Output: "MCMXCIV"

Explanation:

1000 = M
 900 = CM
  90 = XC
   4 = IV



Constraints:

    1 <= num <= 3999


 */
public class Problem_0012 {
    public static void main(String[] args) {
        Problem_0012 obj = new Problem_0012();

        System.out.println(obj.intToRoman(3749));
        System.out.println(obj.intToRoman(58));
        System.out.println(obj.intToRoman(1994));
        System.out.println(obj.intToRoman(3999));
    }

    public String intToRoman(int num) {
        // Skip validations since constraints have been given
        int pos = 0;
        int curr;
        StringBuilder currRom;
        StringBuilder out = new StringBuilder();

        while(num > 0) {
            curr = num % 10;
            pos = pos == 0 ? 1 : pos * 10;
            currRom = lookup(curr, pos);
            out = currRom.append(out);

            num = num / 10;
        }

        return out.toString();
    }

    private StringBuilder lookup(int curr, int pos) {
        if(curr == 5) {
            return getRoman5(pos); // needs to resolve at the top. Otherwise it can be infinitely recursive
        }
        if(curr == 4) {
            return lookup(1, pos) .append( lookup(5, pos) );
        }
        if(curr == 9) {
            return lookup(1, pos) .append( lookup(1, pos * 10) );
        }
        if(curr > 5) {
            return lookup(5, pos) .append( lookup(curr - 5, pos) );
        }
        else { // curr = 1 to 3
            return getRoman1(curr, pos);
        }
    }

    private StringBuilder getRoman5(int pos) {
        return switch (pos) {
            case 1 -> new StringBuilder("V");
            case 10 -> new StringBuilder("L");
            case 100 -> new StringBuilder("D");
            default -> throw new IllegalArgumentException("Invalid value: " + pos);
        };
        }

    private StringBuilder getRoman1(int curr, int pos) {
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < curr; i++) {
            StringBuilder currR = switch (pos) {
                case 1: yield new StringBuilder("I");
                case 10: yield new StringBuilder("X");
                case 100: yield new StringBuilder("C");
                case 1000: yield new StringBuilder("M");
                default: throw new IllegalArgumentException("Invalid value: " + pos);
            };
            out.append(currR);
        }
        return out;
    }
}


/*
The text suggests going from highest to lowest, but I think going from lowest to highest is easier. Let's see:

Input: num = 3749
Output: "MMMDCCXLIX"

lowest to highest:

// do a loop to remvoe a digit once every turn
while x > 0
    curr = x % 10
    pos = pos == 0 ? 1: pos * 10
    currRom = lookup(curr, pos)
    out = currRom + out
return out

lookup(curr, pos)

    if curr == 5
        return getRoman(5, pos) // needs to resolve at the top. Otherwise it can be infinitely recursive

    if curr == 4
        return lookup(1, pos) + lookup (5, pos)
    if curr == 9
        return lookup(1, pos) + lookup (1, pos + 1)

    if curr > 5
        return lookup(5, pos) + lookup(curr - 5, pos)
    else // curr = 1 to 3
        return lookup(curr, pos)



getRoman(curr, pos)

    if curr == 5
        if pos == 1 return "V";
        if pos == 10 return "L";
        if pos == 100 return "D";
        throw

    for(int i = 0; i < curr; i++) { // This should run only for 1, 2, 3
        out = out + getRoman(1, pos)
    }
    if curr == 1
        if pos == 1 return "I";
        if pos == 10 return "X";
        if pos == 100 return "C";
        if pos == 1000 return "M";
        throw



 */