/*
21. Merge Two Sorted Lists
Easy
Topics

You are given the heads of two sorted linked lists list1 and list2.

Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.

Return the head of the merged linked list.



Example 1:

Input: list1 = [1,2,4], list2 = [1,3,4]
Output: [1,1,2,3,4,4]

Example 2:

Input: list1 = [], list2 = []
Output: []

Example 3:

Input: list1 = [], list2 = [0]
Output: [0]



Constraints:

    The number of nodes in both lists is in the range [0, 50].
    -100 <= Node.val <= 100
    Both list1 and list2 are sorted in non-decreasing order.

 */
public class Problem_0021 {
    public static void main(String[] args) {
        Problem_0021 obj = new Problem_0021();

//        Problem_0021.ListNode list1 = new ListNode(1);
//        list1.next(2).next(4);
//        Problem_0021.ListNode list2 = new ListNode(1);
//        list1.next(3).next(4);
//        var out1 = obj.mergeTwoLists(list1, list2);
//        System.out.println(out1);
//        System.out.println();
//
//        Problem_0021.ListNode list3 = new ListNode();
//        Problem_0021.ListNode list4 = new ListNode();
//        var out2 = obj.mergeTwoLists(list3, list4);
//        System.out.println(out2);
//        System.out.println();
//
//        Problem_0021.ListNode list5 = new ListNode();
//        Problem_0021.ListNode list6 = new ListNode(1);
//        var out3 = obj.mergeTwoLists(list5, list6);
//        System.out.println(out3);
//        System.out.println();

        Problem_0021.ListNode list7 = new ListNode(2);
        Problem_0021.ListNode list8 = new ListNode(1);
        var out4 = obj.mergeTwoLists(list7, list8);
        System.out.println(out4);
        System.out.println();
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

    /*

if(curr1 == null)
    return curr2

var head1Bkp = head1
dummy.next = head1
curr1 = dummy

// curr1.next is not null initially
while(curr1.next != null || curr2 != null)
    if(curr1.next == null) // so curr2 is not null
        curr1.next = curr2 // l1's tail has rest of l2, we can quit
        return head1Bkp

    if(curr2 == null) // so curr1 is not null
        return head1Bkp // everything is added already, quit

    // both curr1.next and curr2 are not null
    if(curr2.val < curr1.next.val)
        var curr1NextTemp = curr1.next
        var curr2NextTemp = curr2.next
        curr1.next = curr2
        curr2.next = curr1NextTemp
        curr2 = curr2NextTemp
    else
        curr1 = curr1.next
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(isNull(list1)) {
            return list2;
        }

        var head1Backup = list1;
        var dummy = new ListNode(-1);
        dummy.next = list1;
        list1 = dummy;

        // We know list1.next != null initially
        while(!isNull(list1.next) || !isNull(list2)) {
            if(isNull(list1.next)) { // so list2 is not null
                list1.next = list2; // l1's tail has rest of l2, we can quit
                return dummy.next;
            }

            if(isNull(list2)) { // so list1 is not null
                return dummy.next; // everything is added already, quit
            }

            // both are not null
            if(list2.val < list1.next.val) {
                var list1NextTemp = list1.next;
                list1.next = list2;
                var list2NextTemp = list2.next;
                list2.next = list1NextTemp;

                // The seq of list1 is: list1 -> list 2 -> list1.next
                list1 = list2; // we always compare list1.next to list2, so list1 is the prev i.e., list2
                list2 = list2NextTemp;
            } else {
                list1 = list1.next; // l1's is small, so this is in the right place.
            }

        }
        return dummy.next;
    }

    private boolean isNull(ListNode node) {
        return node == null || node.val == null;
    }
}

/*
We can figure out the next item by comparing current node in both lists.
However, can we splice the lists together? Wouldn't we lose the list if we assign list1's curr.next = list2 curr

Let's write it and see where the problem exactly lies:

curr1 = head1
curr2 = head2

// Assume we're going to splice list 2 on to list 1
while (curr1 != null || curr2 != null)
  currVal1 = curr1 != null ? curr1.val : null
  currVal2 = curr2 != null ? curr2.val : null

  if(curr1 != null && curr2 != null)
    if(curr2.val < curr1.val)
        currTemp = curr1.next // backup
        curr1.next = curr2    // curr1 is entirely lost now, so always compare by currTemp ? Figuring out rest of the steps anyway
        // Take bkp of curr2's next because we're going to add back curr1's original back to the mix
        currTemp2 = curr2.next
        curr2.next = currTemp // This contains curr1 -> curr2 -> currTemp, so currTemp vs currTemp is a curr1 vs curr2 situation

        curr1 = currTemp // Replace with repl(2)
        curr2 = currTemp2

    if(curr1 == null)
        // There's no way to add curr2's ele now, so we should always look up prev curr
        // Continuing anyway
        curr1.next = curr2

    if(curr2 != null)
        curr1 = curr1.next

    repl(2):
    // To prevent nulls for curr1, how about we proactively check and add a dummy?
    if(curr1.next == null)
        curr1.next = dummy // But now we have to always check dummy during sort


Okay, doing it again:

curr1 = head1
curr2 = head2
var prevCurr1 = curr1 // How about we do this rather than always doing a .next check? Check curr = null && curr.next == null for two lists is cumbersome
// Wait we can just remove the case for null list1 with an initial check. We still need the var if it turns to null halfway through

if(curr1 == null)
    return curr2

while(curr1 != null || curr2 != null)
    if(curr1 == null) // so curr2 is not null
        prevCurr1.next = curr2 // l1's tail has rest of l2, we can quit
        return head1

    if(curr2 == null) // so curr1 is not null
        return head1 // everything is added already, quit

    // neither is null
    if curr2.val < curr1.val
        var curr1Temp = curr1
        prevCurr1.next = curr2
        var curr2Temp = curr2.next
        curr2.next = curr1
    else
        prevCurr1 = curr1
        curr1 = curr1.next

return head1

How does it look when comparing curr1.next vs curr2

if(curr1 == null)
    return curr2

var head1Bkp = head1
dummy.next = head1
curr1 = dummy

// curr1.next is not null initially
while(curr1.next != null || curr2 != null)
    if(curr1.next == null) // so curr2 is not null
        curr1.next = curr2 // l1's tail has rest of l2, we can quit
        return head1Bkp

    if(curr2 == null) // so curr1 is not null
        return head1Bkp // everything is added already, quit

    // both curr1.next and curr2 are not null
    if(curr2.val < curr1.next.val)
        var curr1NextTemp = curr1.next
        var curr2NextTemp = curr2.next
        curr1.next = curr2
        curr2.next = curr1NextTemp
        curr2 = curr2NextTemp
    else
        curr1 = curr1.next

// Both are highly similar. The second approach loses an extra class-level variable though.



Lessons:
Often, it's easier and less overhead to go with a dummy at the start


 */