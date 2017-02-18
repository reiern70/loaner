package com.antilia.loan.common.dao.jpa

import java.util
import java.util.Date
import javax.persistence.TypedQuery

import com.antilia.loan.common.dao.ILoanerDataDao
import com.antilia.loan.common.domain.{Currency, LoanerData}
import org.springframework.transaction.annotation.{Propagation, Transactional}

import scala.collection.JavaConversions._


class LoanerDataDao extends AbstractDao[LoanerData](classOf[LoanerData]) with ILoanerDataDao {

  override def getAllFor(currency: Currency): util.List[LoanerData] = {
    val query: TypedQuery[LoanerData] = getEntityManager.createQuery("select p from LoanerData p where p.currency=:currency sorted by p.rate asc", classOf[LoanerData])
    query.setParameter("currency", currency)
    query.getResultList
  }

  /**
    * Returns the next available loaner i.e. the loaner with smallest lastLoanDate and whose rate is
    * >= previous.getRate
    *
    * @param currency
    * @return
    */
  @Transactional(propagation = Propagation.REQUIRED)
  override def getNextAvailableLoaner(currency: Currency, previous: LoanerData): Option[LoanerData] = {
    val query: TypedQuery[LoanerData] = getEntityManager.createQuery("select p from LoanerData p where p.currency=:currency and p.rate >=:rate sorted by p.lastLoanDate, p.rate asc", classOf[LoanerData])
    query.setParameter("currency", currency)
    query.setParameter("rate", previous.getRate)
    query.setMaxResults(40)
    val reults = query.getResultList
    if(reults.size() == 0) {
      return None
    }
    for(r <- reults) {
      if(r != previous && !r.isBeingUsedForLoan) {
        r.setBeingUsedForLoan(true)
        update(r)
        return Some(r)
      }
    }
    None
  }

  /**
    * Update system/data after a loand has been granted.
    *
    * @param data
    * @return
    */
  @Transactional
  override def storeLoanDataAfterLoanGrated(data: util.List[LoanerData]): Unit = {
    for(e <- data) {
      e.setBeingUsedForLoan(false)
      e.setLastLoanDate(new Date())
      update(e)
    }
  }

  /**
    * Revert changes.
    *
    * @param data
    * @return
    */
  @Transactional
  override def revertLoanDataAfterLoanGrated(data: util.List[LoanerData]): Unit = {
    for(e <- data) {
      loadById(e.getId) match {
        case Some(a) =>
          a.setBeingUsedForLoan(false)
          update(a)
        case None => // do nothing
      }
      update(e)
    }
  }
}
