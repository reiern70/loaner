package com.antilia.loan.common.service

import com.antilia.loan.common.domain.{LoanAnswer, LoanRequest}

/**
  * Exception signaling that a loan is not computable for reason specified in message,
  *
  * @param message The reason for the failure...
  */
class LoanComputationException(message: String) extends RuntimeException(message)

trait ILoanService {

  def requestAnonymousLoan(request: LoanRequest): Option[LoanAnswer]

  def requestLoan(request: LoanRequest): Option[LoanAnswer]

}
