package com.tookitaki.dao

import com.tookitaki.models.{Pdt, Products}

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

object ProductsDAO {

  def getAllProducts: Future[List[Pdt]] = Future {
    Products.all
  }

}
