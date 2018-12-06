package com.kirbydee.advent.utils

import java.util.Date

object DateUtils {

    implicit class DateHelper(date: Date) {

        @inline def <(date2: Date): Boolean =
            date before date2

        @inline def >(date2: Date): Boolean =
            date after date2

        @inline def ==(date2: Date): Boolean =
            date equals date2
    }
}
