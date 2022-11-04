package com.johann.jianzhiOffer;
//给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素。
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [2,2,3,2]
//输出：3
// 
//
// 示例 2： 
//
// 
//输入：nums = [0,1,0,1,0,1,100]
//输出：100
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 3 * 10⁴ 
// -2³¹ <= nums[i] <= 2³¹ - 1 
// nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 
// 
//
// 
//
// 进阶：你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？ 
//
// 
//
// 
// 注意：本题与主站 137 题相同：https://leetcode-cn.com/problems/single-number-ii/ 
//
// Related Topics 位运算 数组 👍 109 👎 0


import java.util.HashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJianzhiII004 {
    public int singleNumber(int[] nums) {
        /**
         *  101
         *  101
         *  101
         *   10
         *   10
         *   10
         * 1000
         *
         */
        int res = 0;
        for (int i = 0; i < 32; i++) {
            //最低有效位(0/1)累加值
            int sumLSD = 0;
            for (int anInt : nums) {
                sumLSD += ((anInt >> i) & 1);
            }
            /**
             * 最低有效位(0/1)累加值，将累加值与3取模。
             * 如果模是 0，说明所求的特定数在该位上是 0；
             * 如果模是 1，说明所求的特定数在该位上是 1。此时加上当前位的实值。
             */
            if (sumLSD % 3 == 1) {
                res |= (1 << i);
                //res += (1 << i);
            }
        }
        return res;
    }

    /**
     * 笨比方法
     *
     * @param nums
     * @return
     */
    public int singleNumber2(int[] nums) {
        Map<Integer, Integer> maps = new HashMap(nums.length);
        for (int i = 0; i < nums.length; i++) {
            if (maps.get(nums[i]) == null) {
                maps.put(nums[i], 1);
            } else {
                maps.put(nums[i], 3);
            }
        }
        Integer result = null;
        for (int i = 0; i < nums.length; i++) {
            if (maps.get(nums[i]) == 1) {
                return nums[i];
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] nums = {2,2,2147483647,2};
        System.out.println(new SolutionJianzhiII004().singleNumber(nums));
    }
}
//leetcode submit region end(Prohibit modification and deletion)
