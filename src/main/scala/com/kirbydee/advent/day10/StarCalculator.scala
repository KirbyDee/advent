package com.kirbydee.advent.day10

import scala.annotation.tailrec
import scala.language.postfixOps
import scalaz.Scalaz._
import scalaz.\/

case object StarCalculator {

    def calculatePart1(stars: List[Star]): Main.AdventError \/ Sky =
        buildSky(stars) map (_._1)

    def calculatePart2(stars: List[Star]): Main.AdventError \/ Int =
        buildSky(stars) map (_._2)

    private def buildSky(stars: List[Star]): Main.AdventError \/ (Sky, Int) = {
        @tailrec
        def go(starList: List[Star], secondsPassed: Int): Main.AdventError \/ (Sky, Int) = (Sky(starList), secondsPassed > 50000) match {
            case (_, true)                          => "No message found in the Sky".left
            case (sky, _) if sky.hasPossibleMessage => (sky, secondsPassed).right
            case _                                  => go(starList map(_.move()), secondsPassed + 1)
        }
        go(stars, 0)
    }
}