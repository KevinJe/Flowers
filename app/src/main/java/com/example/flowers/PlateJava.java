package com.example.flowers;

import java.util.ArrayList;
import java.util.List;

public class PlateJava {

    // 直接声明为List，无具体泛型参数
    public static void printList1(List list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }

    // 声明泛型类型为Object，但是即使String是Object的子类，但List<String>和List<Object>仍然不具备从属关系，
    // 导致printList2只能用于一种List<Object>
    public static void printList2(List<Object> list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }

    // 使用Java的类型通配符 ? ，这种写法完全可行，编译器也不会报错
    // ？ 表示我们并不关心具体的泛型类型，而只是想配合其它类型进行一些条件限制
    // 但其并不包含具体的泛型类型，所以我们从中取出的值只能是 Object 类型，且无法向其插入值，这都是为了避免发生 ClassCastException
    public static void printList3(List<?> list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }

    // 泛型协变，如果A是B的子类，那么Generic<A>就是Generic<B>的子类。本质上来说，如果A是B的子类型，那么Generic<A>
    // 不一定是Generic<B>的子类，只是协变禁止了A类型的写入操作，只能读取，也就是现在的操作是安全的，使其看起来像是Generic<A>就是Generic<B>
    // ? extends T 表示 from 接受 T 或者 T 的子类型，而不单单是 T 自身，这意味着我们可以安全地从 from
    // 中取值并声明为 T 类型，但由于我们并不知道 T 代表的具体类型，写入操作并不安全，因此编译器会阻止我们向 from
    // 执行传值操作。有了该限制后，从integerList中取出来的值只能是当做 Number 类型，且避免了向integerList插入非法值的
    // 可能，此时List<Integer>就相当于List<? extends Number>的子类型了，从而使得 copyAll 方法可以正常使用
    private static <T> void copyAll(List<T> to, List<? extends T> from) {
        to.addAll(from);
    }

    // ? super T表示 to 接收 T 或者 T 的父类型，而不单单是 T 自身，这意味着我们可以安全地向 to 传类型为 T 的值，
    // 但由于我们并不知道 T 代表的具体类型，所以从 to 取出来的值只能是 Object 类型。
    // 有了该限制后，integerList只能向 numberList传递类型为 Integer 的值，且避免了从 numberList 中获取到非法类型值的可能，
    // 此时 List<Number>就相当于List<? extends Integer>的子类型了，从而使得 copyAll 方法可以正常使用
    // 泛型逆变，如果 A 是 B 的子类，那么 Generic<B> 就是 Generic<A> 的子类型
    // 带 super 限定了下界的通配符类型使得泛型参数类型是逆变的，即如果 A 是 B 的子类，那么 Generic<B> 就是 Generic<A> 的子类型
    private static <T> void copyAll1(List<? super T> to, List<T> from) {
        to.addAll(from);
    }

    public static void main(String[] args) {
        ArrayList<Number> numberList = new ArrayList<>();

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);

        copyAll(numberList, integerList);
        copyAll1(numberList, integerList);
    }
}
