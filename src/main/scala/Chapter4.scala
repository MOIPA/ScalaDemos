/**
 * @author tr
 * @Date 9/9/21
 * @Desc 类和对象
 *  1. 所有入参都是val 而不是 var
 *  1.1 这个add方法是有副作用的（修改了sum的值） 称为过程（procedure）
 *  2. return可以不写
 *  3. scala中不存在静态成员，使用单例来完成，且 和类同名的单例为类的伴生对象，类为单例的伴生类。他们可以互相访问私有成员
 *       若单例没有同名类，则是孤立对象
 *  3.1 伴生对象
 *  4. 单例可以作为main函数入口
 *  4.1 不继承App的话需要手动实现main函数
 *  5. 使用刚刚的checkSum方法
 *
 */
class ChecksumAccumulator {
  private var sum = 0

  // 1. 所有入参都是val 而不是 var
  // 1.1 这个add方法是有副作用的（修改了sum的值） 称为过程（procedure）
  def add(b: Byte) = sum += b;

  // 2. return可以不写
  def checksum(): Int = ~(sum & 0xFF) + 1
}

// 3. scala中不存在静态成员，使用单例来完成，且 和类同名的单例为类的伴生对象，类为单例的伴生类。他们可以互相访问私有成员
// 若单例没有同名类，则是孤立对象

import scala.collection.mutable

// 3.1 伴生对象
object ChecksumAccumulator {
  private val cache = mutable.Map.empty[String, Int]

  def calculate(s: String): Int =
    if (cache.contains(s)) cache(s)
    else {
      val acc = new ChecksumAccumulator
      for (c <- s) acc.add(c.toByte)
      val cs = acc.checksum()
      cache += (s -> cs)
      cs
    }
}

// 4. 单例可以作为main函数入口
// 4.1 不继承App的话需要手动实现main函数
//object Summer{
//  def main(args: Array[String]): Unit = {
//
//  }
//}

object Chapter4 extends App {
  val acc = new ChecksumAccumulator
  val csa = new ChecksumAccumulator
  // 5. 使用刚刚的checkSum方法

  import ChecksumAccumulator.calculate;
  println(calculate("hello world"))

}
