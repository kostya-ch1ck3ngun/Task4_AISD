package org.algandsd;

public class SortState {
    private final int[] currentArray;
    private final int currentCycleIterationNumber;
    private int elementIndex = -1;
    private boolean arraySorted = false;

    public SortState(int[] arr, int cycleIteration, int element) {
        currentArray = new int[arr.length];
        System.arraycopy(arr, 0, currentArray, 0, currentArray.length);
        currentCycleIterationNumber = cycleIteration;
        elementIndex = element;
    }

    public SortState(int[] arr, int cycleIteration, boolean isSorted) {
        currentArray = new int[arr.length];
        System.arraycopy(arr, 0, currentArray, 0, currentArray.length);
        currentCycleIterationNumber = cycleIteration;
        arraySorted = isSorted;
    }

    public int[] getArray () {return currentArray;}

    public int getCycleIteration () {return currentCycleIterationNumber;}

    public int getElementIndex () {return elementIndex;}

    public boolean isSorted () {return arraySorted;}
}
