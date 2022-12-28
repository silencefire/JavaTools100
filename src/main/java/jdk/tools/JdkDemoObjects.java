package jdk.tools;

import jdk.tools.entity.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Supplier;

public class JdkDemoObjects {
    public static void main(String[] args) {
        JdkDemoObjects demo = new JdkDemoObjects();
        demo.ti7();
    }
    /**
     * @description: equals
     * static boolean equals (Object a, Object b) 返回 true如果参数相等，彼此 false其他。
     * 作用：Objects的equals可以判断空指针；从而避免空指针异常的问题
     * 如果是比较对象，还是要重写该类的equals方法，因为源码调用的还是Object的equals方法；但因为先用了==判断，所以对于基本类型数据使用要慎重
     * @author: zhenghm
     * @time: 2022/12/26
     */
    private void ti1(){
        JdkDemoObjects demo1 = new JdkDemoObjects();
        JdkDemoObjects demo2 = new JdkDemoObjects();
        JdkDemoObjects demo3 = null;
        //常规方法，下面会报错
        System.out.println("常规equals方法：res="+demo3.equals(demo1));
        //下面不会报错
        System.out.println("Objects的equals方法：res="+Objects.equals(demo3,demo1));
    }
    
    /**
     * @description: public static boolean deepEquals(Object a, Object b)
     * 该方法深层次对比两个对象
     *  1.普通对象，使用==直接对比内存地址
     *  2.数组对象，使用Arrays.deepEquals0，内部确定类型之后进行对比，核心都是对比元素的内存地址，用的==
     *  特殊情况：数组的长度和其中元素相等会返回true，而如果单纯使用equals方法，则不会由该规则，只对比内存地址
     *
     *  所以用deepEquals比较两个数组，比较推荐，可是为啥不用Arrays，
     *  看了下源码因为Arrays的equals其实用的也是Objects.equals方法，内部都用的是Arrays.deepEquals0，饶了一大圈；
     *
     *  最终，比较数组，还是建议用Arrays，Objects的equals方法不实用，比较内存地址的用处少；
     * @author: zhenghm
     * @time: 2022/12/26
     */
    private void ti2(){
        int[][] a ={{0,1,2},{2,3,4},{5,6,7}};
        int[][] b ={{0,1,2},{2,3,4},{5,6,7}};
        System.out.println(Objects.deepEquals(a,b));//返回true
        System.out.println(Objects.equals(a,b));//返回false
        System.out.println(Objects.equals(5,5));//返回true
        System.out.println(Objects.deepEquals(a,b));//返回true
        System.out.println(Objects.deepEquals(8,8));//返回true
    }

    /**
     * @description: Objects的hash方法与hashCode方法辨析
     * 看源码可以知道Objects.hashCode与Object的hashCode没有本质区别
     * 而Objects的hash方法主要方便计算一组数据的hashcode值
     * @author: zhenghm
     * @time: 2022/12/26
     */
    private void ti3(){
        System.out.println("a".hashCode());
        System.out.println(Objects.hashCode("a"));
        System.out.println(Objects.hash("a"));//31+96 = 128
        System.out.println(Objects.hash("a","a"));//128*31+96=4065
    }

    /**
     * @description: Objects的checkFromIndexSize,checkFromToIndex与checkIndex方法，感觉实用性不大
     * 这三个都用用来确定下表是否在一定长度范围之内的；
     * @author: zhenghm
     * @time: 2022/12/28
     */
    private void ti4(){
        //checkFromIndexSize表示从fromIndex(包括)到fromIndex+size(不包括)在0到length的范围内，如果在，则返回所在其实下表，如果不在，则抛异常
        System.out.println(Objects.checkFromIndexSize(5,3,8));
        //checkFromToIndex表示从fromIndex(包括)到toIndex(不包括)，是否在0到length的范围内，如果在，则返回所在其实下表，如果不在，则抛异常
        System.out.println(Objects.checkFromToIndex(5,8,8));
        //同样，查看index是否在0到length之间，如果在，则返回所在其实下表，如果不在，则抛异常
        System.out.println(Objects.checkIndex(5,8));
    }

    /**
     * @description: compare方法
     * @author: zhenghm
     * @time: 2022/12/28
     */
    private void ti5(){
        Apple a = new Apple("local","red","good",10);
        Apple b = a;
        Apple c = new Apple("otherCountry","green","just so so",5);
        //如果a，b两个对象指向同一引用地址，返回0；否则调用c的compare方法进行比较
        //第二种我用了Comparator.comparingInt的方法进行了简化，
        //但是这种简化不见得好用，因为他只会返回正负和0，不会返回具体的值；而如果使用Lambda表达式则会返回正确的值；
        System.out.println(Objects.compare(a, b, (o1, o2) -> o1.getPrice()-o2.getPrice()));
        System.out.println(Objects.compare(a, c, Comparator.comparingInt(Apple::getPrice)));
    }

    /**
     * @description: 处理null的方法：isNull,nonNull,requireNonNull的三种变种，requireNonNullElse，requireNonNullElseGet（推荐）
     * @author: zhenghm
     * @time: 2022/12/28
     */
    private void ti6(){
        Apple a = null;
        Apple b = new Apple("otherCountry","green","just so so",5);
        System.out.println(Objects.isNull(a));//简单就是判断是否==Null
        System.out.println(Objects.nonNull(a));//这个就是!=Null

        //System.out.println(Objects.requireNonNull(a));//如果是null，则抛空指针异常
        System.out.println(Objects.requireNonNull(b));//有意思的是写了toString方法之后，println可以自动调用

        //System.out.println(Objects.requireNonNull(a,"ssss"));//如果null，则会提示相应信息，ssss
        System.out.println(Objects.requireNonNull(b,"ssss"));//非空，正常执行，返回Object也就是b本身；

        Supplier<String> s = () ->"messageInfo";
        //System.out.println(Objects.requireNonNull(a,s));//如果null，则会提示相应信息，s的supplier函数实现；
        System.out.println(Objects.requireNonNull(b,s));//非空，正常执行，返回Object也就是b本身；

        System.out.println(Objects.requireNonNullElse(a,b));//如果第一个参数是null，则返回第二个参数，注意第二个也是null时，则报错；

        /*重磅推荐！！！*/
        System.out.println(Objects.requireNonNullElseGet(a,s));//这个比较实用，如果第一个是null，则返回第二的supplier接口实现结果；而不是报错；
    }

    /**
     * @description:  toString的两个方法；第二个方法推荐！！！
     * @author: zhenghm
     * @time: 2022/12/28
     */
    private void ti7(){
        Apple a = new Apple("otherCountry","green","just so so",5);
        System.out.println(Objects.toString(666));//也就是把括号里的值转换成String，调用了String.valueOf
        System.out.println(Objects.toString(null,"default"));//第一个值如果是null，返回默认值；
    }
}
