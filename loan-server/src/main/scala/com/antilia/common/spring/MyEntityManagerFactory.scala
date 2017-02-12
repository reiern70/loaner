package com.antilia.common.spring

import javax.annotation.{PostConstruct, Resource}
import javax.sql.DataSource

import org.hibernate.ejb.HibernatePersistence
import org.springframework.orm.jpa.vendor.HibernateJpaDialect
import org.springframework.orm.jpa.{JpaVendorAdapter, LocalContainerEntityManagerFactoryBean}

/**
 * @author reiern70
 */
class MyEntityManagerFactory extends  LocalContainerEntityManagerFactoryBean {
override def getDataSource: DataSource = {
    dataSource
  }

  def getJpaProperties: java.util.Map[String, _] = {
    jpaProperties
  }

  override def getJpaVendorAdapter: JpaVendorAdapter = {
    jpaVendorAdapter
  }

  @PostConstruct
  def init() {
    setPersistenceUnitName("loans")
    setJpaVendorAdapter(jpaVendorAdapter)
    setJpaPropertyMap(jpaProperties)
    setJpaDialect(new HibernateJpaDialect)
    setPersistenceProviderClass(classOf[HibernatePersistence])
    setDataSource(dataSource)
  }

  override def setDataSource(dataSource: DataSource) {
    this.dataSource = dataSource
  }

  def setJpaProperties(jpaProperties: java.util.Map[String, _<: Any]) {
    this.jpaProperties = jpaProperties
  }

  override def setJpaVendorAdapter(jpaVendorAdapter: JpaVendorAdapter) {
    this.jpaVendorAdapter = jpaVendorAdapter
  }

  @Resource
  private var jpaVendorAdapter: JpaVendorAdapter = null
  private var jpaProperties: java.util.Map[String, _<: Any] = null
  @Resource
  private var dataSource: DataSource = null
}
