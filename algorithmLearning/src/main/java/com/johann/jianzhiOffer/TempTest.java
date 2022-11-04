package com.johann.jianzhiOffer;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TempTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class TempTest {

    public static void main(String[] args) {
        /**
         * int[] hole = {5,4,3,4,2,0};
         * 新创建一个数组，这个数组的索引，对应的是萝卜号
         * int[] radish
         * 这个数组的大小我们还不知道，这个萝卜数组的容量，根据 数组hole 里面的最大值得到，最大值是 5
         * 此时，将 radish数组大小初始化为 6
         * 开始遍历 hole数组，顺便往 radish数组里面放坑位号
         * 1）hole 0号坑里面是5号萝卜，把 5号萝卜拿出来，放到radish数组，并标记5号萝卜有几个坑，此时只有1个。radish[5]=1
         * 2）hole 1号坑里面是4号萝卜，把 4号萝卜拿出来，并标记4号萝卜有几个坑，此时只有1个。radish[4]=1
         * 3）hole 2号坑里面是3号萝卜，把 3号萝卜拿出来，并标记3号萝卜有几个坑，此时只有1个。radish[3]=1
         * 4）hole 3号坑里面是4号萝卜，把 4号萝卜拿出来，并标记4号萝卜有几个坑，此时发现4号萝卜上面已经有一个坑位数了，此时加一。radish[4]=2
         * 5）hole 4号坑里面是2号萝卜，把 2号萝卜拿出来，并标记2号萝卜有几个坑，此时只有1个。radish[2]=1
         * 6）hole 5号坑里面是0号萝卜，把 0号萝卜拿出来，并标记0号萝卜有几个坑，此时只有1个。radish[0]=1
         *
         * 此时 radish数组是这样的：radish[0]=1；radish[1]=0；radish[2]=1；radish[3]=1；radish[4]=2；radish[5]=1
         * 把 radish数组遍历一下，数组中索引对应的值是几，就打印几遍这个索引号。
         * 最终结果是 ：0，2，3，4，4，5 （原数组是 5,4,3,4,2,0）完成排序操作。
         * 鸽巢排序：你把萝卜想成鸽巢，坑位想成几只鸽子（一个萝卜上面可能顶了好几个坑位 ———— 一个鸽巢里面可能好几只鸽子）
         */


    }

}
