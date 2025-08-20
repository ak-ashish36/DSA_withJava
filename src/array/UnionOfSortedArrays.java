package array;

import java.util.*;

public class UnionOfSortedArrays {

    public static void main(String[] args) {
        // Test cases
        int[][] arr1Cases = {
                { 1, 2, 4, 5, 6 }, // Normal
                { 2, 2, 2, 2 }, // Duplicates only
                {}, // Empty arr1
                { 1, 3, 5, 7 }, // Odd numbers
                { 10, 20, 30 } // Larger values
        };

        int[][] arr2Cases = {
                { 2, 3, 5, 7 }, // Overlap with arr1
                { 2, 2, 2 }, // All duplicates
                { 1, 2, 3 }, // arr1 empty, arr2 non-empty
                { 2, 4, 6, 8 }, // Even numbers
                {} // Empty arr2
        };

        for (int i = 0; i < arr1Cases.length; i++) {
            int[] arr1 = arr1Cases[i];
            int[] arr2 = arr2Cases[i];
            System.out.println("Test case " + (i + 1));
            System.out.println("arr1: " + Arrays.toString(arr1));
            System.out.println("arr2: " + Arrays.toString(arr2));
            System.out.println("Union (Set): " + Arrays.toString(unionUsingSet(arr1, arr2)));
            System.out.println("Union (Two Pointer): " + Arrays.toString(unionTwoPointer(arr1, arr2)));
            System.out.println("-----");
        }
    }

    /**
     * Approach 1: Using Set
     * ---------------------
     * Hint:
     * - Insert all elements of arr1 and arr2 into TreeSet.
     * - TreeSet auto-removes duplicates and keeps ascending order.
     *
     * Time Complexity: O((n+m) log(n+m))
     * Space Complexity: O(n+m)
     */
    public static int[] unionUsingSet(int[] arr1, int[] arr2) {
        Set<Integer> set = new TreeSet<>();
        for (int num : arr1) {
            set.add(num);
        }
        for (int num : arr2) {
            set.add(num);
        }
        int[] result = set.stream().mapToInt(Integer::intValue).toArray();
        return result;
    }

    /**
     * Approach 2: Two Pointer Merge (Optimal)
     * ---------------------------------------
     * Hint:
     * - Traverse both arrays with two pointers i, j.
     * - Pick smaller element, skip duplicates.
     * - When equal, add once and move both pointers.
     *
     * Time Complexity: O(n+m)
     * Space Complexity: O(1) extra (apart from result list)
     */
    public static int[] unionTwoPointer(int[] arr1, int[] arr2) {
        // TODO: Implement logic
        int i = 0, j = 0;
        List<Integer> result = new ArrayList<>();
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                if (result.isEmpty() || result.get(result.size() - 1) != arr1[i]) {
                    result.add(arr1[i]);
                }
                i++;
            } else if (arr1[i] > arr2[j]) {
                if (result.isEmpty() || result.get(result.size() - 1) != arr2[j]) {
                    result.add(arr2[j]);
                }
                j++;
            } else { // arr1[i] == arr2[j]
                if (result.isEmpty() || result.get(result.size() - 1) != arr1[i]) {
                    result.add(arr1[i]);
                }
                i++;
                j++;
            }
        }

        return result.stream().mapToInt(Integer::intValue).toArray();// placeholder
    }
}
