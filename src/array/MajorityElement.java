import java.util.*;

/**
 * Coding Platforms (official problem pages):
 * - LeetCode (Majority Element):
 * https://leetcode.com/problems/majority-element/
 * - GeeksForGeeks (Majority Element):
 * https://www.geeksforgeeks.org/problems/majority-element-1587115620/1
 * - Coding Ninjas (Majority Element):
 * https://www.codingninjas.com/studio/problems/majority-element_842495
 * - CodeChef (Practice; search "majority element"):
 * https://www.codechef.com/practice
 *
 * Problem:
 * Given an array of N integers, return an element that occurs more than N/2
 * times (the majority element).
 *
 * Assumptions/Notes:
 * - Typically, the classic problem guarantees a majority exists; if not
 * guaranteed, verify candidate before returning.
 * - If there is no such element, return a sentinel (e.g., Integer.MIN_VALUE) or
 * -1 per platform specification.
 * - Boyer–Moore Voting is the standard O(n) time, O(1) space approach.
 */
public class MajorityElement {

    public static void main(String[] args) {
        int[][] tests = {
                { 3, 2, 3 }, // majority = 3
                { 2, 2, 1, 1, 1, 2, 2 }, // majority = 2
                { 1, 1, 1, 2, 3, 4, 1, 1 }, // majority = 1
                { 6, 5, 5 }, // no strict majority (> N/2) unless defined; here N=3, 5 occurs 2 -> is
                             // majority
                { 1, 2, 3, 4, 5, 6, 7 }, // no majority (if not guaranteed)
                { 9, 9, 9, 9 }, // all same
                { 0, 0, 1, 0, 2, 0, 0 }, // majority = 0
        };
        // For “not guaranteed” cases, expected may be -1 (or a sentinel).
        int[] expected = {
                3,
                2,
                1,
                5,
                -1, // no majority
                9,
                0
        };

        System.out.println("==== Majority Element (> N/2) ====");
        for (int t = 0; t < tests.length; t++) {
            int[] arr = Arrays.copyOf(tests[t], tests[t].length);
            System.out.println("Test #" + (t + 1));
            System.out.println("Input:    " + Arrays.toString(arr));
            System.out.println("Expected: " + expected[t]);

            int a1 = majorityBrute(arr);
            int a2 = majorityHashing(arr);
            int a3 = majoritySorting(arr);
            int a4 = majorityBoyerMoore(arr);

            System.out.println("[Brute]       -> " + a1);
            System.out.println("[Hashing]     -> " + a2);
            System.out.println("[Sorting]     -> " + a3);
            System.out.println("[BoyerMoore]  -> " + a4);
            System.out.println();
        }
    }

    // =========================
    // Approach 1: Brute Force (O(n^2), O(1))
    // =========================

    /**
     * Approach 1: Brute Force
     *
     * Algorithm:
     * - For each element arr[i], count its occurrences in O(n).
     * - If any count > n/2, return that element.
     *
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     *
     * @param arr input array
     * @return the majority element if exists; else -1
     */
    public static int majorityBrute(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int freq = 0;
            for (int j = 0; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    freq++;
                    if (freq > (arr.length / 2)) {
                        return arr[i];
                    }
                }
            }
        }

        return -1;
    }

    // =========================
    // Approach 2: Hashing / Counting (O(n), O(n))
    // =========================

    /**
     * Approach 2: Hashing / Counting
     *
     * Algorithm:
     * - Use a HashMap to count frequencies.
     * - Return the key with frequency > n/2 if any.
     *
     * Time Complexity: O(n) average
     * Space Complexity: O(n)
     *
     * @param arr input array
     * @return the majority element if exists; else -1
     */
    public static int majorityHashing(int[] arr) {
        Map<Integer, Integer> countMap = new HashMap<>();

        for (int num : arr) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
            if (countMap.get(num) > arr.length / 2) {
                return num;
            }
        }
        return -1;
    }

    // =========================
    // Approach 3: Sorting (O(n log n), O(1) or O(n))
    // =========================

    /**
     * Approach 3: Sorting
     *
     * Algorithm:
     * - Sort the array. If a majority element exists (> n/2), it must occupy the
     * middle position.
     * - Candidate = arr[n/2]; optionally verify by counting to handle “not
     * guaranteed” cases.
     *
     * Time Complexity: O(n log n)
     * Space Complexity: O(1) to O(n) depending on sorting implementation
     *
     * @param arr input array
     * @return the majority element if exists; else -1
     */
    public static int majoritySorting(int[] arr) {
        Arrays.sort(arr);
        int candidate = arr[arr.length / 2];
        int count = 0;
        for (int num : arr) {
            if (num == candidate) {
                count++;
            }
        }
        if (count > arr.length / 2) {
            return candidate;
        }
        return -1;
    }

    // =========================
    // Approach 4: Boyer–Moore Voting (O(n), O(1))
    // =========================

    /**
     * Approach 4: Boyer–Moore Voting Algorithm
     *
     * Algorithm:
     * - Maintain a candidate and a counter.
     * - Iterate:
     * - If count == 0, set candidate = current element.
     * - If current == candidate, count++, else count--.
     * - Candidate at end is majority if one exists; if not guaranteed, verify by
     * counting.
     *
     * Why it works:
     * - Pairs off different elements; the majority element cannot be fully canceled
     * out.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param arr input array
     * @return the majority element if exists; else -1
     */
    public static int majorityBoyerMoore(int[] arr) {
        // TODO: implement
        int count = 0;
        int candidate = 0;
        for (int num : arr) {
            if (count == 0) {
                candidate = num;
                count++;
            } else if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }
        int cnt1 = 0;
        for (int num : arr) {
            if (num == candidate) {
                cnt1++;
            }
        }
        return -1;
    }

    // =========================
    // Optional Helpers
    // =========================

    /**
     * Verify if x is a strict majority (> n/2).
     * Time: O(n), Space: O(1)
     */
    @SuppressWarnings("unused")
    private static boolean isMajority(int[] arr, int x) {
        // TODO: implement if you choose to verify candidate
        return false;
    }
}
