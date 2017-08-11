package com.tookitaki.dao

import com.tookitaki.models.{Pdt, Products}

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

object ProductsDAO {

  /** Returns the list of all the products available.
    *
    * @return A future value with the list of products List[Pdt]
    */
  def getProducts: Future[List[Pdt]] = Future {
    Products.all
  }

  /** Returns the product info based on the given id.
    *
    * @return A future value with the product as an Option
    */
  def getProductByID(id: Int): Future[Option[Pdt]] = Future {
    Products.get(id)
  }

}
