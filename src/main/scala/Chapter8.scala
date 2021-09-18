/**
 * @author tr
 * @Date 9/16/21
 * @Desc  函数和闭包
 *
 *
 *        1. scala 中可以在函数内部定义函数，就如同定义局部变量
 *        1.1 这里的局部函数还是传递了参数 没必要，局部函数可以直接访问外部参数
 *        2. 一等函数 （可匿名，可作为值传递，函数字面量 和 函数值的关系 = 类和对象关系
 *        2.1 函数值可以被存放在对变量中
 *        2.2 scala很多函数可以传递函数值,很类似java的lambda
 *        3. scala占位符语法 要求每个参数只能出现一次，这样按顺序分配_和对应参数。 且有了占位符可以不写参数和箭头
 *        3.1 可以不写参数和箭头，但是有时要写明类型
 *        4. 部分应用函数 只给了部分参数或者没给参数
 *        4.1 这样会创建一个函数值返回给doSum变量
 *        4.2 如果不给参数 想转为函数值作为传递变量的话 可以不写下划线
 *        5. 闭包
 *        5.1 闭包内的外部变量是内外都可以改变的
 *        5.2 动态生成闭包：既然闭包和外部变量关联，每次创建的闭包的外部变量状态不同，那么每次的闭包保存的外部变量也不同
 *        5.3 不同闭包的more都不一样
 *        6. 特殊的函数调用形式
 *        6.1 重复参数 * 就是数组的简写
 *        6.1.1 但是重复参数不能传递一个数组，非要传递数组要 _* 表示每个元素单独传递
 *        6.2 带名字的参数  这个python也有很常见的语法
 *        6.3 默认参数（缺省参数）
 *        7. 尾递归
 *
 *
 */

class TestClass {
  def start = {

    // 1. scala 中可以在函数内部定义函数，就如同定义局部变量
    def processFile(fileName: String, width: Int) = {
      // 1.1 这里的局部函数还是传递了参数 没必要，局部函数可以直接访问外部参数
      def process(fileName: String, width: Int, line: String) = {
        if (line.length > width) println("out")
      }

      def processNoArg(line: String) = {
        println(fileName + width + line)
      }

      process("aa", 123, "asdf")
    }

  }
}

object Chpter8 extends App {
  val test = new TestClass

  // 2. 一等函数 （可匿名，可作为值传递，函数字面量 和 函数值的关系 = 类和对象关系
  (x: Int) => x + 1

  // 2.1 函数值可以被存放在对变量中

  val increase = (x: Int) => x + 1
  val increase2 = (x: Int) => {
    println("increasing")
    x + 1
  }
  increase(1) // increase 既是变量，也是函数 所以可以用括号调用

  // 2.2 scala很多函数可以传递函数值,很类似java的lambda
  val someNum = List(1, 2, 3)
  someNum.foreach(x => println(x))

  // 3. scala占位符语法 要求每个参数只能出现一次，这样按顺序分配_和对应参数。 且有了占位符可以不写参数和箭头
  someNum.filter(_ > 1)
  // 3.1 可以不写参数和箭头，但是有时要写明类型
  val f = (_: Int) + (_: Int)
  f(1, 2)

  // 4. 部分应用函数 只给了部分参数或者没给参数
  def sum(x: Int, y: Int, z: Int) = x + y + z

  // 4.1 这样会创建一个函数值返回给doSum变量
  val doSum = sum _
  doSum()
  val doSum2 = sum(1, _: Int, 3)
  doSum2(2)

  // 4.2 如果不给参数 想转为函数值作为传递变量的话 可以不写下划线
  def eat(x: Int) = print("eat a num")

  someNum.foreach(eat _)
  someNum.foreach(eat)

  // 5. 闭包
  (x: Int) => x + 1 // 这里没有引入新的外部变量，成为闭合语（闭合代码）
  val more = 1
  (x: Int) => x + more // 需要去确定more的值 成为闭包

  // 5.1 闭包内的外部变量是内外都可以改变的
  var sum = 0
  someNum.foreach((x) => sum = x + sum)
  someNum.foreach(sum += _)
  println("sum=" + sum)

  // 5.2 动态生成闭包：既然闭包和外部变量关联，每次创建的闭包的外部变量状态不同，那么每次的闭包保存的外部变量也不同
  def mkIncreaser(more: Int) = (x: Int) => x + more

  // 5.3 不同闭包的more都不一样
  val inc1 = mkIncreaser(1)
  val inc2 = mkIncreaser(10)
  println(inc1(1))
  println(inc2(1))

  // 6. 特殊的函数调用形式
  // 6.1 重复参数 * 就是数组的简写
  def echo(sths: String*) = {
    sths.foreach(println)
  }

  echo("hello", "world", "!")
  // 6.1.1 但是重复参数不能传递一个数组，非要传递数组要 _* 表示每个元素单独传递
  val arr1 = Array("hello", "world", "again!")
  echo(arr1: _*)

  // 6.2 带名字的参数  这个python也有很常见的语法
  def speed(distance: Float, time: Float) = {
    distance / time
  }

  printf(f"speed:${speed(distance = 100, time = 3)}%.3f\n")

  //6.3 默认参数（缺省参数）
  def printTime(out: java.io.PrintStream = Console.out, divisor: Int = 1) = {
    out.println("time" + System.currentTimeMillis() / divisor)
  }

  printTime(divisor = 10)
  printTime(out = Console.err)

  // 7. 尾递归
  def isGoodEnough(d: Double) = true

  def improve(d: Double) = 1

  def approximate(guess: Double): Double = {
    if (isGoodEnough(guess)) guess else approximate(improve(guess))
  }

}
