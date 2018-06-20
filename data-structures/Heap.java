import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A heap that supports generic values and priorities. If a comparator is not
 * provided, Key must implement Comparable<Key>.
 */
public class Heap<V, Key> {
    private List<Entry<V, Key>> heap;
    private Map<V, Integer> indexOfValue;
    private Comparator<Key> comparator;

    public Heap() {
        heap = new ArrayList<>();
        indexOfValue = new HashMap<>();
    }

    public Heap(Comparator<Key> comparator) {
        this();
        this.comparator = comparator;
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public boolean containsValue(V value) {
        return indexOfValue.containsKey(value);
    }

    public void add(V value, Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        heap.add(new Entry<>(value, key));
        indexOfValue.put(value, heap.size() - 1);
        decreaseKey(value, key);
    }

    public void decreaseKey(V value, Key newKey) {
        if (!indexOfValue.containsKey(value)) {
            throw new NoSuchElementException();
        }
        int i = indexOfValue.get(value);
        int compareResult = comparator == null
            ? ((Comparable<Key>) newKey).compareTo(heap.get(i).getKey())
            : comparator.compare(newKey, heap.get(i).getKey());
        if (newKey == null || compare(newKey, heap.get(i).getKey()) > 0) {
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

    public V peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return heap.get(0).getValue();
    }

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

    public Set<V> values() {
        Set<V> values = new HashSet<V>();
        for (Entry<V, Key> e : heap) {
            values.add(e.getValue());
        }
        return values;
    }

    int parent(int node) {
        if (node == 0) {
            return 0;
        }
        return (node - 1) >> 1;
    }

    int left(int node) {
        return (node << 1) + 1;
    }

    int right(int node) {
        return (node << 1) + 2;
    }

    void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;
        if (l < size() && compare(heap.get(l).getKey(), heap.get(i).getKey())  < 0) {
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

    private int compare(Key a, Key b) {
        if (comparator == null) {
            return ((Comparable<Key>) a).compareTo(b);
        }
        return comparator.compare(a, b);
    }

    class Entry<V, K> {
        private V value;
        private K key;
        public Entry(V v, K k) {
            value = v;
            key = k;
        }
        public V getValue() { return value; }
        public K getKey() { return key; }
        public K setKey(K k) {
            K old = key;
            key = k;
            return old;
        }
    }
}
