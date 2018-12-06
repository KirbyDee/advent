package com.kirbydee.advent.day5

import scala.language.postfixOps
import scalaz.Scalaz._
import scalaz.\/

case object PolymerCalculator {

    def calculatePart1(polymer: String): Main.AdventError \/ Int =
        collapsePolymerSize(polymer).right

    def calculatePart2(polymer: String): Main.AdventError \/ Int =
        ('a' to 'z'map removeUnits(polymer) map collapsePolymerSize sorted).head.right

    private def removeUnits(polymer: String)(char: Char): String =
        polymer filterNot s"$char${char.toUpper}".toSet

    private def collapsePolymerSize(polymer: String): Int =
        collapsePolymer(polymer).size

    private def collapsePolymer(polymer: String): List[Char] = {
        def go(leftPartReversed: List[Char], rightPart: List[Char]): List[Char] = (leftPartReversed, rightPart) match {
            case (Nil, r :: rs)                         => go(List(r), rs)
            case (SwapCase(l) :: ls, r :: rs) if l == r => go(ls, rs)
            case (l :: ls, r :: rs)                     => go(List(r, l) ++ ls, rs)
            case _                                      => leftPartReversed
        }
        go(Nil, polymer.toList)
    }
}