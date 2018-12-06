package com.kirbydee.advent

import com.kirbydee.advent.utils.Timer

import scalaz.Scalaz._
import scalaz.{-\/, \/, \/-}

trait AdventMainTrait[S, P1, P2] extends Timer {

    type AdventError = String

    val day: Int

    def readStimuli: List[String] =
        Stimuli load day

    def main(args: Array[String]): Unit = {
        // map stimuli
        val stimuli = readStimuli map mapStimuli

        // first part
        time {
            println("PART 1")
            part1(stimuli) match {
                case -\/(e) => println(s"Error: $e")
                case \/-(f) => println(s"Result: $f")
            }
        }

        println("")

        // second part
        time {
            println("PART 2")
            part2(stimuli) match {
                case -\/(e) => println(s"Error: $e")
                case \/-(f) => println(s"Result: $f")
            }
        }
    }

    def mapStimuli(stimuli: String): S

    def part1(stimuli: List[S]): AdventError \/ P1 =
        "not implemented".left

    def part2(stimuli: List[S]): AdventError \/ P2 =
        "not implemented".left
}