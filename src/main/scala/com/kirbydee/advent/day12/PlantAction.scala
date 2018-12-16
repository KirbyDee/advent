package com.kirbydee.advent.day12

import scala.language.postfixOps

trait PlantAction
case class InitialState(pots: List[Pot]) extends PlantAction
case class SpreadAction(LL: Pot, L: Pot, C: Pot, R: Pot, RR: Pot, N: Pot) extends PlantAction