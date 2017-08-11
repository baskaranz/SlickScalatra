package com.tookitaki.commons

import com.typesafe.config.ConfigFactory

object AppConfig {
  lazy val conf = ConfigFactory.load("application")
}
