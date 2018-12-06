package com.kirbydee.advent.day4

import java.util.Date

import scala.annotation.tailrec
import scalaz.Scalaz._
import scalaz.\/

case object GuardCalculator {

    implicit val dateOrder: Ordering[Date] = (x: Date, y: Date) => {
        if (x before y)
            -1
        else if (x after y)
            1
        else
            0
    }

    def calculatePart1(stimuli: List[GuardAction]): Main.AdventError \/ Int = {
        toGuards(stimuli.sortBy(_.date))
                .map(g => (g, SleepHeatMap apply g))
                .map({ case (g, hm) =>
                    (g, hm.sleepMap
                            .toList
                            .sortBy(_._2)
                            .reverse
                            .head)
                })
                .sortBy({ case (g, hm) => hm._2 })
                .reverse
                .map({ case (guard, (minute, count)) => guard.id * minute })
                .head
                .right
    }

    private def toGuards(guardActions: List[GuardAction]): List[Guard] = {
        def updateGuardMap(guardOp: Option[Guard], guardMap: Map[Int, Guard]): Map[Int, Guard] = {
            guardOp flatMap { guard: Guard =>
                guardMap get guard.id map { _ addActions guard }
            } orElse guardOp map { guard =>
                guardMap + (guard.id -> guard)
            } getOrElse guardMap
        }

        @tailrec
        def go(gList: List[GuardAction], currentGuard: Option[Guard], guardMap: Map[Int, Guard]): Map[Int, Guard] = (currentGuard, gList) match {
            case (pastGuardOp, Nil)                    =>
                updateGuardMap(pastGuardOp, guardMap)
            case (pastGuardOp, (g: BeginShift) :: gs)  =>
                go(gs, Some(Guard(g.id) addAction g), updateGuardMap(pastGuardOp, guardMap))
            case (Some(guard), (g: FallsAsleep) :: gs) =>
                go(gs, Some(guard addAction g), guardMap)
            case (Some(guard), (g: WakesUp) :: gs)     =>
                go(gs, Some(guard addAction g), guardMap)

                // should not happen
            case _                                     =>
                guardMap
        }
        go(guardActions, None, Map.empty[Int, Guard]).values.toList
    }
}