package com.kirbydee.advent1

import scala.io.Source

case object Stimuli {

    def load: List[Frequency] =
        Source.fromResource("advent1_stimuli.txt")
                .getLines
                .map(_.toInt)
                .map(Frequency.apply)
                .toList
}
