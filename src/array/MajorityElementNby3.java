package array;

import java.util.*;

/**
 * Problem:
 * Given an array of N integers. Find the elements that appear more than N/3
 * times.
 * If no such element exists, return an empty list.
 *
 * Coding Platforms:
 * - LeetCode (Majority Element II):
 * https://leetcode.com/problems/majority-element-ii/
 * - GeeksForGeeks:
 * https://www.geeksforgeeks.org/n3-repeated-number-array-o1-space/
 * - Coding Ninjas:
 * https://www.codingninjas.com/studio/problems/majority-element-ii_893027
 *
 * Notes:
 * - At most 2 elements can appear more than n/3 times.
 * - Classic interview extension of Boyer-Moore Voting Algorithm.
 */
public class MajorityElementNby3 {

    public static void main(String[] args) {
        int[][] tests = {
                { 3, 2, 3 }, // [3]
                { 1 }, // [1]
                { 1, 2 }, // [1,2] (both appear once, > n/3 = 0)
                { 2, 2, 1, 3 }, // [2]
                { 1, 1, 1, 3, 3, 2, 2, 2 }, // [1,2]
                { 1, 2, 3, 4 }, // [] (none > n/3)
        };

        for (int[] arr : tests) {
            System.out.println("Input: " + Arrays.toString(arr));
            System.out.println("Brute: " + majorityBrute(arr));
            System.out.println("Optimal (Boyer-Moore): " + majorityOptimal(arr));
            System.out.println();
        }
    }

    /**
     * Approach 1: Brute force (HashMap)
     * - Count frequencies using HashMap.
     * - Collect keys where freq > n/3.
     * Time: O(n), Space: O(n).
     */
    public static List<Integer> majorityBrute(int[] nums) {
        List<Integer> res = new ArrayList<>();
        Map<Integer, Integer> freq = new HashMap<>();

        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            if (e.getValue() > nums.length / 3) {
                res.add(e.getKey());
            }
        }
        return res;
    }

    /**
     * Approach 2: Optimal (Boyer-Moore Voting Algorithm extension)
     * - There can be at most 2 majority elements (> n/3).
     * - Maintain 2 candidates and their counts.
     * - First pass: find possible candidates.
     * - Second pass: verify counts.
     * Time: O(n), Space: O(1).
     */
    public static List<Integer> majorityOptimal(int[] nums) {
        List<Integer> res = new ArrayList<>();

        // 1st pass: find potential candidates
        int count1 = 0, count2 = 0;
        int cand1 = 0, cand2 = 0;

        for (int num : nums) {
            if (num == cand1) {
                count1++;
            } else if (num == cand2) {
                count2++;
            } else if (count1 == 0) {
                cand1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                cand2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        // 2nd pass: verify actual counts
        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == cand1)
                count1++;
            else if (num == cand2)
                count2++;
        }

        if (count1 > nums.length / 3)
            res.add(cand1);
        if (count2 > nums.length / 3)
            res.add(cand2);

        return res;
    }
}
