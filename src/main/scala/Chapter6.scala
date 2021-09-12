/**
 * @author tr
 * @Date 9/9/21
 * @Desc 函数式对象 Rational对象
 *
 *      1. 和java 不一样 scala类就可以接受参数  这里构建分数，n是分子，d是分母
 *      1.1 重写toString方法
 *      1.2 可以写前置条件，这里决定分母d不可以为0
 *      1.3 类里面可以访问n和d，但是不可以访问其他对象的n和d，所以要把n和d做成字段
 *      1.4 辅助构造方法：一个类可能要多个构造方法，非主构造方法的成为辅助构造方法
 *       NOTE: 辅助构造函数的第一条语句必须是this(...)即调用主构造方法
 *      1.5 需要简化分子和分母 要算出最大公约数 gcd(辗转取余法)
 *      1.6 定义操作符
 *      1.7 操作符重载改造下，让操作符可以接受整数
 *      1.8 创建隐式转换  现在我们可以写 r1 * 3 但是还不可以写 3*r1，那就弄个能把3转为Rational类型的东西
 */

// 1. 和java 不一样 scala类就可以接受参数  这里构建分数，n是分子，d是分母
class Rational(n: Int, d: Int) {
  // 1.1 重写toString方法
  override def toString: String = s"$number/$denom"

  // 1.2 可以写前置条件，这里决定分母d不可以为0
  require(d != 0)
  // 1.3 类里面可以访问n和d，但是不可以访问其他对象的n和d，所以要把n和d做成字段
  // g:最大公约数，修改number和denom的定义 让他们都除g，这里为了让n/g 的g有值要吧g的初始化放在前面
  private val g = gcd(n.abs, d.abs)
  val number: Int = n / g
  val denom: Int = d / g

  // 1.3.1 现在可以编写add方法了
  def add(that: Rational): Rational = {
    new Rational(this.number * that.denom + that.number * this.denom, that.denom * this.denom)
  }

  // 1.4 辅助构造方法：一个类可能要多个构造方法，非主构造方法的成为辅助构造方法
  // NOTE: 辅助构造函数的第一条语句必须是this(...)即调用主构造方法
  def this(n: Int) = this(n, 1)

  // 1.5 需要简化分子和分母 要算出最大公约数 gcd(辗转取余法)
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  // 1.6 定义操作符
  def +(that: Rational): Rational = this.add(that)

  def *(that: Rational): Rational = new Rational(this.number * that.number, this.denom * that.denom)

  // 1.7 操作符重载改造下，让操作符可以接受整数
  def +(i: Int): Rational = new Rational(this.number + i * this.denom, this.denom)

  def *(i: Int): Rational = new Rational(this.number * i, this.denom)


}

object Chapter6 extends App {
  val r1 = new Rational(1, 2)
  val r2 = new Rational(2, 3)
  val r3 = r1.add(r2)
  val r4 = r1 add r2
  println(r3)
  // 调用了辅助构造函数
  println(new Rational(99, 1))
  // 调用简化
  println(new Rational(66, 42))
  // 调用操作符
  println(r1 * r2)
  // 值得注意的是 scala中 * 的优先级更高，所以r1*r2先
  println(r1 + r1 * r2)
  // 传递整数
  println(r1 + 3)

  // 1.8 创建隐式转换  现在我们可以写 r1 * 3 但是还不可以写 3*r1，那就弄个能把3转为Rational类型的东西
  implicit def intToRational(x: Int) = new Rational(x)

  println(3 * r1)
}
