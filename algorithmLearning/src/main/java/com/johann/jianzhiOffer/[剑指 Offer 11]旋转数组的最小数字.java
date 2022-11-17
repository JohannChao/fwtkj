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
    /**
     * 二分查找法
     *
     * @param numbers
     * @return
     */
    public int minArray(int[] numbers) {
        if (numbers.length == 1) {
            return numbers[0];
        }else {
            if (numbers[0] <= numbers[numbers.length-1]) {
                return numbers[0];
            }else {
                int left = 0,right = numbers.length-1,bingo = 0;
                // FIXME
                while (left <= right){
                    int mid = (left+right)/2;
                    if (numbers[0] > numbers[mid]) {
                        bingo = mid;
                        right = mid-1;
                    }else {
                        left = mid+1;
                    }
                }
                return numbers[bingo];
            }
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
}
//leetcode submit region end(Prohibit modification and deletion)
