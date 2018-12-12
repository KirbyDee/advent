package com.kirbydee.advent.day8

import scala.annotation.tailrec
import scala.language.postfixOps
import scalaz.Scalaz._
import scalaz.\/

case object NodeCalculator {

    def calculatePart1(node: Node): Main.AdventError \/ Int = {
        computeLicense(node)
    } right

    def calculatePart2(node: Node): Main.AdventError \/ Int = {
        computeLicenseComplex(node)
    } right

    private def computeLicense(node: Node): Int = {
        @tailrec
        def goChildren(children: List[Node], license: Int): Int = children match {
            case Nil     => license
            case c :: cs => goChildren(cs, goNode(c, license))
        }

        def goNode(n: Node, license: Int): Int =
            n.sumMetadata + goChildren(n.children, license)

        goNode(node, 0)
    }

    private def computeLicenseComplex(node: Node): Int = {
        @tailrec
        def goChildren(children: List[Node], metadataList: List[Int], licence: Int): Int = metadataList match {
            case Nil                                     => licence
            case m :: ms if m != 0 && m <= children.size => goChildren(children, ms, goNode(children(m - 1), licence))
            case m :: ms                                 => goChildren(children, ms, licence)
        }

        def goNode(n: Node, license: Int): Int = n.children match {
            case Nil => license + n.sumMetadata
            case _   => goChildren(n.children, n.metadata, license)
        }

        goNode(node, 0)
    }
}