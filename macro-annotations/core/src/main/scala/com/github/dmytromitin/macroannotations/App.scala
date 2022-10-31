package com.github.dmytromitin.macroannotations

import Macros.generateFoo

object App {
  @generateFoo
  object MyObject

  def main(args: Array[String]): Unit = {
    MyObject.foo()
  }
}
