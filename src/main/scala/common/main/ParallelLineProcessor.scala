package edu.washington.cs.knowitall.common
package main

import Timing._
import java.util.Scanner

/**
 * This class is to be extended by an object to provide a simple main class
 * that processes lines in parallel.
 */
abstract class ParallelLineProcessor {
  val groupSize = 1000

  def init(args: Array[String]) {}
  def exit(ns: Long) {}
  def process(line: String): String
  def main(args: Array[String]) {
    init(args)
    val lines = io.Source.stdin.getLines

    val lock = new Object()

    val ns = time {
      for (group <- lines.grouped(groupSize)) {
        for (line <- group.par) {
          lock.synchronized {
            println(process(line))
          }
        }
      }
    }

    exit(ns)
  }
}
