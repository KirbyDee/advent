package com.kirbydee.advent.day3

import scala.annotation.tailrec
import scala.language.postfixOps

object Box {

    implicit class BoxHelper(boxes: List[Box]) {

        @inline def overlap: Int = {
            def go(pList: List[Int], overlapSet: Set[Int]): Set[Int] = pList match {
                case Nil                      => overlapSet
                case p :: ps if ps contains p => go(ps, overlapSet + p)
                case p :: ps                  => go(ps, overlapSet)
            }
            go(boxes.flatMap(_.points).map(_.hash), Set.empty).size
        }
    }

    def apply(id: Int, topLeft: BoxPoint, size: (Int, Int)): Box = {
        @tailrec
        def goY(x: Int, yList: List[Int], boxPoints: List[BoxPoint]): List[BoxPoint] = yList match {
            case Nil     => boxPoints
            case y :: ys => goY(x, ys, boxPoints :+ BoxPoint(x, y))
        }

        @tailrec
        def goX(xList: List[Int], yList: List[Int], boxPoints: List[BoxPoint]): List[BoxPoint] = xList match {
            case Nil     => boxPoints
            case x :: xs => goX(xs, yList, goY(x, yList, boxPoints))
        }

        Box(id, goX(topLeft.x until (topLeft.x + size._1) toList, topLeft.y until (topLeft.y + size._2) toList, Nil))
    }
}

case class Box(id: Int, points: List[BoxPoint]) {
    def size: Int =
        points.size
}