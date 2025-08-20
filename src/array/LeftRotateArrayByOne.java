package array;

import java.util.Arrays;

public class LeftRotateArrayByOne {

    public static void main(String[] args) {
        // Test cases
        int[][] testCases = {
                { 1, 2, 3, 4, 5 }, // Normal case
                { 10 }, // Single element
                {}, // Empty array
                { 7, 7, 7, 7 }, // All same values
                { -1, -2, -3, -4, -5 } // Negative numbers
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] arr = testCases[i];
            System.out.println("Test case " + (i + 1) + ": " + Arrays.toString(arr));
            System.out.println("Shift by Loop: " + Arrays.toString(leftRotateByLoop(arr.clone())));
            System.out.println("-----");
        }
    }

    /**
     * Approach 1: Shifting using Loop
     * -------------------------------
     * Hint:
     * - Store the first element separately.
     * - Shift each element to the left.
     * - Place the stored element at the end.
     *
     * Time Complexity: O(n) — every element is shifted
     * Space Complexity: O(1) — in-place
     *
     * Famous because:
     * - Straightforward and commonly expected in interviews.
     */
    public static int[] leftRotateByLoop(int[] arr) {
        if (arr.length == 0)
            return arr; // Handle empty array
        int first = arr[0];

        for (int i = 0; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[arr.length - 1] = first;

        return arr; // placeholder
    }
}
