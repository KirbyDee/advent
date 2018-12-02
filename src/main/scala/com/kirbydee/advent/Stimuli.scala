package com.kirbydee.advent

import scala.io.Source

case object Stimuli {

    def load[A](day: Int): List[String] =
        Source.fromResource(s"advent${day}_stimuli.txt")
                .getLines
                .toList
}
