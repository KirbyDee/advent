package com.kirbydee.advent.day2

import scala.annotation.tailrec
import scalaz.Scalaz._
import scalaz.{Failure, Success, \/}

case object BoxIDCalculator {

    type Checksum = Int

    def calculatePart1(stimuli: List[BoxID]): Main.AdventError \/ Checksum = {
        @tailrec
        def go(list: List[BoxID], doubleCount: Int, tripletCount: Int): Main.AdventError \/ Checksum = list match {
            case boxId :: boxIds => go(boxIds, doubleCount + toInt(boxId.hasDuet), tripletCount + toInt(boxId.hasTriplet))
            case Nil             => (doubleCount * tripletCount).right
        }
        go(stimuli, 0, 0)
    }

    private def toInt(boolean: Boolean): Int = boolean match {
        case true  => 1
        case false => 0
    }

    def calculatePart2(stimuli: List[BoxID]): Main.AdventError \/ String = {
        @tailrec
        def go(boxIds: List[BoxID]): Main.AdventError \/ String = boxIds match {
            case bid :: bids => bid compare bids match {
                case Success(result) => result.right
                case Failure(_)      => go(bids)
            }
            case Nil         => "Could not find any Solution".left
        }
        go(stimuli)
    }
}