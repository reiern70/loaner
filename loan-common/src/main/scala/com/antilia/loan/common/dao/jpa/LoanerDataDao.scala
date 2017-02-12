package com.antilia.loan.common.dao.jpa

import java.util
import javax.persistence.TypedQuery

import com.antilia.loan.common.dao.ILoanerDataDao
import com.antilia.loan.common.domain.{Currency, LoanerData}


class LoanerDataDao extends AbstractDao[LoanerData](classOf[LoanerData]) with ILoanerDataDao {

  override def getAllFor(currency: Currency): util.List[LoanerData] = {
    val query: TypedQuery[LoanerData] = getEntityManager.createQuery("select p from LoanerData p where p.currency=:currency sorted by p.lastLoanDate asc", classOf[LoanerData])
    query.setParameter("currency", currency)
    query.getResultList
  }
}
