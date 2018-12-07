package com.kirbydee.advent.day4

import java.util.Date

import scala.annotation.tailrec
import scala.language.postfixOps

trait GuardAction {
    val date: Date
}

case class BeginShift(id: Int, override val date: Date) extends GuardAction
case class FallsAsleep(override val date: Date) extends GuardAction
case class WakesUp(override val date: Date) extends GuardAction