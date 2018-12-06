package com.kirbydee.advent.day5

import com.kirbydee.advent.AdventMainTrait

import scalaz.\/

object Main extends AdventMainTrait[String, Int, Int] {

    override val day: Int = 5

    override def mapStimuli(stimuli: String): String =
        stimuli

    override def part1(stimuli: List[String]): AdventError \/ Int =
        PolymerCalculator calculatePart1 stimuli.head

    override def part2(stimuli: List[String]): AdventError \/ Int =
        PolymerCalculator calculatePart2 stimuli.head
}