package com.antilia.loanserver.init

import java.util.{Calendar, Collections, Date}
import javax.inject.Inject
import javax.persistence.EntityManager

import com.antilia.loan.common.dao.ILoanerDataDao
import com.antilia.loan.common.domain.{LoanerData, User, UserRole}

import scala.beans.BeanProperty
import scala.util.Random
import scala.collection.JavaConversions._

object InsertInitialData {

  @BeanProperty
  val random = new Random()

  def getRandomDateBetween(fromYear: Int, toYear: Int): Date = {
    val gc: Calendar = Calendar.getInstance
    val year: Int = randBetween(fromYear, toYear)
    gc.set(Calendar.YEAR, year)
    val dayOfYear: Int = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR))
    gc.set(Calendar.DAY_OF_YEAR, dayOfYear)
    gc.getTime
  }

  def randBetween(start: Int, end: Int): Int = {
    start + Math.round(Math.random * (end - start)).asInstanceOf[Int]
  }

  def getRandom[T](list: java.util.List[T], max: Int): java.util.Set[T] = {
    if (list == null || list.size == 0) {
      return Collections.emptySet[T]
    }
    val result: java.util.Set[T] = new java.util.HashSet[T]
    val times: Int = random.nextInt(max) + 1
    for (i <- 1 to times) {
      result.add(list.get(random.nextInt(list.size)))
    }
    result
  }

  def getRandom[T](list: java.util.List[T]): T = {
    list.get(random.nextInt(list.size))
  }
}

/**
 * Script that inserts initial data.
 */
class InsertInitialData extends EntityManagerAwareScript("InsertInitialData11") with IRunAlwaysScript {

  @Inject
  var iLoanerDataDao: ILoanerDataDao = _

  override protected def executeInTransaction(entityManager: EntityManager): Unit = {
    if(usersDao.countAll == 0) {
      val anonymous = new User
      anonymous.setEmail(User.ANONYMOUS_USER)
      anonymous.setName("anonymous")
      anonymous.setLastName("anonymous")
      anonymous.setRole(UserRole.anonymous_requester)
      usersDao.persist(anonymous)
    }
    if(iLoanerDataDao.countAll == 0) {
      for(a: LoanerData <- LoanerData.fromResources()) {
        val user = a.getUser
        usersDao.persist(user)
        usersDao.loadBy(user.getEmail, user.getRole) match {
          case Some(u) =>
            a.setUser(u)
            iLoanerDataDao.persist(a)
          case None => // do nothing
        }

      }
    }
  }

  override def shouldContinueOnError: Boolean = false
}
