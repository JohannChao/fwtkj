package com.johann.jianzhiOffer;
//统计一个数字在排序数组中出现的次数。
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [5,7,7,8,8,10], target = 8
//输出: 2 
//
// 示例 2: 
//
// 
//输入: nums = [5,7,7,8,8,10], target = 6
//输出: 0 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 10⁵ 
// -10⁹ <= nums[i] <= 10⁹ 
// nums 是一个非递减数组 
// -10⁹ <= target <= 10⁹ 
// 
//
// 
//
// 注意：本题与主站 34 题相同（仅返回值不同）：https://leetcode-cn.com/problems/find-first-and-last-
//position-of-element-in-sorted-array/ 
//
// Related Topics 数组 二分查找 👍 373 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJianzhi053I {

    public int search(int[] nums, int target) {
        if (nums.length == 0) {
            return 0;
        }
        int left = binarySearch(nums,target,true);
        int right = binarySearch(nums,target,false);
        if (left <= right && left >= 0 && right <= nums.length - 1 && nums[left] == target && nums[right] == target) {
            return right-left+1;
        }
        return 0;
    }

    /**
     *  寻找目标值的左右边界，其中左边界是第一个大于等于目标值的索引，右边界是第一个大于目标值的索引
     * @param nums
     * @param target
     * @param leftFlag true，寻找左边界
     * @return
     */
    public int binarySearch(int[] nums, int target, boolean leftFlag) {
        int left = 0, right = nums.length - 1, ans = nums.length;
        if (leftFlag) {
            while (left <= right) {
                int mid = (left + right) / 2;
                if (nums[mid] >= target) {
                    right = mid - 1;
                    ans = mid;
                } else {
                    left = mid + 1;
                }
            }
        }else {
            while (left <= right) {
                int mid = (left + right) / 2;
                if (nums[mid] > target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                    ans = mid;
                }
            }
        }
        return ans;
    }

    /**
     * 二分查找，找出目标值的左边界，从左边界往右继续遍历同值个数
     * @param nums
     * @param target
     * @return
     */
    public int search2(int[] nums, int target) {
        if (nums.length == 0) {
            return 0;
        }
        int result = 0;
        int min = 0,max = nums.length-1,mid = 0,leftIndex = 0;
        // 二分查找，找出第一个大于等于目标值的索引位置
        while (min <= max){
            mid = (min+max)>>1;
            if (nums[mid] >= target){
                max = mid-1;
                leftIndex = mid;
            }else {
                min = mid+1;
            }
        }
        // 如果当前索引位置的数组值同目标值相同，还需要统计当前索引的前后同值数字的个数
        if (nums[leftIndex] == target){
            while (leftIndex < nums.length) {
                if (nums[leftIndex] != target){
                    return result;
                    //break;
                }
                leftIndex++;
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int [] nums = {0,1,2,2,3,3,3,3,4,4,4,4,4,4,6,7,8,9};
        new SolutionJianzhi053I().search2(nums,4);
        new SolutionJianzhi053I().search(nums,4);
    }

}
//leetcode submit region end(Prohibit modification and deletion)
