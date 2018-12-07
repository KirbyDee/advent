package com.kirbydee.advent.day3

import com.kirbydee.advent.day3.Box.Dimensions

import scala.language.postfixOps

object Box {
    type Dimensions = (Int, Int)
}

case class Box(id: Int, topLeft: (Int, Int), dimensions: Dimensions) {
    def xRange: List[Int] =
        (topLeft._1 until (topLeft._1 + dimensions._1)).toList

    def yRange: List[Int] =
        (topLeft._2 until (topLeft._2 + dimensions._2)).toList

    def size: Int =
        dimensions._1 * dimensions._2
}