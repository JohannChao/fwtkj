package com.johann.jianzhiOffer;//找出数组中重复的数字。
//
// 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。
//请找出数组中任意一个重复的数字。 
//
// 示例 1： 
//
// 输入：
//[2, 3, 1, 0, 2, 5, 3]
//输出：2 或 3 
// 
//
// 
//
// 限制： 
//
// 2 <= n <= 100000 
//
// Related Topics 数组 哈希表 排序 👍 997 👎 0


import java.util.HashMap;
import java.util.HashSet;

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJianzhi03 {
    /**
     * 鸽巢排序
     * 时间复杂度O(n),空间复杂度O(n)
     */
    public int findRepeatNumber(int[] nums) {
        /**
         * nums，一个坑（nums索引号）里面对应一个萝卜（nums索引对应的值），不同的坑里面可能有相同的萝卜
         * verify，一个萝卜（verify索引号）指定一个是否有坑位标识（verify索引对应的值）【1,该萝卜已经有坑位号；2，该萝卜没有坑位号】
         * 如果这个萝卜已经有坑位号了，直接将萝卜返回。
         *
         * 其实这是鸽巢排序的一种变形，如果是鸽巢排序的话，verify数组中，索引对应的值就不是坑位标识了，而是坑位数量（截止当前遍历位置，该萝卜对应了几个坑）
         */
        int[] verify = new int[nums.length];
        for (int num : nums) {
            if (verify[num] > 0){
                return num;
            }else {
                verify[num] = 1;
            }
        }
        return -1;
    }

    /**
     * 交换元素，筛选重复元素
     * 时间复杂度O(n^2),空间复杂度O(1)
     */
    public int findRepeatNumber2(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            /**
             * 如果当前索引和索引元素值不相同，则一直交换元素，直到满足该条件
             * 例如 :
             *     int[] nums = [2, 3, 1, 0, 2, 5, 3]
             *     nums[0] = 2，不满足 nums[0] == 0；此时，nums[0] != nums[2]，交换 索引0和索引2的元素
             *     交换后的数组为 [1, 3, 2, 0, 2, 5, 3]
             *     nums[0] = 1；此时，nums[0] != nums[1]，继续交换 索引0和索引1的元素
             *     交换后的数组为 [3, 1, 2, 0, 2, 5, 3]
             *     nums[0] = 3；此时，nums[0] != nums[3]，继续交换 索引0和索引3的元素
             *     交换后的数组为 [0, 1, 2, 3, 2, 5, 3]
             *     此时 nums[0] = 0; i++, i = 1
             *
             *     nums[1] = 1，i++, i = 2
             *
             *     nums[2] = 2，i++, i = 3
             *
             *     nums[3] = 3，i++, i = 4
             *
             *     nums[4] = 2，不满足 nums[4] == 4；此时，nums[4] = nums[2]，出现重复元素，终止循环，返回重复元素。
             */
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]){
                    return nums[i];
                }else {
                    swap(nums,i,nums[i]);
                }
            }
        }
        return -1;
    }

    public void swap(int[] arr,int i,int j){
        int m = arr[i];
        arr[i] = arr[j];
        arr[j] = m;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,5,2,4,8,9,5,1};
        System.out.println(new SolutionJianzhi03().findRepeatNumber(nums));
        System.out.println(new SolutionJianzhi03().findRepeatNumber2(nums));
    }
}
//leetcode submit region end(Prohibit modification and deletion)
