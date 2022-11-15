package com.johann;

public class Main {

    public int search(int[] nums,int target){
        int result = 0;
        int min = 0,max = nums.length-1,index = 0;
        while (min <= max){
            index = (min+max)>>1;
            if (nums[index] == target) {
                break;
            }
            if (nums[index] > target){
                max = index-1;
            }else {
                min = index+1;
            }
        }

        if (nums[index] == target){
            result++;
            int p = index+1;
            while (p < nums.length) {
                if (nums[p] != target){
                    break;
                }
                p++;
                result++;
            }
            p = index-1;
            while (p >= 0) {
                if (nums[p] != target){
                    break;
                }
                p--;
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println((1>>1));
    }
}