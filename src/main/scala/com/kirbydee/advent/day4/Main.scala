package com.kirbydee.advent.day4

import com.kirbydee.advent.AdventMainTrait

import scalaz.\/

object Main extends AdventMainTrait[Option[GuardAction], Int, String] {

    override val day: Int = 4

    override def mapStimuli(stimuli: String): Option[GuardAction] =
        GuardActionParser parse stimuli

    override def part1(stimuli: List[Option[GuardAction]]): AdventError \/ Int =
        GuardCalculator calculatePart1 stimuli.flatten
//
//    override def part2(stimuli: List[BoxID]): AdventError \/ String =
//        BoxIDCalculator calculatePart2 stimuli
}