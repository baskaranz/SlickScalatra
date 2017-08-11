package com.tookitaki.models

import com.tookitaki.db.DBConfiguration._
import org.slf4j.LoggerFactory
import scala.slick.driver.MySQLDriver.simple._

case class Pdt(productId: Option[Int], productName: String, orgId: Int)

class ProductTableDef(tag: Tag) extends Table[Pdt](tag, "products") {

  def productId = column[Int]("product_id", O.PrimaryKey, O.AutoInc)

  def productName = column[String]("product_name")

  def orgId = column[Int]("org_id")

  def * = (productId.?, productName, orgId) <> ((Pdt.apply _).tupled, Pdt.unapply)

  def org = foreignKey("org_product_fk", orgId, Orgs.orgs)(_.orgId)
}

object Products {

  lazy val products = TableQuery[ProductTableDef]

  val logger = LoggerFactory.getLogger(getClass)

  def insert(productName: String, orgId: Int): Option[Pdt] = {

    try {
      db withSession { implicit session =>
        products returning products.map(_.productId) insert (
          Pdt(None, productName, orgId)
          ) match {
          case productId: Int => Some(Pdt(Some(productId), productName, orgId))
        }
      }
    }
  }

  def all: List[Pdt] = {
    db withSession { implicit session =>
      products.list
    }
  }

  def get(productId: Int): Option[Pdt] = {
    db withSession { implicit session =>
      products.filter(_.productId === productId).firstOption
    }
  }

  def getByOrgProductName(orgId: Int, productName: String): Option[Pdt] = {
    db withSession { implicit session =>
      products.filter(prod => prod.orgId === orgId && prod.productName === productName).firstOption
    }
  }

  def getByOrg(orgId: Int): List[Pdt] = {
    db withSession { implicit session =>
      products.filter(_.orgId === orgId).list
    }
  }

  def getByProductAndOrg(orgId: Int, productId: Int): Option[Pdt] = {
    db withSession { implicit session =>
      products.filter(product => product.productId === productId && product.orgId === orgId).firstOption
    }
  }

  def delete(productId: Int): Int = {
    db withSession { implicit session =>
      products.filter {
        _.productId === productId
      }.delete
    }
  }

  def deleteByOrg(orgId: Int): Int = {
    db withSession { implicit session =>
      products.filter {
        _.orgId === orgId
      }.delete
    }
  }

}