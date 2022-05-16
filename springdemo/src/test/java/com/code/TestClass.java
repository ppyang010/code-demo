package com.code;


import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

public class TestClass {
    final private String x = new String();
    private int m;

    public int inc() {
        //命令式编程方式
        try {
            //action
        } catch (Exception e) {
            //error

        } finally {
            //complete
        }
        return m + 1;
    }

    public static void stream() {

        Optional<Integer> total1 = Stream.of(1, 2, 3, 4, 5).reduce((x, y) -> {
            System.out.println("x=" + x);
            System.out.println("y=" + y);
            return x + y;
        });
        System.out.println(total1.get());
        System.out.println("--------------------");
        Integer total2 = Stream.of(1, 2, 3, 4, 5).reduce(10, (x, y) -> {
            System.out.println("x=" + x);
            System.out.println("y=" + y);
            return x + y;
        });
        System.out.println(total2);
    }

    public static void flux() {

    }

    public static void main(String[] args) {
//        stream();
        //null 使用 instanceof 不会报错并返回false
        System.out.println(null instanceof TestClass);

        //枚举test
        System.out.println(Color.RED.name());
        System.out.println(Color.RED.ordinal());
        System.out.println(Color.RED);

        Color.valueOf("RED");

        Color.values();
        System.out.println("test()=" + test());
        fanxin.fun(1);
        System.out.println(0b11111111111111111111111111111111);
        System.out.println(Integer.toBinaryString(1<<31));

        new BigDecimal("100.1");
        Integer a =100;
        Integer b = 100;
        System.out.println(new Integer(200)==(a+b));
        new String();
        new B();
        System.out.println(System.getProperty("java.class.path"));

        ThreadLocal local = new ThreadLocal();
        local.set("ggod");
        local.get();
        local.remove();
        new LinkedList<>();
        ArrayList<Object> listTest = new ArrayList<>();
        Iterator<Object> iterator = listTest.iterator();
        new Vector<>();
        HashSet<Object> objects = new HashSet<>();
        objects.add("good");
    }

    private static int test() {
        int x = 0;
        try {
            x = 1;
            return x;
        } finally {
            x = 2;
//            return x;
        }
    }

    public static final class fanxin<T> {
        private T x;

        public static <V> V fun(V v) {
            return v;
        }

        public T fun2(T v) {
            return v;
        }
    }

    public static class A{
        public A() {
            System.out.println("A 构造方法");
        }
    }

    public static class B extends A{
        {
            System.out.println("B 实例初始化方法");
        }
        public B(){
            super();
            System.out.println("B 构造方法");
        }
    }
}



