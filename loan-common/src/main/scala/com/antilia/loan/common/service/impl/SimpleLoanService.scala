package com.antilia.loan.common.service.impl

import java.util
import java.util.Date

import com.antilia.loan.common.dao.ILoanerDataDao
import com.antilia.loan.common.domain.{LoanAnswer, LoanRequest, LoanerData}
import com.antilia.loan.common.service.{ILoanService, LoanComputationException}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConversions._

/**
  * To go from annual rate to monthly rate you can do the following
*monthlyRate = (1 + annualRate)^(1/12) -1
*monthlyPayment = (loanAmount*monthlyRate)/(1 - (1+monthlyRate)^-period)
*
 **
 *Where period = 36 for instance for a 36 month loan, loanAmount = 1000, annualRate = 7%
  */
@Service
class SimpleLoanService extends ILoanService {

  @Autowired
  var loanerDataDao: ILoanerDataDao = _


  override def requestLoan(request: LoanRequest): LoanAnswer = {
    val answer = new LoanAnswer
    val usedLoaners = new util.HashSet[LoanerData]()
    var amount =  0L
    var previousLoaner: LoanerData = null
    while (amount < request.getAmount) {
      loanerDataDao.getNextAvailableLoaner(request.getCurrency, previousLoaner) match {
        case Some(loaner) =>
          usedLoaners.add(loaner)
          previousLoaner = loaner
          //TODO: this logic is good for people that ask for money not good for loaners...
          // introduce something limiting ammout of loaned money,
          val toDeduce = request.getAmount - amount
          if(loaner.deduceAmount(toDeduce)) {
            amount += toDeduce
          } else {
            amount += loaner.deduceAllAmount()
          }
        case None =>
          loanerDataDao.revertLoanDataAfterLoanGrated(usedLoaners)
          throw new LoanComputationException("Unable to full-fill loan: not enough loaners")
      }
    }
    var totalRate = 0.0
    for(l <- usedLoaners) {
      totalRate += l.getRate
    }
    val annualRate = totalRate / usedLoaners.size()
    answer.setRate(annualRate)
    val monthlyRate = Math.pow(1 + annualRate,1.0/12) - 1
    val monthlyPayment = (amount*monthlyRate)/(1 - Math.pow(1+monthlyRate,-request.getPeriod))
    val totalRepayment = monthlyPayment*request.getPeriod
    answer.setAnswerDate(new Date())
    answer.setTotalPayment(totalRepayment)
    answer.setCurrency(request.getCurrency)
    answer.setMonthlyPayment(monthlyPayment)
    // update data on database
    loanerDataDao.storeLoanDataAfterLoanGrated(usedLoaners)
    answer
  }
}
