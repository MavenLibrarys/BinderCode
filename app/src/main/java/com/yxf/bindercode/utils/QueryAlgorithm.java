package com.yxf.bindercode.utils;

public class QueryAlgorithm {
    public static int binaryQuery(int[] arr, int target) {
        int l = 0;
        int r = arr.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            int mv = arr[m];
            if (target < mv) {
                r = m - 1;
            } else if (target == mv) {
                return m;
            } else if (target > mv) {
                l = m + 1;
            }
        }
        return -1;
    }
}
