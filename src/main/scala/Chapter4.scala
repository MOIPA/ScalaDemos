/**
 * @author tr
 * @Date 9/8/21
 * @Desc
 * 1.1 数组  可变序列
 * 2. <- 的使用
 * 3. 操作符重载
 * 4. apply方法
 * 4.1 update方法
 * 5. list 不可变序列 和 :: 以及 Nil(空)
 * 5.1. ::: 符号拼接
 * 5.2  :: 新增
 * 5.3 List的删除遍历等方法
 * 6 元祖 tuple
 * 7 集合 set ，有不可变和可变版本，可变版本需要手动引入 import scala.collection.mutable
 * 8 映射 map 同样有可变不可变版本
 * 9 函数式编程
 * 9.3 最佳函数式
 * 10 读取文件
 */
object hello extends App {
  println("hello world")
  // 1. foreach 的三种使用方法
  args.foreach(println(_))
  args.foreach(arg => println(arg))
  args.foreach(println)
  // 1.1 数组  可变序列
  val greetings = new Array[String](3)
  greetings(0) = "1"
  greetings(1) = "2"
  greetings(2) = "3"
  val names = Array("tzq", "tr") // equals:  Array.apply("...
  // 2. <- 的使用
  for (i <- 0 to 2)
    print(greetings(i) + " ")
  // 3. 操作符重载
  println((1).+(1))
  // 4. apply方法
  println(greetings.apply(0))
  println(greetings(0))
  // 4.1 update方法
  greetings.update(0, "hi")
  greetings(0) = "hi"
  // 5. list 不可变序列 和 :: 以及 Nil(空)
  val oneTwoThree = List(1, 2, 3)
  val oneTwoThreeOther = 1 :: 2 :: 3 :: Nil
  val oneTwo = List(1, 2)
  val threeFour = List(3, 4)
  // 5.1. ::: 符号拼接
  val allNum = oneTwo ::: threeFour
  //  allNum(0) = 1; List不可变
  // 5.2  :: 新增
  val oneToFive = 0 :: allNum // NOTE: 如果方法名是:号结尾，默认调用者为右边的参数 equals: allNum.::(0)
  // 5.3 List的删除遍历等方法
  val thrill = "will" :: "all" :: "until" :: Nil
  thrill.count(_.length == 4) // 所有长度为4的数据的个数
  thrill.drop(2) // 删除头两个
  thrill.dropRight(2) // 删除后两个
  thrill.filter(_.length == 4) // 过滤出长度为4的数据
  thrill.filterNot(_.length == 4) // 过滤不为...
  thrill.exists(_ == "until") // 是否存在
  thrill.forall(_ == "all") // 是否全是
  thrill.head // 返回首个
  thrill.last // 最后
  thrill.init // 返回除了最后一个元素的剩下
  thrill.tail // 返回除了第一个元素的剩下
  thrill.map(_ + " ")
  thrill.mkString(",") // 返回用，号隔开的字符串
  thrill.reverse // 逆序
  thrill.sortWith(_.charAt(0) < _.charAt(0)) // 首字母排序
  // 6 元祖 tuple
  val pair = (99, "LuftBullons")
  println(pair._2)
  // 7 集合 set ，有不可变和可变版本，可变版本需要手动引入 import scala.collection.mutable
  var jetSet = Set("1", "2")
  jetSet += "3"
  // 8 映射 map 同样有可变不可变版本

  import scala.collection.immutable

  var myMap = immutable.Map[Int, String](1 -> "2人", 2 -> "3人")
  println(myMap(2))

  // 9 函数式编程
  // 9.1 指令风格
  def printArgs(args: Array[String]) = {
    var i = 0;
    while (i < args.length) {
      println(args(i))
      i += 1
    }
  }

  // 9.2 函数式
  def printArgsS(args: Array[String]) = {
    for (arg <- args) println(arg)
  }

  def printArgsThr(args: Array[String]) = args.foreach(println)

  // 9.3 最佳函数式
  def printArgsFor(args: Array[String]) = args.mkString("\n")

  // 10 读取文件

  import scala.io.Source

  //  for (line <- Source.fromFile("data/access_log.txt").getLines())
  //    println(line.length + line)
  // 10.1 对齐数字 找到最大长度
  val lines = Source.fromFile("data/wordcount.txt").getLines().toList

  def widthOfLength(s: String) = s.length.toString.length

  var maxWidth = 0
  for (line <- lines) maxWidth = maxWidth.max(widthOfLength(line))
  // 10.2 函数式找到最大长度
  val longestLine = lines.reduceLeft((a, b) => if (a.length > b.length) a else b)
  val maxWidthNew = widthOfLength(longestLine)
  // 10.3 格式化打印文件内容
  for (line <- lines) {
    val numSpace = maxWidthNew - widthOfLength(line)
    val padding = " " * numSpace
    println(padding + line.length + "|" + line)
  }

}
