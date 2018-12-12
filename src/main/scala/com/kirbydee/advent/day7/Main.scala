package com.kirbydee.advent.day7

import com.kirbydee.advent.AdventMainTrait

import scalaz.\/

object Main extends AdventMainTrait[Option[TaskAction], String, String] {

    override val day: Int = 7

    override def mapStimuli(stimuli: String): Option[TaskAction] =
        TaskActionParser parse stimuli

    override def part1(stimuli: List[Option[TaskAction]]): AdventError \/ String =
        TaskCalculator calculatePart1 stimuli.flatten
}