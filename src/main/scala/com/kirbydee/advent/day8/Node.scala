package com.kirbydee.advent.day8

import scala.language.postfixOps

case class Node(children: List[Node], metadata: List[Int]) {
    def sumMetadata: Int =
        metadata.sum
}