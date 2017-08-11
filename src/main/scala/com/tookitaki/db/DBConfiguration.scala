package com.tookitaki.db

import com.mchange.v2.c3p0.ComboPooledDataSource
import com.tookitaki.commons.AppConfig
import org.slf4j.LoggerFactory

import scala.slick.driver.{JdbcProfile, MySQLDriver}

object DBConfiguration {

  val logger = LoggerFactory.getLogger(getClass)

  val cpds = new ComboPooledDataSource

  val dbSource = getConfString("app.db.source")

  val dbHost   = getConfString(s"app.db.${dbSource}.host")
  val dbDriver = getConfString(s"app.db.${dbSource}.driver")
  val dbName   = getConfString(s"app.db.${dbSource}.name")
  val dbPort   = getConfString(s"app.db.${dbSource}.port")
  val dbUser   = getConfString(s"app.db.${dbSource}.user")
  val dbPass   = getConfString(s"app.db.${dbSource}.pass")

  val jdbcUrl = s"jdbc:${dbSource}://${dbHost}:${dbPort}/${dbName}"

  val profile: JdbcProfile = dbSource match {
    case _ => MySQLDriver
  }

  logger.info(s"JDBC URL: ${jdbcUrl}")

  cpds.setDriverClass(dbDriver)
  cpds.setJdbcUrl(jdbcUrl)
  cpds.setUser(dbUser)
  cpds.setPassword(dbPass)

  cpds.setMinPoolSize(3)
  cpds.setAcquireIncrement(3)
  cpds.setMaxPoolSize(30)
  cpds.setMaxIdleTime(600)
  cpds.setTestConnectionOnCheckout(true)

  import profile.simple._

  lazy val db = Database.forDataSource(cpds)
  logger.info("Created c3p0 connection pool")
  logger.info("Creating tables if not exist")

  private def getConfString(key: String): String = {
    AppConfig.conf.getString(key)
  }

  private def closeDbConnection() {
    logger.info("Closing c3po connection pool")
    cpds.close
  }

}
