package com.kirbydee.advent.day10

import scala.language.postfixOps
import scala.util.parsing.combinator.RegexParsers

object StarParser extends RegexParsers {

    private def star: Parser[Star] =
        position~velocity ^^ {
            case p~v => Star(p, v)
        }

    private def position: Parser[(Int, Int)] =
        point("position")

    private def velocity: Parser[(Int, Int)] =
        point("velocity")

    private def point(tpe: String): Parser[(Int, Int)] =
        s"$tpe=<"~> number~","~number <~">" ^^ {
            case x~_~y => (x.toInt, y.toInt)
        }

    private def number: Parser[String] =
        "-?[0-9]+".r ^^ identity

    def parse(string: String): Option[Star] = parseAll(star, string) match {
        case Success(result, _) => Some(result)
        case NoSuccess(_, _)    => None
    }
}