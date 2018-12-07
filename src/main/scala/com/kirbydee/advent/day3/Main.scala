package com.kirbydee.advent.day3

import com.kirbydee.advent.AdventMainTrait

import scalaz.\/

object Main extends AdventMainTrait[Option[Box], Int, Int] {

    override val day: Int = 3

    override def mapStimuli(stimuli: String): Option[Box] =
        BoxParser parse stimuli

    override def part1(stimuli: List[Option[Box]]): AdventError \/ Int =
        BoxCalculator calculatePart1 stimuli.flatten

    override def part2(stimuli: List[Option[Box]]): AdventError \/ Int =
        BoxCalculator calculatePart2 stimuli.flatten
}