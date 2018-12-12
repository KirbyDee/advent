package com.kirbydee.advent.day8

import com.kirbydee.advent.AdventMainTrait

import scalaz.\/

object Main extends AdventMainTrait[Option[Node], Int, Int] {

    override val day: Int = 8

    override def mapStimuli(stimuli: String): Option[Node] =
        NodeParser parse stimuli

    override def part1(stimuli: List[Option[Node]]): AdventError \/ Int =
        NodeCalculator calculatePart1 stimuli.flatten.head

    override def part2(stimuli: List[Option[Node]]): AdventError \/ Int =
        NodeCalculator calculatePart2 stimuli.flatten.head
}