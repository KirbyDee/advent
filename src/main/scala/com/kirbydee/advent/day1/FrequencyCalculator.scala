package com.kirbydee.advent.day1

import Frequency.FrequencyHelper
import scala.annotation.tailrec
import scalaz.Scalaz._
import scalaz.\/

case object FrequencyCalculator {

    def calculatePart1(stimuli: List[Frequency]): Main.AdventError \/ Frequency =
        stimuli.fold(Frequency.ZERO)(_ + _).right

    def calculatePart2(stimuli: List[Frequency]): Main.AdventError \/ Frequency = {
        calculatePart2(Frequency.ZERO, stimuli)(tries = 20)
    }

    private def calculatePart2(start: Frequency, rest: List[Frequency])(tries: Int): Main.AdventError \/ Frequency = {
        @tailrec
        def go(current: Frequency, stimuli: List[Frequency], reached: Set[Frequency])(t: Int): Main.AdventError \/ Frequency =
            (t <= 0, reached.contains(current), stimuli) match {
                case (true, _, _)    => s"Could not find any Solution after $tries tries".left
                case (_, true, _)    => current.right
                case (_, _, Nil)     => go(current, rest, reached)(t - 1)
                case (_, _, f :: fs) => go(current + f, fs, reached + current)(t - 1)
            }
        go(start, rest, Set.empty[Frequency])(tries)
    }
}