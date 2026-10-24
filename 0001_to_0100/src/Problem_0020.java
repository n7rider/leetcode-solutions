import java.util.Stack;

/*
20. Valid Parentheses
Easy
Topics
Hint

Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

An input string is valid if:

    Open brackets must be closed by the same type of brackets.
    Open brackets must be closed in the correct order.
    Every close bracket has a corresponding open bracket of the same type.



Example 1:

Input: s = "()"

Output: true

Example 2:

Input: s = "()[]{}"

Output: true

Example 3:

Input: s = "(]"

Output: false

Example 4:

Input: s = "([])"

Output: true

Example 5:

Input: s = "([)]"

Output: false



Constraints:

    1 <= s.length <= 104
    s consists of parentheses only '()[]{}'.


 */
public class Problem_0020 {
    static void main() {
        Problem_0020 obj = new Problem_0020();
        System.out.println(obj.isValid("()"));
        System.out.println(obj.isValid("()[]{}"));
        System.out.println(obj.isValid("(]"));
        System.out.println(obj.isValid("([])"));
        System.out.println(obj.isValid("([)]"));
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '{' || c == '[' || c == '(') {
                stack.push(c);
            } else { // Skip check if chars are in }])
                if(stack.isEmpty()) {
                    return false;
                }
                if(areOfSameType(stack.peek(), c)) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }

        return stack.empty();
    }

    private boolean areOfSameType(char opening, char closing) {
        if(opening == '{') {
            return closing == '}';
        }
        if(opening == '[') {
            return closing == ']';
        } else {
            return closing == ')';
        }

    }

}

/*
Go for the simplest solution:
Go through the string: count number of ()[]{}
Check if count matches for ()[] etc. But that's not enough for ([)]
    The idea is, you can't close inner ones without closing outer ones. So a textbook stack problem.

([{ makes you push to stack. Rest makes you pop

If the topmost doesn't match what you're trying to pop, throw error

Alg:
Go through char
If char = ([{ push to stack
If char = ]})
    check if stack.peek matches expected // return false otherwise
    pop from stack

Stack DS:
    List<> data
    int currIdx

Alternatives for creating a stack DS:
    We need both count and position, so it's the same


 */