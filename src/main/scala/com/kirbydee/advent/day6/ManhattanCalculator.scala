package com.kirbydee.advent.day6

import scala.collection.immutable.Iterable
import scala.language.postfixOps
import scalaz.Scalaz._
import scalaz.\/

case object ManhattanCalculator {

    def calculatePart1(points: List[Point]): Main.AdventError \/ Int = {
        val g = buildGrid(points) filter {
            case (pId, ps)   => pId >= 0
        }

        // filter "infinite" areas

        g map {
            case (pId, ps)   => ps.size
        } max
    } right

    private def buildGrid(points: List[Point]): List[(Int, Iterable[GridPoint])] = {
        buildGridPoints(points) groupBy {
            gridPoint => gridPoint.x -> gridPoint.y
        } map {
            case (xy, ps) => ps minBy (_.distance) match {
                case minPoint if ps.count(_.distance == minPoint.distance) > 1 => minPoint.copy(id = -1)
                case minPoint                                                  => minPoint
            }
        } groupBy {
            point => point.id
        }
    } toList

    private def buildGridPoints(points: List[Point]): Seq[GridPoint] = for {
        x        <- 0 until (points maxBy (_.x) x)
        y        <- 0 until (points maxBy (_.y) y)
        (p, pId) <- points zipWithIndex
    } yield {
        GridPoint(x, y, pId, (p.x - x).abs + (p.y - y).abs)
    }
}