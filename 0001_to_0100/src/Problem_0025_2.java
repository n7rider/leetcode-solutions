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
public class Problem_0025_2 {
    public static void main() {
        Problem_0025_2 obj = new Problem_0025_2();

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
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode groupPrev = dummy;

        while (true) {
            // Find the kth node in the current group.
            ListNode kth = groupPrev;
            for (int i = 0; i < k && kth != null; i++) {
                kth = kth.next;
            }

            // Not enough nodes left: leave the remaining nodes unchanged.
            if (kth == null) {
                break;
            }

            ListNode groupNext = kth.next;

            // Reverse the current group.
            ListNode prev = groupNext;
            ListNode curr = groupPrev.next;

            while (curr != groupNext) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }

            // Connect the reversed group to the preceding part.
            ListNode oldGroupHead = groupPrev.next;
            groupPrev.next = kth;
            groupPrev = oldGroupHead;
        }

        return dummy.next;
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

This is just to test AI's solution

 */