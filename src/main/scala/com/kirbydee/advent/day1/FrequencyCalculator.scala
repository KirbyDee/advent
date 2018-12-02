package com.kirbydee.advent.day1

import Frequency.FrequencyHelper
import scala.annotation.tailrec
import scalaz.Scalaz._
import scalaz.\/

case object FrequencyCalculator {

    def calculatePart1(stimuli: List[Frequency]): Main.AdventError \/ Frequency =
        stimuli.fold(Frequency.ZERO)(_ + _).right

    def calculatePart2(stimuli: List[Frequency]): Main.AdventError \/ Frequency = {
        calculatePart2(Frequency.ZERO, stimuli)(tries = 200)
    }

    private def calculatePart2(start: Frequency, rest: List[Frequency])(tries: Int): Main.AdventError \/ Frequency = {
        @tailrec
        def go(current: Frequency, stimuli: List[Frequency], reached: Set[Frequency])(t: Int): Main.AdventError \/ Frequency =
            stimuli match {
                case _ if t <= 0 => s"Could not find any Solution after $tries tries".left
                case f :: fs     => current + f match {
                    case newF if reached contains newF  => newF.right
                    case newF                           => go(newF, fs, reached + newF)(t)
                }
                case Nil         =>
                    go(current, rest, reached)(t - 1)
            }
        go(start, rest, Set.empty[Frequency])(tries)
    }
}