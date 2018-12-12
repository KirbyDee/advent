package com.kirbydee.advent.day7

import scala.language.postfixOps
import scala.util.parsing.combinator.RegexParsers

object TaskActionParser extends RegexParsers {

    def taskAction: Parser[TaskAction] =
        "Step"~task~"must be finished before step"~task~"can begin." ^^ {
            case _~taskBefore~_~taskAfter~_ => TaskAction(taskBefore, taskAfter)
        }

    def task: Parser[String] =
        "[A-Z]".r ^^ identity

    def parse(string: String): Option[TaskAction] = parseAll(taskAction, string) match {
        case Success(result, _) => Some(result)
        case NoSuccess(_, _)    => None
    }
}