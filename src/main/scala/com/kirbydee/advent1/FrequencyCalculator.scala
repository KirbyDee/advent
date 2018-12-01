package com.kirbydee.advent1

import com.kirbydee.advent1.Frequency.FrequencyHelper

import scala.annotation.tailrec
import scalaz.Scalaz._
import scalaz.\/

case object FrequencyCalculator {

    def calculatePart1(): Frequency = {
        val stimuli = Stimuli.load
        stimuli.fold(Frequency.ZERO)(_ + _)
    }

    type FrequencyError =  String

    def calculatePart2(tries: Int): FrequencyError \/ Frequency = {
        val stimuli = Stimuli.load
        calculatePart2(Frequency.ZERO, stimuli)(tries)
    }

    private def calculatePart2(start: Frequency, rest: List[Frequency])(tries: Int): FrequencyError \/ Frequency = {
        @tailrec
        def go(current: Frequency, stimuli: List[Frequency], reached: Set[Frequency])(t: Int): FrequencyError \/ Frequency =
            (t <= 0, reached.contains(current), stimuli) match {
                case (true, _, _)    => s"Could not find any Solution after $tries tries".left
                case (_, true, _)    => current.right
                case (_, _, Nil)     => go(current, rest, reached)(t - 1)
                case (_, _, f :: fs) => go(current + f, fs, reached + current)(t - 1)
            }
        go(start, rest, Set.empty[Frequency])(tries)
    }
}