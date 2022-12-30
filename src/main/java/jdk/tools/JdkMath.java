package jdk.tools;

/**
 * @description: 本类主要研究Math的常用方法，真的是常用的，一些比较专业的数学函数就不看了；用到的时候再看；
 * 需要注意的是Math被final修饰，无法被继承；
 * @author: zhenghm
 * @time: 2022/12/29
 */
public class JdkMath {
    public static void main(String[] args) {
        JdkMath jdkMath = new JdkMath();
        jdkMath.ti3();
    }
    /**
     * @description: 方法如下：加减乘除，外加：最大，最小，随机，绝对，次方
     * 绝对值：Math.abs
     * 取最大值：Math.max
     * 取最小值：Math.min
     * 随机数：Math.random
     * 四舍五入：Math.round
     */
    private void ti1(){
        //绝对值
        System.out.println("abs："+Math.abs(-1));
        System.out.println("abs："+Math.abs(1.123F));
        //取最大值（支持各种数字类型），算法中常用
        System.out.println("max："+Math.max(1.1,1.2));
        //取最小值
        System.out.println("min："+Math.min(1.1,1.2));
        //随机数，0.0 =< Math.random < 1.0
        System.out.println("random："+Math.random());
        for(int i=1;i<10;i++){
            //System.out.println(Math.ceil("random(10)："+Math.random()*10));//10以内的随机整数
        }
        //四舍五入，该方法慎用，不能商用
        System.out.println("round："+Math.round(1.49));
        System.out.println("round："+Math.round(1.499999999999999999999999999999999999999999999));
        System.out.println("round："+Math.round(10.5));
        System.out.println("round："+Math.round(-10.5));
        System.out.println("round："+Math.round(-10.4));
    }

    /**
     * @description: 算法常用的方法
     * 计算次方：Math.pow
     * 向上取整：Math.ceil，向正数的方向取整
     * 向下取整：Math.floor，向负数的方向取整
     * 平方根：Math.sqrt
     */
    private void ti2(){
        System.out.println("pow："+Math.pow(-10,3));

        System.out.println("ceil："+Math.ceil(-10.3));
        System.out.println("ceil："+Math.ceil(10.3));

        System.out.println("floor："+Math.floor(-10.3));
        System.out.println("floor："+Math.floor(10.3));

        System.out.println("sqrt："+Math.sqrt(9));

    }

    /**
     * @description:  我觉得有意思的方法，其他的方法看了一遍，都是数学性质太强，暂时用不到
     */
    private void ti3(){
        System.out.println("copySign："+Math.copySign(-1.2,0.12));//返回第一个参数的值，带有第二个参数的符号（正负值）
        System.out.println("decrementExact："+Math.decrementExact(0));//返回参数递减1，这个方法搞笑；
        System.out.println("incrementExact："+Math.incrementExact(0));//返回参数递减1，这个方法搞笑；
        System.out.println("floorDiv："+Math.floorDiv(8,3));//返回小于或等于代数商的最大值
        System.out.println("floorMod："+Math.floorMod(7,3));//返回小于两个数的模的最大值
        System.out.println("multiplyExact："+Math.multiplyExact(7,3));//计算两个数的乘积，超过最大值报错，也没啥用
        System.out.println("negateExact："+Math.negateExact(-7));//返回一个数的相反数
        System.out.println("nextAfter："+Math.nextAfter(1,-1));//第二个参数方向上第一个参数旁边的浮点数
    }
}
