import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
/**
 * Your implementation of various sorting algorithms.
 *
 * @author Huy Le
 * @version 1.0
 * @userid hduc6
 * @GTID 903845849
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array/Comparator cant be null");
        }
        //length of array
        int length = arr.length;
        //2nd element -> last element
        for (int n = 1; n < length; n++) {
            int i = n; //j starts from index 1
            //j > 0 & prev index > curr index
            while (i > 0 && comparator.compare(arr[i - 1], arr[i]) > 0) {
                //swap 2 elements
                T temp = arr[i - 1];
                arr[i - 1] = arr[i];
                arr[i] = temp;
                //move loop towards beginning of array
                i--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null)  {
            throw new IllegalArgumentException("Array/Comparator cant be null");
        }
        boolean swapsMade = true; //track if any swaps are made in a pass
        int startInd = 0; //start index for next forward pass
        int endInd = arr.length - 1; //end index for back forward pass
        //startInd < endInd
        while (startInd < endInd && swapsMade) {
            //forward pass
            swapsMade = false;
            int lastEndSwap = endInd - 1;
            //start -> end & swap
            for (int i = startInd; i < endInd; i++) {
                //curr index > next index
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    //swap 2 elements
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapsMade = true; //if swaps are made -> true
                    lastEndSwap = i; //keep track of last index of a swap in forward pass
                }
            }
            endInd = lastEndSwap;

            //backward pass
            if (swapsMade) {
                swapsMade = false;
                int lastFrontSwap = startInd + 1;
                //end -> start & swap
                for (int j = endInd; j > startInd; j--) {
                    //curr index < prev index
                    if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                        //swap 2 elements
                        T temp2 = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = temp2;
                        swapsMade = true; //if swaps are made -> true
                        lastFrontSwap = j; //keep track of last index of a swap in backward pass
                    }
                }
                startInd = lastFrontSwap;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cant be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cant be null");
        }
        if (arr.length <= 1) {
            return;
        }
        int length = arr.length;
        //middle index
        int midIndex = length / 2;
        //two arrays: Left & Right store half of input array
        T[] leftArray = (T[]) new Object[midIndex];
        T[] rightArray = (T[]) new Object[length - midIndex];

        //0 -> mid: copy from arr (left half) -> left arr
        for (int i = 0; i < midIndex; i++) {
            leftArray[i] = arr[i];
        } //mid -> length: copy from arr (right half) -> right arr
        for (int i = midIndex; i < arr.length; i++) {
            rightArray[i - midIndex] = arr[i];
        }
        //recursion on both L & R to sort separately
        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);

        //merge sorted Left array + Right array -> arr
        int i = 0;
        int j = 0;
        //while i & j are still within bounds of leftArray & rightArray
        while (i < leftArray.length && j < rightArray.length) {
            //if left[i] <= right[j] -> left[i] should come 1st in merged array to maintain stability
            //(keep equal elements in their original order)
            if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                //left[i] is placed in merged arr[i+j], then left[i] moves to next element in left[]
                arr[i + j] = leftArray[i++];
            } else { //right[j] is smaller
                //right[j] is placed in merged arr[i+j], then right[i] moves to next element in right[]
                arr[i + j] = rightArray[j++];
            }
        }
        //Handle remaining elements that were not merged previously -> place in original arr
        while (i < leftArray.length) {
            arr[i + j] = leftArray[i++];
        }
        while (j < rightArray.length) {
            arr[i + j] = rightArray[j++];
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Array/Comparator/Rand value cant be null");
        }
        int end = arr.length - 1;
        int start = 0;
        //recursion
        quickSortHelper(start, end, arr, comparator, rand);
    }

    /**
     *
     * @param arr array to sort
     * @param comparator compare data in arr
     * @param rand Random object used to select pivots
     * @param start start index of sub array
     * @param end end index of sub array
     * @param <T> refers to the element type
     */
    private static <T> void quickSortHelper(int start, int end, T[] arr, Comparator<T> comparator,
                                            Random rand) {
        //if sub array has less than 2 elements -> already sorted -> stop recursion
        if (end - start < 1) {
            return;
        }
        //rand.nextInt() gives a rand [0,specified value] -> + start -> generate rand [start,end]
        //range of rand numbers (end - start + 1) to include both start & end in range
        int pivotIdx = rand.nextInt(end - start + 1) + start;
        //pivot value is where pivot index is (and swap them) //swap pivot[i] <-> 1st index
        T pivotVal = arr[pivotIdx];
        arr[pivotIdx] = arr[start];
        arr[start] = pivotVal;
        //pointers to traverse sub array & swap elements to divide array around pivot
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                j--;
            }
            //in loop, swap i & j if on wrong side of pivot
            if (i <= j) {
                T tempo = arr[i];
                arr[i] = arr[j];
                arr[j] = tempo;
                i++;
                j--;
            }
        }
        //swap pivot element (start of sub array) with element at j index -> pivot in correct sorted position
        T tempo2 = arr[start];
        arr[start] = arr[j];
        arr[j] = tempo2;
        //recursion on left (elements < pivot) + right (elements > pivot) division of array
        quickSortHelper(start, j - 1, arr, comparator, rand);
        quickSortHelper(j + 1, end, arr, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cant be null");
        }
        if (arr.length == 0) {
            return;
        }
        //maxNum with 1st element of array (find max num in array)
        int maxNum = arr[0];
        for (int i = 0; i < arr.length; i++) {
            int currNum = 0;
            //prevent overflow where input array has min integer
            if (arr[i] == Integer.MIN_VALUE) {
                currNum = Integer.MAX_VALUE;
            } else {
                currNum = Math.abs(arr[i]);
            } //if curr num > max num
            if (currNum > maxNum) {
                maxNum = currNum; //update max num
            }
        }
        //number of iteration needed for radix sort based on num of digits in maxNum
        int k = 1;
        while (maxNum >= 10) {
            k++;
            maxNum = maxNum / 10;
        }
        int base = 1;
        //19 buckets: 9 negative + 0 + 9 positive
        ArrayList<Integer>[] buckets = new ArrayList[19];
        //make array of buckets to hold elements based on digits
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        //Do each digit from LSD->MSD
        for (int i = 0; i < k; i++) {
            //Put elements into buckets
            for (int j = 0; j < arr.length; j++) {
                int bucket = (arr[j] / base) % 10 + 9;
                buckets[bucket].add(a
                        rr[j]);
            }
            //update base for next loop
            base *= 10;
            int idx = 0;
            //collect elements from buckets -> array in sorted order
            for (int buck = 0; buck < buckets.length; buck++) {
                while (buckets[buck].size() != 0) {
                    arr[idx] = buckets[buck].remove(0);
                    idx++;
                }
            }
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cant be null.");
        }
        //make a new prior. queue
        PriorityQueue<Integer> queue = new PriorityQueue<>(data);
        //make new arr of same data.size
        int[] arr = new int[data.size()];
        //remove elements from PQ 1 by 1 -> store in arr
        for (int i = 0; i < data.size(); i++) {
            arr[i] = queue.remove();
        }
        return arr;
    }
}
