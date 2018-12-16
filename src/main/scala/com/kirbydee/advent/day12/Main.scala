package com.kirbydee.advent.day12

import com.kirbydee.advent.AdventMainTrait

import scalaz.\/

object Main extends AdventMainTrait[Option[PlantAction], Int, Long] {

    override val day: Int = 12

    override def mapStimuli(stimuli: String): Option[PlantAction] =
        PlantActionParser parse stimuli

    override def part1(stimuli: List[Option[PlantAction]]): AdventError \/ Int =
        PlantCalculator calculatePart1 stimuli.flatten

    override def part2(stimuli: List[Option[PlantAction]]): AdventError \/ Long =
        PlantCalculator calculatePart2 stimuli.flatten
}