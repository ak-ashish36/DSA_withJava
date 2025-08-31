package array;

import java.util.*;

/**
 * Problem:
 * Given an array of N integers, find all unique triplets [arr[a], arr[b], arr[c]]
 * such that i != j, j != k, k != i and arr[a] + arr[b] + arr[c] = 0.
 *
 * Coding Platforms:
 * - LeetCode (3Sum):
 *   https://leetcode.com/problems/3sum/
 * - GeeksForGeeks:
 *   https://www.geeksforgeeks.org/find-triplets-array-whose-sum-equal-zero/
 * - Coding Ninjas:
 *   https://www.codingninjas.com/studio/problems/three-sum_6922132
 *
 * Notes:
 * - Triplets must be unique (no duplicates in output).
 * - Optimal approach: sort + two-pointers, widely asked in FAANG interviews.
 */
public class ThreeSum {

    public static void main(String[] args) {
        int[][] tests = {
            {-1, 0, 1, 2, -1, -4}, // [[-1, -1, 2], [-1, 0, 1]]
            {0, 0, 0, 0},          // [[0,0,0]]
            {1, 2, -2, -1},        // []
            {-2, 0, 1, 1, 2}       // [[-2,0,2], [-2,1,1]]
        };

        for (int[] arr : tests) {
            System.out.println("Input: " + Arrays.toString(arr));
            System.out.println("Brute: " + threeSumBrute(arr));
            System.out.println("Optimal: " + threeSumOptimal(arr));
            System.out.println();
        }
    }

    /**
     * Approach 1: Brute Force
     * - Check all triplets (i,j,k).
     * - If sum = 0, add to a Set (to avoid duplicates).
     * Time: O(n^3), Space: O(n^2).
     */
    public static List<List<Integer>> threeSumBrute(int[] nums) {
        Set<List<Integer>> set = new HashSet<>();
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> triplet = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(triplet);
                        set.add(triplet);
                    }
                }
            }
        }
        return new ArrayList<>(set);
    }

    /**
     * Approach 1.1: Brute Force (Optimised)
     * - Check all triplets (i,j,k).
     * - If sum = 0, add to a Set (to avoid duplicates).
     * Time: O(n^3), Space: O(n^2).
     */
    public static List<List<Integer>> threeSumBruteOptimized(int n, int[] arr) {
        Set<List<Integer>> st = new HashSet<>();

        for (int i = 0; i < n; i++) {
            Set<Integer> hashset = new HashSet<>();
            for (int j = i + 1; j < n; j++) {
                //Calculate the 3rd element:
                int third = -(arr[i] + arr[j]);

                //Find the element in the set:
                if (hashset.contains(third)) {
                    List<Integer> temp = Arrays.asList(arr[i], arr[j], third);
                    temp.sort(null);
                    st.add(temp);
                }
                hashset.add(arr[j]);
            }
        }

        // store the set elements in the answer:
        List<List<Integer>> ans = new ArrayList<>(st);
        return ans;
    }

    /**
     * Approach 2: Optimal (Sort + Two Pointers)
     * - Sort the array.
     * - Fix one element nums[i].
     * - Use two pointers (l, r) to find pairs that sum = -nums[i].
     * - Skip duplicates carefully.
     * Time: O(n^2), Space: O(1) (ignoring output).
     */
    public static List<List<Integer>> threeSumOptimal(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // skip duplicate i

            int l = i + 1, r = n - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];

                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));

                    l++;
                    r--;

                    // skip duplicates for l and r
                    while (l < r && nums[l] == nums[l - 1]) l++;
                    while (l < r && nums[r] == nums[r + 1]) r--;
                } else if (sum < 0) {
                    l++;
                } else {
                    r--;
                }
            }
        }
        return res;
    }
}
