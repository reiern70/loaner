package com.antilia.loanserver.init

import java.beans.Transient
import javax.inject.Inject
import javax.persistence.EntityManager

import com.antilia.loan.common.dao.IUserDao

/**
 * Script that know about persistence.
 */
abstract class EntityManagerAwareScript(id: String) extends AbstractSpringInjectedScript(id) {

  @Inject
  var  usersDao: IUserDao = _
  @Transient
  var entityManager: EntityManager = _

  final override def execute() {
    try {
      executeInTransaction(getEntityManager)
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
        throw new RuntimeException(e)
    }
  }

  protected def executeInTransaction(entityManager: EntityManager)

  def getEntityManager: EntityManager = {
    if (entityManager == null) {
      entityManager = usersDao.getEntityManager
    }
    entityManager
  }
}
