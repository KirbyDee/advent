package com.kirbydee.advent.day6

import com.kirbydee.advent.AdventMainTrait

import scalaz.\/

object Main extends AdventMainTrait[Option[Point], Int, Int] {

    override val day: Int = 6

    override def mapStimuli(stimuli: String): Option[Point] =
        PointParser parse stimuli

    override def part1(stimuli: List[Option[Point]]): AdventError \/ Int =
        ManhattanCalculator calculatePart1 stimuli.flatten
}