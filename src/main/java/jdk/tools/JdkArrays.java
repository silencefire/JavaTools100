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
    /**
     * 老规矩，先看看常用方法，再全部从上到下玩一遍，找找有意思的；
     * binarySearch() ——二分搜索法搜索指定元素的下标，需要先排序
     * copyOf() ——复制指定数组长度的元素到新数组
     * equals() ——判断两个数组彼此是否相等
     * sort() ——按照数字升序排列指定数组
     * parallelSort() ——按照数字升序排列指定数组（并行算法）
     * stream() ——将自定数组作为源（流）--创建流对象
     * toString() ——打印数组内的数据
     */
    public static void main(String[] args) {
        JdkArrays jdkArrays = new JdkArrays();
        jdkArrays.ti7();
    }


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
         * 但是这个方法也是有优点的，排序，返回应该插入的位置；
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
    
    /**
     * @description:  copyOf()方法/copyOfRange()方法
     * 作用：复制数组；
     * 使用注意点：
     * 1.内部使用的是 System.arraycopy 也就是最后复制出来的数组与原数组已经没有任何关系，内存地址指向的位置并不同；
     * 2.第二个参数如果大于原数组位数，会被用0填充；
     * @author: zhenghm
     * @time: 2023/1/1
     */
    private void ti2(){
        int[] a = {1,2,3,4};
        int[] arraysCopy = Arrays.copyOf(a,2);
        arraysCopy[0] = 9;
        System.out.println("copyOf:a:"+Arrays.toString(a));
        System.out.println("copyOf:b:"+Arrays.toString(arraysCopy));

        /*
         * 其他变种方法的使用
         * 1.三参数，指定返回数组类型
         * 2.copyOfRange，复制指定部分；
         */
        Integer[] aI = new Integer[]{1,2,3,4};
        //指定返回的数组的类型，但是必须是之前数组元素的父类或同类；
        System.out.println("copyOf:3param:"+Arrays.toString(Arrays.copyOf(aI,2,Integer[].class)));
        System.out.println("copyOf:3param:"+Arrays.toString(Arrays.copyOf(aI,2,Object[].class)));

        //只复制指定的部分
        System.out.println("copyOfRange:"+Arrays.toString(Arrays.copyOfRange(aI,1,3)));
        //复制指定的部分，并指定返回类型
        System.out.println("copyOfRange:4param:"+Arrays.toString(Arrays.copyOfRange(aI,1,3,Object[].class)));
    }

    /**
     * @description: equals方法
     * @author: zhenghm
     * @time: 2023/1/1
     *
     *  结论：普通的常用对象使用Arrays.equals够用
     * 其他的多维基础类型数组（数组里套数组），要深入比较，则可以用Arrays.deepEquals；也就是Objects基本不用；
     *
     * 两个常用的变种方法：
     * 1.指定比较范围的方法；
     * 2.对象数组，指定比较器的方法；
     */
    private void ti3(){
        /*
         * 该方法在Objects已经研究过，直接说结论：Arrays.deepEquals与Objects.deepEquals对于数组用的是同一个方法；
         * 比较的都是内部数据是否一致，而不是内部地址
         * 而Objects.equals则是比较的内存地址；
         */
        int[] a = {1,2,3,4};
        int[] b = {1,2,3,4};
        Apple[] apples1 = new Apple[2];
        apples1[0] = new Apple("local","red","good",10);
        apples1[1] = new Apple("local","red","good",11);
        Apple[] apples2 = new Apple[2];
        apples2[0] = new Apple("local","red","good",10);
        apples2[1] = new Apple("local","red","good",11);
        Apple[] apples3 = new Apple[2];
        apples3[0] = new Apple("local","red","good",11);
        apples3[1] = new Apple("local","red","good",11);
        System.out.println("Arrays.equals:"+Arrays.equals(a,b));
        System.out.println("Objects.deepEquals:"+Objects.deepEquals(a,b));
        //这里都会提示使用 Arrays.equals方法
        System.out.println("Objects.equals:"+Objects.equals(a,b));
        //Arrays.equals与deepEquals对于普通对象是没什么区别的，但是对于基础类型的的多维数组对象则不同；deepEquals更深入比较
        System.out.println("Arrays.deepEquals:"+Arrays.deepEquals(apples1,apples2));
        System.out.println("Arrays.equals:"+Arrays.equals(apples1,apples2));

        //其他变种方法，更加常用，指定对比范围
        System.out.println("Arrays.equals:6params:"+Arrays.equals(a,1,2,b,1,2));
        System.out.println("Arrays.equals:6params:"+Arrays.equals(a,1,3,b,1,4));

        //对象数组比较，如果没有写对象的equals或者想用另一套比较规则，使用带比较器的方法
        Comparator<Apple> comparator = new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getPrice()-o2.getPrice();
            }
        };
        System.out.println("Arrays.equals:withComparator:"+Arrays.equals(apples1,apples2,comparator));
        System.out.println("Arrays.equals:withComparator:"+Arrays.equals(apples1,apples3,comparator));
    }

    /**
     * @description: toString，deepToString
     * 结论：
     *  toString打印一维数组
     *  多维数组，则用deepToString打印
     *  打印对象数组，需要写对象的toString方法；
     * @author: zhenghm
     * @time: 2023/1/2
     */
    private void ti4(){
        //整型
        int[] a = {1,2,3,4,5};
        System.out.println("int[]:"+Arrays.toString(a));
        //字符
        char[] b = {'a','b','c','d','e'};
        System.out.println("char[]:"+Arrays.toString(b));
        //对象
        Apple[] apples1 = new Apple[2];
        apples1[0] = new Apple("local","red","good",10);
        apples1[1] = new Apple("local","red","good",11);
        System.out.println("apples[]:"+Arrays.toString(apples1));

        int[] c = {1,2,3,4,5};
        int[] d = {1,2,3,4,5};
        int[][] s = {a,c,d};
        System.out.println("int[]:"+Arrays.toString(s));
        System.out.println("int[][]:"+Arrays.deepToString(s));
    }

    /**
     * @description: sort方法，parallelSort方法
     * @author: zhenghm
     * @time: 2023/1/3
     */
    private void ti5(){
        int[] a = new int[]{1,7,3,2,5,0};
        Arrays.sort(a);
        System.out.println("sort:1param:"+Arrays.toString(a));

        //其他变种
        //Arrays.sort(数组名，起始下标，排序个数)
        int[] b = new int[]{1,7,3,2,5,0};
        //只排序前三个；
        Arrays.sort(b,0,3);
        System.out.println("sort:3param:"+Arrays.toString(b));

        //对对象排序，如果对象写了比较器继承了Comparator，则可以进行比较
        Apple[] apples1 = new Apple[3];
        apples1[0] = new Apple("local","red","good",10);
        apples1[1] = new Apple("local","red","good",19);
        apples1[2] = new Apple("local","red","good",15);
        Arrays.sort(apples1);
        System.out.println(Arrays.toString(apples1));

        //对象排序，比较时放入比较器，这次进行逆序排序
        //sort(Object[] a)
        //sort(T[] a, Comparator<? super T> c)
        Apple[] apples2 = new Apple[3];
        apples2[0] = new Apple("local","red","good",10);
        apples2[1] = new Apple("local","red","good",19);
        apples2[2] = new Apple("local","red","good",15);
        Arrays.sort(apples2, new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o2.getPrice()-o1.getPrice();
            }
        });
        System.out.println(Arrays.toString(apples2));

        //上面的对象排序，还可以加入下标，用来标识排序哪些数据；
        //sort(Object[] a, int fromIndex, int toIndex)
        //sort(T[] a, int fromIndex, int toIndex, Comparator<? super T> c)

        /**
         * @description:  parallelSort() 在功能上有所不同。
         * 与 sort() 使用单个线程对数据进行顺序排序不同，它使用并行排序-合并排序算法。
         * 它将数组分成子数组，这些子数组本身先进行排序然后合并
         *
         * 只有在满足某些条件时，它才会使用并行性。如果数组大小小于或等于 8192，
         * （这里我实验有些不同，上10万的数据才会用parallel更快，之后数据量越大，区别越明显）
         * 或者处理器只有一个核心，则它将使用顺序的 Dual-Pivot Quicksort 算法。否则，它使用并行排序
         *
         * parallel的变种与sor一样，都是带下标的和带比较器的；就不赘述了
         */
        Apple[] applesMax = new Apple[1000000];
        for(int i=0;i<applesMax.length;i++){
            applesMax[i] = new Apple("local"+i,"red","good", (int) Math.round(Math.random()*1000));
        }
        long t1 = System.currentTimeMillis();
        Arrays.parallelSort(applesMax);
        System.out.println("applesMax:parallelSort,TIME:"+(System.currentTimeMillis()-t1));

        Apple[] applesMax2 = new Apple[10000001];
        for(int i=0;i<applesMax2.length;i++){
            applesMax2[i] = new Apple("local"+i,"red","good", (int) Math.round(Math.random()*1000));
        }
        long t2 = System.currentTimeMillis();
        Arrays.sort(applesMax2);
        System.out.println("applesMax:sort,TIME:"+(System.currentTimeMillis()-t2));
    }

    /**
     * @description: stream方法
     * 主要作用：获取数组的流，同样的方法还有Stream.of
     *
     * 变种方法：
     * 1.stream(T[] array, int startInclusive, int endExclusive)
     * 作用，将一定索引的元素转换为流；
     * 转化之后流的操作就丰富了，几乎很多都能用，也是JDK8比较好玩的特性，这里不多说，研究stream的玩法的时候再说；
     * @author: zhenghm
     * @time: 2023/1/4
     */
    private void ti6(){
        int[] a = {1,5,3,6,2,4};
        System.out.println(Arrays.stream(a).toString());
        Arrays.stream(a).forEach(System.out::print);
        System.out.println();

        //将下标1到3（不包括3）的元素转换为流，并进行打印
        Arrays.stream(a,1,3).forEach(System.out::print);
    }

    /**
     * @description: 接下来就是自由时间：来看看都有啥好玩的方法
     * @author: zhenghm
     * @time: 2023/1/4
     */
    private void ti7(){
        /*
         * compare方法：首先比较内存地址，然后看是否各元素都匹配，都匹配返回-1，否则会比较不匹配位置的大小，返回1或-1；
         * 这里的匹配：元素是否一致；可能的情况是相等或者一个包含另一个数组的内容；
         * 如果都匹配（元素相等或者包含），则比较两个数组的长度规则是a-b；
         * 分析：一般比较数组相等，用equals就可以，比较谁长？这个方法不具有考察性，如下例子，结果相同，无法判断何种情况；
         * 结论：只能用正负，来表示是否按照[字典顺序]谁在前面；>0表示第一个元素更大，<0表示第一个元素更小；
         *
         * ：：其他变种，
         * 1.指定了下标，指定比较元素的位置起点和终点，不赘述；
         * 2.就是compareUnsigned方法，进行无符号比较，将里面的元素进行无符号处理（都是正值）
         * 3.对于对象元素，也可以比较，但是需要实现Comparable接口，或者给定一个comparator接口；
         *
         */

        int[] a = {1,2,3,4,5,6};
        int[] b = {1,2,3,4,5};
        int[] c = {1,2,6,7,8};
        System.out.println("匹配但是长度不等："+Arrays.compare(b,a));
        System.out.println("不匹配，比较元素："+Arrays.compare(b,c));

        Apple[] apples1 = new Apple[3];
        apples1[0] = new Apple("local","red","good",10);
        apples1[1] = new Apple("local","red","good",19);
        apples1[2] = new Apple("local","red","good",15);
        Apple[] apples2 = new Apple[3];
        apples2[0] = new Apple("local","red","good",10);
        apples2[1] = new Apple("local","red","good",19);
        apples2[2] = new Apple("local","red","good",17);
        //结论就是第一个苹果比第二个苹果小，这样为啥不用sort...
        System.out.println("比较对象结果："+Arrays.compare(apples1,apples2));
    }
    
    /**
     * @description: 明天加上，今天游戏，算上昨天的，明天一天白天不休息；
     * @author: zhenghm
     * @time: 2023/1/6
     */
    private void ti8(){
        
    }
}
