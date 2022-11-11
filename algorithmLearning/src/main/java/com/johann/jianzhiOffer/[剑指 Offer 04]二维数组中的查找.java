package com.johann.jianzhiOffer;
//在一个 n * m 的二维数组中，每一行都按照从左到右 非递减 的顺序排序，每一列都按照从上到下 非递减 的顺序排序。请完成一个高效的函数，输入这样的一个二
//维数组和一个整数，判断数组中是否含有该整数。 
//
// 
//
// 示例: 
//
// 现有矩阵 matrix 如下： 
//
// 
//[
//  [1,   4,  7, 11, 15],
//  [2,   5,  8, 12, 19],
//  [3,   6,  9, 16, 22],
//  [10, 13, 14, 17, 24],
//  [18, 21, 23, 26, 30]
//]
// 
//
// 给定 target = 5，返回 true。 
//
// 给定 target = 20，返回 false。 
//
// 
//
// 限制： 
//
// 0 <= n <= 1000 
//
// 0 <= m <= 1000 
//
// 
//
// 注意：本题与主站 240 题相同：https://leetcode-cn.com/problems/search-a-2d-matrix-ii/ 
//
// Related Topics 数组 二分查找 分治 矩阵 👍 818 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionJianzhi004 {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        //数据类型[][]数组名=new 数据类型[外维数组容量][内维数组容量]; [m行][n列]
        //int[][] arr = new int[2][3];
        int m,n;
        if (matrix == null || (m = matrix.length)==0 || matrix[0]==null || (n = matrix[0].length)==0){
            return false;
        }
        //从二维数组的左下角 matrix[m-1][0] 开始遍历
        for(int i=m-1,j=0;i>=0&&j<n;){
            int temp = matrix[i][j];
            //如果左下角的数据大于目标值，则行索引向上走一个格
            if (matrix[i][j] > target){
                i--;
            //如果左下角的数据小于目标值，则列索引向右走一个格
            }else if(matrix[i][j] < target){
                j++;
            }else {
                System.out.println("命中，位于 "+(i+1)+" 行, "+(j+1)+" 列");
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        /**
         * 1，定义方法一：数据类型[][]数组名=new 数据类型[外维数组容量][内维数组容量];
         *     //定义一个一维数组，长度为 2 ，在这个一维数组中存放子数组，子数组的默认长度是 3
         *     int[][] arr = new int[2][3];
         *     int[][] arr = new int[2][];
         *     //1.1，赋值方式一：
         *     // 采用这种方式赋值时，二维数组的 [行索引][列索引] 不能超出定义的 [2][3]
         *     //第1个内维数组
         *     arr [0][0]=90;
         *     arr [0][1]=80;
         *     arr [0][2]=70;
         *     //第2个内维数组
         *     arr [1][0]=90;
         *     arr [1][1]=90;
         *     arr [1][2]=80;
         *     //1.2，赋值方式二：
         *     // 采用这种方式赋值时，一维数组的子数组的长度，可以重新指定，不再局限于定义时预设的 [3]
         *     arr[0] = new int[]{0,1,2,3};
         *     arr[1] = new int[]{10,11,12,13};
         *
         * 2，定义方法二：数据类型[][] 数组名 = {{},{}...};
         *     int[][] matrix = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
         *
         * 3，定义方法三：数据类型[][] 数组名 = new 数据类型[][]{{},{}...};
         *     int[][] matrix = new int[][]{{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
         */
        int[][] matrix = new int[][]{{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
        System.out.println(matrix.length+" * "+matrix[0].length);
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + "\t");
            }
            System.out.println();
        }
        System.out.println(new SolutionJianzhi004().findNumberIn2DArray(matrix,15));
    }
}
//leetcode submit region end(Prohibit modification and deletion)
