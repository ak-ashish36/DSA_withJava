package array;

import java.util.*;

/**
 * Coding Platforms (official problem pages):
 * - LeetCode (Two Sum): https://leetcode.com/problems/two-sum/
 * - GeeksForGeeks (Key Pair | Pair with given sum):
 * https://www.geeksforgeeks.org/problems/key-pair/0
 * - Coding Ninjas (Two Sum):
 * https://www.codingninjas.com/studio/problems/two-sum_839653
 * - CodeChef (Practice; search "Two Sum" / "Pair with given sum"):
 * https://www.codechef.com/practice
 *
 * Problem:
 * Given an array of integers arr[] and an integeri+ target.
 * 1st variant: Return "true" if there exist two numbers such that their sum
 * equals target; otherwise "false".
 * 2nd variant: Return indices of the two numbers such that their sum equals
 * target; otherwise return {-1, -1}.
 *
 * Notes:
 * - Indices variant expects original indices from the unsorted input.
 * - If multiple answers exist, return any valid pair unless specified
 * otherwise.
 * - Consider negatives, duplicates, and edge cases.
 */
public class TwoSum {

    // =========================
    // Main at TOP with Multiple Test Cases
    // =========================
    public static void main(String[] args) {
        // Test cases: arrays and targets
        int[][] arrays = {
                { 2, 7, 11, 15 }, // classic
                { 3, 2, 4 }, // duplicates and mid
                { 3, 3 }, // same element value twice (different indices)
                { -5, -1, 0, 2, 3, 10 }, // negatives + positives
                { 1, 5, 1, 5 }, // multiple valid pairs
                { Integer.MAX_VALUE, -2, 2, -Integer.MAX_VALUE }, // extreme values
                {}, // empty
                { 1 }, // single
        };
        int[] targets = { 9, 6, 6, 5, 6, 0, 0, 1 };

        // Expected for Variant 1 (existence)
        boolean[] expectedExist = { true, true, true, true, true, true, false, false };

        // Example expected for Variant 2 (indices) — any valid answer is acceptable.
        // These are illustrative; the program prints actual outputs from each approach.
        int[][] exampleExpectedIndices = {
                { 0, 1 }, // 2 + 7
                { 1, 2 }, // 2 + 4
                { 0, 1 }, // 3 + 3
                { 3, 4 }, // 2 + 3 (one valid option)
                { 0, 1 }, // 1 + 5 (or 2,3)
                { 0, 3 }, // MAX + (-MAX),
                { -1, -1 },
                { -1, -1 }
        };

        for (int t = 0; t < arrays.length; t++) {
            int[] arr = arrays[t];
            int target = targets[t];
            System.out.println("Test #" + (t + 1));
            System.out.println("arr: " + Arrays.toString(arr) + ", target: " + target);
            System.out.println("Expected: " + expectedExist[t]);
            boolean ans1 = existsBrute(arr.clone(), target);
            boolean ans2 = existsTwoPointers(arr.clone(), target);
            boolean ans3 = existsHashing(arr.clone(), target);
            System.out.println("[Brute]       -> " + ans1);
            System.out.println("[TwoPointers] -> " + ans2);
            System.out.println("[Hashing]     -> " + ans3);
            System.out.println("Expected Output: " + Arrays.toString(exampleExpectedIndices[t]));
            int[] a1 = twoSumBrute(arr.clone(), target);
            int[] a2 = twoSumTwoPointers(arr.clone(), target);
            int[] a3 = twoSumHashing(arr.clone(), target);
            int[] a4 = twoSumBinarySearch(arr.clone(), target);
            System.out.println("[Brute]         -> " + Arrays.toString(a1));
            System.out.println("[TwoPointers]   -> " + Arrays.toString(a2));
            System.out.println("[Hashing]       -> " + Arrays.toString(a3));
            System.out.println("[BinarySearch]  -> " + Arrays.toString(a4));
            System.out.println();
        }

    }

    // =========================
    // Variant 1: Existence (YES/NO)
    // =========================

    /**
     * Approach 1: Brute Force (Existence)
     *
     * Algorithm:
     * - Try all pairs (i, j), i < j, and check if arr[i] + arr[j] == target.
     *
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     *
     * @param arr    input array
     * @param target target sum
     * @return "true" if a pair exists, otherwise "false"
     */
    public static boolean existsBrute(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Approach 2: Sorting + Two Pointers (Existence)
     *
     * Algorithm:
     * - Copy and sort the array.
     * - Use two pointers l (start) and r (end).
     * If sum < target -> l++; sum > target -> r--; else found.
     *
     * Time Complexity: O(n log n) due to sorting
     * Space Complexity: O(n) for copy (or O(1) if in-place allowed without needing
     * original indices)
     *
     * @param arr    input array
     * @param target target sum
     * @return "true" if a pair exists, otherwise "false"
     */
    public static boolean existsTwoPointers(int[] arr, int target) {
        Arrays.sort(arr);
        int i = 0;
        int j = arr.length - 1;

        while (i < j) {
            if (arr[i] + arr[j] == target) {
                return true;
            } else if (arr[i] + arr[j] < target) {
                i++;
            } else {
                j--;
            }
        }
        return false;
    }

    /**
     * Approach 3: Hashing (Existence)
     *
     * Algorithm:
     * - Iterate and maintain a HashSet of seen numbers.
     * - For each x, if target - x is in set, return "YES"; else add x.
     *
     * Time Complexity: O(n) average
     * Space Complexity: O(n)
     *
     * @param arr    input array
     * @param target target sum
     * @return "true" if a pair exists, otherwise "false"
     */
    public static boolean existsHashing(int[] arr, int target) {
        Set<Integer> set = new HashSet<>();

        for (int x : arr) {
            if (set.contains(target - x)) {
                return true;
            } else {
                set.add(x);
            }
        }
        return false;
    }

    // =========================
    // Variant 2: Indices {-1, -1} if not found
    // =========================

    /**
     * Approach 1: Brute Force (Indices)
     *
     * Algorithm:
     * - Try all pairs (i, j) with i < j. If arr[i] + arr[j] == target, return {i,
     * j}.
     *
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     *
     * @param arr    input array
     * @param target target sum
     * @return pair of indices {i, j} or {-1, -1} if none
     */
    public static int[] twoSumBrute(int[] arr, int target) {
        // TODO: implement
        int[] res = new int[] { -1, -1 };
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    res[0] = i;
                    res[1] = j;
                    break;
                }
            }
            if (res[0] != -1) {
                break;
            }
        }
        return res;
    }

    /**
     * Approach 2: Sorting + Two Pointers (Indices)
     *
     * Algorithm:
     * - Build array of (value, index) pairs and sort by value.
     * - Use two pointers on the sorted values; return original indices when sum
     * hits target.
     *
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     *
     * @param arr    input array
     * @param target target sum
     * @return original indices {i, j} or {-1, -1}
     */
    public static int[] twoSumTwoPointers(int[] arr, int target) {

        int[] res = new int[] { -1, -1 };

        int n = arr.length;
        Pair[] pairs = new Pair[n];
        for (int i = 0; i < n; i++)
            pairs[i] = new Pair(arr[i], i);
        Arrays.sort(pairs, Comparator.comparingInt(p -> p.val));

        int l = 0, r = n - 1;
        while (l < r) {
            long sum = (long) pairs[l].val + (long) pairs[r].val; // guard against overflow
            if (sum == target) {
                return new int[] { pairs[l].idx, pairs[r].idx };
            } else if (sum < target) {
                l++;
            } else {
                r--;
            }
        }
        return res;
    }

    /**
     * Approach 3: Hashing (Indices) – canonical
     *
     * Algorithm:
     * - Maintain a map value -> index.
     * - For each i, let need = target - arr[i]; if need in map, return
     * {map.get(need), i}; else put arr[i] -> i.
     * - Insert AFTER checking to avoid using the same element twice.
     *
     * Time Complexity: O(n) average
     * Space Complexity: O(n)
     *
     * @param arr    input array
     * @param target target sum
     * @return original indices {i, j} or {-1, -1}
     */
    public static int[] twoSumHashing(int[] arr, int target) {
        int[] res = new int[] { -1, -1 };

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(target - arr[i])) {
                res[0] = map.get(target - arr[i]);
                res[1] = i;
                break;
            } else {
                map.put(arr[i], i);
            }
        }

        return res;
    }

    /**
     * Approach 4: Binary Search on Sorted Values (Indices)
     *
     * Algorithm:
     * - Build (value, index) pairs sorted by value.
     * - For each i, binary search for target - pairs[i].val in (i+1..n-1).
     *
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     *
     * @param arr    input array
     * @param target target sum
     * @return original indices {i, j} or {-1, -1}
     */
    public static int[] twoSumBinarySearch(int[] arr, int target) {
        int n = arr.length;
        Pair[] pairs = new Pair[n];
        for (int i = 0; i < n; i++)
            pairs[i] = new Pair(arr[i], i);
        Arrays.sort(pairs, Comparator.comparingInt(p -> p.val));

        for (int i = 0; i < n; i++) {
            int need = target - pairs[i].val;
            int j = lowerBoundOnVal(pairs, i + 1, n - 1, need);
            if (j != -1 && pairs[j].val == need) {
                return new int[] { pairs[i].idx, pairs[j].idx };
            }
        }
        return new int[] { -1, -1 };
    }

    // =========================
    // Optional Helpers (stubbed or minimal)
    // =========================

    /**
     * Helper Pair structure for value-index after sorting.
     */
    private static class Pair {
        int val, idx;

        Pair(int v, int i) {
            this.val = v;
            this.idx = i;
        }
    }

    /**
     * lower_bound style helper on Pair[]. Searches for first index with value >=
     * key in [lo..hi].
     * Returns -1 if not found or if exact equality not met by caller check.
     *
     * Time: O(log n)
     * Space: O(1)
     */
    @SuppressWarnings("unused")
    private static int lowerBoundOnVal(Pair[] a, int lo, int hi, int key) {
        // TODO: implement if you choose to use Binary Search approach
        return -1;
    }
}
