package com.antilia.common.spring

import javax.naming.NamingException
import javax.sql.DataSource

import org.apache.commons.dbcp.BasicDataSource
import org.eclipse.jetty.plus.jndi.Resource
import org.slf4j.{Logger, LoggerFactory}

import scala.beans.BeanProperty

object DataSourceHelper {
  private val LOGGER: Logger = LoggerFactory.getLogger(classOf[DataSourceHelper])
  @BeanProperty
  val instance: DataSourceHelper = new DataSourceHelper
}

/**
 * Creates data-source for jetty development.
 */
class DataSourceHelper {
  try {
    if (System.getProperty("java.naming.factory.initial") == null) {
      System.setProperty("java.naming.factory.initial", classOf[org.eclipse.jetty.jndi.InitialContextFactory].getName)
    }
    new Resource("java:comp/env/jdbc/loans", getDataSource)
  }
  catch {
    case e: NamingException =>
      DataSourceHelper.LOGGER.error("DataSourceHelper", e)
  }

  def getDataSource: DataSource = {
      val basicDataSource: BasicDataSource = new BasicDataSource
      basicDataSource.setDriverClassName(classOf[org.h2.Driver].getName)
      basicDataSource.setUrl("jdbc:h2:~/loans")
      basicDataSource.setUsername("loans")
      basicDataSource.setPassword("loans")
      basicDataSource
  }
}
