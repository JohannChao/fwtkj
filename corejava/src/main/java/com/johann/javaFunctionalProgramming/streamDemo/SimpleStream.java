package com.johann.javaFunctionalProgramming.streamDemo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/** 流相关操作
 * @ClassName: SimpleStream
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class SimpleStream {

    /**
     * collect操作
     * collect方法通常会结合 Collectors 接口一起使用，是一个通用的强大结构，可以满足数据转换、数据分块、字符串处理等操作。
     */
    private static void collectTest(){
        // 1, 生成集合。使用 collect(Collectors.toList()) 方法从 Stream 中生成一个列表。
        List<String> collected = Stream.of("A","B","C","D","B","D","E").collect(Collectors.toList());
        System.out.println(collected);

        // 2, 集合转换。使用 toCollection 来定制集合收集元素，这样就把 List 集合转换成了 TreeSet
//        collected.stream().collect(Collectors.toCollection(new Supplier<TreeSet<String>>() {
//            @Override
//            public TreeSet<String> get() {
//                return new TreeSet<>();
//            }
//        }));
        //collected.stream().collect(Collectors.toCollection(() -> new TreeSet<>()));
        TreeSet<String> treeSet = collected.stream().collect(Collectors.toCollection(TreeSet::new));
        System.out.println(treeSet);

        // 3, 转换成值。使用 collect 来对元素求值，使用 maxBy 接口让收集器生成一个值。
//        collected.stream().collect(Collectors.maxBy(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareTo(o2);
//            }
//        })).get();
        //collected.stream().collect(Collectors.maxBy((o1, o2) -> o1.compareTo(o2))).get();
        String maxStr = collected.stream().collect(Collectors.maxBy(String::compareTo)).get();
        System.out.println("最大值: "+maxStr);

        // 4, 数据分块。通过 partitioningBy 接口可以把数据分成两类，即满足条件的和不满足条件的，最后将其收集成为一个 Map 对象，其 Key 为 Boolean 类型，Value 为相应的集合元素。
        // 同样我们还可以使用 groupingBy 方法来对数据进行分组收集，这类似于 SQL 中的 group by 操作。
//        collected.stream().collect(Collectors.partitioningBy(new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return s.compareTo("C") > 0;
//            }
//        }));
        Map<Boolean,List<String>> partitionMap = collected.stream().collect(Collectors.partitioningBy(s -> s.compareTo("C") > 0));
        System.out.println("不大于C: "+partitionMap.get(false));
        System.out.println("大于C: "+partitionMap.get(true));

        // 5, 字符串处理。将元素组合成一个字符串。
        String joinStr = collected.stream().collect(Collectors.joining(" | ","{","}"));
        System.out.println("joining: "+joinStr);
    }

    /**
     * map 操作是将流中的对象换成一个新的流对象，是 Stream 上常用操作之一。
     */
    private static void mapTest(){
        // Java8以前，将对集合进行操作
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "a","b","c");
        List<String>  newList = new ArrayList<>();
        for (String s : list) {
            newList.add(s.toUpperCase());
        }
        System.out.println(newList);

        // 使用流对集合操作
//        List<String> newStreamList = list.stream().map(new Function<String, String>() {
//            @Override
//            public String apply(String s) {
//                return s.toUpperCase();
//            }
//        }).collect(Collectors.toList());
        //List<String> newStreamList = list.stream().map(s -> s.toUpperCase()).collect(Collectors.toList());
        List<String> newStreamList = list.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(newStreamList);
    }

    /**
     * flatmap 与 map 功能类似，只不过 map 对应的是一个流，而 flatmap 可以对应多个流。
     */
    private static void flatMapTest(){
        List<String> list1 = new ArrayList<>();
        Collections.addAll(list1, "a","b","c");
        List<String> list2 = new ArrayList<>();
        Collections.addAll(list2, "e","f","g");
        List<List<String>> lists = Arrays.asList(list1,list2);
        System.out.println(lists);

//        lists.stream().flatMap(new Function<List<String>, Stream<String>>() {
//            @Override
//            public Stream<String> apply(List<String> list) {
//                return list.stream();
//            }
//        }).collect(Collectors.toList());
        //lists.stream().flatMap(list -> list.stream()).collect(Collectors.toList());
        List<String> newLists = lists.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(newLists);
    }

    /**
     * filter 用来过滤元素，在元素遍历时，可以使用 filter 来提取我们想要的内容，这也是集合常用的方法之一。
     */
    private static void filterTest(){
        List<String> collected = Stream.of("A","B","C","D","B","E","F").collect(Collectors.toList());
        System.out.println(collected);

//        collected.stream().filter(new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return s.compareTo("C")>0;
//            }
//        }).collect(Collectors.toList());
        List<String> filterList = collected.stream().filter(s -> s.compareTo("C")>0).collect(Collectors.toList());
        System.out.println(filterList);
    }

    /**
     * max/min 求最大值和最小值也是集合上常用的操作。它通常会与 Comparator 接口一起使用来比较元素的大小。
     */
    private static void maxMinTest(){
        List<String> collected = Stream.of("A","B","C","D","B","E","F").collect(Collectors.toList());
        System.out.println(collected);
//        String max = collected.stream().max(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareTo(o2);
//            }
//        }).get();
        String max = collected.stream().max(String::compareTo).get();
        System.out.println("max: "+max);

//        collected.stream().min(Comparator.comparing(new Function<String, String>() {
//            @Override
//            public String apply(String s) {
//                return s;
//            }
//        })).get();
        String min = collected.stream().min(Comparator.comparing(s -> s)).get();
        System.out.println("min: "+min);
    }

    /**
     * reduce 操作是可以实现从流中生成一个值，我们前面提到的如 count、max、min 这种及早求值就是由reduce 提供的。
     */
    private static void reduceTest(){
        List<Integer> collected = Stream.of(1,2,3,4,5,6,7).collect(Collectors.toList());
        System.out.println(collected);

        //使用 reduce 方法对数组元素进行求和。这个方法接收两个参数，第一个参数相当于是一个初始值，第二参数则为具体的业务逻辑。
//        collected.stream().reduce(0, new BinaryOperator<Integer>() {
//            @Override
//            public Integer apply(Integer i1, Integer i2) {
//                return i1+i2;
//            }
//        });
        //Integer sum = collected.stream().reduce(5, Integer::sum);
        //Integer sum = collected.stream().reduce(Integer::sum).get();
        Optional<Integer> op = collected.stream().reduce(Integer::sum);
        Integer sum = op.orElse(0);
        System.out.println(sum);
    }

    public static  void testss(){
        List<Map<String,String>> li = new ArrayList<Map<String,String>>();
        Map<String,String> m1 = new HashMap<>(8);
        m1.put("stubs", "未婚");
        m1.put("name", "老赵");
        Map<String,String> m2 = new HashMap<>(8);
        m2.put("stubs", "未婚");
        m2.put("name", "张杰");
        Map<String,String> m3 = new HashMap<>(8);
        m3.put("stubs", "未婚");
        m3.put("name", "豆子");
        li.add(m1);
        li.add(m2);
        li.add(m3);

        List<Map<String,String>> lx = new ArrayList<>();
        li.forEach(m->{
            IntStream.range(0, 2).forEach(i->{
                System.out.println(i);
                //xm = m;
                Map<String, String> xm = new HashMap<>(m);
                System.out.println("sout "+i);
                if(i>0) {
                    System.out.println("sout== "+i);
                    xm.put("stubs", "已婚");
                }
                lx.add(xm);
            });
        });
        lx.forEach(m->{
            System.out.println(m.get("name")+"=="+m.get("stubs"));
        });
    }

    public static void main(String[] args) {
        //collectTest();
        //mapTest();
        //flatMapTest();
        //filterTest();
        //maxMinTest();
        //reduceTest();
        //testss();
        Map<String,Integer> map = new HashMap<>();
        map.putAll(new HashMap<>(0));
    }
}
