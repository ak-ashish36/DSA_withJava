package array;

import java.util.*;

public class MissingNumberFinderArrayList {

    public static void main(String[] args) {
        // Test cases
        ArrayList<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 4, 5)); // Missing 3
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 6, 7, 8, 9, 10)); // Missing 5
        ArrayList<Integer> list3 = new ArrayList<>(); // Edge: Missing 1
        ArrayList<Integer> list4 = new ArrayList<>(Arrays.asList(2, 3, 4)); // Missing 1
        ArrayList<Integer> list5 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)); // Missing 6

        ArrayList<ArrayList<Integer>> testCases = new ArrayList<>();
        testCases.add(list1);
        testCases.add(list2);
        testCases.add(list3);
        testCases.add(list4);
        testCases.add(list5);

        for (int i = 0; i < testCases.size(); i++) {
            ArrayList<Integer> arr = testCases.get(i);

            System.out.println("Test case " + (i + 1));
            System.out.println("ArrayList = " + arr);
            System.out.println("Missing (Sum Formula): " + findMissingSum(arr));
            System.out.println("Missing (XOR): " + findMissingXOR(arr));
            System.out.println("Missing (Boolean): " + findMissingBoolean(arr));
            System.out.println("-----");
        }
    }

    /**
     * Approach 1: Sum Formula
     * -----------------------
     * Formula: ExpectedSum = N*(N+1)/2
     * Missing = ExpectedSum - ActualSum
     *
     * LeetCode Link: https://leetcode.com/problems/missing-number/
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int findMissingSum(ArrayList<Integer> arr) {
        int n = arr.size() + 1; // size of array + 1 (missing number)
        int sum = (n * (n + 1)) / 2;

        int arrSum = 0;
        for (int i : arr) {
            arrSum += i;
        }
        return sum - arrSum;
    }

    /**
     * Approach 2: XOR Trick
     * ---------------------
     * Idea:
     * XOR of two same numbers is always 0 i.e. a ^ a = 0.
     * XOR of a number with 0 will result in the number itself i.e. 0 ^ a = a.
     * 
     * xor1 = 1^2^.......^N
     * xor2 = 1^2^......^N (contains all the numbers except the missing one).
     * xor1 ^ xor2 = (1^1)^(2^2)^........^(missing Number)^.....^(N^N)
     * 
     * LeetCode Link: https://leetcode.com/problems/missing-number/
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int findMissingXOR(ArrayList<Integer> arr) {
        int xor1 = 0, xor2 = 0;
        for (int i = 0; i < arr.size(); i++) {
            xor1 ^= arr.get(i);
            xor2 ^= i + 1;
        }
        xor2 ^= arr.size() + 1; // Include the missing number in xor2

        return xor1 ^ xor2;
    }

    /**
     * Approach 3: Boolean/Hashing
     * ---------------------------
     * Idea:
     * - Use boolean array to mark presence of each element.
     * - First false index gives the missing number.
     *
     * GeeksforGeeks Link: https://www.geeksforgeeks.org/find-the-missing-number/
     *
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static int findMissingBoolean(ArrayList<Integer> arr) {
        int N = arr.size() + 1; // size of array + 1 (missing number)
        int present[] = new int[N + 1];

        for (int num : arr) {
            present[num - 1]++;
        }

        for (int i = 0; i < N + 1; i++) {
            if (present[i] == 0) {
                return i + 1;
            }
        }

        return -1;
    }
}
