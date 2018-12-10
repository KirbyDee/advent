package com.kirbydee.advent.day6

import scala.language.postfixOps
import scala.util.parsing.combinator.RegexParsers

object PointParser extends RegexParsers {

    def points: Parser[Point] =
        point~","~point ^^ {
            case x~_~y => Point(x, y)
        }

    def point: Parser[Int] =
        "[0-9]+".r ^^ (_.toInt)

    def parse(string: String): Option[Point] = parseAll(points, string) match {
        case Success(result, _) => Some(result)
        case NoSuccess(_, _)    => None
    }
}