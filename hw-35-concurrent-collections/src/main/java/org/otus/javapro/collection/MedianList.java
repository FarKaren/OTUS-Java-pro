package org.otus.javapro.collection;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class MedianList<T extends Number> {
    private final Multiset<T> collection = HashMultiset.create();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public int size() {
        lock.readLock().lock();
        try {
            return collection.size();
        } finally {
            lock.readLock().unlock();
        }
    }
    public void add(T item) {
        lock.writeLock().lock();
        try {
            collection.add(item);
        } finally {
            lock.writeLock().unlock();
        }
    }
    public void remove(T item) {
        lock.writeLock().lock();
        try {
            collection.remove(item);
        } finally {
            lock.writeLock().unlock();
        }
    }
    public double getMedian() {
        lock.writeLock().lock();
        try {
            if(!collection.isEmpty()) {
                var skip = collection.size() / 2;
                if (collection.size() % 2 != 0) {
                    return collection.stream()
                            .sorted()
                            .skip(skip)
                            .limit(1)
                            .map(Number::doubleValue)
                            .findFirst()
                            .orElseThrow();
                } else {
                    var targets = collection.stream()
                            .sorted()
                            .skip(skip)
                            .limit(2)
                            .map(Number::doubleValue)
                            .toList();
                    return (targets.get(0) + targets.get(1)) / 2;
                }
            } else return Double.NaN;

        } finally {
            lock.writeLock().unlock();
        }
    }
}
