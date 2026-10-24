/*
25. Reverse Nodes in k-Group
Hard
Topics
premium lock iconCompanies

Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified list.

k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.

You may not alter the values in the list's nodes, only nodes themselves may be changed.



Example 1:

Input: head = [1,2,3,4,5], k = 2
Output: [2,1,4,3,5]

Example 2:

Input: head = [1,2,3,4,5], k = 3
Output: [3,2,1,4,5]



Constraints:

    The number of nodes in the list is n.
    1 <= k <= n <= 5000
    0 <= Node.val <= 1000



Follow-up: Can you solve the problem in O(1) extra memory space?

 */
public class Problem_0025 {
    public static void main() {
        Problem_0025 obj = new Problem_0025();

        ListNode list1 = new ListNode(1);
        list1.next(2).next(3).next(4).next(5);
        var out1 = obj.reverseKGroup(list1, 2);
        System.out.println(out1);

        ListNode list2 = new ListNode(1);
        list2.next(2).next(3).next(4).next(5);
        var out2 = obj.reverseKGroup(list2, 3);
        System.out.println(out2);

        ListNode list3 = new ListNode(1);
        var out3 = obj.reverseKGroup(list3, 7);
        System.out.println(out3);
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode();
        dummy.next = head;

        reverseHelper(dummy, k);

        return dummy.next;
    }

    private void reverseHelper(ListNode dummy, int k) {
        ListNode prev = dummy;
        while(true) {
            boolean canRotate = isRotatePossible(prev, k);
            if (canRotate) {
                prev = rotate(prev, k);
            } else {
                break;
            }
        }
    }

    private boolean isRotatePossible(ListNode prev, int k) {
        ListNode curr = prev; // Skipping null check for prev
        boolean nullFlag = false;
        for(int i = 0; i < k; i++) {
            if(curr.next == null) {
                nullFlag = true;
                break;
            }
            curr = curr.next;
        }

        return !nullFlag;
    }

    private ListNode rotate(ListNode prev, int k) {
        ListNode headForBatch = prev;
        ListNode firstEle = prev.next;
        ListNode curr = firstEle; // init set to firstEle, eleToMove thus becomes next
        for(int i = 1; i < k; i++) {
            ListNode eleToMove = curr.next;

            firstEle.next = eleToMove.next; // 1 -> 3. do this before 2->3 is changed to 2->1. Otherwise we'll lose 3 forever
            eleToMove.next = headForBatch.next; // 0 -> 2 -> 1 -> 3
            headForBatch.next = eleToMove; // 0 -> 2
        }
        return firstEle;
    }

    static class ListNode {
        Integer val;
        ListNode next;

        ListNode() {
        }

        ListNode(Integer val) {
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
}
/*
if k = 2, we do this:

dummy = new LN()
dummy.next = head
a = head
prev = dummy

while (a != null & a.next != null)
    var b = a.next

    prev.next = b // prev -> a -> b -> c ==> prev -> b -> c  || a -> b
    a.next = b.next // prev -> b -> c || a -> c
    b.next = a      // prev -> b -> a -> c

    prev = a
    a = a.next

How we do this for n > 2? Is there a common order to this?
A stack makes it very easy

for (int i = 0; i < k; i++)
    stack.pop

while stack.peek
    temp = stack.pop
    prev.next = temp
    prev = temp

How do we do it in O(1) space?

Wouldn't a stack still do it in O(1) memory space if we use a specialized one that wraps the node and adds a pointer?

We can't use arrays, so we can use a rec_loop to go to the end and then reverse associate?

e.g.,
rotate(0, n, prev, prev, null) // next = unknown

rotate(currCount, n, nullFlag, currNode, prev, next)
    if nullFlag
        return

    if currNode.next == null
        nullFlag = true
        return

    if(currCount == n-1)

Wait, this can get weird when backpropagating the next and so on. Why not try a simple forward pass?

e.g., 0 1 2 3 4 becomes 0 3 2 1 4 when k=3

prev = 0
curr = 0

First step:
curr = 1 // passes null check
Get it to 0 2 1 3 4 . // Just change 0's next, 2's next, 1's next
Get it to 0 3 2 1 4 . // Change 0's next, 3's next, 1's next

So we change the prev's next, the moved node's next, and the final one's next

Alg:
Node curr = prev
nullFlag = false
for int i = 0; i <k; i++
    curr = curr.next
    if curr.next == null
        nullFlag = true

if(nullFlag)
   stop

headForBatch = prev
firstEle = curr.next
curr = curr.next // init increment to move from prev to 1, eleToMove thus becomes next
for(int i = 0; i < k; i++)
    eleToMove = curr.next
    headForBatch.next = eleToMove // 0 -> 2
    firstEle.next = eleToMove.next . // 0 -> 2 | 1 -> 3. do this before 2->3 is changed to 2->1. Otherwise we'll lose 3 forever
    eleToMove.next = firstEle // 0 -> 2 -> 1 -> 3

    curr = curr.next


while(true)
    ifRotatePossible()
        rotate()
    else
        break()

After checking solution:
I had an incorrect assignment because it worked for swapping 1 and 2 in k=3, but not for 1 & 2 with 3. Always test with two or
more cases. With one case, you might have created a highly specialized way.

The AI solution also shows that doing 3 assignments every time is not necessary. You can do the first and last assignments
only once. Other than that, you just keep taking ele from the beginning, and do ele.next = end

THis shows you don't always need to think in terms of taking the first element and putting it behind another element. You just take an item and prepend to
the list at the end, and continue until we have the final solution.

Therefore, the key takeaway is not just think in terms of taking a new node and adding other ends to its end, but also to
think in terms of prepending the current node to a list. Looks weird, but this makes approach reversing a list very simple.
You don't need to stack up and unwind them. You can just take each ele and prepend it to the previous ele.
e.g., reversing a_b_c_d, backup b.next, and then do b.next=a and so on.







 */