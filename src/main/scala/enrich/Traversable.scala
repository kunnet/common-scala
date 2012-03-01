package edu.washington.cs.knowitall
package common
package enrich

sealed trait SuperTraversableOnce[T] extends scalaz.PimpedType[TraversableOnce[T]] {
  def histogram: Map[T, Int] = {
    value.foldLeft(Map[T, Int]()) { (m, c) =>
      m.updated(c, m.getOrElse(c, 0) + 1)
    }
  }
}

sealed trait SuperTraversableOncePairInt[T] extends scalaz.PimpedType[TraversableOnce[(T, Int)]] {
  def histogramFromPartials: Map[T, Int] = {
    value.foldLeft(Map[T, Int]()) { (m, item) => item match {
        case (x, c) => m.updated(x, m.getOrElse(x, 0) + c)
      }
    }
  }
}

object Traversables {
  implicit def traversableOnceTo[T](as: TraversableOnce[T]): SuperTraversableOnce[T] = new SuperTraversableOnce[T] {
    val value = as
  }
  
  implicit def traversableOncePairIntTo[T](as: TraversableOnce[(T,Int)]): SuperTraversableOncePairInt[T] = new SuperTraversableOncePairInt[T] {
    val value = as
  }
}
