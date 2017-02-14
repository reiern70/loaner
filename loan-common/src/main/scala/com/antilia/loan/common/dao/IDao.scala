package com.antilia.loan.common.dao

import java.io.Serializable
import java.util
import javax.persistence.EntityManager

import com.antilia.loan.common.domain.IIdentifiable

/**
 * Trait for hibernate DAOs.
 *
 * @author reiern70
 */
trait IDao[T <: IIdentifiable] extends Serializable {

  def loadById(id: Long): Option[T]

  def loadByUUId(uuid: String): Option[T]

  def loadByIds(ids: util.Collection[Long]): util.List[T]

  def executeQuery(query: String)

  def persist(bean: T)

  /**
   * throws no errors
   */
  def safePersist(bean: T)

  def update(bean: T)

  def delete(bean: T)

  def delete(id: Long)

  def persist(beans: util.Set[T])

  def persistAvoidDuplicates(beans: util.Set[T])

  def findAll: util.List[T]

  def findAllFor(start: Long, size: Long): util.List[T]

  def countAll: Long

  def getEntityManager: EntityManager
}
