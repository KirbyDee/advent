package com.kirbydee.advent.day8

import scala.language.postfixOps
import scala.util.parsing.combinator.RegexParsers

object NodeParser extends RegexParsers {

    private def node: Parser[Node] =
        header>>body ^^ {
            case children~metadata => Node(children, metadata)
        }

    private def header: Parser[(Int, Int)] =
        number~number ^^ {
            case childrenNumber~metadataNumber => (childrenNumber.toInt, metadataNumber.toInt)
        }

    private def body(header: (Int, Int)): Parser[NodeParser.~[List[Node], List[Int]]] = header match {
        case (childrenNumber, metadataNumber) => childNodes(childrenNumber)~metadataEntries(metadataNumber)
    }

    private def childNodes(childrenNumber: Int): Parser[List[Node]] =
        repN(childrenNumber, node) ^^ identity

    private def metadataEntries(metadataNumber: Int): Parser[List[Int]] =
        repN(metadataNumber, number) ^^ (_ map(_.toInt))

    private def number: Parser[String] =
        "[0-9]+".r ^^ identity

    def parse(string: String): Option[Node] = parseAll(node, string) match {
        case Success(result, _) => Some(result)
        case NoSuccess(_, _)    => None
    }
}