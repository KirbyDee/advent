package com.kirbydee.advent.day7

import scala.annotation.tailrec
import scala.language.postfixOps
import scalaz.Scalaz._
import scalaz.\/

case object TaskCalculator {

    def calculatePart1(taskActions: List[TaskAction]): Main.AdventError \/ String =
        generateTaskList(taskActions).right

    private def generateTaskList(taskActions: List[TaskAction]): String = {
        val lastTask = findLastTask(taskActions)
        @tailrec
        def go(taskActionList: List[TaskAction], taskList: List[String]): List[String] = findNextTask(taskActionList) match {
            case None       => taskList :+ lastTask.getOrElse("")
            case Some(task) => go(taskActionList.filterNot(_.taskBefore == task), taskList :+ task)
        }
        go(taskActions, Nil) mkString ""
    }

    private def findNextTask(taskActions: List[TaskAction]): Option[String] =
        (taskActions.map(_.taskBefore).toSet diff taskActions.map(_.taskAfter).toSet).toList.sorted.headOption

    private def findLastTask(taskActions: List[TaskAction]): Option[String] =
        (taskActions.map(_.taskAfter).toSet diff taskActions.map(_.taskBefore).toSet).toList.sorted.headOption
}