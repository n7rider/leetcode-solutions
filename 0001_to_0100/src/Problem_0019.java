/*
19. Remove Nth Node From End of List
Medium
Topics
premium lock iconCompanies
Hint

Given the head of a linked list, remove the nth node from the end of the list and return its head.

Example 1:

Input: head = [1,2,3,4,5], n = 2
Output: [1,2,3,5]

Example 2:

Input: head = [1], n = 1
Output: []

Example 3:

Input: head = [1,2], n = 1
Output: [1]



Constraints:

    The number of nodes in the list is sz.
    1 <= sz <= 30
    0 <= Node.val <= 100
    1 <= n <= sz



Follow up: Could you do this in one pass?

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }


AI suggests a solution based on 2 pointers. Glad that I get to work both approaches.
For recursion, I used a int[0] to pass a mutable object but AI thinks it's bad for interviews. I should stick to objects then. AI uses a
class-level field, but it warns that it doesn't like recursion for this anyway.
Also, since the output will be a null cascaded all the way through from the end if head is the one to be removed, I simply return
head.next if output is null. It recommends against this by returning node.next instead. So we'll return n+1 at n, and we can assign it
to n-1.next (In other places, we return n and so n-1.next = n keeps things intact).
Writing this also makes it clear why this appraoch is bad. We usually wind and unwind so we do O(2n), and also either reassign everything,
or have a complex approach
 */


public class Problem_0019 {
    public static void main(String[] args) {
        Problem_0019 obj = new Problem_0019();

        ListNode list1 = obj.new ListNode(1);
        list1.next(2).next(3).next(4).next(5);
        var out1 = obj.removeNthFromEnd(list1, 2);
        System.out.println(out1);
        System.out.println();

        ListNode list2 = obj.new ListNode(1);
        var out2 = obj.removeNthFromEnd(list2, 1);
        System.out.println(out2);
        System.out.println();

        ListNode list3 = obj.new ListNode(1);
        list3.next(2);
        var out3 = obj.removeNthFromEnd(list3, 1);
        System.out.println(out3);
        System.out.println();
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        ListNode next(int val) {
            this.next = new ListNode(val);
            return this.next;
        }

        @Override
        public String toString() {
            StringBuilder out = new StringBuilder();
            var currNode = this;
            while(currNode != null) {
                out.append(currNode.val).append(", ");
                currNode = currNode.next;
            }
            return out.toString();
        }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        // Skip null check
        // Skip code that needs to check if n <= list's size
        int[] idxFromLast = new int[] { -1 }; // A mutable way to pass value
        ListNode output = recHelper(head, n, idxFromLast);

        if(output != null) {
            return head;
        } else {
            return head.next;
        }
    }

    private ListNode recHelper(ListNode node, int n, int[] idxFromLast) {
        if(node == null) {
            idxFromLast[0] = 1;
            return null;
        }

        var out = recHelper(node.next, n, idxFromLast);
        if(idxFromLast[0] == n + 1) {
            node.next = node.next.next;
            idxFromLast[0]++;
            return node;
        } else {
            idxFromLast[0]++;
            return out;
        }

    }

}

/*
Simplest alg:
iterate twice. Find the length first time
Second time, stop at len - nth item.
Do curr.next = curr.next.next
Return head

Simpler alg:
Iterate only once, keep last 'n' items always
So when the list ends, we have the nth from the lat

Keeping the last 'n', and actively doing FIFO needs a queue

Algo is like this:


queue of size (n + 1)
curr = list.head
while curr != null
    if queue is full
        queue.popAndPush curr.val // FIFO
    else
        queue.push curr.val
    curr = curr.next

queue.peek.next = queue.peek.next.next
return head

Alg is simple, but needs additional data structure.
Also needs O(n) extra storage.

To prevent O(n), we can recursively reach the end and count from backwards

Algo snippet:

OutputType{count, Node} recursiveMethod(Node, n):
    // base
    if node == null // reached the end. Can return -1 too
        return null

    out = recursiveMethod(curr.next, n)
    if out == null // true for last element
        count = 1

    if(count == n + 1) // true for output
       curr.next = curr.next.next
       count++
       return count, Node
    if(count > n)
        return count, out.Node
    else
        return count+1, null

calling method:
    out = recursiveMethod(head, n)
    if output = null && n > count // wont happen. given constraint
        throw
    else if output == null // head is being removed
        return head.next
    else
        return out.node

Observation:
    Can use an atomic int, or use an Integer in the params to pass around the count rather than having an output object.
    However, having the count helps with the additional validation in the calling method (won't happen here due to constraint
        but this may not be guaranteed)
    With the extra param, we can just return count from prev node. Writing this way

recursiveMethod(Node, n, curr):
    if node.next == null
        curr.set 0   // n is 1 based, so null after the last can be considered 0
        return;

    outNode = recursiveMethod(node.next, n, curr)
    if curr == n + 1
        node.next = node.next.next
        curr+

 out = recursiveMethod(head, n, -1)
 if out = null // can happen only if head is remvoed. constraint
    return head.next
 else
    out


 */
