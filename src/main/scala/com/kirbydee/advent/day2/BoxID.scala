package com.kirbydee.advent.day2

import scala.annotation.tailrec
import scalaz.{Failure, Success, Validation}

case class BoxID(id: String) {

    val (hasDuet: Boolean, hasTriplet: Boolean) = {
        @tailrec
        def go(idParts: List[Char], solo: Set[Char], duet: Set[Char], triplet: Set[Char]): (Boolean, Boolean) = idParts match {
            case c :: cs if solo contains c    => go(cs, solo - c, duet + c, triplet)
            case c :: cs if duet contains c    => go(cs, solo, duet - c, triplet + c)
            case c :: cs if triplet contains c => go(cs, solo, duet, triplet - c)
            case c :: cs                       => go(cs, solo + c, duet, triplet)
            case Nil                           => (duet.nonEmpty, triplet.nonEmpty)
        }
        go(id.toList, Set.empty, Set.empty, Set.empty)
    }

    def compare(others: List[BoxID]): Validation[_, String] = {
        @tailrec
        def go(boxId: BoxID, boxIds: List[BoxID]): Validation[_, String] = boxIds match {
            case bid :: bids => boxId compare bid match {
                case Success(result) => Success(result)
                case Failure(_)      => go(boxId, bids)
            }
            case Nil             => Failure(None)
        }
        go(this, others)
    }

    def compare(other: BoxID): Validation[_, String] = {
        @tailrec
        def go(box1Ids: List[Char], box2Ids: List[Char], similarIds: List[Char], failCount: Int): Validation[_, String] = (box1Ids, box2Ids) match {
            case (b1id :: b1ids, b2id :: b2ids) if failCount < 2 && b1id == b2id  => go(b1ids, b2ids, similarIds :+ b1id, failCount)
            case (b1id :: b1ids, b2id :: b2ids) if failCount == 0 && b1id != b2id => go(b1ids, b2ids, similarIds, failCount + 1)
            case (Nil, Nil)                     if failCount < 2                  => Success(similarIds.mkString)
            case _                                                                => Failure(None)
        }
        go(this.id.toList, other.id.toList, List.empty[Char], 0)
    }

    override def toString: String =
        id
}