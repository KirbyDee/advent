package com.kirbydee.advent.day3

import scalaz.Scalaz._
import scalaz.\/

case object BoxCalculator {

    def calculatePart1(stimuli: List[Box]): Main.AdventError \/ Int = {
        stimuli.overlap.right
    }
}