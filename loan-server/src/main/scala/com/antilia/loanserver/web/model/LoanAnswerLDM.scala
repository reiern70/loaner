package com.antilia.loanserver.web.model

import javax.inject.Inject

import com.antilia.loan.common.dao.{IDao, ILoanAnswerDao}
import com.antilia.loan.common.domain.LoanAnswer


class LoanAnswerLDM(answer: LoanAnswer) extends IDentifiableLDM[LoanAnswer](answer){

  @Inject
  var dao: ILoanAnswerDao = _

  override protected def getDao: IDao[LoanAnswer] = dao
}
