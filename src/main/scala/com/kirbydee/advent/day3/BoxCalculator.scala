package com.kirbydee.advent.day3

import scala.annotation.tailrec
import scala.language.postfixOps
import scalaz.Scalaz._
import scalaz.\/

case object BoxCalculator {

    def calculatePart1(stimuli: List[Box]): Main.AdventError \/ Int =
        (buildFabric(stimuli) map (_.count(_ == "x")) sum).right

    def calculatePart2(stimuli: List[Box]): Main.AdventError \/ Int =
        buildFabricHistogram(stimuli, buildFabric(stimuli)) find {
            case (boxId, size) => stimuli.find(_.id == boxId).exists(_.size == size)
        } map(_._1.right) getOrElse "No sole fabric found!".left

    private def buildFabricHistogram(stimuli: List[Box], fabric: Array[Array[Int]]): List[(Int, Int)] = {
        fabric flatMap {
            _ groupBy identity map {
                case (boxId, claimList) => boxId -> claimList.length
            } toList
        } groupBy {
            case (boxId, _) => boxId
        } map {
            case (boxId, listCount) => (boxId, listCount.map(_._2).sum)
        } toList
    }

    private def draw(fabric: Array[Array[Int]]): Unit =
        print(fabric.map(_.mkString).mkString("\n"))

    private def buildFabric(stimuli: List[Box]): Array[Array[Int]] = {
        @tailrec
        def go(boxList: List[Box], fabric: Array[Array[Int]]): Array[Array[Int]] = boxList match {
            case Nil => fabric
            case b :: bs => go(bs, boxToFabric(b, fabric))
        }
        go(stimuli, Array.fill[Int](1000, 1000)(0))
    }

    private def boxToFabric(box: Box, fabric: Array[Array[Int]]): Array[Array[Int]] = {
        def updateFabric(box: Box, x: Int, y: Int, f: Array[Array[Int]]): Array[Array[Int]] = f(y)(x) match {
            // not used
            case 0  => f(y)(x) = box.id; f

            // used
            case -1 => f
            case _  => f(y)(x) = -1; f
        }

        @tailrec
        def goY(box: Box, x: Int, yList: List[Int], f: Array[Array[Int]]): Array[Array[Int]] = yList match {
            case Nil => f
            case y :: ys => goY(box, x, ys, updateFabric(box, x, y, f))
        }

        @tailrec
        def goX(box: Box, xList: List[Int], yList: List[Int], f: Array[Array[Int]]): Array[Array[Int]] = xList match {
            case Nil => f
            case x :: xs => goX(box, xs, yList, goY(box, x, yList, f))
        }
        goX(box, box.xRange, box.yRange, fabric)
    }
}