package array;

import java.util.Arrays;

public class MoveZerosToEnd {

    public static void main(String[] args) {
        // Test cases
        int[][] testCases = {
                { 0, 1, 0, 3, 12 }, // Mixed zeros
                { 1, 2, 3, 4 }, // No zeros
                { 0, 0, 0, 0 }, // All zeros
                { 1, 0, 2, 0, 3, 0 }, // Alternate zeros
                {}, // Empty array
                { 10 } // Single element
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] arr = testCases[i];
            System.out.println("Test case " + (i + 1) + ": " + Arrays.toString(arr));
            System.out.println("Extra Array: " + Arrays.toString(moveZerosExtraArray(arr.clone())));
            System.out.println("Two Pointer: " + Arrays.toString(moveZerosTwoPointer(arr.clone())));
            System.out.println("Swap Variant: " + Arrays.toString(moveZerosSwapVariant(arr.clone())));
            System.out.println("-----");
        }
    }

    /**
     * Approach 1: Extra Array
     * -----------------------
     * Hint:
     * - Collect all non-zeros into a new array.
     * - Fill remaining with zeros.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static int[] moveZerosExtraArray(int[] arr) {
        // TODO: Implement logic
        return arr; // placeholder
    }

    /**
     * Approach 2: Two Pointer (In-Place)
     * -----------------------------------
     * Hint:
     * - Use pointer `pos` to track next non-zero placement.
     * - Traverse, place non-zeros at `pos`, then fill rest with zeros.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * Most common interview solution.
     */
    public static int[] moveZerosTwoPointer(int[] arr) {
        if (arr == null || arr.length == 0)
            return arr;

        int pos = 0; // position to place next non-zero
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                arr[pos] = arr[i];
                pos++;
            }
        }

        // Fill remaining with zeros
        while (pos < arr.length) {
            arr[pos] = 0;
            pos++;
        }
        return arr;
    }

    /**
     * Approach 3: Swap Variant
     * ------------------------
     * Hint:
     * - Traverse array with index i.
     * - If arr[i] != 0, swap arr[i] with arr[pos].
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * Optimizes unnecessary writes.
     */
    public static int[] moveZerosSwapVariant(int[] arr) {
        // TODO: Implement logic
        if (arr.length <= 1) {
            return arr;
        }
        int pos = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                pos = i;
                break;
            }
        }
        if (pos == -1)
            return arr; // No zeros found, return as is

        for (int i = pos + 1; i < arr.length; i++) {
            if (arr[i] != 0) {
                int temp = arr[i];
                arr[i] = arr[pos];
                arr[pos] = temp;
                pos++;
            }

        }
        return arr; // placeholder
    }

}
