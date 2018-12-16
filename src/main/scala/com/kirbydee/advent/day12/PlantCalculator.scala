package com.kirbydee.advent.day12

import scala.annotation.tailrec
import scala.language.postfixOps
import scalaz.Scalaz._
import scalaz.\/

case object PlantCalculator {

    def calculatePart1(plantActions: List[PlantAction]): Main.AdventError \/ Int = {
        runGenerations(plantActions, 20)._2.sumPots
    } right

    def calculatePart2(plantActions: List[PlantAction]): Main.AdventError \/ Long = {
        runGenerations(plantActions, 50000000000l) match {
            case (g, genMap) =>
                val numberOfPlants = genMap.count {
                    case (_, Plant) => true
                    case _          => false
                }
                g * numberOfPlants + genMap.sumPots
        }
    } right

    private def runGenerations(plantActions: List[PlantAction], generations: Long): (Long, Map[Int, Pot]) = {
        val initialState = plantActions
                .find(_.isInstanceOf[InitialState])
                .map(_.asInstanceOf[InitialState])
                .get
                .pots
                .zipWithIndex
                .map({
                    case (pot, index) => index -> pot
                })
                .toMap
        val spreadActions = plantActions
                .filter(_.isInstanceOf[SpreadAction])
                .map(_.asInstanceOf[SpreadAction])

        def computeNewState(state: Map[Int, Pot]): Map[Int, Pot] = {
            val newPots = for {
                potId <- state.keys.min - 2 to state.keys.max + 2

                ll = state.getOrElse(potId - 2, NoPlant)
                l  = state.getOrElse(potId - 1, NoPlant)
                c  = state.getOrElse(potId, NoPlant)
                r  = state.getOrElse(potId + 1, NoPlant)
                rr = state.getOrElse(potId + 2, NoPlant)

                newPot = spreadActions
                        .find({
                            case SpreadAction(`ll`, `l`, `c`, `r`, `rr`, n) => true
                            case _                                          => false
                        }).map(_.N)
                        .getOrElse(NoPlant)
            } yield {
                potId -> newPot
            }
            newPots
                    .sortBy(_._1)
                    .dropWhile({
                        case (_, NoPlant) => true
                        case _            => false
                    })
                    .reverse
                    .dropWhile({
                        case (_, NoPlant) => true
                        case _            => false
                    })
                    .reverse
                    .toMap
        }

        @tailrec
        def go(g: Long, oldState: Map[Int, Pot], currentState: Map[Int, Pot]): (Long, Map[Int, Pot]) = {
            val currentStatePrediction = oldState.sumPots + oldState.count {
                case (_, Plant) => true
                case _          => false
            }

            (g, currentStatePrediction == currentState.sumPots) match {
                case (0, _) | (_, true) => (g, currentState)
                case _                  => go(g - 1, currentState, computeNewState(currentState))
            }
        }
        go(generations, Map.empty[Int, Pot], initialState)
    }
}