package com.github.dmytromitin.macroannotations

import scala.language.experimental.macros
import MacroMacros.mkMacroAnnotation

object Macros {
  @mkMacroAnnotation
  class generateFoo

//  @compileTimeOnly("enable macro annotations")
//  class generateFoo extends StaticAnnotation {
//    def macroTransform(annottees: Any*): Any = macro generateFoo.impl
//  }
//
//  object generateFoo {
//    def impl(c: blackbox.Context)(annottees: c.Tree*): c.Tree = {
//      import c.universe._
//
//      annottees match {
//        case ModuleDef(mods, tname, Template(parents, self, body)) :: Nil =>
//          ModuleDef(mods, tname, Template(parents, self,
//            DefDef(NoMods, TermName("foo"), Nil, List(Nil), TypeTree(typeOf[Unit]),
//              Apply(
//                typeOf[Predef.type].decl(TermName("println"))
//                  .asTerm.alternatives
//                  .find(_.asMethod.paramLists.map(_.map(_.typeSignature)) == List(List(typeOf[Any])))
//                  .get.asMethod,
//                Literal(Constant("foo"))
//              )
//            ) :: body
//          ))
////        case q"$mods object $tname extends { ..$earlydefns } with ..$parents { $self => ..$body }" :: Nil =>
////          q"""$mods object $tname extends { ..$earlydefns } with ..$parents { $self =>
////              def foo(): _root_.scala.Unit = _root_.scala.Predef.println("foo")
////              ..$body
////            }"""
//        case _ => c.abort(c.enclosingPosition, "This annotation only supports objects")
//      }
//    }
//
//  }


}
