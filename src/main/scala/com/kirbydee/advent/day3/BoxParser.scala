package com.kirbydee.advent.day3

import com.kirbydee.advent.day3.Box.Dimensions

import scala.language.postfixOps
import scala.util.parsing.combinator.RegexParsers

object BoxParser extends RegexParsers {

    def box: Parser[Box] =
        id~"@"~topLeft~":"~size ^^ {
            case id~_~topLeft~_~size => Box(id, topLeft, size)
        }

    def id: Parser[Int] =
        "#"~> number ^^ identity

    def topLeft: Parser[(Int, Int)] =
        number~","~number ^^ {
            case x~_~y => (x, y)
        }

    def size: Parser[Dimensions] =
        number~"x"~number ^^ {
            case width~_~height => (width, height)
        }

    def number: Parser[Int] =
        """0|[1-9][0-9]*(\.[0-9]+)?""".r ^^ (_.toInt)

    def parse(string: String): Option[Box] = parseAll(box, string) match {
        case Success(result, _) => Some(result)
        case NoSuccess(_, _)    => None
    }
}