package array;

import java.util.*;

/**
 * Problem:
 * Given two sorted arrays arr1[] (size n) and arr2[] (size m) in non-decreasing
 * order,
 * merge them in sorted order without using extra space.
 * Modify arr1 so that it contains the first n elements,
 * and modify arr2 so that it contains the last m elements.
 *
 * Coding Platforms:
 * - GeeksForGeeks:
 * https://www.geeksforgeeks.org/merge-two-sorted-arrays-without-extra-space/
 * - Coding Ninjas:
 * https://www.codingninjas.com/studio/problems/merge-two-sorted-arrays-without-extra-space_985294
 *
 * Notes:
 * - Brute Force: merge using extra space, then copy back.
 * - Optimal: "Gap method" (Shell sort inspired).
 */
public class MergeSortedArrays {

    public static void main(String[] args) {
        int[][] arr1Tests = {
                { 1, 3, 5, 7 },
                { 10, 12 },
                { 1, 2, 3 },
                { 1, 4, 7, 8, 10 },
                { 2, 3, 5, 6 }
        };
        int[][] arr2Tests = {
                { 0, 2, 6, 8, 9 },
                { 5, 18, 20 },
                { 4, 5, 6 },
                { 2, 3, 9 },
                { 1, 4, 7, 8 }
        };

        // Expected outputs
        int[][] expectedArr1 = {
                { 0, 1, 2, 3 },
                { 5, 10 },
                { 1, 2, 3 },
                { 1, 2, 3, 4, 7 },
                { 1, 2, 3, 4 }
        };
        int[][] expectedArr2 = {
                { 5, 6, 7, 8, 9 },
                { 12, 18, 20 },
                { 4, 5, 6 },
                { 8, 9, 10 },
                { 5, 6, 7, 8 }
        };

        for (int t = 0; t < arr1Tests.length; t++) {
            int[] arr1Input = Arrays.copyOf(arr1Tests[t], arr1Tests[t].length);
            int[] arr2Input = Arrays.copyOf(arr2Tests[t], arr2Tests[t].length);

            System.out.println("Test #" + (t + 1));
            System.out.println("Before:");
            System.out.println("arr1: " + Arrays.toString(arr1Input));
            System.out.println("arr2: " + Arrays.toString(arr2Input));
            System.out.println("Expected arr1: " + Arrays.toString(expectedArr1[t]));
            System.out.println("Expected arr2: " + Arrays.toString(expectedArr2[t]));

            // Brute Force
            int[] arr1Brute = Arrays.copyOf(arr1Input, arr1Input.length);
            int[] arr2Brute = Arrays.copyOf(arr2Input, arr2Input.length);
            mergeBrute(arr1Brute, arr2Brute);
            System.out.println("[Brute] arr1: " + Arrays.toString(arr1Brute));
            System.out.println("[Brute] arr2: " + Arrays.toString(arr2Brute));

            // Optimal
            int[] arr1Opt = Arrays.copyOf(arr1Input, arr1Input.length);
            int[] arr2Opt = Arrays.copyOf(arr2Input, arr2Input.length);
            mergeOptimal(arr1Opt, arr2Opt);
            System.out.println("[Optimal] arr1: " + Arrays.toString(arr1Opt));
            System.out.println("[Optimal] arr2: " + Arrays.toString(arr2Opt));

            System.out.println();
        }
    }

    /**
     * Approach 1: Brute Force
     * - Use extra space to merge both arrays like merge-sort.
     * - Copy first n elements back to arr1, rest to arr2.
     * Time: O(n+m), Space: O(n+m).
     */
    public static void mergeBrute(int[] arr1, int[] arr2) {
        int n = arr1.length, m = arr2.length;
        int[] arr3 = new int[n + m];
        int left = 0;
        int right = 0;
        int index = 0;

        // Insert the elements from the 2 arrays
        // into the 3rd array using left and right
        // pointers:

        while (left < n && right < m) {
            if (arr1[left] <= arr2[right]) {
                arr3[index] = arr1[left];
                left++;
                index++;
            } else {
                arr3[index] = arr2[right];
                right++;
                index++;
            }
        }

        // If right pointer reaches the end:
        while (left < n) {
            arr3[index++] = arr1[left++];
        }

        // If left pointer reaches the end:
        while (right < m) {
            arr3[index++] = arr2[right++];
        }

        // Fill back the elements from arr3[]
        // to arr1[] and arr2[]:
        for (int i = 0; i < n + m; i++) {
            if (i < n) {
                arr1[i] = arr3[i];
            } else {
                arr2[i - n] = arr3[i];
            }
        }
    }

    /**
     * Approach 2: Optimal (Gap Method)
     * - Initialize gap = ceil((n+m)/2).
     * - Compare elements across arr1 & arr2 with given gap.
     * - Keep reducing gap until gap=1.
     * Time: O((n+m) log(n+m)), Space: O(1).
     */
    public static void mergeOptimal(int[] arr1, int[] arr2) {
        // TODO: implement
        int n = arr1.length, m = arr2.length;
        int gap = (n + m + 1) / 2;
        while (gap > 0) {
            int left = 0;
            int right = gap;
            while (right < n + m) {
                // case 1: left in arr1[]
                // and right in arr2[]:
                if (left < n && right >= n) {
                    swapIfGreater(arr1, arr2, left, right - n);
                }
                // case 2: both pointers in arr2[]:
                else if (left >= n) {
                    swapIfGreater(arr2, arr2, left - n, right - n);
                }
                // case 3: both pointers in arr1[]:
                else {
                    swapIfGreater(arr1, arr1, left, right);
                }
                left++;
                right++;
            }
            gap = (gap + 1) / 2;
        }
    }

    public static void swapIfGreater(int[] arr1, int[] arr2, int ind1, int ind2) {
        if (arr1[ind1] > arr2[ind2]) {
            int temp = arr1[ind1];
            arr1[ind1] = arr2[ind2];
            arr2[ind2] = temp;
        }
    }
}
