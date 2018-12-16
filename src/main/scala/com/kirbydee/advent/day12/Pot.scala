package com.kirbydee.advent.day12

import scala.language.postfixOps

object Pot {
    implicit class PotMapHelper(potMap: Map[Int, Pot]) {
        def sumPots: Int =
            potMap.toList
                    .filter(_._2 == Plant)
                    .map(_._1)
                    .sum
    }
}

trait Pot
case object Plant extends Pot
case object NoPlant extends Pot