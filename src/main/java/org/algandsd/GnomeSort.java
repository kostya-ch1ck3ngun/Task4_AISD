package org.algandsd;

import java.util.ArrayList;
import java.util.List;

public class GnomeSort {
    public static List<SortState> sort(int[] arr) {
        List<SortState> sortStateList = new ArrayList<>();

        int cyclesCount = 0;

        for (int i = 0; i < arr.length - 1;) {
            SortState currentState;

            if (arr[i] > arr[i + 1]) {
                currentState = new SortState(arr, cyclesCount, i + 1);

                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;

                if (i > 0) {
                    i--;
                } else {
                    i++;
                }
            } else {
                currentState = new SortState(arr, cyclesCount, i + 1);

                i++;
            }

            sortStateList.add(currentState);

            cyclesCount++;
        }
        SortState currentState = new SortState(arr, cyclesCount, true);
        sortStateList.add(currentState);

        return sortStateList;
    }

    public static List<SortState> fastSort(int[] arr) {
        List<SortState> sortStateList = new ArrayList<>();

        int cyclesCount = 0;
        int maxPos = 0;

        for (int i = 0; i < arr.length - 1;) {
            SortState currentState;

            if (i == maxPos) {
                maxPos = i + 1;
            }

            if (arr[i] > arr[i + 1]) {
                currentState = new SortState(arr, cyclesCount, i + 1);

                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;

                if (i > 0) {
                    i--;
                } else {
                    i = maxPos;
                }
            } else {
                currentState = new SortState(arr, cyclesCount, i + 1);

                i = maxPos;
            }

            sortStateList.add(currentState);

            cyclesCount++;
        }
        SortState currentState = new SortState(arr, cyclesCount, true);
        sortStateList.add(currentState);

        return sortStateList;
    }
}
