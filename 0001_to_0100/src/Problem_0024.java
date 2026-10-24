import java.util.List;

/*
24. Swap Nodes in Pairs
Medium
Topics
premium lock iconCompanies

Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without modifying the values in the list's nodes (i.e., only nodes themselves may be changed.)



Example 1:

Input: head = [1,2,3,4]

Output: [2,1,4,3]

Explanation:

Example 2:

Input: head = []

Output: []

Example 3:

Input: head = [1]

Output: [1]

Example 4:

Input: head = [1,2,3]

Output: [2,1,3]



Constraints:

    The number of nodes in the list is in the range [0, 100].
    0 <= Node.val <= 100


 */
public class Problem_0024 {
    public static void main() {
        Problem_0024 obj = new Problem_0024();

        ListNode list1 = new ListNode(1);
        list1.next(2).next(3).next(4);
        var out1 = obj.swapPairs(list1);
        System.out.println(out1);

        ListNode list2 = null;
        var out2 = obj.swapPairs(list2);
        System.out.println(out2);

        ListNode list3 = new ListNode(1);
        var out3 = obj.swapPairs(list3);
        System.out.println(out3);
    }

    public ListNode swapPairs(ListNode head) {
        var dummy = new ListNode();
        dummy.next = head;

        var a = head;
        var b = head == null ? null : head.next;
        var prev = dummy;

        while(a != null && b != null) {
            prev.next = b; // prev -> a -> b ===> prev -> b -> c
            a.next = b.next; // prev -> b -> c   || a -> c
            b.next = a;  // prev -> b -> a -> c

            prev = a;
            a = a.next;
            b = a == null ? null : a.next;
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

On a high level, we just rotate 1, 2 | 3, 4 , and so on.
LL usually uses a dummy as a virtual head

A simple solution: Just bring up every two, swap until one of them becomes null
Will we lose pointers? Let's see

Create dummy = new Node()
dummy.next = head

prev = dummy
a = head
b = a == null ? null : a.next

while (a != null && b != null)
    prev.next = b // prev -> a -> b ===> prev -> b -> c
    a.next = b.next // prev -> b -> c   || a -> c
    b.next = a  // prev -> b -> a -> c

    prev = a
    a = c
    b = a == null ? null : a.next

return dummy.next




 */