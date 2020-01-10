import java.util.*;

class Heap<V, K> {
    List<Entry> heap;
    Map<V, Integer> indexOfValue;
    Comparator<K> comparator;

    public Heap() {
        heap = new ArrayList<>();
        indexOfValue = new HashMap<>();
    }

    public Heap(Comparator<K> comparator) {
        this();
        this.comparator = comparator;
    }

    int size() {
        return heap.size();
    }

    boolean isEmpty() {
        return heap.isEmpty();
    }

    boolean containsValue(V value) {
        return indexOfValue.containsKey(value);
    }

    void add(V value, K key) {
        heap.add(new Entry(value, key));
        indexOfValue.put(value, heap.size() - 1);
        decreaseKey(value, key);
    }

    void decreaseKey(V value, K newKey) {
        int i = indexOfValue.get(value);
        heap.get(i).setKey(newKey);
        while (i > 0 && compare(heap.get(parent(i)).getKey(), heap.get(i).getKey()) > 0) {
            Entry temp = heap.get(i);
            heap.set(i, heap.get(parent(i)));
            indexOfValue.put(heap.get(parent(i)).getValue(), i);
            heap.set(parent(i), temp);
            indexOfValue.put(temp.getValue(), parent(i));
            i = parent(i);
        }
    }

    V peek() {
        return heap.get(0).getValue();
    }

    V extractMin() {
        V min = heap.get(0).getValue();
        heap.set(0, heap.get(heap.size() - 1));
        indexOfValue.put(heap.get(0).getValue(), 0);
        heap.remove(heap.size() - 1);
        indexOfValue.remove(min);
        heapify(0);
        return min;
    }

    Set<V> values() {
        Set<V> values = new HashSet<V>();
        for (Entry e : heap) values.add(e.getValue());
        return values;
    }

    int parent(int index) {
        return index == 0 ? 0 : (index - 1) >> 1;
    }

    int left(int index) {
        return (index << 1) + 1;
    }

    int right(int index) {
        return (index << 1) + 2;
    }

    void heapify(int i) {
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
        Entry temp = heap.get(i);
        heap.set(i, heap.get(smallest));
        indexOfValue.put(heap.get(smallest).getValue(), i);
        heap.set(smallest, temp);
        indexOfValue.put(temp.getValue(), smallest);
        heapify(smallest);
    }

    @SuppressWarnings("unchecked")
	int compare(K a, K b) {
        return comparator == null
            ? ((Comparable<K>) a).compareTo(b)
            : comparator.compare(a, b);
    }

    class Entry {
        V value;
        K key;

        public Entry(V v, K k) {
            value = v;
            key = k;
        }

        V getValue() {
            return value;
        }

        K getKey() {
            return key;
        }

        K setKey(K k) {
            K old = key;
            key = k;
            return old;
        }
    }
}
