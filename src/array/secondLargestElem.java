package array;
import java.util.Arrays;

public class secondLargestElem {
    public static void main(String[] args) {
        System.out.println("=== Testing Second Largest Element Algorithms ===\n");
        
        // Test Case 1: Normal array with distinct elements
        testCase("Normal array with distinct elements", new int[]{12, 35, 1, 10, 34, 1});
        
        // Test Case 2: Array with two elements
        testCase("Array with two elements", new int[]{5, 10});
        
        // Test Case 3: Array with all same elements
        testCase("Array with all same elements", new int[]{7, 7, 7, 7});
        
        // Test Case 4: Array with duplicates of largest element
        testCase("Array with duplicates of largest", new int[]{10, 5, 10, 3, 8});
        
        // Test Case 5: Array in ascending order
        testCase("Array in ascending order", new int[]{1, 2, 3, 4, 5});
        
        // Test Case 6: Array in descending order
        testCase("Array in descending order", new int[]{10, 8, 6, 4, 2});
        
        // Test Case 7: Array with negative numbers
        testCase("Array with negative numbers", new int[]{-5, -1, -10, -3, -2});
        
        // Test Case 8: Array with mix of positive and negative
        testCase("Array with positive and negative", new int[]{-5, 10, -1, 3, 0});
        
        // Test Case 9: Array with minimum integer values
        testCase("Array with minimum integer values", new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE + 1, Integer.MIN_VALUE + 2});
        
        // Test Case 10: Array with maximum integer values
        testCase("Array with maximum integer values", new int[]{Integer.MAX_VALUE - 2, Integer.MAX_VALUE, Integer.MAX_VALUE - 1});
        
        // Test Case 11: Single element array (edge case)
        testCase("Single element array", new int[]{42});
        
        // Test Case 12: Empty array (edge case)
        testCase("Empty array", new int[]{});
        
        // Test Case 13: Null array (edge case)
        testCase("Null array", null);
        
        // Test Case 14: Large array with duplicates
        testCase("Large array with duplicates", new int[]{1, 5, 2, 5, 3, 5, 4, 5, 2, 1});
        
        // Test Case 15: Array where second largest appears multiple times
        testCase("Second largest appears multiple times", new int[]{10, 8, 8, 6, 8, 4});
    }
    
    /**
     * Helper method to test all approaches with a given test case
     * 
     * @param description description of the test case
     * @param arr input array to test
     */
    private static void testCase(String description, int[] arr) {
        System.out.println("Test: " + description);
        System.out.println("Array: " + (arr == null ? "null" : Arrays.toString(arr)));
        
        try {
            int[] arrCopy = arr == null ? null : arr.clone();
            int result1 = findSecondLargestUsingSorting(arrCopy);
            int result2 = findSecondLargestTwoPass(arr);
            int result3 = findSecondLargestSinglePass(arr);
            
            System.out.println("Approach 1 (Sorting): " + result1);
            System.out.println("Approach 2 (Two Pass): " + result2);
            System.out.println("Approach 3 (Single Pass): " + result3);
            
            // Verify all approaches give same result
            if (result1 == result2 && result2 == result3) {
                System.out.println("✓ All approaches agree");
            } else {
                System.out.println("✗ MISMATCH: Results differ!");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        
        System.out.println("----------------------------------------\n");
    }

    /**
     * Approach 1: Using Sorting
     * 
     * Algorithm:
     * 1. Sort the array in descending order
     * 2. Find the first element that is smaller than the maximum
     * 
     * Time Complexity: O(n log n) - due to sorting
     * Space Complexity: O(1) - if using in-place sorting like Arrays.sort()
     * 
     * Pros: Simple to implement and understand
     * Cons: Not optimal due to O(n log n) complexity, modifies original array
     * 
     * @param arr input array
     * @return second largest element, or -1 if not found
     */
    public static int findSecondLargestUsingSorting(int[] arr) {
        if (!isValidArray(arr)) return -1;
        Arrays.sort(arr); 
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] < arr[arr.length - 1]) {
                return arr[i];
            }
        }
        return -1;
    }

    /**
     * Approach 2: Two Pass Linear Scan
     * 
     * Algorithm:
     * 1. First pass: Find the maximum element
     * 2. Second pass: Find the largest element that is smaller than maximum
     * 
     * Time Complexity: O(n) - two passes through the array
     * Space Complexity: O(1) - only using constant extra space
     * 
     * Pros: Better than sorting, easy to understand
     * Cons: Requires two passes through the array
     * 
     * @param arr input array
     * @return second largest element, or -1 if not found
     */
    public static int findSecondLargestTwoPass(int[] arr) {
        if (!isValidArray(arr)) return -1;
        int max = Integer.MIN_VALUE;
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        int secondLargest = Integer.MIN_VALUE;
        for (int num : arr) {
            if (num > secondLargest && num < max) {
                secondLargest = num;
            }
        }
        return secondLargest == Integer.MIN_VALUE ? -1 : secondLargest;
    }

    /**
     * Approach 3: Single Pass Linear Scan (Optimal)
     * 
     * Algorithm:
     * 1. Initialize largest and secondLargest variables
     * 2. Traverse array once, updating both variables as needed
     * 3. Handle cases where current element is larger than largest or between largest and secondLargest
     * 
     * Time Complexity: O(n) - single pass through the array
     * Space Complexity: O(1) - only using constant extra space
     * 
     * Pros: Most efficient approach, single traversal
     * Cons: Slightly more complex logic to handle edge cases
     * 
     * Edge Cases to Handle:
     * - Array with less than 2 elements
     * - All elements are same
     * - Array with duplicates of maximum element
     * 
     * @param arr input array
     * @return second largest element, or -1 if not found
     */
    public static int findSecondLargestSinglePass(int[] arr) {
        if (!isValidArray(arr)) return -1;
        
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        
        for(int num : arr) {
            if(num > largest) {
                secondLargest = largest;
                largest = num;
            } else if(num > secondLargest && num < largest) {
                secondLargest = num;
            }
        }
        
        // If secondLargest was never updated, no second largest exists
        return secondLargest == Integer.MIN_VALUE ? -1 : secondLargest;
    }

    /**
     * Helper method to validate input array
     * 
     * @param arr input array
     * @return true if array is valid for finding second largest
     */
    private static boolean isValidArray(int[] arr) {
        return arr != null && arr.length >= 2;
    }
}
