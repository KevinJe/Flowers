package com.example.flowers

import com.google.gson.Gson


interface Soft

// where 上界约束拥有多层类型元素
class Plate<T>(val t: T) where T : Fruit, T: Soft

open class Fruit

class Apple : Fruit()

class Banana : Fruit(), Soft

fun main() {
//    val applePlate = Plate(Apple())   // 报错，不满足上界约束条件
    val bananaPlate = Plate(Banana()) // 正确

    val list: MutableList<*> = ArrayList<Number>() // 正确
//    val list2: MutableList<*> = ArrayList<*>() // 错误， 不包含具体类型

    val numberList = mutableListOf<Number>()
    val intList = mutableListOf(1, 2, 3, 4)
    copyAll2(numberList, intList) // 报错

    numberList.forEach {
        println(it)
    }

    println(1.isInstanceOf<String>())
    println("string".isInstanceOf<Int>())
    println("string".isInstanceOf<String>())

    val json = """{"name":"Kevin","url":"https://juejin.cn/"}"""
    val listJson = """[{"name":"Kevin","url":"https://juejin.cn/"},{"name":"Kevin","url":"https://juejin.cn/"}]"""

    val blogBean = toBean<BlogBean>(json)
    val blogMap = toBean<Map<String, String>>(json)
    val blogBeanList = toBean<List<BlogBean>>(json)

    println(blogBean)
    println(blogMap)
    println(blogBeanList)

}

// out  本身带有出去的意思，本身带有倾向于取值操作的意思，用于泛型协变
// out 关键字就相当于 Java 中的<? extends T>，其作用就是限制了 from 不能用于接收值而只能向其取值，这样就避免了从 to 取出值
// 然后向 from 赋值这种不安全的行为了，即实现了泛型协变
fun <T> copyAll(to: MutableList<T>, from: MutableList<out T>) {
    to.addAll(from)
}

// in 本身带有进来的意思，本身带有倾向于传值操作的意思，用于泛型逆变
// in 关键字就相当于 Java 中的<? super T>，其作用就是限制了 to 只能用于接收值而不能向其取值，这样就避免了从 to 取出值
// 然后向 from 赋值这种不安全的行为了，即实现了泛型逆变
fun <T> copyAll1(to: MutableList<in T>, from: MutableList<T>) {
    to.addAll(from)
}

// Kotlin 中的集合框架分为两种大类：可读可写和只能读不能写
// 以 Java 中的 ArrayList 为例，Kotlin 将之分为了 MutableList 和 List 两种类型的接口
// 将 from 的类型声明从 MutableList<T>修改为 List<T> 后，可以发现 copyAll 方法就可以正常调用了
// 而 List 接口中的泛型已经使用 out 关键字进行修饰了，且不包含任何传入值并保存的方法，即 List 接口只支持读值而不支持写值，
// 其本身就已经满足了协变所需要的条件，因此copyAll 方法可以正常使用
fun <T> copyAll2(to: MutableList<T>, from: List<T>) {
    to.addAll(from)
}

// 从这也可以联想到, MutableList<*> 就相当于 MutableList<out Any?>了，两者都带有相同的限制条件：不允许写值操作，允许读值操作，
// 且读取出来的值只能当做 Any?进行处理
// Kotlin中的 星号投影 （*） 对应于 Java的类型通配符 （?）
fun printList(list: List<*>) {
    for (any in list) {
        println(any)
    }
}

// 由于类型擦除，Java 和 Kotlin 的泛型类型实参都会在编译阶段被擦除。而在 Kotlin 中存在一个额外手段可以来避免这个问题，即内联函数
// 用关键字 inline 标记的函数就称为内联函数，再用 reified 关键字修饰内联函数中的泛型形参，编译器在进行编译的时候便会将内联函数的
// 字节码插入到每一个调用的地方，当中就包括泛型的类型实参。而内联函数的类型形参能够被实化，就意味着我们可以在运行时引用实际的类型实参了
// 内联函数，用于判断一个对象是否是指定类型
inline fun <reified T> Any.isInstanceOf(): Boolean {
    return this is T
}

val gson = Gson()

// inline 和 reified 比较有用的一个场景是用在 Gson 反序列的时候。由于泛型运行时类型擦除的问题，目前用 Gson 反序列化泛型类时
// 步骤是比较繁琐的，利用 inline 和 reified 我们就可以简化很多操作
inline fun <reified T> toBean(json: String): T {
    return gson.fromJson(json, T::class.java)
}

data class BlogBean(val name: String, val url: String)