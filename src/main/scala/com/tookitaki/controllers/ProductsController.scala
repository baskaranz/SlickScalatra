package com.tookitaki.controllers

import com.tookitaki.dao.ProductsDAO
import com.tookitaki.models.JsonResponse
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class ProductsController extends BaseController with FutureSupport {

  val logger = LoggerFactory.getLogger(getClass)

  protected implicit def executor: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  get("/") {
    new AsyncResult {
      val is =
        Future {
          ProductsDAO.getProducts map { products =>
              if (products.size > 0) {
                Ok(JsonResponse(PRODUCTS_RETRIEVED_SUCCESSFULLY._1, PRODUCTS_RETRIEVED_SUCCESSFULLY._2, products))
              } else {
                Ok(JsonResponse(NO_PRODUCTS_FOUND._1, NO_PRODUCTS_FOUND._2))
              }
          }
        }
    }
  }
  get("/:id") {
    new AsyncResult {
      val is =
        Future {
          val maybeID: Option[Int] = Try(params.get("id").map(_.toInt)).getOrElse(None)
          maybeID match {
            case Some(id) =>
              ProductsDAO.getProductByID(id) map { products =>
                if (products.size > 0) {
                  Ok(JsonResponse(PRODUCTS_RETRIEVED_SUCCESSFULLY._1, PRODUCTS_RETRIEVED_SUCCESSFULLY._2, products))
                } else {
                  Ok(JsonResponse(NO_PRODUCTS_FOUND._1, NO_PRODUCTS_FOUND._2))
                }
              }
            case None =>
              BadRequest(JsonResponse(INVALID_REQUEST_PARAMETER._1, INVALID_REQUEST_PARAMETER._2))
          }
        }
    }
  }
  protected implicit val jsonFormats: Formats = DefaultFormats
}
