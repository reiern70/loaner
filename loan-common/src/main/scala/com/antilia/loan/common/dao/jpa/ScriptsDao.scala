package com.antilia.loan.common.dao.jpa

import java.util
import javax.persistence.TypedQuery

import com.antilia.loan.common.dao.IScriptsDao
import com.antilia.loan.common.domain.Script

/**
 * JPA DAO for scripts
 */
class ScriptsDao extends AbstractDao[Script](classOf[Script]) with IScriptsDao {
  override def findById(scriptId: String): Option[Script] = {
    val query: TypedQuery[Script] = getEntityManager.createQuery("select p from Script p where p.scriptId=:scriptId", classOf[Script])
    query.setParameter("scriptId", scriptId)
    findOne(query)
  }

  override def exists(scriptId: String): Boolean = {
    val query: TypedQuery[Script] = getEntityManager.createQuery("select p from Script p where p.scriptId=:scriptId", classOf[Script])
    query.setParameter("scriptId", scriptId)
    val list: util.List[Script] = query.getResultList
    if (list == null || list.size == 0) {
      return false
    }
    true
  }
}
