package array;

import java.util.*;

/**
 * Problem:
 * Given an array of intervals [start, end], merge all overlapping intervals
 * and return an array of non-overlapping intervals covering all intervals.
 *
 * Coding Platforms:
 * - LeetCode (Merge Intervals):
 *   https://leetcode.com/problems/merge-intervals/
 * - GeeksForGeeks:
 *   https://www.geeksforgeeks.org/merging-intervals/
 * - Coding Ninjas:
 *   https://www.codingninjas.com/studio/problems/merge-intervals_699917
 *
 * Notes:
 * - Classic interval problem; widely asked in FAANG interviews.
 * - Optimal solution: sort by start, merge as we go.
 */
public class MergeIntervals {

    public static void main(String[] args) {
        int[][][] tests = {
            { {1,3}, {2,6}, {8,10}, {15,18} }, // [[1,6],[8,10],[15,18]]
            { {1,4}, {4,5} },                  // [[1,5]]
            { {1,10}, {2,6}, {8,9} },          // [[1,10]]
            { {1,2}, {3,4}, {5,6} },           // [[1,2],[3,4],[5,6]]
            { {1,4}, {0,4} }                   // [[0,4]]
        };

        for (int[][] intervals : tests) {
            System.out.println("Input: " + Arrays.deepToString(intervals));
            System.out.println("Brute: " + Arrays.deepToString(mergeBrute(intervals)));
            System.out.println("Optimal: " + Arrays.deepToString(mergeOptimal(intervals)));
            System.out.println();
        }
    }

    /**
     * Approach 1: Brute Force
     * - Sort by start.
     * - Use nested loops to repeatedly merge overlapping intervals.
     * - Store merged intervals in a list, checking overlaps one by one.
     * Time: O(n^2), Space: O(n).
     */
    public static int[][] mergeBrute(int[][] intervals) {
        Arrays.sort(intervals, (a,b) -> a[0] - b[0]);
        boolean[] merged = new boolean[intervals.length];
        List<int[]> res = new ArrayList<>();

        for (int i = 0; i < intervals.length; i++) {
            if (merged[i]) continue;
            int start = intervals[i][0];
            int end = intervals[i][1];

            for (int j = i+1; j < intervals.length; j++) {
                if (intervals[j][0] <= end) {
                    end = Math.max(end, intervals[j][1]);
                    merged[j] = true;
                }
            }
            res.add(new int[]{start, end});
        }
        return res.toArray(new int[res.size()][]);
    }

    /**
     * Approach 2: Optimal (Sort + Merge on the fly)
     * - Sort intervals by start.
     * - Iterate, merging current interval with the last added if overlapping.
     * - Otherwise add as new interval.
     * Time: O(n log n), Space: O(n).
     */
    public static int[][] mergeOptimal(int[][] intervals) {
        Arrays.sort(intervals, (a,b) -> a[0] - b[0]);
        List<int[]> res = new ArrayList<>();

        for (int[] interval : intervals) {
            if (res.isEmpty() || res.get(res.size()-1)[1] < interval[0]) {
                res.add(interval);
            } else {
                res.get(res.size()-1)[1] = Math.max(res.get(res.size()-1)[1], interval[1]);
            }
        }
        return res.toArray(new int[res.size()][]);
    }
}
