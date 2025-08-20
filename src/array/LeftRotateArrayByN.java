package array;

import java.util.Arrays;

public class LeftRotateArrayByN {

    public static void main(String[] args) {
        // Test cases
        int[][] testCases = {
            {1, 2, 3, 4, 5, 6, 7}, // Normal
            {10},                  // Single element
            {},                    // Empty
            {7, 7, 7, 7},          // Duplicates
            {-1, -2, -3, -4, -5}   // Negatives
        };

        int[] ks = {2, 3, 1, 2, 4}; // Rotation values

        for (int i = 0; i < testCases.length; i++) {
            int[] arr = testCases[i];
            int k = ks[i];
            System.out.println("Test case " + (i + 1) + " | k=" + k + ": " + Arrays.toString(arr));
            System.out.println("Brute Force: " + Arrays.toString(rotateByBruteForce(arr.clone(), k)));
            System.out.println("Extra Array: " + Arrays.toString(rotateByExtraArray(arr.clone(), k)));
            System.out.println("Reversal Algo: " + Arrays.toString(rotateByReversalAlgo(arr.clone(), k)));
            System.out.println("-----");
        }
    }

    /**
     * Approach 1: Brute Force (Rotate one by one)
     * --------------------------------------------
     * Hint:
     *  - Repeat left rotate by 1, k times.
     *
     * Time Complexity: O(n * k)
     * Space Complexity: O(1)
     */
    public static int[] rotateByBruteForce(int[] arr, int k) {
        // TODO: Implement logic
        return arr; // placeholder
    }

    /**
     * Approach 2: Using Extra Array
     * ------------------------------
     * Hint:
     *  - Store first k elements separately.
     *  - Shift rest left.
     *  - Append stored elements at end.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(k)
     */
    public static int[] rotateByExtraArray(int[] arr, int k) {
        // TODO: Implement logic
        return arr; // placeholder
    }

    /**
     * Approach 3: Reversal Algorithm (Optimal)
     * -----------------------------------------
     * Hint:
     *  - Reverse first k elements.
     *  - Reverse remaining n-k elements.
     *  - Reverse entire array.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int[] rotateByReversalAlgo(int[] arr, int k) {
        // TODO: Implement logic
        return arr; // placeholder
    }

    // Utility for reversal
    private static void reverse(int[] arr, int start, int end) {
        // TODO: Implement logic
    }
}
