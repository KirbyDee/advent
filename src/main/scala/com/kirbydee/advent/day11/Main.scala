package com.kirbydee.advent.day11

import com.kirbydee.advent.AdventMainTrait

import scalaz.\/

object Main extends AdventMainTrait[Int, (Int, Int), (Int, Int, Int)] {

    override val day: Int = 11

    override def mapStimuli(stimuli: String): Int =
        stimuli.toInt

    override def part1(stimuli: List[Int]): AdventError \/ (Int, Int) =
        FuelCalculator calculatePart1 stimuli.head

    override def part2(stimuli: List[Int]): AdventError \/ (Int, Int, Int) =
        FuelCalculator calculatePart2 stimuli.head
}