package com.kirbydee.advent.day2

import com.kirbydee.advent.AdventMainTrait

import scalaz.\/

object Main extends AdventMainTrait[BoxID, Int, String] {

    override val day: Int = 2

    override def mapStimuli(stimuli: String): BoxID =
        BoxID(stimuli)

    override def part1(stimuli: List[BoxID]): AdventError \/ Int =
        BoxIDCalculator calculatePart1 stimuli

    override def part2(stimuli: List[BoxID]): AdventError \/ String =
        BoxIDCalculator calculatePart2 stimuli
}