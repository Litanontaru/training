package com.epam.training.task2;

import java.util.*;

public class Test {

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(2, 6, 5, 8, 9, 10, 7);
        List<Integer> predicate = Arrays.asList(6, 7);

        FilterList<Integer> filterList = new FilterList<>(integers, predicate);

        System.out.print("All elements in filterList: ");
        printAllElements(filterList);
        System.out.print("Elements in filterList by iterator: ");
        printFilterElements(filterList);
        filterList.add(6);
        System.out.print("All Elements after trying to add element from predicate: ");
        printAllElements(filterList);


    }

    private static <T> void printAllElements(FilterList<T> filterList) {
        for (int i = 0; i < filterList.size(); ++i) {
            System.out.print(filterList.get(i));
            System.out.print(" ");
        }
        System.out.println();
    }

    private static <T> void printFilterElements(FilterList<T> filterList) {
        for (T value : filterList) {
            System.out.print(value);
            System.out.print(" ");
        }
        System.out.println();
    }
}
