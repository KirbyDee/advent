package com.kirbydee.advent1

import scalaz.{-\/, \/-}

object Main {
    def main(args: Array[String]): Unit = {
        // first part
        FrequencyCalculator.calculatePart1() match {
            case f: Frequency => println(s"PART1: Resulting frequency is: $f")
        }

        // second part
        FrequencyCalculator.calculatePart2(20) match {
            case -\/(e) => println(s"PART2: $e")
            case \/-(f) => println(s"PART2: Resulting frequency is: $f")
        }
    }
}