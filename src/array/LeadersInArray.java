package array;

import java.util.*;

/**
 * Problem:
 * Given an array, print all the elements which are leaders.
 * A Leader is an element that is greater than all of the elements
 * on its right side in the array.
 *
 * Coding Platforms:
 * - GeeksForGeeks: https://www.geeksforgeeks.org/leaders-in-an-array/
 * - LeetCode (variant discussions): search "Leaders in Array"
 * - Coding Ninjas:
 * https://www.codingninjas.com/studio/problems/leaders-in-an-array_873144
 *
 * Notes:
 * - The rightmost element is always a leader.
 * - Multiple leaders possible.
 * - Leaders must be printed in the order they appear in the array.
 */
public class LeadersInArray {

    public static void main(String[] args) {
        int[][] tests = {
                { 16, 17, 4, 3, 5, 2 }, // leaders: 17, 5, 2
                { 1, 2, 3, 4, 0 }, // leaders: 4, 0
                { 7, 4, 5, 7, 3 }, // leaders: 7, 7, 3
                { 10, 9, 8 }, // leaders: 10, 9, 8
                { 5 }, // leaders: 5
                { 100, 90, 80, 70 }, // leaders: 100, 90, 80, 70
        };

        int[][] expected = {
                { 17, 5, 2 },
                { 4, 0 },
                { 7, 7, 3 },
                { 10, 9, 8 },
                { 5 },
                { 100, 90, 80, 70 }
        };

        for (int t = 0; t < tests.length; t++) {
            int[] arr = Arrays.copyOf(tests[t], tests[t].length);
            System.out.println("Test #" + (t + 1));
            System.out.println("Input:    " + Arrays.toString(arr));
            System.out.println("Expected: " + Arrays.toString(expected[t]));

            List<Integer> ans1 = leadersBrute(arr);
            List<Integer> ans2 = leadersRightScan(arr); // interview approach

            System.out.println("[Brute O(n^2)]   -> " + ans1);
            System.out.println("[Right-to-Left] -> " + ans2);
            System.out.println();
        }
    }

    /**
     * Approach 1: Brute force
     * For each element, check if it's greater than all elements to its right.
     * Time: O(n^2), Space: O(1)
     */
    public static List<Integer> leadersBrute(int[] arr) {
        List<Integer> leaders = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            boolean isLeader = true;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] >= arr[i]) {
                    isLeader = false;
                    break;
                }
            }
            if (isLeader) {
                leaders.add(arr[i]);
            }
        }
        return leaders;
    }

    /**
     * Approach 2: Right-to-left scan (Interview Standard)
     * Algorithm:
     * - Start from rightmost element (always leader).
     * - Track current maximum.
     * - If arr[i] >= current max, mark as leader and update max.
     * - Reverse collected leaders at the end to maintain original order.
     *
     * Time: O(n), Space: O(n) for storing leaders
     */
    public static List<Integer> leadersRightScan(int[] arr) {
        List<Integer> result = new ArrayList<>();
        int n = arr.length;
        int currMax = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] >= currMax) {
                result.add(currMax);
                currMax = arr[i];
            }
        }
        result.add(currMax);
        Collections.reverse(result);
        return result;
    }
}