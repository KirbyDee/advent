package com.kirbydee.advent.day1

import com.kirbydee.advent.AdventMainTrait

import scalaz.\/

object Main extends AdventMainTrait[Frequency, Frequency, Frequency] {

    override val day: Int = 1

    override def mapStimuli(stimuli: String): Frequency =
        Frequency(stimuli.toInt)

    override def part1(stimuli: List[Frequency]): AdventError \/ Frequency =
        FrequencyCalculator calculatePart1 stimuli

    override def part2(stimuli: List[Frequency]): AdventError \/ Frequency =
        FrequencyCalculator calculatePart2 stimuli
}