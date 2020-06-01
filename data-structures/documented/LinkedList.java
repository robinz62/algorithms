/**
 * A custom linked list class that provides access to the underlying nodes.
 * This class offers O(1) deletion of a node given the node and O(1)
 * insertion given the insertion position as relative to a node in the list.
 * 
 * Given the underlying nodes, this class can be easily extended for other
 * behavior, for example O(1) contains using hashsets.
 */
class LinkedList {
    ListNode head;
    ListNode tail;
    int size;

    /**
     * Returns a reference to the head node, or null if the list is empty.
     */
    ListNode getFirst() {
        return head;
    }

    /**
     * Returns a reference to the tail node, or null if the list is empty.
     */
    ListNode getLast() {
        return tail;
    }

    /**
     * Returns the size of the list.
     */
    int size() {
        return size;
    }

    /**
     * Adds a node with the given value to the head of the list and returns the
     * newly created node.
     */
    ListNode addFirst(int val) {
        size++;
        ListNode node = new ListNode(val);
        if (head == null) return head = tail = node;
        head.prev = node;
        node.next = head;
        head = node;
        return node;
    }

    /**
     * Adds a node with the given value to the tail of the list and returns the
     * newly created node.
     */
    ListNode addLast(int val) {
        size++;
        ListNode node = new ListNode(val);
        if (head == null) return head = tail = node;
        tail.next = node;
        node.prev = tail;
        tail = node;
        return node;
    }

    /**
     * Inserts a new node with the given value to the left/in front of the
     * given node. The given node MUST be a node in this list. Returns the
     * newly created node.
     */
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

    /**
     * Inserts a new node with the given value to the right/behind the given
     * node. The given node MUST be a node in this list. Returns the newly
     * created node.
     */
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

    /**
     * Removes the head node and returns its value, or -1 if the list is empty.
     */
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

    /**
     * Removes the tail node and returns its value, or -1 if the list is empty.
     */
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

    /**
     * Deletes the specified node. The node MUST be in this list.
     */
    int delete(ListNode node) {
        if (node == head) return removeFirst();
        if (node == tail) return removeLast();
        size--;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        return node.val;
    }

    /**
     * Swaps the given node with the node on its left. Does nothing if the
     * given node is the head.
     */
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
    public ListNode(int v) { 
        val = v;
    }
}