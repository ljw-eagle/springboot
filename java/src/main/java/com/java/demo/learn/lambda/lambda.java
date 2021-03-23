package com.java.demo.learn.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class lambda {
    public static void main(String args[]){
        test();
        test1();
        test2();
        test3();
    }
    /**
     * 1.用lambda表达式实现Runnable
     * lambda表达式替换了原来匿名内部类的写法，
     * 没有了匿名内部类繁杂的代码实现，而是突出了，真正的处理代码。
     * 最好的示例就是 实现Runnable 的线程实现方式了: 用() -> {}代码块替代了整个匿名类
     */
    public static void test() {
        //old
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类 实现线程");
            }
        }).start();

        //lambda写法
        new Thread(() -> System.out.println("java8 lambda实现线程")).start();
    }

    /**
     * 2.forEach 遍历集合
     *  使用 forEach方法，直接通过一行代码即可完成对集合的遍历
     */
    public static void test1(){
    List<Person> list = getPersonList();
    list.forEach(person -> System.out.println(person.toString()));
    }

    /**
     * 双冒号::  表示方法引用，可以引用其他方法
     */
    public static void test2(){
        List<Person> list = getPersonList();
        Consumer<Person> changeAge = person -> person.setAge(person.getAge()+3);
        list.forEach(changeAge);
        list.forEach(System.out::print);
    }

    /**
     * filter 对集合进行过滤
     * filter 可以根据传入的 Predicate 对象，对集合进行过滤操作，Predicate 实质就是描述了过滤的条件:
     */
    public static void test3(){
    List<Person> list = getPersonList();
    list.stream().filter(person -> person.getAge()>20).forEach(person -> System.out.println(person.toString()));
    }

    /**
     * 当需要通过 多个过滤条件对集合进行过滤时，可以采取两种方式：
     * 1.可以通过调用多次filter 通过传入不同的 Predicate对象来进行过滤
     * 2.也可以通过 Predicate 对象的 and  or 方法，对多个Predicate 对象进行 且 或 操作
     */
    @Test
    public void test4(){
        List<Person> list = getPersonList();
        Predicate<Person> ageFilter = e -> e.getAge() > 20;
        Predicate<Person> sexFilter = e -> e.getSex().equals("male");
        //多条件过滤
        list.stream().filter(ageFilter)
                .filter(sexFilter)
                .forEach(e -> System.out.println(e.toString()));
        System.out.println("----------------------------");
        // Predicate : and or
        list.stream().filter(ageFilter.and(sexFilter))
                .forEach(e -> System.out.println(e.toString()));
    }

    /**
     * limit 可以控制 结果集返回的数据条数：返回三条数据，返回年龄>20的前两条数据
     */
    @Test
    public void  test5(){
        List<Person> list = getPersonList();
        list.stream().limit(3).forEach(e -> System.out.println(e.toString()));

        System.out.println("----------------------------");
        list.stream().limit(2).filter(e -> e.getAge() > 20)
                .forEach(e -> System.out.println(e.toString()));
    }


    /**
     * 通过sorted，可以按自定义的规则，
     * 对数据进行排序，可以用两种写法，分别按 年龄 和 姓名排序
     */
    @Test
    public void test6(){
        List<Person> list = getPersonList();
        //年龄排序
        list.stream().sorted((p1,p2) -> (p1.getAge() - p2.getAge()))
                .forEach(e -> System.out.println(e.toString()));
        //姓名排序
        System.out.println("----------------------------");
        list.stream().sorted(Comparator.comparing(Person::getName))
                .forEach(e -> System.out.println(e.toString()));
    }

    /**
     * 6.max min 获取结果中 某个值最大最小的的对象
     *  max min 可以按指定的条件，获取到最大、最小的对象，当集合里有多个满足条件的最大最小值时，只会返回一个对象。
     * 如： 返回年龄最大的人
     */
    @Test
    public void test7(){
        List<Person> list = getPersonList();
        // 如果 最大最小值 对应的对象有多个 只会返回第一个
        Person oldest = list.stream().max(Comparator.comparing(Person::getAge)).get();
        System.out.println(oldest.toString());
    }

    /**
     * 7.map 与 reduce也是两个十分重要的方法，
     * map：对集合中的每个元素进行遍历，并且可以对其进行操作，转化为其他对象，如将集合中的每个人的年龄增加3岁
     */
    @Test
    public void test8(){
        List<Person> list = getPersonList();
        //将 每人的年龄 +3
        System.out.println("修改前：");
        list.forEach(e -> System.out.println(e.toString()));
        System.out.println("修改后：");
//        list.stream().map(person -> e.setAge(e.getAge() + 3 ))
//                .forEach(e -> System.out.println(e.toString()));

    }


    /**
     * reduce：也是对所有值进行操作，但它是将所有值，按照传入的处理逻辑，将结果处理合并为一个
     * 如：将集合中的所有整数相加，并返回其总和
     */
    @Test
    public void test9(){
        //第一个参数是上次函数执行的返回值（也称为中间结果），第二个参数是stream中的元素，
        // 这个函数把这两个值相加，得到的和会被赋值给下次执行这个函数的第一个参数。
        //要注意的是：第一次执行的时候第一个参数的值是Stream的第一个元素，第二个参数是Stream的第二个元素

        //将所有人的年龄加起来 求和
        List<Integer> ages = Arrays.asList(2,5,3,4,7);
        int totalAge = ages.stream().reduce((sum,age) -> sum + age).get();

        System.out.println(totalAge);
        //带 初始值的计算， 如果list没有元素 即stream为null 则直接返回初始值
        int totalAge1 = ages.stream().reduce(0,(sum,age) -> sum+age);
        List<Integer> initList = new ArrayList<>();
        int initTotalAge = initList.stream().reduce(0,(sum,age) -> sum+age);

        System.out.println("totalAge1: "+ totalAge1 + " initTotalAge: " + initTotalAge);
    }




















    private static List<Person> getPersonList(){
        Person p1 = new Person("liu",22,"male");
        Person p2 = new Person("zhao",21,"male");
        Person p3 = new Person("li",18,"female");
        Person p4 = new Person("wang",21,"female");
        List<Person> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        return list;
    }


    
    
    


}
