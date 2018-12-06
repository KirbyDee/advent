package com.kirbydee.advent.day4

import scala.annotation.tailrec
import scala.language.postfixOps

object SleepHeatMap {

    val emptySleepMap: Map[Int, Int] = (0 until 60 toList) map { i =>
        i -> 0
    } toMap

    def apply(guard: Guard): SleepHeatMap = {
        @tailrec
        def goMap(sleepRange: List[Int], sleepMap: Map[Int, Int]): Map[Int, Int] = sleepRange match {
            case Nil     => sleepMap
            case i :: is => goMap(is, sleepMap + (i -> (sleepMap.getOrElse(i, 0) + 1)))
        }

        @tailrec
        def go(aList: List[GuardAction], fallsAsleep: Option[FallsAsleep], sleepMap: Map[Int, Int]): Map[Int, Int] = (fallsAsleep, aList) match {
            case (None, Nil)                              =>
                sleepMap
            case (None, (g: BeginShift) :: gs)            =>
                go(gs, None, sleepMap)
            case (None, (g: FallsAsleep) :: gs)           =>
                go(gs, Some(g), sleepMap)
            case (Some(sleep), (wake: WakesUp) :: gs)     =>
                go(gs, None, goMap(sleep.date.getMinutes() until wake.date.getMinutes() toList, sleepMap))

            // should not happen
            case _                                     =>
                sleepMap
        }
        SleepHeatMap(go(guard.actions, None, emptySleepMap))
    }
}

case class SleepHeatMap(sleepMap: Map[Int, Int])