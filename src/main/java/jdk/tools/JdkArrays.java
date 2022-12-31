package jdk.tools;

import jdk.tools.entity.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 主要研究Arrays
 * @author coldE
 */
public class JdkArrays {
    public static void main(String[] args) {
        JdkArrays jdkArrays = new JdkArrays();
        jdkArrays.ti1();
    }

    /**
     * 老规矩，先看看常用方法，再全部从上到下玩一遍，找找有意思的；
     * binarySearch() ——二分搜索法搜索指定元素的下标，需要先排序
     * copyOf() ——复制指定数组长度的元素到新数组
     * equals() ——判断两个数组彼此是否相等
     * sort() ——按照数字升序排列指定数组
     * parallelSort() ——按照数字升序排列指定数组
     * stream() ——将自定数组作为源（流）--创建流对象
     * toString()
     */
    /**
     * @description:  binarySearch
     */
    private void ti1(){
        /*
         * 该方法算法常用，有多个变种，原理都是用的二分法，具体看源码
         * 都是在第一个参数里找另一个参数的位置，前提是一定要排好队，例如用sort方法，没排好队，则结果未知；
         * 如果找到了值，则返回对应值的下表（从0开始算）
         * 如果没找到值，则会返回对应应该插入的位置，但是需要将返回的值x计算一下：-(x+1);也就是+1后取反；
         * 可以用Math.negateExact()方法取反，这不就用上了；
         */

        char[] chs = {'a','b','e','g','h','z'};
        //该值返回5，正好是'z'字符所在的位置；
        System.out.println("binarySearch:"+Arrays.binarySearch(chs,'z'));
        System.out.println("binarySearch:"+Arrays.binarySearch(chs,'s'));
        //该值返回-6，计算后得到5，正好是该插入的位置；
        System.out.println("binarySearch:"+Math.negateExact(Arrays.binarySearch(chs,'s')+1));
        System.out.println("binarySearch:"+Arrays.binarySearch(chs,'T'));

        /*
         * 变种的其他方法
         * 这种指定了fromIndex和toIndex的方法，需要注意的是
         * fromIndex - 要搜索的第一个元素（包括）的索引
         * toIndex - 要搜索的最后一个元素（不包括）的索引
         */
        //搜索的下标包括5，所以搜得到，结果为5
        System.out.println("binarySearch:4参数："+Arrays.binarySearch(chs,0,6,'z'));
        //搜索的下表不包括5，最大到4，搜不到，结果为-6，也就是该放到索引为5的位置，这个方法很灵活，用好了有大用；
        System.out.println("binarySearch:4参数："+Arrays.binarySearch(chs,0,5,'z'));

        /*
         * 验证过，如果里面放的是Object类型的数据，无法比较，则结果未知，这个方法不能用；
         * API明确说明：如果数组包含不可相互比较的元素（例如，字符串），则无法根据其元素的自然顺序对其进行排序，因此结果未定义
         */
        Object[] as = new Object[3];
        as[0] = 1;
        as[1] = 5;
        as[2] = 9;
        //无该元素情况
        System.out.println(Arrays.binarySearch(as,0));
        //有该元素情况
        System.out.println(Arrays.binarySearch(as,9));
        //有该元素情况
        System.out.println(Arrays.binarySearch(as,2));

        /*
         *  binarySearch(T[] a, T key, Comparator<? super T> c)
         * 原因：可以带自己的比较器，也就是可以比较对象了；优势是对所有的对象排序后，根据排序规则找到应该插入的位置；还是算法常用吧；
         * 用法，先用sort(a[],comparator)将数据排序，然后调用该方法，切记，也要先排好序；
         */

        Apple a = new Apple("local","red","good",10);
        Apple b = new Apple("local","red","good",12);
        Apple c = new Apple("local","red","good",1);
        Apple d = new Apple("local","red","good",14);
        Apple e = new Apple("local","red","good",100);
        Apple[] apples = new Apple[1000000];
        for(int i=0;i<1000000;i++){
            Apple x = new Apple("local"+i,"red","good", (int) Math.round(Math.random()*1000));
            apples[i] = x;
        }
        Comparator<Apple> comparator = new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                //如果返回-1，则调换未知
                //返回0和1，则不变；
                return o1.getPrice()-o2.getPrice();
            }
        };
        /*
         * 用了10万数据测试，发现还是链表更快，因为不需要排序；差距随着数量的增加，随之拉大；
         * 查找元素推荐：链表的contains
         * 但是这个方法也是有优点的，排序，查到插入的位置；
         */
        long t1 = System.currentTimeMillis();
        //sort的算法后面介绍，情况略复杂；
        Arrays.sort(apples,comparator);
        int appPosition = Arrays.binarySearch(apples, b, comparator);
        System.out.println("appPosition:"+appPosition);
        System.out.println("appPosition time:"+(System.currentTimeMillis()-t1));

        long t2 = System.currentTimeMillis();
        List<Apple> ala = Arrays.asList(apples);
        System.out.println("Arrays.contains:"+ala.contains(a));
        System.out.println("appPosition time:"+(System.currentTimeMillis()-t2));
    }
}
