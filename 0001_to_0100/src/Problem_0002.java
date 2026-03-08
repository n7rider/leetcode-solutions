/*
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val;   this.next = next; }
 * }
 *
 * Notes:
 * - Don't create output object at the class-level. This might work for the tests, but it is a bad pattern since
 * different invocations can have odd results. Fit in the output object inside the method.
 * - If code needs an extra init logic (like my v1), see if you can create a dummy object at the beginning to avoid
 * this. This results in cleaner code.
 */
public class Problem_0002 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode outHead = new ListNode(0);
        ListNode out = outHead;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int sum = (l1 == null ? 0 : l1.val) +
                    (l2 == null ? 0 : l2.val) +
                    carry;

            out.next = new ListNode(sum % 10);
            out = out.next;

            carry = sum / 10;

            l1 = (l1 == null) ? null : l1.next;
            l2 = (l2 == null) ? null : l2.next;
        }
        return outHead.next;
    }

    public ListNode addTwoNumbers_v1(ListNode l1, ListNode l2) {
        ListNode outHead = new ListNode();
        ListNode out = null;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int sum = (l1 == null ? 0 : l1.val) +
                    (l2 == null ? 0 : l2.val) +
                    carry;

            out = prepareOutputObj(out, outHead);

            out.val = sum % 10;
            carry = sum / 10;

            l1 = (l1 == null) ? null : l1.next;
            l2 = (l2 == null) ? null : l2.next;
        }
        return outHead;
    }

    private ListNode prepareOutputObj(ListNode out, ListNode outHead) {
        if (out == null) {
            out = outHead;
        } else {
            out.next = new ListNode();
            out = out.next;
        }
        return out;
    }

    public static void main() {
        ListNode l1a = new ListNode(2);
        ListNode l1b = new ListNode(4);
        ListNode l1c = new ListNode(3);
        l1a.next = l1b;
        l1b.next = l1c;

        ListNode l2a = new ListNode(5);
        ListNode l2b = new ListNode(6);
        ListNode l2c = new ListNode(4);
        l2a.next = l2b;
        l2b.next = l2c;
        Problem_0002 obj = new Problem_0002();
        System.out.println(obj.addTwoNumbers(l1a, l2a));

    }
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

    @Override
    public String toString() {
        return val + " -> " + (next == null ? null : next.toString());
    }
}