/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Problem_0002 {
    ListNode outStart = null;

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode out = null;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int sum = (l1 == null ? 0 : l1.val) +
                    (l2 == null ? 0 : l2.val) +
                    carry;

            out = prepareOutputObj(out);

            out.val = sum % 10;
            carry = sum / 10;

            l1 = (l1 == null) ? null : l1.next;
            l2 = (l2 == null) ? null : l2.next;
        }
        return outStart;
    }

    private void setOutStart(ListNode listNode) {
        this.outStart = listNode;
    }

    private ListNode prepareOutputObj(ListNode listNode) {
        if (listNode == null) {
            listNode = new ListNode();
            setOutStart(listNode);
        } else {
            listNode.next = new ListNode();
            listNode = listNode.next;
        }
        return listNode;
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