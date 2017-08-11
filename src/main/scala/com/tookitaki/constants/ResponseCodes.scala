package com.tookitaki.constants

trait ResponseCodes {

  //common failure codes
  val INTERNAL_SYSTEM_ERROR = (5001, "System is experiencing problem. Please try after sometime")
  val INVALID_REQUEST_PARAMETER = (5003, "Invalid request, please check the entered parameter")

  //products response codes
  val PRODUCTS_RETRIEVED_SUCCESSFULLY = (1000, "Products retrieved successfully")
  val NO_PRODUCTS_FOUND = (1002, "No products found")
  val PRODUCTS_RETRIEVE_FAILED = (1001, "Products could not be retrieved at the moment")

}
