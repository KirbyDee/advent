package com.kirbydee.advent

import scala.language.{implicitConversions, postfixOps}

trait Timer {

    trait TimeUnit {

        val unit: String

        def format(t: Long): String =
            s"${"%.3f".format(convert(t))}$unit"

        def convert(t: Long): Double
    }
    case object NanoSecond  extends TimeUnit {
        override val unit: String = "ns"
        override def convert(t: Long): Double = t.toDouble
    }
    case object MicroSecond extends TimeUnit {
        override val unit: String = "μs"
        override def convert(t: Long): Double = t.toDouble / 1000
    }
    case object MilliSecond extends TimeUnit {
        override val unit: String = "ms"
        override def convert(t: Long): Double = t.toDouble / 1000000
    }
    case object Second      extends TimeUnit {
        override val unit: String = "s"
        override def convert(t: Long): Double = t.toDouble / 1000000000
    }

    def time[R](block: => R): R =
        time("Timer", MilliSecond)(block)

    def time[R](name: String, timeUnit: TimeUnit)(block: => R): R = {
        val t0 = System.nanoTime()
        try {
            block
        } finally {
            val t1 = System.nanoTime()
            printTime(name)(t0, t1, timeUnit)
        }
    }

    private def printTime(name: String)(t0: Long, t1: Long, timeUnit: TimeUnit): Unit =
        println(f"($name) Elapsed time: ${timeUnit.format(t1 - t0)}")
}