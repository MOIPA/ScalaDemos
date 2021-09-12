/**
 * @author tr
 * @Date 9/9/21
 * @Desc 基础类型和操作
 *
 * 1. java和scala的基础类型一致，且取值范围一致
 * 1.1 不同进制 打印的时候都是十进制结果
 * 2. 浮点数float和double 以及他们的指数形式
 * 3. 字符
 * 3.1 可以使用unicode表示字符
 * 3.2 可以用unicode命名变量 虽然ide爆红了，但是可以通过编译的
 * 4. 字符串 可以转义字符
 * 4.1  """ ... """ 可以用来显示原始字符，不转义
 * 4.2 s字符串插值 $符号里面的内容会被先计算,可以用{}的
 * 4.3 raw 字符串插值 唯一和 s的区别是不识别转义
 * 4.4 f字符串插值 可以变成c语言里面的printf风格
 * 4.5 prinf 和c语言的一样
 * 5. 符号字面量 写法： '...  类型是symbol scala特有的 比起string来说效率高很多，因为同名的symbol必定指向一个类型，而String判断是否相同要逐个比对字符
 * 6. Boolean
 * 7. 操作符就是方法 符号重载  只有四个前缀操作符可以被重载 ~ + - !
 * 7.1 方法是可以不用 . 和 括号的 这是scala的一种简写 比如 s.indexOf('o')
 * 7.2 多参数不可以简写 indexOf('0',start) start:表示从第几个开始找
 * 8. 关系逻辑 || 和 && 可以只算部分 & 和 | 会全都算
 * 8.1 == 比较可以和 null直接比较，scala默认做了null比较处理, 并且scala是比较内容的，也可以比较对象引用 eq,ne
 */
object Chapter5 extends App {
  // 1. java和scala的基础类型一致，且取值范围一致
  // 1.1 不同进制 打印的时候都是十进制结果
  val hex = 0xaf; // 16进制 Int
  val prog = 0xafL; // 16进制 Long
  // 2. 浮点数float和double 以及他们的指数形式
  val big = 1.23e2 // double类型 1.23 * 10^2 = 123
  val bigf = 1.23e2f // float类型，只有f结尾才是float 否则默认double,double结尾也可以加上D
  println(big)
  // 3. 字符
  // 3.1 可以使用unicode表示字符
  val a = 'A'
  val d = '\u0041'
  println(d)
  // 3.2 可以用unicode命名变量 虽然ide爆红了，但是可以通过编译的
  //  val B\u0041\u0044 = 1 // BAD
  //  print(B\u0041\u0044)

  // 4. 字符串 可以转义字符
  val hello = "hello \\"
  println(hello)
  // 4.1  """ ... """ 可以用来显示原始字符，不转义
  println(
    """this is
      |my life
      |""".stripMargin)

  // 4.2 s字符串插值 $符号里面的内容会被先计算,可以用{}的
  val name = "reader"
  println(s"hello \',$name!")
  println(s"hello \',${name}!")
  println(s"1+1 = ${1 + 1}")
  // 4.3 raw 字符串插值 唯一和 s的区别是不识别转义
  println(raw"hello \',$name!")
  // 4.4 f字符串插值 可以变成c语言里面的printf风格
  println(f"PI is ${math.Pi}%.5f")
  // 4.5 prinf 和c语言的一样
  printf("hello %s\n", name)
  printf("PI is %f\n", math.Pi)
  printf("PI is %5.2f!\n", math.Pi)


  // 5. 符号字面量 写法： '...  类型是symbol scala特有的 比起string来说效率高很多，因为同名的symbol必定指向一个类型，而String判断是否相同要逐个比对字符
  def updateRecord(r: Symbol, value: Any) = {
    if (r == 't) 1 // 快速比较（相对于String）
  }

  updateRecord('t, 1)
  // 6. Boolean
  val isTrue = true;

  // 7. 操作符就是方法 符号重载  只有四个前缀操作符可以被重载 ~ + - !
  val sumMore = (1).+(2)
  val negative = (2).unary_- // equals = -2
  // 7.1 方法是可以不用 . 和 括号的 这是scala的一种简写 比如 s.indexOf('o')
  val s = "hello world"
  s toLowerCase;
  s indexOf 'o'
  1 + 1
  // 7.2 多参数不可以简写 indexOf('0',start) start:表示从第几个开始找
  s.indexOf('o', 3)

  // 8 关系逻辑 || 和 && 可以只算部分 & 和 | 会全都算
  // 8.1 == 比较可以和 null直接比较，scala默认做了null比较处理, 并且scala是比较内容的，也可以比较对象引用 eq,ne

}
