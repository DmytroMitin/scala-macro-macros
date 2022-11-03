package com.github.dmytromitin.runtime

import scala.reflect.runtime
import scala.reflect.runtime.universe.Quasiquote
import scala.tools.reflect.ToolBox

object App {
  val rm = runtime.currentMirror
  val tb = rm.mkToolBox()

  def main(args: Array[String]): Unit = {
    println(tb.eval(q"1 + 1"))
    println(tb.eval(
      q"""
        import com.github.dmytromitin.runtime.App._
        tb.eval(tb.parse("1 + 1"))
      """))
    println(tb.eval(
      q"""
        import com.github.dmytromitin.runtime.App._
        tb.eval(tb.parse("import com.github.dmytromitin.runtime.App._; tb.eval(tb.parse(\"1 + 1\"))"))
      """))
  }
}
