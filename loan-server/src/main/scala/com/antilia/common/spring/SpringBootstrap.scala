package com.antilia.common.spring

import java.util
import javax.persistence.EntityManagerFactory

import com.antilia.loan.common.dao.{ILoanAnswerDao, ILoanerDataDao, IScriptsDao, IUserDao}
import com.antilia.loan.common.dao.jpa.{LoanAnswerDao, LoanerDataDao, ScriptsDao, UserDao}
import com.antilia.loan.common.service.ILoanService
import com.antilia.loan.common.service.impl.SimpleLoanService
import com.antilia.loanserver.Jetty
import org.hibernate.cfg.AvailableSettings
import org.hibernate.service.jdbc.connections.internal.DatasourceConnectionProviderImpl
import org.springframework.beans.factory.FactoryBean
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.jndi.JndiObjectFactoryBean
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager
import org.springframework.orm.jpa.vendor.{Database, HibernateJpaDialect, HibernateJpaVendorAdapter}
import org.springframework.orm.jpa.{JpaTransactionManager, JpaVendorAdapter}
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * Bootstrap spring.
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(Array("com.antilia.common"))
class SpringBootstrap {

  if (Jetty.isJetty) {
    DataSourceHelper.getInstance
  }
  
  @Bean
  def getJpaVendorAdapter: JpaVendorAdapter = {
    val adapter: HibernateJpaVendorAdapter = new HibernateJpaVendorAdapter
    adapter.setDatabase(Database.H2)
    adapter
  }

  @Bean
  def getDataSource: JndiObjectFactoryBean = {
    val bean: JndiObjectFactoryBean = new JndiObjectFactoryBean
    bean.setJndiName("java:comp/env/jdbc/loans")
    bean
  }

  @Bean def getJpaProperties: util.Map[String, _] = {
    val map: util.Map[String, AnyRef] = new util.HashMap[String, AnyRef]
    map.put(AvailableSettings.DATASOURCE, getDataSource.getObject)

    val mode = System.getProperty("database-mode")
    map.put(AvailableSettings.HBM2DDL_AUTO, if(mode != null) mode else "update")
    map.put(AvailableSettings.CONNECTION_PROVIDER, classOf[DatasourceConnectionProviderImpl].getName)
    map
  }

  @Bean
  def getEntityManagerFactory: FactoryBean[EntityManagerFactory] = {
    val mFBean: MyEntityManagerFactory = new MyEntityManagerFactory
    mFBean.setJpaProperties(getJpaProperties)
    mFBean
  }

  @Bean def getJpaTransactionManager: JpaTransactionManager = {
    val jpaTransactionManager: JpaTransactionManager = new JpaTransactionManager
    jpaTransactionManager.setEntityManagerFactory(getEntityManagerFactory.getObject)
    jpaTransactionManager.setJpaDialect(new HibernateJpaDialect)
    jpaTransactionManager
  }

  @Bean def getDefaultPersistenceUnitManager: DefaultPersistenceUnitManager = {
    new DefaultPersistenceUnitManager
  }
  
  // DAOs
  @Bean def getCompaniesDao: IUserDao = new UserDao()

  @Bean def getIScriptsDao: IScriptsDao = new ScriptsDao()

  @Bean def getLoanerDataDao: ILoanerDataDao = new LoanerDataDao()

  @Bean def getILoanAnswerDao: ILoanAnswerDao = new LoanAnswerDao()


  @Bean def getILoanService: ILoanService = new SimpleLoanService()

}
