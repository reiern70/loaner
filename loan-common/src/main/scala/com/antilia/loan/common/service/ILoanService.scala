package com.antilia.loan.common.service

import com.antilia.loan.common.domain.{LoanAnswer, LoanRequest}

/**
  * Exception signaling that a loan is not computable for reason specified in message,
  *
  * @param message The reason for the failure...
  */
class LoanComputationExcepation(message: String) extends RuntimeException(message)

trait ILoanService {

  def requestLoan(request: LoanRequest): LoanAnswer

}
