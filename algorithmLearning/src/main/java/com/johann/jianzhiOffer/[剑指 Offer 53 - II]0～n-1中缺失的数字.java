package com.johann.jianzhiOffer;
//一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出
//这个数字。 
//
// 
//
// 示例 1: 
//
// 输入: [0,1,3]
//输出: 2
// 
//
// 示例 2: 
//
// 输入: [0,1,2,3,4,5,6,7,9]
//输出: 8 
//
// 
//
// 限制： 
//
// 1 <= 数组长度 <= 10000 
//
// Related Topics 位运算 数组 哈希表 数学 二分查找 👍 319 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJianzhi053II {
    /**
     * 位运算
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int xor = 0;
        int n = nums.length + 1;
        for (int i = 0; i < n - 1; i++) {
            xor ^= nums[i];
        }
        for (int i = 0; i <= n - 1; i++) {
            xor ^= i;
        }
        return xor;
    }

    /**
     * 直接遍历
     * 时间O(n),空间O(1)
     */
    public int missingNumber2(int[] nums) {
        for (int i = 0;i < nums.length; i++) {
//            if (nums[i] > i) {
//                return nums[i]-1;
//            }else if (nums[i] < i) {
//                return nums[i]+1;
//            }
            if (nums[i] != i) {
                return i;
            }
        }
        return nums.length;
    }

    public static void main(String[] args) {
        int[] nums = {0,1,2,3,4,6};
        new SolutionJianzhi053II().missingNumber(nums);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
