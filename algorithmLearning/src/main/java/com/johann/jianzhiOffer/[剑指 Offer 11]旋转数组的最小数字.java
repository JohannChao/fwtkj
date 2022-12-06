package com.johann.jianzhiOffer;
//把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
//
// 给你一个可能存在 重复 元素值的数组 numbers ，它原来是一个升序排列的数组，并按上述情形进行了一次旋转。请返回旋转数组的最小元素。例如，数组 [3
//,4,5,1,2] 为 [1,2,3,4,5] 的一次旋转，该数组的最小值为 1。 
//
// 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], 
//..., a[n-2]] 。 
//
// 
//
// 示例 1： 
//
// 
//输入：numbers = [3,4,5,1,2]
//输出：1
// 
//
// 示例 2： 
//
// 
//输入：numbers = [2,2,2,0,1]
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// n == numbers.length 
// 1 <= n <= 5000 
// -5000 <= numbers[i] <= 5000 
// numbers 原来是一个升序排序的数组，并进行了 1 至 n 次旋转 
// 
//
// 注意：本题与主站 154 题相同：https://leetcode-cn.com/problems/find-minimum-in-rotated-
//sorted-array-ii/ 
//
// Related Topics 数组 二分查找 👍 726 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJianzhi011 {


    /**【数组中元素不重复 https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/description/】
     * 该题目的条件是，数组中可能存在重复的元素
     * 如果将题目条件变更为“数组中中的元素不重复”，此时的题解如下
     * @param nums
     * @return
     */
    public int findMin(int[] nums){
        if (nums.length == 1){
            return nums[0];
        }else {
            int right = nums.length-1,left = 0;
            while(left < right) {
                int mid = left+(right-left)/2;
                // 如果二分中间节点的元素，小于最右端的元素，则此时mid右侧的元素处于右半段的范围，不符合条件，舍去。
                // 最右侧边界变更为 mid（mid可能是我们查找的最小值）
                if (nums[mid] < nums[right]) {
                    right = mid;
                }else {
                    // 如果二分中间节点的元素，大于最右端的元素，则此时mid左侧的元素处于左半段的范围不符合条件，舍去。
                    // 最左侧边界变更为 mid+1
                    left = mid+1;
                }
            }
            return nums[left];
        }
    }

    /**
     * 二分查找法
     *
     * @param numbers
     * @return
     */
    public int minArray(int[] numbers) {
        if (numbers.length == 1){
            return numbers[0];
        }else {
            int right = numbers.length-1,left = 0;
            while(left < right) {
                int mid = left+(right-left)/2;
                // 如果二分中间节点mid的元素，小于最右端right的元素，此时最小值在mid的左侧，mid右侧的元素处于右半段的范围，不符合条件，舍去。
                // 最右侧边界变更为 mid（mid可能是我们查找的最小值）
                if (numbers[mid] < numbers[right]) {
                    right = mid;
                }else if (numbers[mid] > numbers[right]) {
                    // 如果二分中间节点mid的元素，大于最右端right的元素，此时最小值在mid的右侧，mid左侧的元素处于左半段的范围不符合条件，舍去。
                    // 最左侧边界变更为 mid+1
                    left = mid+1;
                }else {
                    // 如果二分中间节点的元素，等于最右端的元素，此时无法判断最小值是在mid的左侧还是右侧，因此只能缩小一点点最右侧leght的范围
                    right -= 1;
                }
            }
            return numbers[left];
        }
    }

    /**
     * 笨方法，从头到尾遍历
     * 时间O(n),空间O(1)
     * @param numbers
     * @return
     */
    public int minArray2(int[] numbers){
        int i = 0;
        for (i = 0;i < numbers.length; i++) {
            if (i+1 >= numbers.length) {
                i = 0;
                break;
            }
            if (numbers[i] > numbers[i+1]) {
                i += 1;
                break;
            }
        }
        return numbers[i];
    }

    public static void main(String[] args) {
        int[] nums = {3,3,3,1,3};
        new SolutionJianzhi011().minArray(nums);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
