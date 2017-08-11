package com.tookitaki.models

import com.tookitaki.db.DBConfiguration._
import org.slf4j.LoggerFactory
import scala.slick.driver.MySQLDriver.simple._

case class Org(val orgId: Option[Int], val orgName: String, val url: String, val desc: String, val address: String, val logo: Option[String], val phone: Option[String], val contact: Option[String])

class OrgTableDef(tag: Tag) extends Table[Org](tag, "orgs") {

  def orgId = column[Int]("org_id", O.PrimaryKey, O.AutoInc) // This is the primary key column
  def orgName = column[String]("org_name")
  def url = column[String]("url")
  def desc = column[String]("desc")
  def address = column[String]("address")
  def logo = column[String]("logo", O.Nullable)
  def phone = column[String]("phone", O.Nullable)
  def contact = column[String]("contact", O.Nullable)

  def * = (orgId.?, orgName, url, desc, address, logo.?, phone.?, contact.?) <> ((Org.apply _).tupled, Org.unapply)

  def orgNameUnique = index("org_name_unique", orgName, unique = true)
}

object Orgs {

  lazy val orgs = TableQuery[OrgTableDef]

  val logger = LoggerFactory.getLogger(getClass())

  def insert(orgName: String, url: String, desc: String, address: String): Option[Org] = {
    try {
      db withSession { implicit session =>
        orgs returning orgs.map(_.orgId) insert (Org(None, orgName, url, desc, address, None, None, None)) match {
          case orgId: Int => Some(Org(Some(orgId), orgName, url, desc, address, None, None, None))
        }
      }
    }
  }

  def all: List[Org] = {
    db withSession { implicit session =>
      orgs.list
    }
  }

  def getByName(orgName: String): Option[Org] = {
    db withSession { implicit session =>
      orgs.filter { _.orgName === orgName }.firstOption
    }
  }

  def get(orgId: Int): Option[Org] = {
    db withSession { implicit session =>
      orgs.filter { _.orgId === orgId }.firstOption
    }
  }

  def delete(orgId: Int): Int = {
    db withSession { implicit session =>
      orgs.filter { _.orgId === orgId }.delete
    }
  }
}
