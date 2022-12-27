package jdk.tools;

import java.util.Objects;

public class JdkDemoObjects {
    public static void main(String[] args) {
        JdkDemoObjects demo = new JdkDemoObjects();
        demo.ti3();
    }
    /**
     * @description: Objects的常用方法--equals
     * static boolean equals (Object a, Object b) 返回 true如果参数相等，彼此 false其他。
     * 作用：Objects的equals可以判断空指针；从而避免空指针异常的问题
     * 如果是比较对象，还是要重写该类的equals方法，因为源码调用的还好Object的equals方法；但因为也用了==，所以对于基本类型数据使用要慎重
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

    private void ti4(){

    }
}
