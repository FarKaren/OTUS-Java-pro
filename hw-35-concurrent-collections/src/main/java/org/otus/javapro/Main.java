package org.otus.javapro;

import org.otus.javapro.collection.MedianList;

import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        System.out.println("-".repeat(100));
        checkSpeed();
        var collection = new MedianList<Integer>();
        collection.add(5);
        collection.add(2);
        collection.add(1);
        collection.add(6);
        collection.add(4);
        collection.add(3);
        collection.add(7);
        collection.add(8);

        System.out.println("Collection size: " + collection.size());
        System.out.println("Collection median: " + collection.getMedian());
        System.out.println("-".repeat(100));
    }

    private static void checkSpeed() {
        var collection = new MedianList<Integer>();
        var start = System.currentTimeMillis();
                IntStream.range(0, 10_000_000)
                .forEach(i -> collection.add(new Random().nextInt(1000)));
        System.out.println("Check speed of adding 10M elements: " + (System.currentTimeMillis() - start) + " ms");
    }
}