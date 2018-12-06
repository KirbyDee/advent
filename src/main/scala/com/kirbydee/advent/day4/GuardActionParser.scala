package com.kirbydee.advent.day4

import java.text.SimpleDateFormat
import java.util.Date

import scala.language.postfixOps
import scala.util.parsing.combinator.RegexParsers

object GuardActionParser extends RegexParsers {

    def guardAction: Parser[GuardAction] =
        dateTime~action ^^ {
            case date~actionBuilder => actionBuilder(date)
        }

    def dateTime: Parser[Date] =
        "["~> date~time <~"]" ^^ {
            case date~time => new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(s"$date $time")
        }

    def date: Parser[String] =
        "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))".r ^^ identity

    def time: Parser[String] =
        "(2[0-3]|1[0-9]|0?[0-9]):([0-5][0-9]|0?[0-9])".r ^^ identity

    def action: Parser[Date => GuardAction] =
        beginShift | fallsAsleep | wakesUp

    def beginShift: Parser[Date => GuardAction] =
        "Guard #"~> id <~"begins shift" ^^ {
            case id => date: Date => BeginShift(id, date)
        }

    def fallsAsleep: Parser[Date => GuardAction] =
        "falls asleep" ^^ {
            case _ => date: Date => FallsAsleep(date)
        }

    def wakesUp: Parser[Date => GuardAction] =
        "wakes up" ^^ {
            case _ => date: Date => WakesUp(date)
        }

    def id: Parser[Int] =
        """0|[1-9][0-9]*(\.[0-9]+)?""".r ^^ (_.toInt)

    def parse(string: String): Option[GuardAction] = parseAll(guardAction, string) match {
        case Success(result, _) => Some(result)
        case NoSuccess(_, _)    => None
    }
}