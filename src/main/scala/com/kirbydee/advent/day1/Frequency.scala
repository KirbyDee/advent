package com.kirbydee.advent.day1

object Frequency {

    def ZERO: Frequency =
        Frequency(0)

    implicit class FrequencyHelper(f: Frequency) {

        @inline def +(f2: Frequency): Frequency =
            Frequency(f.amount + f2.amount)
    }
}

case class Frequency(amount: Int) {

    override def equals(that: Any): Boolean =
        that match {
            case that: Frequency => that.canEqual(this) && this.hashCode == that.hashCode
            case _               => false
        }
    override def hashCode: Int =
        amount

    override def toString: String =
        amount.toString
}