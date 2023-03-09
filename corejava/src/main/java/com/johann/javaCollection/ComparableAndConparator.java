package com.johann.javaCollection;

import java.util.*;

/**
 * @ClassName: ComparableAndConparator
 * @Description: TODO
 * @Author: Johann
 * @Date: 2019-12-31 13:50
 **/
public class ComparableAndConparator {

    /**
    *  聊一下Comparable接口和Comparator接口的差异
     *
     *  java.lang.Comparable接口，里面只有一个方法，compareTo()
     *
     *  public interface Comparable<T> {
     *      public int compareTo(T o);
     *  }
     *
     *  java.util.Comparator接口中，提供的方法，则要多一些。常用的就是 compare()方法
     *
     *  public interface Comparator<T> {
     *
     *      int compare(T o1, T o2);
     *
     *      boolean equals(Object obj);
     *
     *      default Comparator<T> reversed() {
     *         return Collections.reverseOrder(this);
     *     }
     *
     *     <p>剩下的方法不在此罗列，详细移步只 @see java.util.Comparator
     *
     *      ... ...
     *
     *  }
     *
    */

    public static void main(String[] args) {
        // TreeSet直接按照ComparableAndConparatorJohann指定的排序规则完成排序
//        Set<ComparableAndConparatorJohann> johannSet1 = new TreeSet<ComparableAndConparatorJohann>();
//        ComparableAndConparatorJohann johann1 = new ComparableAndConparatorJohann("test1",14,20D);
//        johannSet1.add(johann1);
//        ComparableAndConparatorJohann johann2 = new ComparableAndConparatorJohann("test2",13,20D);
//        johannSet1.add(johann2);
//        ComparableAndConparatorJohann johann3 = new ComparableAndConparatorJohann("test3",12,20D);
//        johannSet1.add(johann3);
//        ComparableAndConparatorJohann johann4 = new ComparableAndConparatorJohann("test4",14,18D);
//        johannSet1.add(johann4);
//        ComparableAndConparatorJohann johann5 = new ComparableAndConparatorJohann("test5",13,18D);
//        johannSet1.add(johann5);
//        ComparableAndConparatorJohann johann6 = new ComparableAndConparatorJohann("test6",12,18D);
//        johannSet1.add(johann6);
//        System.out.println(johannSet1);

//        List<ComparableAndConparatorJohann> johannList1 = new ArrayList<ComparableAndConparatorJohann>();
//        ComparableAndConparatorJohann johann1 = new ComparableAndConparatorJohann("test1",14,10D);
//        johannList1.add(johann1);
//        ComparableAndConparatorJohann johann2 = new ComparableAndConparatorJohann("test2",13,10D);
//        johannList1.add(johann2);
//        ComparableAndConparatorJohann johann3 = new ComparableAndConparatorJohann("test3",12,10D);
//        johannList1.add(johann3);
//        ComparableAndConparatorJohann johann4 = new ComparableAndConparatorJohann("test4",14,18D);
//        johannList1.add(johann4);
//        ComparableAndConparatorJohann johann5 = new ComparableAndConparatorJohann("test5",13,18D);
//        johannList1.add(johann5);
//        ComparableAndConparatorJohann johann6 = new ComparableAndConparatorJohann("test6",12,18D);
//        johannList1.add(johann6);
//        //Collections.sort(johannList1);
//        //使用的是 外比较器实现的比较策略。 策略模式
//        Collections.sort(johannList1,new JohannAgeComparator());
//        System.out.println(johannList1);

        List<ComparableAndConparatorJohann1> johannList2 = new ArrayList<ComparableAndConparatorJohann1>();
        ComparableAndConparatorJohann1 johann11 = new ComparableAndConparatorJohann1("test11",14,10D);
        johannList2.add(johann11);
        ComparableAndConparatorJohann1 johann12 = new ComparableAndConparatorJohann1("test12",12,20D);
        johannList2.add(johann12);
        ComparableAndConparatorJohann1 johann13 = new ComparableAndConparatorJohann1("test13",13,30D);
        johannList2.add(johann13);
        ComparableAndConparatorJohann1 johann14 = new ComparableAndConparatorJohann1("test14",15,5D);
        johannList2.add(johann14);
        ComparableAndConparatorJohann1 johann15 = new ComparableAndConparatorJohann1("test15",19,8D);
        johannList2.add(johann15);
        ComparableAndConparatorJohann1 johann16 = new ComparableAndConparatorJohann1("test16",10,9D);
        johannList2.add(johann16);
        // 无法直接排序，因为 Collections.sort(List<T>) 方法，要求list中的对象 T 实现Comparable接口
        //Collections.sort(johannList2);
        Collections.sort(johannList2,new JohannAgeComparator1());
        System.out.println(johannList2);
    }

}
/**
*  实现 Comparable 接口的实体类
*/
class ComparableAndConparatorJohann implements Comparable<ComparableAndConparatorJohann>{

    private String name;

    private Integer age;

    private Double score;

    public ComparableAndConparatorJohann() {
        super();
    }

    //

    public ComparableAndConparatorJohann(String name, Integer age, Double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComparableAndConparatorJohann)) return false;

        ComparableAndConparatorJohann johann = (ComparableAndConparatorJohann) o;

        if (getName() != null ? !getName().equals(johann.getName()) : johann.getName() != null) return false;
        if (getAge() != null ? !getAge().equals(johann.getAge()) : johann.getAge() != null) return false;
        return getScore() != null ? getScore().equals(johann.getScore()) : johann.getScore() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getAge() != null ? getAge().hashCode() : 0);
        result = 31 * result + (getScore() != null ? getScore().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ComparableAndConparatorJohann{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(ComparableAndConparatorJohann o) {// age,score升序排列
        if(getAge()!=null){
            if(o.getAge()==null){
                return  1;
            }else{
                // age都不为null，比较age
                if(getAge() > o.getAge()){
                    return 1;
                }else if (getAge() < o.getAge()){
                    return -1;
                }else {
                    // age都不为null且age相等 , 继续比较score
                    if(getScore()!=null){
                        if(o.getScore()==null){
                            return 1;
                        }else{
                            // score都不为null
                            if(getScore() > o.getScore()){
                                return 1;
                            }else if(getScore() < o.getScore()){
                                return -1;
                            }else{
                                return 0;
                            }
                        }
                    }else{
                        if(o.getScore()!=null){
                            return -1;
                        }else{
                            // score都为null
                            return 0;
                        }
                    }
                }
            }
        }else{
            if(o.getAge()!=null){
                return -1;
            }else{
                // age 都为null , 继续比较score
                if(getScore()!=null){
                    if(o.getScore()==null){
                        return 1;
                    }else{
                        // score都不为null
                        if(getScore() > o.getScore()){
                            return 1;
                        }else if(getScore() < o.getScore()){
                            return -1;
                        }else{
                            return 0;
                        }
                    }
                }else{
                    if(o.getScore()!=null){
                        return -1;
                    }else{
                        // score都为null
                        return 0;
                    }
                }

            }
        }

    }
}

/**
 *  实现 Comparable 接口的实体类
 */
class ComparableAndConparatorJohann1{

    private String name;

    private Integer age;

    private Double score;

    public ComparableAndConparatorJohann1() {
        super();
    }

    public ComparableAndConparatorJohann1(String name, Integer age, Double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComparableAndConparatorJohann1)) return false;

        ComparableAndConparatorJohann1 johann = (ComparableAndConparatorJohann1) o;

        if (getName() != null ? !getName().equals(johann.getName()) : johann.getName() != null) return false;
        if (getAge() != null ? !getAge().equals(johann.getAge()) : johann.getAge() != null) return false;
        return getScore() != null ? getScore().equals(johann.getScore()) : johann.getScore() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getAge() != null ? getAge().hashCode() : 0);
        result = 31 * result + (getScore() != null ? getScore().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ComparableAndConparatorJohann1{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }
}

/**
 *  这是个实现 Comparator 接口的外比较器
 */
class JohannAgeComparator implements Comparator<ComparableAndConparatorJohann> {

    /**
     *  根据年龄降序排列，不考虑 score
     */
    @Override
    public int compare(ComparableAndConparatorJohann o1, ComparableAndConparatorJohann o2) {
        if(o1.getAge()!=null){
            if(o2.getAge()==null){
                return -1;
            }else{
                if(o1.getAge() > o2.getAge()){
                    return -1;
                }else if (o1.getAge() < o2.getAge()){
                    return 1;
                }else {
                    return 0;
                }
            }
        }else{
            if(o2.getAge()!=null){
                return 1;
            }else{
                return 0;
            }
        }
    }
}

/**
*  这是个实现 Comparator 接口的外比较器
*/
class JohannAgeComparator1 implements Comparator<ComparableAndConparatorJohann1> {

    /**
    *  根据年龄降序排列，不考虑 score
    */
    @Override
    public int compare(ComparableAndConparatorJohann1 o1, ComparableAndConparatorJohann1 o2) {
        if(o1.getAge()!=null){
            if(o2.getAge()==null){
                return -1;
            }else{
                if(o1.getAge() > o2.getAge()){
                    return -1;
                }else if (o1.getAge() < o2.getAge()){
                    return 1;
                }else {
                    return 0;
                }
            }
        }else{
            if(o2.getAge()!=null){
                return 1;
            }else{
                return 0;
            }
        }
    }
}


class JohannScoreComparator1 implements Comparator<ComparableAndConparatorJohann1> {

    /**
     *  根据score降序排列，不考虑 age
     */
    @Override
    public int compare(ComparableAndConparatorJohann1 o1, ComparableAndConparatorJohann1 o2) {
        if(o1.getScore()!=null){
            if(o2.getScore()==null){
                return -1;
            }else{
                if(o1.getScore() > o2.getScore()){
                    return -1;
                }else if (o1.getScore() < o2.getScore()){
                    return 1;
                }else {
                    return 0;
                }
            }
        }else{
            if(o2.getScore()!=null){
                return 1;
            }else{
                return 0;
            }
        }
    }
}