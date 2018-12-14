package com.kirbydee.advent.day10

import scala.language.postfixOps

case class Star(position: (Int, Int), velocity: (Int, Int)) {
    def move(): Star =
        copy(position = (position._1 + velocity._1, position._2 + velocity._2))
}