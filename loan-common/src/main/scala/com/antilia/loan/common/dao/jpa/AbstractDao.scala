package com.antilia.loan.common.dao.jpa

import java.util
import java.util.UUID
import javax.persistence._

import com.antilia.loan.common.dao.IDao
import com.antilia.loan.common.domain.IIdentifiable
import org.springframework.transaction.annotation.{Propagation, Transactional}

import scala.collection.JavaConversions._

/**
 * Base class for JPA Daos.
 */
class AbstractDao[T <: IIdentifiable](val entityClass: Class[T]) extends IDao[T] {

  protected def setPagination(query: Query, start: Long, size: Long) {
    query.setFirstResult(start.asInstanceOf[Int])
    query.setMaxResults(size.asInstanceOf[Int])
  }

  @Transactional(propagation = Propagation.REQUIRED)
  def loadById(id: Long): Option[T] = {
    val e = entityManager.find(entityClass, id)
    Option(e)
  }

  @Transactional(propagation = Propagation.REQUIRED)
  def loadByUUId(uuid: String): Option[T] = {
    val query: TypedQuery[T] = entityManager.createQuery("select p from " + entityClass.getSimpleName + " p where p.uuid in :IDS", entityClass)
    query.setParameter("IDS", uuid)
    findOne(query)
  }

  def loadByIds(ids: util.Collection[Long]): util.List[T] = {
    val query: TypedQuery[T] = entityManager.createQuery("select p from " + entityClass.getSimpleName + " p where p.id in :IDS", entityClass)
    query.setParameter("IDS", ids)
    query.getResultList
  }

  @Transactional(propagation = Propagation.REQUIRED)
  def executeQuery(query: String) {
    val q: Query = entityManager.createNativeQuery(query)
    q.executeUpdate
  }

  @Transactional(propagation = Propagation.REQUIRED)
  def persist(bean: T) {
    if (bean.getUuid == null) {
      bean.setUuid(UUID.randomUUID.toString + System.currentTimeMillis)
    }
    entityManager.persist(bean)
  }

  def safePersist(bean: T) {
    try {
      persist(bean)
    }
    catch {
      case _: Exception =>
    }
  }

  @Transactional(propagation = Propagation.REQUIRED) 
  def delete(bean: T) {
    entityManager.remove(bean)
  }

  @Transactional(propagation = Propagation.REQUIRED) 
  def delete(id: Long) {
    loadById(id) match {
      case Some(e) => entityManager.remove(e)
      case None => // do nothing
    }
  }

  @Transactional(propagation = Propagation.REQUIRED) 
  def update(bean: T) {
    entityManager.merge(bean)
  }

  protected def findOne(typedQuery: TypedQuery[T]): Option[T] = {
    val list: util.List[T] = typedQuery.getResultList
    if (list == null || list.size == 0) {
      return None
    }
    Some(list.get(0))
  }


  override def findAllFor(start: Long, size: Long): util.List[T] = {
    val queryStr = "select p from " + entityClass.getSimpleName + " p"
    val query: TypedQuery[T]  = entityManager.createQuery(queryStr, entityClass)
    setPagination(query, start, size)
    query.getResultList
  }

  def findAll: util.List[T] = {
    val query = "select p from " + entityClass.getSimpleName + " p"
    entityManager.createQuery(query, entityClass).getResultList
  }

  def countAll: Long = {
    entityManager.createQuery("select COUNT(p) from " + entityClass.getSimpleName + " p").getSingleResult.asInstanceOf[Long]
  }

  def getEntityClass: Class[T] = {
    entityClass
  }

  def getEntityManager: EntityManager = {
    entityManager
  }

  def setEntityManager(entityManager: EntityManager) {
    this.entityManager = entityManager
  }

  @Transactional(propagation = Propagation.REQUIRED)
  def persist(beans: util.Set[T]) {
    if (beans != null && beans.size > 0) {
      for (bean <- beans) {
        persist(bean)
      }
    }
  }

  def persistAvoidDuplicates(beans: util.Set[T]) {
    if (beans != null && beans.size > 0) {

      for (bean <- beans) {
        try {
          persist(bean)
        }
        catch {
          case _: PersistenceException =>
        }
      }
    }
  }

  @PersistenceContext(unitName = "loans", `type` = PersistenceContextType.TRANSACTION)
  private var entityManager: EntityManager = _
}