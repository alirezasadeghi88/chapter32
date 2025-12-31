package com.learn.thread.homework;

import java.util.concurrent.*;

public class ParallelMergeSort {
    public static void main(String[] args) {
        final int SIZE = 7_000_000;
        int[] list1 = new int[SIZE];
        int[] list2 = new int[SIZE];

        // مقداردهی اولیه با داده‌های تصادفی
        for (int i = 0; i < list1.length; i++)
            list1[i] = list2[i] = (int) (Math.random() * 10_000_000);

        // اجرای موازی
        long startTime = System.nanoTime();
        parallelMergeSort(list1);
        long endTime = System.nanoTime();
        System.out.println("\nParallel time with "
                + Runtime.getRuntime().availableProcessors() +
                " processors is " + (endTime - startTime) / 1_000_000 + " milliseconds");

        // اجرای ترتیبی
        startTime = System.nanoTime();
        mergeSort(list2);
        endTime = System.nanoTime();
        System.out.println("\nSequential time is " +
                (endTime - startTime) / 1_000_000 + " milliseconds");
    }

    // مرج‌سورت ترتیبی
    public static void mergeSort(int[] list) {
        if (list.length > 1) {
            int mid = list.length / 2;
            int[] left = java.util.Arrays.copyOfRange(list, 0, mid);
            int[] right = java.util.Arrays.copyOfRange(list, mid, list.length);

            mergeSort(left);
            mergeSort(right);

            merge(left, right, list);
        }
    }

    // تابع ادغام
    public static void merge(int[] left, int[] right, int[] result) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            result[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }
        while (i < left.length) result[k++] = left[i++];
        while (j < right.length) result[k++] = right[j++];
    }

    // مرج‌سورت موازی
    public static void parallelMergeSort(int[] list) {
        RecursiveAction mainTask = new SortTask(list);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);
    }

    // کلاس وظیفه برای ForkJoin
    private static class SortTask extends RecursiveAction {
        private static final int THRESHOLD = 500;
        private final int[] list;

        SortTask(int[] list) {
            this.list = list;
        }

        @Override
        protected void compute() {
            if (list.length < THRESHOLD) {
                java.util.Arrays.sort(list);
            } else {
                int mid = list.length / 2;

                int[] firstHalf = java.util.Arrays.copyOfRange(list, 0, mid);
                int[] secondHalf = java.util.Arrays.copyOfRange(list, mid, list.length);

                invokeAll(new SortTask(firstHalf), new SortTask(secondHalf));

                merge(firstHalf, secondHalf, list);
            }
        }
    }
}