package com.tookitaki.controllers

import org.scalatra.test.specs2._

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class ProductsControllerSpec extends ScalatraSpec {
  def is =
    "GET /all on ProductsController" ^
      "should return status 200" ! root200 ^
      end

  addServlet(classOf[ProductsController], "/all")

  def root200 = get("/") {
    status must_== 200
  }
}
