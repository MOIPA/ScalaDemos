import java.io.{File, PrintWriter}

/**
 * @author tr
 * @Date 9/18/21
 * @Desc  控制抽象
 *
 *        1. 传递函数值作为参数
 *        1.1 定义一个接受函数值（检查文件名是否满足条件的函数）的函数 matcher
 *        1.2 有了这个fileMathching方法后可以快速建立其他方法
 *        1.3 使用闭包进一步简化
 *        建议简写
 *        2. 调用scala提供的api简写
 *        3. 柯里化 示例
 *        4. 可以配合占位符  _表示第二个参数列表
 *        5. 打开文件，操作，释放资源的demo，可以将打开和释放整合在一起，中间的操作抽象出来接受函数
 *        5.1 这个技巧也叫作贷出模式
 *        5.1 如果只有一个参数，是可以使用花括号代替括号的
 *        5.1.1 首先柯里化
 *        参数使用花括号
 *        6. 传名参数
 *        6.1 不用传名参数的例子 接受predicate函数
 *        6.2 传名参数 删了()就行了，这样调用的时候也不用写了
 */

// 1. 传递函数值作为参数
// 这里编写了三个拥有很多重复代码的方法 用于文件查找
object FileMatcher {
  private def filesHere = (new File("data")).listFiles

  def filesEnding(query: String) = {
    for (file <- filesHere; if file.getName.endsWith(query)) yield file
  }

  def fileContaing(query: String) = {
    for (file <- filesHere; if file.getName.contains(query)) yield file
  }

  def filesRegex(query: String) = {
    for (file <- filesHere; if file.getName.matches(query)) yield file
  }

  // 1.1 定义一个接受函数值（检查文件名是否满足条件的函数）的函数 matcher
  def fileMatching(query: String, matcher: (String, String) => Boolean) = {
    for (file <- filesHere; if matcher(file.getName, query)) yield file
  }

  // 1.2 有了这个fileMathching方法后可以快速建立其他方法
  def fileEndingNew(query: String) = fileMatching(query, _.endsWith(_))

  def fileContainsNew(query: String) = fileMatching(query, _.contains(_))

  // 1.3 使用闭包进一步简化
  def fileMatchingNew(matcher: (String) => Boolean) = {
    for (file <- filesHere; if matcher(file.getName)) yield file
  }

  def fileRegexNew(query: String) = {
    fileMatchingNew(_.matches(query))
  }
}

object Chapter9 extends App {

  // 建议简写
  val res = FileMatcher.fileMatching("access", (name: String, query: String) => {
    name.contains(query)
  })
  val resn = FileMatcher.fileMatching("access", _.contains(_))
  res.foreach(println(_))

  // 2. 调用scala提供的api简写
  val list = List(1, 2, 3, -1)
  // 是否存在奇数
  list.exists(_ % 2 == 1)

  // 3. 柯里化 示例
  def curriedSum(x: Int)(y: Int) = x + y

  curriedSum(1)(2)
  // 4. 可以配合占位符  _表示第二个参数列表
  val onePlus = curriedSum(1) _
  onePlus(3)

  // 5. 打开文件，操作，释放资源的demo，可以将打开和释放整合在一起，中间的操作抽象出来接受函数
  // 5.1 这个技巧也叫作贷出模式
  def withPrintWriter(file: File, op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  withPrintWriter(new File("data/wordcountx.txt"), writer => writer.println(new java.util.Date))
  // 5.1 如果只有一个参数，是可以使用花括号代替括号的
  // 5.1.1 首先柯里化

  def withPrintWriterCurrying(file: File)(op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  val writeCurrying = withPrintWriterCurrying(new File("data/currying.txt")) _
  writeCurrying(_.println("this currying"))
  // 参数使用花括号
  writeCurrying {
    _.println("this currying 1")
  }
  withPrintWriterCurrying(new File("data/currying.txt")) {
    _.println("this currying 2")
  }

  // 6. 传名参数
  // 6.1 不用传名参数的例子 接受predicate函数
  def myAssert(predicate: () => Boolean) = {
    if (!predicate()) throw new AssertionError
  }

  myAssert(() => 2 > 1) // 抛出异常  但是（）=> 这么写不好看

  // 6.2 传名参数 删了()就行了，这样调用的时候也不用写了
  def byNameAssert(predicate: => Boolean) = {
    if (!predicate) throw new AssertionError
  }

  byNameAssert(2 > 1)

}
