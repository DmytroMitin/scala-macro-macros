package com.github.dmytromitin.defmacros

import scala.language.experimental.macros
import scala.reflect.api.Trees
import scala.reflect.macros.blackbox

object MacroMacros {
  def sayHelloMacroMacro(c1: blackbox.Context): Trees#Tree = macro sayHelloMacroMacroImpl

  def sayHelloMacroMacroImpl(c: blackbox.Context)(c1: c.Tree): c.Tree = {
    import c.universe._
    q"""
      val u = $c1.universe
      u.Apply(u.Select(u.Select(u.Select(u.Ident(u.termNames.ROOTPKG), u.TermName("scala")), u.TermName("Predef")), u.TermName("println")), _root_.scala.List(u.Literal(u.Constant("hello"))))
    """
  }
}
