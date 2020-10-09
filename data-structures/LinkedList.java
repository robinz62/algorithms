/**
 * A linked list implementation that provides access to the underlying nodes.
 * Features: O(1) deletion of specific node, O(1) insertion given relative
 * position to an existing node, O(1) swapping with node on left.
 */
class LinkedList {
    ListNode head;
    ListNode tail;
    int size;

    ListNode getFirst() { return head; }
    ListNode getLast() { return tail; }
    int size() { return size; }

    ListNode addFirst(ListNode node) {
        size++;
        if (head == null) return head = tail = node;
        head.prev = node;
        node.next = head;
        head = node;
        return node;
    }

    ListNode addLast(ListNode node) {
        size++;
        if (head == null) return head = tail = node;
        tail.next = node;
        node.prev = tail;
        tail = node;
        return node;
    }

    ListNode insertBefore(ListNode node, int val) {
        size++;
        ListNode toAdd = new ListNode(val);
        if (node == head) {
            node.prev = toAdd;
            toAdd = node;
            head = toAdd;
        } else {
            ListNode left = node.prev;
            left.next = toAdd;
            toAdd.prev = left;
            toAdd.next = node;
            node.prev = toAdd;
        }
        return toAdd;
    }

    ListNode insertAfter(ListNode node, int val) {
        size++;
        ListNode toAdd = new ListNode(val);
        if (node == tail) {
            node.next = toAdd;
            toAdd.prev = node;
            tail = toAdd;
        } else {
            ListNode right = node.next;
            right.prev = toAdd;
            toAdd.next = right;
            toAdd.prev = node;
            node.next = toAdd;
        }
        return toAdd;
    }

    int removeFirst() {
        if (head == null) return -1;
        size--;
        int val = head.val;
        if (head == tail) {
            head = tail = null;
            return val;
        }
        head = head.next;
        head.prev = null;
        return val;
    }

    int removeLast() {
        if (tail == null) return -1;
        size--;
        int val = tail.val;
        if (head == tail) {
            head = tail = null;
            return val;
        }
        tail = tail.prev;
        tail.next = null;
        return val;
    }

    void delete(ListNode node) {
        if (node == head) return removeFirst();
        if (node == tail) return removeLast();
        size--;
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    void swapLeft(ListNode node) {
        if (node == head) return;
        ListNode ll = node.prev.prev;
        ListNode l = node.prev;
        ListNode r = node.next;
        if (l == head) head = node;
        if (node == tail) tail = l;
        if (ll != null) ll.next = node;
        node.prev = ll;
        node.next = l;
        l.prev = node;
        l.next = r;
        if (r != null) r.prev = l;
    }
}

class ListNode {
    int val;
    ListNode prev;
    ListNode next;
    ListNode(int v) { 
        val = v;
    }
}