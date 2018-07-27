import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A binary heap that supports generic values and priorities. As this heap
 * supports decrease-key, the values must be unique.
 * <p>
 * If a comparator is not provided, {@code Key} must implement
 * {@code Comparable<Key>}.
 */
public class Heap<V, Key> {
    private List<Entry<V, Key>> heap;
    private Map<V, Integer> indexOfValue;
    private Comparator<Key> comparator;

    /**
     * Initialize a heap using the key's default comparator.
     */
    public Heap() {
        heap = new ArrayList<>();
        indexOfValue = new HashMap<>();
    }

    /**
     * Initialize a heap using the specified comparator.
     * 
     * @param comparator the comparator function.
     */
    public Heap(Comparator<Key> comparator) {
        this();
        this.comparator = comparator;
    }

    /**
     * Returns the number of elements in the heap.
     * 
     * @return the number of elements in the heap.
     */
    public int size() {
        return heap.size();
    }

    /**
     * Returns whether or not the heap is empty.
     * 
     * @return {@code true} if the heap is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Returns whether or not the heap contains the specified value.
     * 
     * @return {@code true} if the heap contains {@code value}, {@code false}
     *         otherwise.
     */
    public boolean containsValue(V value) {
        return indexOfValue.containsKey(value);
    }

    /**
     * Adds the specified element to the heap with the specified priority.
     * 
     * @param value the element to add to the heap.
     * @param key   the priority of the element.
     * @throws IllegalArgumentException if the provided key is {@code null}
     */
    public void add(V value, Key key) {
        if (containsValue(value) || key == null) {
            throw new IllegalArgumentException();
        }
        heap.add(new Entry<>(value, key));
        indexOfValue.put(value, heap.size() - 1);
        decreaseKey(value, key);
    }

    /**
     * Decreases the priority value of the specified element.
     * 
     * @param value  the element whose key should be decreased.
     * @param newKey the new priority value.
     * @throws IllegalArgumentException if the provided key is {@code null} or is
     *                                  not smaller than the previous key.
     * @throws NoSuchElementException   if the heap does not contain the provided
     *                                  value.
     */
    public void decreaseKey(V value, Key newKey) {
        if (!indexOfValue.containsKey(value)) {
            throw new NoSuchElementException();
        }
        if (newKey == null) {
            throw new IllegalArgumentException();
        }
        int i = indexOfValue.get(value);
        if (compare(newKey, heap.get(i).getKey()) > 0) {
            throw new IllegalArgumentException();
        }
        heap.get(i).setKey(newKey);
        while (i > 0 && compare(heap.get(parent(i)).getKey(), heap.get(i).getKey()) > 0) {
            Entry<V, Key> temp = heap.get(i);
            heap.set(i, heap.get(parent(i)));
            indexOfValue.put(heap.get(parent(i)).getValue(), i);
            heap.set(parent(i), temp);
            indexOfValue.put(temp.getValue(), parent(i));
            i = parent(i);
        }
    }

    /**
     * Returns the smallest element of the heap.
     * 
     * @return the smallest element of the heap.
     * @throws NoSuchElementException if the heap is empty.
     */
    public V peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return heap.get(0).getValue();
    }

    /**
     * Removes and returns the smallest element of the heap.
     * 
     * @return the smallest element of the heap.
     * @throws NoSuchElementException if the heap is empty.
     */
    public V extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        V min = heap.get(0).getValue();
        heap.set(0, heap.get(heap.size() - 1));
        indexOfValue.put(heap.get(0).getValue(), 0);
        heap.remove(heap.size() - 1);
        indexOfValue.remove(min);
        heapify(0);
        return min;
    }

    /**
     * Returns the set of values contained in the heap.
     * 
     * @return a set containing the values in the heap.
     */
    public Set<V> values() {
        Set<V> values = new HashSet<V>();
        for (Entry<V, Key> e : heap) {
            values.add(e.getValue());
        }
        return values;
    }

    /**
     * Returns the index of the parent of specified index.
     */
    private int parent(int index) {
        return index == 0 ? 0 : (index - 1) >> 1;
    }

    /**
     * Returns the left child index of the specified index.
     */
    private int left(int index) {
        return (index << 1) + 1;
    }

    /**
     * Returns the right child index of the specified index.
     */
    private int right(int index) {
        return (index << 1) + 2;
    }

    /**
     * The canonical heapify algorithm.
     */
    private void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;
        if (l < size() && compare(heap.get(l).getKey(), heap.get(i).getKey()) < 0) {
            smallest = l;
        }
        if (r < size() && compare(heap.get(r).getKey(), heap.get(smallest).getKey()) < 0) {
            smallest = r;
        }
        if (smallest == i) {
            return;
        }
        Entry<V, Key> temp = heap.get(i);
        heap.set(i, heap.get(smallest));
        indexOfValue.put(heap.get(smallest).getValue(), i);
        heap.set(smallest, temp);
        indexOfValue.put(temp.getValue(), smallest);
        heapify(smallest);
    }

    /**
     * Compares the input keys using the specified comparator, if specified.
     */
    private int compare(Key a, Key b) {
        if (comparator == null) {
            return ((Comparable<Key>) a).compareTo(b);
        }
        return comparator.compare(a, b);
    }

    /**
     * A container that stores an element and its associated priority key.
     */
    class Entry<V, K> {
        private V value;
        private K key;

        /**
         * Creates an entry with the specified value and key.
         */
        public Entry(V v, K k) {
            value = v;
            key = k;
        }

        /**
         * Returns the entry's value.
         * 
         * @return the entry's value.
         */
        public V getValue() {
            return value;
        }

        /**
         * Returns the entry's priority key.
         * 
         * @return the entry's priority key.
         */
        public K getKey() {
            return key;
        }

        /**
         * Sets the entry's key to the specified key and returns the old key.
         * 
         * @return the old priority key.
         */
        public K setKey(K k) {
            K old = key;
            key = k;
            return old;
        }
    }
}
