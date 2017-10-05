package com.epam.training.task2;

import java.util.*;

public class FilterList<T> extends ArrayList<T> {

    private List<T> predicate;

    public FilterList(Collection<T> values, Collection<T> predicate) {
        super(values);
        this.predicate = new ArrayList<>(predicate);
    }

    @Override
    public boolean add(T t) {
        return !predicate.contains(t) && super.add(t);
    }

    @Override
    public T remove(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        if (predicate.contains(FilterList.this.get(index))) {
            throw new IllegalArgumentException();
        }
        return super.remove(index);
    }

    @Override
    public Iterator<T> iterator() {
        return new FilterIterator();
    }


    private class FilterIterator implements Iterator<T> {

        int cursor;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            if (cursor == FilterList.this.size()) {
                return false;
            }
            while (predicate.contains(FilterList.this.get(cursor))) {
                ++cursor;
                if (cursor == FilterList.this.size()) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public T next() {
            if (cursor >= FilterList.this.size())
                throw new NoSuchElementException();

            return (T) FilterList.this.get(lastRet = cursor++);
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                FilterList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
