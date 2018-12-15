package com.kirbydee.advent.day10

import scala.language.postfixOps

case class Sky(stars: List[Star]) {

    def hasPossibleMessage: Boolean =
        stars.takeWhile(hasNeighbour).length == stars.length

    private def hasNeighbour(star: Star): Boolean = stars.count({ s =>
        math.abs(s.position._1 - star.position._1) +
                math.abs(s.position._2 - star.position._2) <= 3
    }) > 1

    override def toString: String = {
        val xMin = stars map (_.position._1) min
        val xMax = stars map (_.position._1) max
        val yMin = stars map (_.position._2) min
        val yMax = stars map (_.position._2) max

        val sky = Array.fill[String](math.abs(yMax - yMin) + 1, math.abs(xMax - xMin) + 1)(".")
        stars foreach { star =>
            sky(star.position._2 - yMin)(star.position._1 - xMin) = "#"
        }
        s"\n${sky.map(_.mkString) mkString "\n"}"
    }
}