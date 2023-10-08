package com.yxf.bindercode.utils;

public class SortAlgorithmUtil {
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            if (min != i) {
                int tmp = arr[min];
                arr[min] = arr[i];
                arr[i] = tmp;
            }
        }
    }

    public static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int k = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > k) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = k;
        }
    }

    public static void quickSort(int[] arr) {
        quick(arr, 0, arr.length - 1);
    }

    private static void quick(int[] arr, int l, int r) {
        if (l > r) {
            return;
        }
        int pivot = partition(arr, l, r);
        quick(arr, l, pivot - 1);
        quick(arr, pivot + 1, r);
    }

    private static int partition(int[] arr, int start, int end) {
        int tmp = arr[start];
        while (start < end) {
            while (start < end && arr[end] >= tmp) {
                end--;
            }
            arr[start] = arr[end];
            while (start < end && arr[start] < tmp) {
                start++;
            }
            arr[end] = arr[start];
        }
        arr[start] = tmp;
        return start;
    }
}
