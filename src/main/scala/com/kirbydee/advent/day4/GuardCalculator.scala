package com.kirbydee.advent.day4

import scala.annotation.tailrec
import scala.language.postfixOps
import scalaz.Scalaz._
import scalaz.\/

case object GuardCalculator {

    def calculatePart1(stimuli: List[GuardAction]): Main.AdventError \/ Int = {
        createHistogram(stimuli.sortBy(_.date)) maxBy {
            case (guardId, sleepHisto) => sleepHisto.sum
        } match {
            case (guardId, sleepHisto) => guardId * sleepHisto.zipWithIndex.maxBy(_._1)._2
        }
    } right

    def calculatePart2(stimuli: List[GuardAction]): Main.AdventError \/ Int = {
        createHistogram(stimuli.sortBy(_.date)) maxBy {
            case (guardId, sleepHisto) => sleepHisto.max
        } match {
            case (guardId, sleepHisto) => guardId * sleepHisto.zipWithIndex.maxBy(_._1)._2
        }
    } right

    private def createHistogram(guardActions: List[GuardAction]): Map[Int, Array[Int]] = {
        @tailrec
        def go(actionList: List[GuardAction], guardId: Int, fallAsleep: Option[FallsAsleep], guardSleepHistogram: Map[Int, Array[Int]]): Map[Int, Array[Int]] = (actionList, fallAsleep) match {
            // last guard
            case (Nil, Some(asleep)) => updateHistogram(guardId, asleep.date.getMinutes, 60, guardSleepHistogram)

            // first guard
            case ((a: BeginShift)  :: as, None) => go(as, a.id, None, guardSleepHistogram)

            // next guard
            case ((a: BeginShift)  :: as, Some(asleep)) => go(as, a.id, None, updateHistogram(guardId, asleep.date.getMinutes, 60, guardSleepHistogram))

            // actions
            case ((a: FallsAsleep) :: as, _)            => go(as, guardId, Some(a), guardSleepHistogram)
            case ((a: WakesUp)     :: as, Some(asleep)) => go(as, guardId, None, updateHistogram(guardId, asleep.date.getMinutes, a.date.getMinutes, guardSleepHistogram))

            // does not happen
            case _                                      => guardSleepHistogram
        }

        go(guardActions, -1, None, Map.empty)
    }

    private def updateHistogram(guardId: Int, sleepMin: Int, wakeUpInt: Int, histogram: Map[Int, Array[Int]]): Map[Int, Array[Int]] = {
        val histo = histogram getOrElse (guardId, Array.fill[Int](60)(0))
        sleepMin to wakeUpInt foreach { min =>
            histo(min) = histo(min) + 1
        }
        histogram + (guardId -> histo)
    }
}