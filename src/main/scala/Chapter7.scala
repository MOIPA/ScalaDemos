import java.io.{File, FileNotFoundException}

import scala.io.Source

/**
 * @author tr
 * @Date 9/9/21
 * @Desc scala的内建控制
 *
 *      1. if表达式
 *      2. while循环
 *      3. for loop
 *      3.1 for里面的过滤器
 *      3.2 for的嵌套for
 *      3.3 产出集合yield，之前的例子都是副作用的 打印
 *      4. try catch finally throw
 *      5. match scala特有的一个控制结构 类似java的swtich 但是更为强大
 *       和switch的区别：1. case支持任何东西 2. 默认尾部break
 *      6. 变量的作用域  和java唯一区别是scala可以在嵌套的作用域内创建同名的变量
 *      7. 函数式编程的方式创建乘法表
 */

object Chapter7 extends App {

  // 1. if表达式
  val fileName = if (args.isEmpty) "data/wordcount.txt" else args(0)
  println(if (args.isEmpty) "xx" else "yy")

  // 2. while循环
  def getGcdLoop(x: Int, y: Int) = {
    var a = x;
    var b = y;
    while (a != 0) {
      val tmp = a;
      a = b % a;
      b = tmp;
    }
    b
  }

  println(getGcdLoop(12, 9))
  // 3. for loop
  val filesHere = (new File("./data")).listFiles
  for (file <- filesHere) print(file + " ")
  println()
  // 3.1 for里面的过滤器
  for (file <- filesHere if file.getName.endsWith(".txt") if file.isFile) print(s"$file ")
  println()

  // 3.2 for的嵌套for
  def grep(pattern: String) = {
    for (
      file <- filesHere
      if file.getName.endsWith(".txt");
      line <- Source.fromFile(file).getLines().toList;
      trimmed = line.trim
      if trimmed.matches(pattern)
    ) println(s"$file : $trimmed")
  }

  println("search content result:")
  grep(".*tzq.*")

  // 3.3 产出集合yield，之前的例子都是副作用的 打印
  def txtFiles = for (file <- filesHere; if file.getName.endsWith("txt")) yield file;
  println("txt files:")
  txtFiles.foreach(println)

  // 4. try catch finally throw
  val n = 11
  try {
    val half = if (n % 2 == 0) n / 2 else throw new RuntimeException("n must be even")
  } catch {
    case ex: FileNotFoundException => {} // 处理找不到文件的情况
    case ex: java.io.IOException => // 处理其他io错误
    case ex: RuntimeException => println("n must be even!!!")
  } finally {
    println("exception proceeded")
  }
  // 5. match scala特有的一个控制结构 类似java的swtich 但是更为强大
  // 和switch的区别：1. case支持任何东西 2. 默认尾部break
  val firstArg = if (args.nonEmpty) args(0) else ""
  val friend = firstArg match {
    case "salt" => "pepper"
    case "eggs" => "bacon"
    case _ => "nothing"
  }
  println(friend)
  // 6. 变量的作用域  和java唯一区别是scala可以在嵌套的作用域内创建同名的变量
  val a = 1;
  {
    // 遮挡了外部的a
    val a = 2;
  }

  // 7. 函数式编程的方式创建乘法表
  // 第n行产生的应该是 (n 到 9) * n
  def mkRowSeq(row: Int) = {
    for (i <- row to 9) yield {
      f"${i * row}%5s"
    }
  }

  def mkRow(row: Int) = "     " * (row - 1) + mkRowSeq(row).mkString

  def multiTable() = {
    val rows = for (i <- 1 to 9) yield mkRow(i)
    rows.mkString("\n")
  }

  println(multiTable())
}
