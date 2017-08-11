package com.tookitaki.controllers

import com.tookitaki.constants.ResponseCodes
import org.scalatra.ScalatraServlet
import org.scalatra.json._

abstract class BaseController extends ScalatraServlet with JacksonJsonSupport with ResponseCodes {

  before() {
    contentType = formats("json")
  }

}
