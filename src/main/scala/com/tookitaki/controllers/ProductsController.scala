package com.tookitaki.controllers

import com.tookitaki.dao.ProductsDAO
import com.tookitaki.models.JsonResponse
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

class ProductsController extends BaseController with FutureSupport {

  val logger = LoggerFactory.getLogger(getClass)

  protected implicit def executor: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  get("/all") {
    new AsyncResult {
      val is =
        Future {
          ProductsDAO.getAllProducts map { products =>
              if (products.size > 0) {
                Ok(JsonResponse(PRODUCTS_RETRIEVED_SUCCESSFULLY._1, PRODUCTS_RETRIEVED_SUCCESSFULLY._2, products))
              } else {
                Ok(JsonResponse(NO_PRODUCTS_FOUND._1, NO_PRODUCTS_FOUND._2))
              }
          }
        }
    }
  }
  protected implicit val jsonFormats: Formats = DefaultFormats
}
