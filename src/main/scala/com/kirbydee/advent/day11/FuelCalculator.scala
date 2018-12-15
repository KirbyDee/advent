package com.kirbydee.advent.day11

import scala.language.postfixOps
import scalaz.Scalaz._
import scalaz.\/

case object FuelCalculator {

    def calculatePart1(serialNumber: Int): Main.AdventError \/ (Int, Int) = {
        val powerLvlGrid = buildPowerGrid(serialNumber)
        computeTotalPowerLvlGrid(powerLvlGrid, 3)
                .map(_ maxBy(_._2))
                .maxBy(_._2)
                ._1
    } right

    def calculatePart2(serialNumber: Int): Main.AdventError \/ (Int, Int, Int) = {
        val powerLvlGrid = buildPowerGrid(serialNumber)
        val totalPowerLvl = 1 to 299 map { size =>
            val powerLvl = computeTotalPowerLvlGrid(powerLvlGrid, size)
                    .map(_ maxBy(_._2))
                    .maxBy(_._2)
            powerLvl._2 -> (powerLvl._1._1, powerLvl._1._2, size)
        } maxBy {
            case (power, _) => power
        }
        totalPowerLvl._2
    } right

    private def computeTotalPowerLvlGrid(grid: Array[Array[Int]], size: Int): Array[Array[((Int, Int), Int)]] = {
        val gridSize = 300 - size
        val newGrid = Array.fill[((Int, Int), Int)](gridSize, gridSize)((0,0) -> 0)
        for {
            xID <- 1 to gridSize
            yID <- 1 to gridSize
            x    = xID - 1
            y    = yID - 1
        } yield {
            newGrid(y)(x) = (xID, yID) -> grid.slice(y, y + size).map(_.slice(x, x + size).sum).sum
        }
        newGrid
    }

    private def buildPowerGrid(serialNumber: Int): Array[Array[Int]] = {
        val grid = Array.fill[Int](300, 300)(0)
        for {
            xID <- 1 to 300
            yID <- 1 to 300
            x        = xID - 1
            y        = yID - 1
            rackID   = xID + 10
            power    = (rackID * yID + serialNumber) * rackID
            powerLvl = power.toString.map(_.asDigit).toArray.reverse match {
                case ls if ls.length < 3  => -5
                case ls                   => ls(2) - 5
            }
        } yield {
            grid(y)(x) = powerLvl
        }
        grid
    }

}