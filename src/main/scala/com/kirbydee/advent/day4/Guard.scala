package com.kirbydee.advent.day4

import java.util.Date

import scala.annotation.tailrec
import scala.language.postfixOps

object Guard {
    def apply(id: Int): Guard =
        Guard(id, Nil)
}


case class Guard(id: Int, actions: List[GuardAction]) {
    def addAction(a: GuardAction): Guard =
        Guard(id, actions :+ a)

    def addActions(guard: Guard): Guard =
        Guard(id, actions ++ guard.actions)

    def sleepTime: Int = {
        @tailrec
        def go(aList: List[GuardAction], fallsAsleep: Option[FallsAsleep], sleepTime: Int): Int = (fallsAsleep, aList) match {
            case (None, Nil)                              =>
                sleepTime
            case (None, (g: BeginShift) :: gs)            =>
                go(gs, None, sleepTime)
            case (None, (g: FallsAsleep) :: gs)           =>
                go(gs, Some(g), sleepTime)
            case (Some(sleep), (wake: WakesUp) :: gs)     =>
                go(gs, None, sleepTime + sleepInMinutes(sleep, wake))

            // should not happen
            case _                                     =>
                sleepTime
        }
        go(this.actions, None, 0)
    }

    private def sleepInMinutes(sleep: FallsAsleep, wake: WakesUp): Int =
        ((wake.date.getTime - sleep.date.getTime) / 60000).toInt
}

trait GuardAction {
    val date: Date
}

case class BeginShift(id: Int, override val date: Date) extends GuardAction
case class FallsAsleep(override val date: Date) extends GuardAction
case class WakesUp(override val date: Date) extends GuardAction