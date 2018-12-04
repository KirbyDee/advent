package com.kirbydee.advent.day3

import scala.language.postfixOps

case class BoxPoint(x: Int, y: Int) {
    def hash: Int =
        31*x + 961*y
}