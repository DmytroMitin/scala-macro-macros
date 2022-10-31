package com.github.dmytromitin.macroannotations

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object MacroMacros {
  @compileTimeOnly("enable macro annotations")
  class mkMacroAnnotation extends StaticAnnotation {
    def macroTransform(annottees: Any*): Any = macro mkMacroAnnotation.impl
  }

  object mkMacroAnnotation {
    def impl(c: blackbox.Context)(annottees: c.Tree*): c.Tree = {
      import c.universe._

      def impl0(cls: Tree, obj: Tree): Tree = (cls, obj) match {
        case (q"$mods class $tpname[..$tparams] $ctorMods(...$paramss) extends { ..$earlydefns } with ..$parents { $self => ..$stats }",
        q"$mods1 object $tname extends { ..$earlydefns1 } with ..$parents1 { $self1 => ..$body }") =>
          val newParents = parents.filter{ case tq"scala.AnyRef" => false; case _ => true} :+ tq"_root_.scala.annotation.StaticAnnotation"
          val newMods = mods.mapAnnotations(q"""new _root_.scala.annotation.compileTimeOnly("enable macro annotations")""" :: _)
          q"""
             $newMods class $tpname[..$tparams] $ctorMods(...$paramss) extends { ..$earlydefns } with ..$newParents { $self =>
               def macroTransform(annottees: _root_.scala.Any*): _root_.scala.Any = macro $tname.impl
               ..$stats
             }

             $mods1 object $tname extends { ..$earlydefns1 } with ..$parents1 { $self1 =>
               def impl(c: _root_.scala.reflect.macros.blackbox.Context)(annottees: c.Tree*): c.Tree = {
                 val u: c.universe.type = c.universe

                 annottees match {
                   case u.ModuleDef(mods, tname, u.Template(parents, self, body)) :: _root_.scala.Nil =>
                     u.ModuleDef(mods, tname, u.Template(parents, self,
                       u.DefDef(u.NoMods, u.TermName("foo"), _root_.scala.Nil, _root_.scala.List(_root_.scala.Nil), u.TypeTree(u.typeOf[_root_.scala.Unit]),
                         u.Apply(
                           u.typeOf[_root_.scala.Predef.type].decl(u.TermName("println"))
                             .asTerm.alternatives
                             .find(_.asMethod.paramLists.map(_.map(_.typeSignature)) == _root_.scala.List(_root_.scala.List(u.typeOf[_root_.scala.Any])))
                             .get.asMethod,
                           u.Literal(u.Constant("foo"))
                         )
                       ) :: body
                     ))
                    case _ => c.abort(c.enclosingPosition, "This annotation only supports objects")
                 }
               }
               ..$body
             }
          """
      }

      annottees match {
        case (cls@q"$mods class $tpname[..$tparams] $ctorMods(...$paramss) extends { ..$earlydefns } with ..$parents { $self => ..$stats }") ::
          (obj@q"$mods1 object $tname extends { ..$earlydefns1 } with ..$parents1 { $self1 => ..$body }") :: Nil =>
          impl0(cls, obj)
        case (cls@q"$mods class $tpname[..$tparams] $ctorMods(...$paramss) extends { ..$earlydefns } with ..$parents { $self => ..$stats }") :: Nil =>
          impl0(cls, q"object ${tpname.toTermName}")
        case _ => c.abort(c.enclosingPosition, "This annotation only supports classes")
      }
    }

  }

}
