import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;

public class SortingTests {
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Integer[] array = new Integer[]{54, 28, 20, 58, 84, 20, 122, -85, -85, 3, 8, 7, 9, 5, 5, 5, 5, -1000, 3};
        Integer[] sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray);
        Sorting.insertionSort(array, Integer::compareTo);
        assertArrayEquals(sortedArray, array);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSort() {
        Integer[] array = new Integer[]{54, 28, 20, 58, 84, 20, 122, -85, -85, 3, 8, 7, 9, 5, 5, 5, 5, -1000, 3};
        Integer[] sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray);
        Sorting.cocktailSort(array, Integer::compareTo);
        assertArrayEquals(sortedArray, array);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Integer[] array = new Integer[]{54, 28, 20, 58, 84, 20, 122, -85, -85, 3, 8, 7, 9, 5, 5, 5, 5, -1000, 3};
        Integer[] sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray);
        Sorting.mergeSort(array, Integer::compareTo);
        assertArrayEquals(sortedArray, array);
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSort() {
        Integer[] array = new Integer[]{54, 28, 20, 58, 84, 20, 122, -85, -85, 3, 8, 7, 9, 5, 5, 5, 5, -1000, 3};
        Integer[] sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray);
        Sorting.quickSort(array, Integer::compareTo, new Random());
        assertArrayEquals(sortedArray, array);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSort() {
        List<Integer> list = new ArrayList<>(Arrays.asList(
                54, 28, 20, 58, 84, 20, 122, -85, -85, 3, 8, 7, 9, 5, 5, 5, 5, -1000, 3
        ));

        List<Integer> sortedList = new ArrayList<>(list);
        Collections.sort(sortedList);

        int[] sortedArray = new int[sortedList.size()];
        for (int i = 0; i < sortedList.size(); i++) {
            sortedArray[i] = sortedList.get(i);
        }

        int[] array = Sorting.heapSort(list);
        assertEquals(sortedArray.length, array.length);
        for (int i = 0; i < array.length; i++) {
            assertEquals(sortedArray[i], array[i]);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] array = new int[]{54, 28, 20, 58, 84, 20, 122, -85, -85, 3, 8, 7, 9, 5, 5, 5, 5, -1000, 3};
        int[] sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray);
        Sorting.lsdRadixSort(array);
        assertArrayEquals(sortedArray, array);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testInsertionSortWithNullArray() {
        Integer[] nullArray = null;
        Sorting.insertionSort(nullArray, Integer::compareTo);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testInsertionSortWithNullComparator() {
        Integer[] array = new Integer[]{3, 2, 1, 4, 5};
        Sorting.insertionSort(array, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCocktailSortWithNullArray() {
        Integer[] nullArray = null;
        Sorting.cocktailSort(nullArray, Integer::compareTo);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testMergeSortWithNullArray() {
        Integer[] nullArray = null;
        Sorting.mergeSort(nullArray, Integer::compareTo);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testMergeSortWithNullComparator() {
        Integer[] array = new Integer[]{3, 2, 1, 4, 5};
        Sorting.mergeSort(array, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickSortWithNullArray() {
        Integer[] nullArray = null;
        Sorting.quickSort(nullArray, Integer::compareTo, new Random());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickSortWithNullComparator() {
        Integer[] array = new Integer[]{3, 2, 1, 4, 5};
        Sorting.quickSort(array, null, new Random());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickSortWithNullRandom() {
        Integer[] array = new Integer[]{3, 2, 1, 4, 5};
        Sorting.quickSort(array, Integer::compareTo, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLsdRadixSortWithNullArray() {
        int[] nullArray = null;
        Sorting.lsdRadixSort(nullArray);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testHeapSortWithNullData() {
        List<Integer> data = null;
        Sorting.heapSort(data);
    }

}