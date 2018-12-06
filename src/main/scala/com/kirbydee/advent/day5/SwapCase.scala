package com.kirbydee.advent.day5

object SwapCase {
    def unapply(c: Char): Option[Char] = c.isLower match {
        case true  => Some(c.toUpper)
        case false => Some(c.toLower)
    }
}