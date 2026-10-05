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
 */


public class Problem_0019_2 {
    public static void main(String[] args) {
        Problem_0019_2 obj = new Problem_0019_2();

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
        var out3 = obj.removeNthFromEnd(list3, 2);
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
        ListNode ptr1 = head;
        for(int i = 0; i < n; i++) {
            // Skip validation to immediately exit if null is encountered
            ptr1 = ptr1.next;
        }


        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode ptr2 = dummy;
        while(ptr1 != null) {
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }

        // removed -1 is dummy. So we need to remove head in this case
        if(ptr2 == dummy) {
            return head.next;
        } else {
            ptr2.next = ptr2.next.next;
            return head;
        }
    }

}

/*
alg with two pointers:
find nth from end:
go n stops forward with ptr1

go forward with ptr1 and ptr2 from this point
when ptr1 reaches end, ptr2 is at n.


remove nth from list:
conn n-1.next to n.next
so if we stop at n-1, it's easier

assume l = 11, n = 3, say we need to remove 8, and make it 7 -> 9

              v       V
0 1 2 3 4 5 6 7 8 9 10
              0 1 2 3
Okay, so going n nodes with ptr1 automatically gets ptr2 to stop at n-1 when ptr1 ends


Consider
0 1 2
If we want to remove 1, ptr2 remains at 0
But if we want to remove 0? Adding a dummy node before head, and making ptr2 start there would make it easier
We never return it anyway. If ptr2 == dummy, then we'll do ptr.next = head.next, and return head.next

 */
