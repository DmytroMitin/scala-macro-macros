package com.github.dmytromitin.defmacros

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object Macros {
  def sayHello(): Unit = macro sayHelloImpl

  def sayHelloImpl(c: blackbox.Context)(): c.Tree = {
    MacroMacros.sayHelloMacroMacro(c).asInstanceOf[c.Tree]
    //import c.universe._
    //q"""_root_.scala.Predef.println("hello")"""
    //Apply(Select(Select(Select(Ident(termNames.ROOTPKG), TermName("scala")), TermName("Predef")), TermName("println")), List(Literal(Constant("hello"))))
  }
}
