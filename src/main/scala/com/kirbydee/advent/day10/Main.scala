package com.kirbydee.advent.day10

import com.kirbydee.advent.AdventMainTrait

import scalaz.\/

object Main extends AdventMainTrait[Option[Star], Sky, Int] {

    override val day: Int = 10

    override def mapStimuli(stimuli: String): Option[Star] =
        StarParser parse stimuli

    override def part1(stimuli: List[Option[Star]]): AdventError \/ Sky =
        StarCalculator calculatePart1 stimuli.flatten

    override def part2(stimuli: List[Option[Star]]): AdventError \/ Int =
        StarCalculator calculatePart2 stimuli.flatten
}