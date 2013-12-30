
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Surya Wono
 *
 */
public class MinHeap<T extends Comparable> implements Queue<T> {

    private T[] tree;
    private int heapSize = -1;
    private int maxSize = 100;
    private Class<T> c = null;
    private int countMod = 0;

    public MinHeap(Class<T> c) {
        this.c = c;
        if (c != null) {
            tree = (T[]) Array.newInstance(c, maxSize);
        } else {
            tree = (T[]) new Comparable[maxSize];
        }
    }

    public MinHeap(Class<T> c, int maxSize) {
        this.c = c;
        this.maxSize = maxSize;
        if (c != null) {
            tree = (T[]) Array.newInstance(c, maxSize);
        } else {
            tree = (T[]) new Comparable[maxSize];
        }
    }

    public MinHeap() {
        tree = (T[]) new Comparable[maxSize];
    }

    public MinHeap(int maxSize) {
        this.maxSize = maxSize;
        tree = (T[]) new Comparable[maxSize];
    }

    public MinHeap(MinHeap<T> m) {
        this.maxSize = m.maxSize;
        this.c = m.c;
        this.tree=m.toArray();
        this.heapSize=m.heapSize;
    }

    public boolean isFull() {
        if (heapSize + 1 == maxSize) {
            return true;
        }
        return false;
    }

    private void swap(int i, int j) {
        T t = tree[i];
        tree[i] = tree[j];
        tree[j] = t;
    }

    private void minHeapify(int i) {
        int left = leftIndex(i);
        int right = rightIndex(i);
        int smallest = i;
        if (left != -1) {
            if (tree[i].compareTo(tree[left]) > 0) {
                smallest = left;
            }
        }
        if (right != -1) {
            if (tree[smallest].compareTo(tree[right]) > 0) {
                smallest = right;
            }
        }
        if (i != smallest) {
            swap(i, smallest);
            minHeapify(smallest);
        }
    }

    private int parentIndex(int i) {
        int index;
        if (i % 2 == 0) {
            index = i / 2 - 1;
        } else {
            index = i / 2;
        }
        if (index > heapSize) {
            index = -1;
        }
        return index;
    }

    private T parent(int i) {
        int index = parentIndex(i);
        if (index != -1) {
            return tree[index];
        }
        return null;
    }

    private int leftIndex(int i) {
        int index = i * 2 + 1;
        if (index > heapSize) {
            index = -1;
        }
        return index;
    }

    private T left(int i) {
        int index = leftIndex(i);
        if (index != -1) {
            return tree[index];
        }
        return null;
    }

    private int rightIndex(int i) {
        int index = i * 2 + 2;
        if (index > heapSize) {
            index = -1;
        }
        return index;
    }

    private T right(int i) {
        int index = rightIndex(i);
        if (index != -1) {
            return tree[index];
        }
        return null;
    }

    public T extractMin() {
        return remove();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> t = new Iterator() {
            private int itCurrent = -1;
            private int currentMod = countMod;

            @Override
            public boolean hasNext() {
                if (itCurrent < heapSize) {
                    return true;
                }
                return false;
            }

            @Override
            public T next() {
                if (currentMod != countMod) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return tree[++itCurrent];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return t;
    }

    @Override
    public T[] toArray() {
        T[] t = makeArray();
        System.arraycopy(tree, 0, t, 0, heapSize + 1);
        return t;
    }

    public T[] toArraySorted() {
        MinHeap<T> min = new MinHeap(this);
        T[] t = makeArray();
        int i=0;
        while (min.size()>0){
            t[i++]=min.extractMin();
        }
        return t;
    }

    private T[] makeArray() {
        T[] t;
        if (c != null) {
            t = (T[]) Array.newInstance(c, heapSize + 1);
        } else {
            t = (T[]) new Comparable[heapSize + 1];
        }
        return t;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(T e) {
        if (!isFull()) {
            heapSize++;
            countMod++;
            tree[heapSize] = e;
            int i = parentIndex(heapSize);
            while (i > -1) {
                minHeapify(i);
                i = parentIndex(i);
            }

        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        try {
            for (T item : c) {
                add(item);
            }
            countMod++;
        } catch (ClassCastException | ArrayStoreException cce ) {
            this.heapSize--;
            return false;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        heapSize = -1;
        countMod++;
    }

    @Override
    public String toString() {
        String s = "{";
        for (int i = 0; i <= heapSize; i++) {
            s += tree[i] + ",";
        }
        if (isEmpty()) {
            s += " ";
        }
        return s.substring(0, s.length() - 1) + "}";
    }

    @Override
    public int size() {
        return heapSize + 1;
    }

    @Override
    public boolean isEmpty() {
        if (heapSize != -1) {
            return false;
        }
        return true;
    }

    @Override
    public boolean offer(T e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new NullPointerException();
        }
        T t = tree[heapSize];
        heapSize--;
        countMod++;
        return t;
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NullPointerException();
        }
        T t = tree[0];
        swap(0, heapSize);
        heapSize--;
        countMod++;
        minHeapify(0);
        return t;
    }

    @Override
    public T poll() {
        try {
            T t = remove();
            return t;
        } catch (NullPointerException ne) {
            return null;
        }
    }

    @Override
    public T element() {
        if (isEmpty()) {
            throw new NullPointerException();
        }
        return tree[0];
    }

    @Override
    public T peek() {
        try {
            T t = element();
            return t;
        } catch (NullPointerException ne) {
            return null;
        }
    }
}
