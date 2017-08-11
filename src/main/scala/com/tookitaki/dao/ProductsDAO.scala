package com.tookitaki.dao

import com.tookitaki.models.{Pdt, Products}

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

object ProductsDAO {

  /** Returns the list of all the products available.
    *
    * @return A future value with the list of products List[Pdt]
    */
  def getAllProducts: Future[List[Pdt]] = Future {
    Products.all
  }

}
