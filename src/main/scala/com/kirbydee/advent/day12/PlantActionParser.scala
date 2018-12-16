package com.kirbydee.advent.day12

import scala.language.postfixOps
import scala.util.parsing.combinator.RegexParsers

object PlantActionParser extends RegexParsers {

    private def plantAction: Parser[PlantAction] =
        (initialState | spreadAction) ^^ identity

    private def initialState: Parser[InitialState] =
        "initial state: "~> rep[Pot](pot) ^^ InitialState.apply

    private def spreadAction: Parser[SpreadAction] =
        pot~pot~pot~pot~pot~"=>"~pot ^^ {
            case ll~l~c~r~rr~_~n => SpreadAction(ll, l, c, r, rr, n)
        }

    private def pot: Parser[Pot] =
        plant | noPlant

    private def plant: Parser[Pot] =
        "#" ^^^ Plant

    private def noPlant: Parser[Pot] =
        "." ^^^ NoPlant

    def parse(string: String): Option[PlantAction] = parseAll(plantAction, string) match {
        case Success(result, _) => Some(result)
        case NoSuccess(_, _)    => None
    }
}