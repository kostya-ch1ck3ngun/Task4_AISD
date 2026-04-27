package org.algandsd;

public class SortState {
    private final int[] currentArray;
    private final int currentCycleIterationNumber;
    private int elementIndex = -1;

    public SortState(int[] arr, int cycleIteration, int element) {
        currentArray = new int[arr.length];
        System.arraycopy(arr, 0, currentArray, 0, currentArray.length);
        currentCycleIterationNumber = cycleIteration;
        elementIndex = element;
    }

    public SortState(int[] arr, int cycleIteration) {
        currentArray = new int[arr.length];
        System.arraycopy(arr, 0, currentArray, 0, currentArray.length);
        currentCycleIterationNumber = cycleIteration;
    }

    public int[] getArray () {return currentArray;}

    public int getCycleIteration () {return currentCycleIterationNumber;}

    public int getElementIndex () {return elementIndex;}

    public boolean isSorted () {
        int temp = currentArray[0];
        for (int i = 1; i < currentArray.length; i++) {
            if (temp > currentArray[i]) {
                return false;
            }
            temp = currentArray[i];
        }
        return true;
    }
}
