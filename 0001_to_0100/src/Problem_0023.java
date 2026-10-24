import java.util.List;

/*
23. Merge k Sorted Lists
Hard
Topics
premium lock iconCompanies

You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.

Merge all the linked-lists into one sorted linked-list and return it.



Example 1:

Input: lists = [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]
Explanation: The linked-lists are:
[
  1->4->5,
  1->3->4,
  2->6
]
merging them into one sorted linked list:
1->1->2->3->4->4->5->6

Example 2:

Input: lists = []
Output: []

Example 3:

Input: lists = [[]]
Output: []



Constraints:

    k == lists.length
    0 <= k <= 104
    0 <= lists[i].length <= 500
    -104 <= lists[i][j] <= 104
    lists[i] is sorted in ascending order.
    The sum of lists[i].length will not exceed 104.


 */
public class Problem_0023 {
    public static void main() {
        Problem_0023 obj = new Problem_0023();

        ListNode list1 = new ListNode(1);
        list1.next(4).next(5);
        ListNode list2 = new ListNode(1);
        list2.next(3).next(4);
        ListNode list3 = new ListNode(2);
        list3.next(6);
        var out1 = obj.mergeKLists(new ListNode[] {list1, list2, list3});
        System.out.println(out1);

        var out2 = obj.mergeKLists(new ListNode[] {});
        System.out.println(out2);

        var out3 = obj.mergeKLists(new ListNode[] { new ListNode(0)});
        System.out.println(out3);
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode dummy = new ListNode();
        ListNode curr = dummy;

        while(true) {
            int smallest = Integer.MAX_VALUE;
            int nodeWithSmallestIdx = -1;
            boolean allNullFlag = true;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null && lists[i].val < smallest) {
                    smallest = lists[i].val;
                    nodeWithSmallestIdx = i;
                }
                if (lists[i] != null) {
                    allNullFlag = false;
                }
            }

            // After each loop
            if(allNullFlag) {
                break;
            }
            curr.next = lists[nodeWithSmallestIdx];
            curr = curr.next;
            lists[nodeWithSmallestIdx] = lists[nodeWithSmallestIdx].next;
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
Can we extend this as a case of merging 2 LL

start with a dummy

compare each's head
find smallest
dummy = curr.next = smallest
iterate smallest's LL

Lessons learnt:
Always think about overflow and choose datatype. These weird checks are for that - The sum of lists[i].length will not exceed 104.
The solutions just adds everything to a priority queue, and prints it out as a LL. It is simple but I was going for a solution with
no additional storage like the other one. Remember to question how to optimize before going for a solution. Think about multiple
possibilities too.
Or can create a PQ of length lists.length and keep adding entries as they added to the final queue.

 */