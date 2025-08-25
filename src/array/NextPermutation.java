package array;

import java.util.*;

/**
 * Coding Platforms (official pages):
 * - LeetCode (Next Permutation):
 * https://leetcode.com/problems/next-permutation/
 * - GeeksForGeeks (Next Permutation):
 * https://www.geeksforgeeks.org/dsa/next-permutation/
 * - Coding Ninjas (Next Permutation):
 * https://www.codingninjas.com/studio/problems/next-permutation_758946
 * - CodeChef (Practice; search "Next Permutation"):
 * https://www.codechef.com/practice
 *
 * Problem:
 * Rearrange Arr[] into the lexicographically next greater permutation.
 * If not possible, rearrange to the lowest possible order (ascending).
 *
 * Notes:
 * - Standard expectation: in-place with O(1) extra space for the optimal
 * approach.
 * - Duplicates are allowed and handled by the algorithm.
 */
public class NextPermutation {

    // Main at TOP with impactful tests; prints expected outputs alongside actual
    // results
    public static void main(String[] args) {
        int[][] tests = {
                { 1, 2, 3 }, // expected -> {1, 3, 2}
                { 3, 2, 1 }, // expected -> {1, 2, 3} (reset to lowest)
                { 1, 1, 5 }, // expected -> {1, 5, 1}
                { 1, 3, 2 }, // expected -> {2, 1, 3}
                { 2, 4, 1, 7, 5, 0 }, // expected -> {2, 4, 5, 0, 1, 7}
                { 1 }, // expected -> {1}
                {} // expected -> {}
        };

        int[][] expected = {
                { 1, 3, 2 },
                { 1, 2, 3 },
                { 1, 5, 1 },
                { 2, 1, 3 },
                { 2, 4, 5, 0, 1, 7 },
                { 1 },
                {}
        };

        System.out.println("==== NEXT PERMUTATION – SKELETON ====");
        for (int t = 0; t < tests.length; t++) {
            int[] a1 = Arrays.copyOf(tests[t], tests[t].length);
            int[] a2 = Arrays.copyOf(tests[t], tests[t].length);
            int[] a3 = Arrays.copyOf(tests[t], tests[t].length);
            int[] a4 = Arrays.copyOf(tests[t], tests[t].length);

            System.out.println("Test #" + (t + 1));
            System.out.println("Input:     " + Arrays.toString(tests[t]));
            System.out.println("Expected:  " + Arrays.toString(expected[t]));

            nextPermutationBrute(a1); // Approach 1
            System.out.println("[Approach 1 - Brute]        -> " + Arrays.toString(a1));

            nextPermutationOptimal(a2); // Approach 2
            System.out.println("[Approach 2 - Optimal]       -> " + Arrays.toString(a2));

            nextPermutationSuffixOps(a4); // Approach 4
            System.out.println("[Approach 3 - Suffix Ops]    -> " + Arrays.toString(a4));

            System.out.println();
        }
    }

    /**
     * Approach 1: Brute Force (Generate All Permutations)
     *
     * Idea:
     * - Generate all permutations, sort them lexicographically, find the current,
     * and move to the next; if current is last, move to the first (ascending).
     *
     * Time Complexity: Exponential (O(n! * n) or worse depending on
     * generation/sort)
     * Space Complexity: O(n! * n) if storing permutations
     *
     * @param nums input array to transform in-place (stub: no-op)
     */
    public static void nextPermutationBrute(int[] nums) {
        int n = nums.length;
        List<int[]> all = new ArrayList<>();
        permute(nums, 0, all);
        all.sort(NextPermutation::lexCompare);
        int idx = findIndex(all, nums);
        int[] next = (idx == all.size() - 1) ? all.get(0) : all.get(idx + 1);
        System.arraycopy(next, 0, nums, 0, n);

    }

    private static void permute(int[] a, int l, List<int[]> out) {
        if (l == a.length) {
            out.add(Arrays.copyOf(a, a.length));
            return;
        }
        for (int i = l; i < a.length; i++) {
            swap(a, l, i);
            permute(a, l + 1, out);
            swap(a, l, i);
        }
    }

    private static int lexCompare(int[] x, int[] y) {
        int n = Math.min(x.length, y.length);
        for (int i = 0; i < n; i++) {
            if (x[i] != y[i])
                return Integer.compare(x[i], y[i]);
        }
        return Integer.compare(x.length, y.length);
    }

    private static int findIndex(List<int[]> all, int[] target) {
        for (int i = 0; i < all.size(); i++) {
            if (Arrays.equals(all.get(i), target))
                return i;
        }
        return -1;
    }

    /**
     * Approach 2: Optimal In-Place (Pivot + Rightmost Successor + Reverse Suffix)
     *
     * Idea:
     * - From right, find pivot i where nums[i] < nums[i+1]; if none, reverse entire
     * array and return.
     * - From right, find rightmost j > i with nums[j] > nums[i], swap i and j.
     * - Reverse suffix nums[i+1..end] to minimal ascending order.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param nums input array to transform in-place (stub: no-op)
     */
    public static void nextPermutationOptimal(int[] nums) {
        int n = nums.length;
        if (n <= 1)
            return;

        // 1) Find pivot: first i from right with a[i] < a[i+1]
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            // 2) Find rightmost successor > a[i]
            int j = n - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }

        // 3) Reverse suffix starting at i+1
        reverse(nums, i + 1, n - 1);
    }

    /**
     * Approach 3: Suffix Operations Perspective (Non-increasing suffix handling)
     *
     * Idea:
     * - Treat the right side as a non-increasing suffix.
     * - After finding the pivot, scan backward to find the successor (> pivot),
     * swap them,
     * then reverse the suffix to obtain the minimal tail.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param nums input array to transform in-place (stub: no-op)
     */
    public static void nextPermutationSuffixOps(int[] nums) {
        // TODO: implement suffix viewpoint (pivot + successor + reverse)
        // Safe no-op
    }

    /**
     * Helper: Reverse subarray nums[l..r] in-place.
     *
     * Time: O(r - l + 1), Space: O(1)
     */
    private static void reverse(int[] nums, int l, int r) {
        while (l < r)
            swap(nums, l++, r--);
    }

    /**
     * Helper: Swap elements at i and j in-place.
     *
     * Time: O(1), Space: O(1)
     */
    private static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
